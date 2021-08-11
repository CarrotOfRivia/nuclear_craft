package com.song.nuclear_craft.blocks.tileentity;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.C4Bomb;
import com.song.nuclear_craft.blocks.container.C4BombContainer;
import com.song.nuclear_craft.items.defuse_kit.DefuseKit;
import com.song.nuclear_craft.network.BombDefuseProgressPacket;
import com.song.nuclear_craft.network.C4BombSynPacket;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import com.song.nuclear_craft.network.SoundPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Objects;

public class C4BombTileEntity extends BlockEntity implements MenuProvider {
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

    public C4BombTileEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos blockPos, BlockState blockState) {
        super(tileEntityTypeIn, blockPos, blockState);
        this.is_active = false;
        this.fuse_age = 0;
        this.explode_time = DEFAULT_FUSE_TIME;
        this.threshold1 = (int) (this.explode_time * 0.33);
        this.threshold2 = (int) (this.explode_time * 0.66);
    }

    public C4BombTileEntity(BlockEntityType<?> tileEntityTypeIn, int explode_time, BlockPos blockPos, BlockState blockState){
        super(tileEntityTypeIn, blockPos, blockState);
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

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, C4BombTileEntity c4) {
        assert level != null;
        if (c4.is_active){
            if (!level.isClientSide) {
                c4.fuse_age++;
                c4.setChanged();

                if (c4.fuse_age % c4.getBeepInterval() == 0) {
                    NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(c4.worldPosition.getX(), c4.worldPosition.getY(), c4.worldPosition.getZ(), 20, c4.level.dimension())),
                            new SoundPacket(c4.worldPosition, "c4_beep"));
                    c4.synToClient();
                }

                if (c4.fuse_age >= c4.explode_time) {
                    C4Bomb block = (C4Bomb) c4.getBlockState().getBlock();
                    BlockPos pos = c4.getBlockPos();
                    c4.setRemoved();
                    level.removeBlock(pos, false);
                    block.explode(level, pos.getX(), pos.getY(), pos.getZ());
                }
            }
        }
        if(! level.isClientSide() && c4.currentDefuseStatus>=0){
            c4.setChanged();
            Entity entity = level.getEntity(c4.defusingEntityID);
            if(entity!=null){
                if(c4.entityStillDefusing(entity)){
                    c4.currentDefuseStatus ++;
                    if (c4.currentDefuseStatus%4==0 && entity instanceof ServerPlayer){
                        NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.PLAYER.with(()-> (ServerPlayer) entity), new BombDefuseProgressPacket(c4.getBlockPos(), c4.currentDefuseStatus, c4.defuseTime));
                    }
                    if(c4.currentDefuseStatus >= c4.defuseTime){
                        c4.beDefused();
                    }
                }
                else {
                    c4.currentDefuseStatus = -1;
                    if (entity instanceof ServerPlayer) {
                        // Render cancel packet
                        NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) entity), new BombDefuseProgressPacket(c4.getBlockPos(), -1, c4.defuseTime));
                    }
                }
            }
        }
    }

    protected boolean entityStillDefusing(Entity entity){
        if(entity instanceof LivingEntity){
            return entity.blockPosition().closerThan(getBlockPos(), 5) && Objects.requireNonNull(((LivingEntity) entity).getItemInHand(InteractionHand.MAIN_HAND).getItem().getRegistryName()).toString().equals(defusingTool);
        }
        else {
            return false;
        }
    }

    protected void beDefused(){
        assert level != null;
        if(! level.isClientSide()){
            Block block = level.getBlockState(getBlockPos()).getBlock();
            ItemStack itemStack = new ItemStack(block.asItem());
            level.addFreshEntity(new ItemEntity(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), itemStack));
            level.destroyBlock(getBlockPos(), false);
            NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 14, level.dimension())),
                    new SoundPacket(worldPosition, "defused"));
            Entity entity = level.getEntity(defusingEntityID);
            if(entity instanceof LivingEntity){
                ItemStack itemStack1 = ((LivingEntity) entity).getMainHandItem();
                if(itemStack1.getItem() instanceof DefuseKit){
                    itemStack1.hurtAndBreak(1, (LivingEntity)entity, (p)->p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                }
            }
        }
    }

    private void setActive(){
        this.is_active = true;
        assert level != null;
        if (! level.isClientSide){
            NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), 7, this.level.dimension())),
                    new SoundPacket(this.worldPosition, "c4_activate"));
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player entity) {
        return new C4BombContainer(id, inventory, this);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent(String.format("menu.%s.c4_bomb.display_name", NuclearCraft.MODID));
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        CompoundTag nbt = super.save(compound);
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
    public void load(CompoundTag nbt) {
        super.load(nbt);
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
            this.setChanged();
        }
    }
    public void addNum(int num){
        if (!is_active){
            this.inputPanel+=num;
            synToClient();
            this.setChanged();
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
        if (level != null&&!level.isClientSide){
            NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(()-> {
                assert this.level != null;
                return this.level.getChunkAt(this.worldPosition);
            }), new C4BombSynPacket(this.worldPosition, this.inputPanel, fuse_age, explode_time, is_active));
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
