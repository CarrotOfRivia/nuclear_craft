package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.entities.Ammo9MMEntity;
import com.song.nuclear_craft.misc.SoundEventList;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractGunItem extends Item {
    protected int coolDown;
    public AbstractGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack heldItemStack = playerIn.getHeldItem(handIn);
        if (heldItemStack.getItem() instanceof AbstractGunItem){
            AbstractGunItem gunItem = (AbstractGunItem) heldItemStack.getItem();
            if((!worldIn.isRemote)&&(hasAmmo(heldItemStack))){
                if(gunItem.getCoolDown()>0){
                    playerIn.getCooldownTracker().setCooldown(heldItemStack.getItem(), gunItem.getCoolDown());
                }
                ItemStack toBeFired = new ItemStack(gunItem.getAmmo());
                toBeFired.getOrCreateChildTag("Fireworks").putByte("Flight", (byte) 127);
                AbstractAmmoEntity entity = gunItem.getAmmoEntity(playerIn.getPosX(), playerIn.getPosYEye() - (double)0.15F, playerIn.getPosZ(), worldIn, toBeFired, playerIn);
                worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), getSoundEvent(), SoundCategory.PLAYERS, 0.1f, 1f);
                entity.setSilent(true);
                Vector3d vec3d = playerIn.getLookVec();
                entity.shoot(vec3d.x, vec3d.y, vec3d.z, gunItem.getVelocity(), 0);
                worldIn.addEntity(entity);
                shrinkAmmoNBT(heldItemStack);
                return ActionResult.func_233538_a_(heldItemStack, worldIn.isRemote());
            }
            else{
                return super.onItemRightClick(worldIn, playerIn, handIn);
            }
        }
        else {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }
    }

    public static boolean hasAmmo(ItemStack itemStack){
        CompoundNBT nbt = itemStack.getChildTag("ammo");
        if (nbt == null){
            return false;
        }
        else {
            int nAmmo = nbt.getInt("count");
            if (nAmmo > 0){
                return true;
            }
            else {
                clearAmmoNBT(itemStack);
                return false;
            }
        }
    }

    public static void clearAmmoNBT(ItemStack itemStack){
        itemStack.removeChildTag("ammo");
    }

    public static int getAmmoCount(ItemStack itemStack){
        CompoundNBT nbt = itemStack.getChildTag("ammo");
        if (nbt == null){
            return 0;
        }else {
            int nAmmo = nbt.getInt("count");
            if (nAmmo > 0){
                return nAmmo;
            }
            else {
                clearAmmoNBT(itemStack);
                return 0;
            }
        }
    }

    public static void addAmmoNBT(ItemStack itemStack, int n, String type){
        CompoundNBT nbt = itemStack.getOrCreateChildTag("ammo");
        nbt.putInt("count", nbt.getInt("count")+n);
        nbt.putString("type", type);
    }

    public static void shrinkAmmoNBT(ItemStack itemStack){
        int nAmmo = getAmmoCount(itemStack);
        int nAmmoAfter = nAmmo - 1;
        if (nAmmoAfter <= 0){
            clearAmmoNBT(itemStack);
        }
        else {
            CompoundNBT nbt = itemStack.getOrCreateChildTag("ammo");
            nbt.putInt("count", nAmmoAfter);
        }
    }

    public static String getAmmoType(ItemStack itemStack){
        CompoundNBT nbt = itemStack.getChildTag("ammo");
        if (nbt == null){
            return null;
        }else{
            return nbt.getString("type");
        }
    }

    public static String getAmmoShowType(ItemStack itemStack){
        String type = getAmmoType(itemStack);
        return type == null ? "none" : type;
    }

    public abstract Item getAmmo();

    public int getCoolDown(){
        return this.coolDown;
    }

    public AbstractAmmoEntity getAmmoEntity(double x, double y, double z, World world, ItemStack toBeFired, PlayerEntity shooter) {
        return new AbstractAmmoEntity(x, y, z, world, toBeFired, shooter);
    }

    public float getVelocity(){
        return 0.5f;
    }

    public abstract void addAmmo(ItemStack offhand, ItemStack mainHand, int slot, PlayerEntity entity);

    public abstract int maxAmmo();

    public abstract SoundEvent getSoundEvent();

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        // loading ammo
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if(isSelected && !worldIn.isRemote && NuclearCraft.keyBinding.isPressed() && (entityIn instanceof PlayerEntity)){
            ItemStack itemStackMain = ((PlayerEntity) entityIn).getHeldItemMainhand();
            if (itemStackMain.getItem() instanceof AbstractGunItem){
                ItemStack itemStackOff = ((PlayerEntity) entityIn).getHeldItemOffhand();
                ((AbstractGunItem) itemStackMain.getItem()).addAmmo(itemStackOff, itemStackMain, itemSlot, (PlayerEntity) entityIn);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        int n_ammo = getAmmoCount(stack);
        tooltip.add(new TranslationTextComponent(String.format("item.%s.guns.ammo_left", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY).
                append(new StringTextComponent(" "+n_ammo).mergeStyle(TextFormatting.GOLD)));
        tooltip.add(new TranslationTextComponent(String.format("item.%s.guns.ammo_type", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY).
                append(new StringTextComponent(getAmmoShowType(stack)).mergeStyle(TextFormatting.GOLD)));
    }
}
