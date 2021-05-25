package com.song.nuclear_craft.blocks.tileentity;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.C4Bomb;
import com.song.nuclear_craft.blocks.container.C4BombContainer;
import com.song.nuclear_craft.entities.NukeExplosionHandler;
import com.song.nuclear_craft.items.defuse_kit.DefuseKit;
import com.song.nuclear_craft.network.BombDefuseProgressPacket;
import com.song.nuclear_craft.network.C4BombSynPacket;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import com.song.nuclear_craft.network.SoundPacket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Objects;

public class C4BombTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    private static final int DEFAULT_FUSE_TIME = 800;
    protected boolean is_active;
    protected int fuse_age;
    protected int explode_time;

    protected int threshold1;
    protected int threshold2;
    public String inputPanel="";

    // defuse properties
    public int defuseTime = 100;
    // -1 means not been defused, otherwise means it is being defused
    public int currentDefuseStatus = -1;
    public int defusingEntityID = 0;
    public String defusingTool = "";

    public C4BombTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.is_active = false;
        this.fuse_age = 0;
        this.explode_time = DEFAULT_FUSE_TIME;
        this.threshold1 = (int) (this.explode_time * 0.33);
        this.threshold2 = (int) (this.explode_time * 0.66);
    }

    public C4BombTileEntity(TileEntityType<?> tileEntityTypeIn, int explode_time){
        this(tileEntityTypeIn);
        this.explode_time = explode_time;
    }

    private int getBeepInterval(){
        if (fuse_age > explode_time-40){
            return 2;
        }
        else if(fuse_age > threshold2){
            return 8;
        }
        else if(fuse_age > threshold1){
            return 15;
        }
        else {
            return 20;
        }
    }

    @Override
    public void tick() {
        assert world != null;
        if (is_active){
            if (!world.isRemote) {
                fuse_age++;
                this.markDirty();

                if (fuse_age % getBeepInterval() == 0) {
                    NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(this.pos.getX(), this.pos.getY(), this.pos.getZ(), 20, this.world.getDimensionKey())),
                            new SoundPacket(this.pos, "c4_beep"));
                    synToClient();
                }

                if (fuse_age >= explode_time) {
                    C4Bomb block = (C4Bomb) this.getBlockState().getBlock();
                    World world = this.world;
                    BlockPos pos = this.getPos();
                    this.remove();
                    world.removeBlock(pos, false);
                    block.explode(world, pos.getX(), pos.getY(), pos.getZ());
                }
            }
        }
        if(! world.isRemote() && currentDefuseStatus>=0){
            this.markDirty();
            Entity entity = world.getEntityByID(defusingEntityID);
            if(entity!=null){
                if(entityStillDefusing(entity)){
                    currentDefuseStatus ++;
                    if (currentDefuseStatus%4==0 && entity instanceof ServerPlayerEntity){
                        NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.PLAYER.with(()-> (ServerPlayerEntity) entity), new BombDefuseProgressPacket(getPos(), currentDefuseStatus, defuseTime));
                    }
                    if(currentDefuseStatus >= defuseTime){
                        beDefused();
                    }
                }
                else {
                    currentDefuseStatus = -1;
                    if (entity instanceof ServerPlayerEntity) {
                        // Render cancel packet
                        NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) entity), new BombDefuseProgressPacket(getPos(), -1, defuseTime));
                    }
                }
            }
        }
    }

    protected boolean entityStillDefusing(Entity entity){
        if(entity instanceof LivingEntity){
            return entity.getPosition().withinDistance(getPos(), 5) && Objects.requireNonNull(((LivingEntity) entity).getHeldItem(Hand.MAIN_HAND).getItem().getRegistryName()).toString().equals(defusingTool);
        }
        else {
            return false;
        }
    }

    protected void beDefused(){
        assert world != null;
        if(! world.isRemote()){
            Block block = world.getBlockState(getPos()).getBlock();
            ItemStack itemStack = new ItemStack(block.asItem());
            world.addEntity(new ItemEntity(world, getPos().getX(), getPos().getY(), getPos().getZ(), itemStack));
            world.destroyBlock(getPos(), false);
            NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), 14, world.getDimensionKey())),
                    new SoundPacket(pos, "defused"));
            Entity entity = world.getEntityByID(defusingEntityID);
            if(entity instanceof LivingEntity){
                ItemStack itemStack1 = ((LivingEntity) entity).getHeldItemMainhand();
                if(itemStack1.getItem() instanceof DefuseKit){
                    itemStack1.damageItem(1, (LivingEntity)entity, (p)->p.sendBreakAnimation(Hand.MAIN_HAND));
                }
            }
        }
    }

    private void setActive(){
        this.is_active = true;
        assert world != null;
        if (! world.isRemote){
            NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(this.pos.getX(), this.pos.getY(), this.pos.getZ(), 7, this.world.getDimensionKey())),
                    new SoundPacket(this.pos, "c4_activate"));
        }
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory inventory, PlayerEntity entity) {
        return new C4BombContainer(id, inventory, this);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(String.format("menu.%s.c4_bomb.display_name", NuclearCraft.MODID));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT nbt = super.write(compound);
        nbt.putInt("fuse_age", fuse_age);
        nbt.putInt("explode_time", explode_time);
        nbt.putBoolean("is_active", is_active);
        nbt.putInt("threshold1", threshold1);
        nbt.putInt("threshold2", threshold2);
        nbt.putString("inputPanel", inputPanel);
        nbt.putInt("defuseTime", defuseTime);
        nbt.putInt("currentDefuseStatus", currentDefuseStatus);
        return nbt;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        fuse_age = nbt.getInt("fuse_age");
        explode_time = nbt.getInt("explode_time");
        is_active = nbt.getBoolean("is_active");
        threshold1 = nbt.getInt("threshold1");
        threshold2 = nbt.getInt("threshold2");
        inputPanel = nbt.getString("inputPanel");
        defuseTime = nbt.getInt("defuseTime");
        currentDefuseStatus = nbt.getInt("currentDefuseStatus");
        synToClient();
    }

    public void delete(){
        if(! is_active){
            this.inputPanel="";
            synToClient();
            this.markDirty();
        }
    }
    public void addNum(int num){
        if (!is_active){
            this.inputPanel+=num;
            synToClient();
            this.markDirty();
        }
    }
    public void activate(){
        if (!is_active){
            // sec to tick
            if (!inputPanel.isEmpty()){
                this.explode_time=Integer.parseInt(this.inputPanel)*20;
            }
            else{
                this.explode_time=DEFAULT_FUSE_TIME;
            }
            setActive();
            synToClient();
        }
    }

    public void synToClient(){
        if (world != null&&!world.isRemote){
            NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(()-> {
                assert this.world != null;
                return this.world.getChunkAt(this.pos);
            }), new C4BombSynPacket(this.pos, this.inputPanel, fuse_age, explode_time, is_active));
        }
    }

    public int getCounter(){
        return (explode_time-fuse_age)/20;
    }

    public boolean isActive(){
        return is_active;
    }

    public void setAttr(String inputPanel, int fuse_age, int explode_time, boolean is_active){

        this.inputPanel = inputPanel;
        this.fuse_age = fuse_age;
        this.explode_time = explode_time;
        this.is_active = is_active;
    }
}
