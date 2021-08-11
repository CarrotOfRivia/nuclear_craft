package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.events.ClientEventForgeSubscriber;
import com.song.nuclear_craft.network.GunLoadingPacket;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import com.song.nuclear_craft.network.SoundPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class RocketLauncherWithAmmo extends Item {
    private static final int MAX_AMMO = 10;
    protected int coolDown = 0;
    public RocketLauncherWithAmmo(Properties properties) {
        super(properties);
    }

    protected int getMAX_AMMO(){
        return MAX_AMMO;
    }

    public abstract Item getBoundedAmmo();

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, @Nonnull Player playerIn, @Nonnull InteractionHand handIn) {
        enterCD(playerIn);
        ItemStack toBeFired = new ItemStack(getBoundedAmmo());
        toBeFired.getOrCreateTagElement("Fireworks").putByte("Flight", (byte) 127);

        ItemStack thisItemStack = playerIn.getItemInHand(handIn);

        if (!worldIn.isClientSide){
            FireworkRocketEntity entity = getEntity(worldIn, toBeFired, playerIn, playerIn.getX(), playerIn.getEyeY() - (double)0.15F, playerIn.getZ(), true);
            Vec3 vec3d = playerIn.getLookAngle();
            entity.shoot(vec3d.x, vec3d.y, vec3d.z, 5f, 0);
            worldIn.addFreshEntity(entity);
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.f);
        }
        if (playerIn.isCreative()){
            return InteractionResultHolder.sidedSuccess(thisItemStack, worldIn.isClientSide());
        }
        else {
            return afterFire(worldIn, thisItemStack);
        }
    }

    protected abstract FireworkRocketEntity getEntity(Level worldIn, ItemStack toBeFired, Entity playerIn, double x, double y, double z, boolean p_i231582_10_);

    protected int getAmmoCount(ItemStack itemStack){
        CompoundTag compoundnbt = itemStack.getOrCreateTag();
        if(! compoundnbt.contains("ammo")){
            compoundnbt.putInt("ammo", this.getMAX_AMMO());
        }
        return compoundnbt.getInt("ammo");
    }

    protected void addAmmoCount(ItemStack itemStack, int n){
        CompoundTag compoundnbt = itemStack.getOrCreateTag();
        assert compoundnbt.contains("ammo");
        compoundnbt.putInt("ammo", n+compoundnbt.getInt("ammo"));
    }

    public void clearAmmo(ItemStack itemStack){
        addAmmoCount(itemStack, -getAmmoCount(itemStack));
    }

    protected InteractionResultHolder<ItemStack>  afterFire(Level worldIn, ItemStack thisItemStack){
        CompoundTag compoundnbt = thisItemStack.getOrCreateTag();
        if(! compoundnbt.contains("ammo")){
            compoundnbt.putInt("ammo", this.getMAX_AMMO());
        }
        if(!worldIn.isClientSide){
            int n_ammo = compoundnbt.getInt("ammo");
            n_ammo --;
            compoundnbt.putInt("ammo", n_ammo);
        }
        if (compoundnbt.getInt("ammo") <= 0){
            return InteractionResultHolder.sidedSuccess(new ItemStack(ItemList.ROCKET_LAUNCHER.get()), worldIn.isClientSide());
        }
        else {
            return InteractionResultHolder.sidedSuccess(thisItemStack, worldIn.isClientSide());
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        int n_ammo = getAmmoCount(stack);
        tooltip.add(new TranslatableComponent(String.format("item.%s.rocket_launcher.ammo_left", NuclearCraft.MODID)).withStyle(ChatFormatting.GRAY));
        tooltip.add(new TextComponent(""+n_ammo).withStyle(ChatFormatting.GOLD));
    }

    public void addAmmo(ItemStack ammo, ItemStack rocket, int itemSlot, Entity entityIn){
        if (getBoundedAmmo() != null){
            if(ammo.getItem().getRegistryName().equals(getBoundedAmmo().getRegistryName())){
                int n_ammo = getAmmoCount(rocket);
                if (n_ammo<getMAX_AMMO()){
                    int n_loaded = Math.min(getMAX_AMMO()-n_ammo, ammo.getCount());
                    ammo.shrink(n_loaded);
                    addAmmoCount(rocket, n_loaded);
                    BlockPos pos = entityIn.blockPosition();
                    NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), 20, entityIn.level.dimension())),
                            new SoundPacket(pos, "rocket_load"));
                    ((Player) entityIn).getCooldowns().addCooldown(rocket.getItem(), 20);
                }
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        // loading ammo
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if(isSelected && worldIn.isClientSide){
            if(ClientEventForgeSubscriber.gunReload.consumeClick() && (entityIn instanceof Player)){
                NuclearCraftPacketHandler.KEY_BIND.sendToServer(new GunLoadingPacket(itemSlot));
            }
        }
//        if(isSelected && !worldIn.isRemote && ClientEventForgeSubscriber.gunReload.isPressed() && (entityIn instanceof PlayerEntity)){
//            ItemStack itemStackMain = ((PlayerEntity) entityIn).getHeldItemMainhand();
//            if (itemStackMain.getItem() instanceof RocketLauncherWithAmmo){
//                ItemStack itemStackOff = ((PlayerEntity) entityIn).getHeldItemOffhand();
//                addAmmo(itemStackOff, itemStackMain, itemSlot, entityIn);
//            }
//        }
    }

    protected void enterCD(Player playerEntity){
        playerEntity.getCooldowns().addCooldown(this, coolDown);
    }
}
