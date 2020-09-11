package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.entities.AmmoEntities.AmmoNormalEntity;
import com.song.nuclear_craft.entities.AmmoEntities.TestAmmoEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

import javax.annotation.Nonnull;
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
                String ammoType = getAmmoType(heldItemStack);
                assert ammoType != null;
                String ammoSize = compatibleSize();
                if(gunItem.getCoolDown()>0){
                    playerIn.getCooldownTracker().setCooldown(heldItemStack.getItem(), gunItem.getCoolDown());
                }
                ItemStack toBeFired = new ItemStack(gunItem.getAmmoItem(ammoType, ammoSize));
                AbstractAmmoEntity entity = gunItem.getAmmoEntity(playerIn.getPosX(), playerIn.getPosYEye() - (double)0.15F, playerIn.getPosZ(), worldIn, toBeFired, playerIn, ammoType, ammoSize);
                worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), getSoundEvent(), SoundCategory.PLAYERS, 0.1f, 1f);
                entity.setSilent(true);
                // handle it myself
                entity.setNoGravity(true);
                entity.setGravity(getGravity(ammoType, ammoSize));
                Vector3d vec3d = playerIn.getLookVec();
                entity.shoot(vec3d.x, vec3d.y, vec3d.z, gunItem.getAmmoVelocity(ammoType, ammoSize)*getSpeedModifier(), 0);
                entity.setBaseDamage(getDamageModifier()*entity.getBaseDamage());
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

    public Item getAmmoItem(String ammoType, String ammoSize) {
        // get Ammo Item instance from type and size
        // TODO add size
        switch (ammoType){
            case "test":
                return ItemList.AMMO_TEST;
            case "anti_gravity":
                return ItemList.AMMO_9MM_ANTI_GRAVITY;
            case "explosive":
                return ItemList.AMMO_9MM_EXPLOSIVE;
            case "incendiary":
                return ItemList.AMMO_9MM_INCENDIARY;
            case "nuke":
                return ItemList.AMMO_9MM_NUKE;
            case "silver":
                return ItemList.AMMO_9MM_SILVER;
            case "tungsten":
                return ItemList.AMMO_9MM_TUNGSTEN;
            case "normal":
            default:
                return ItemList.AMMO_9MM;
        }

    }

    public double getGravity(String ammoType, String ammoSize) {
        // TODO
        switch (ammoType){
            case "test":
            case "anti_gravity":
                return 0.f;
            case "normal":
            case "explosive":
            case "incendiary":
            case "nuke":
            case "silver":
            case "tungsten":
            default:
                return 0.03f;
        }
    }

    public int getCoolDown(){
        return this.coolDown;
    }

    public AbstractAmmoEntity getAmmoEntity(double x, double y, double z, World world, ItemStack toBeFired, PlayerEntity shooter, String ammoType, String ammoSize) {
        // TODO
        switch (ammoType){
            case "test":
                return new TestAmmoEntity(x, y, z, world, toBeFired, shooter);
            case "normal":
            default:
                return new AmmoNormalEntity(x, y, z, world, toBeFired, shooter);
        }
    }

    public float getAmmoVelocity(String ammoType, String ammoSize) {
        // TODO
        switch (ammoType){
            case "test":
                return 0.5f;
            case "normal":
            case "anti_gravity":
            case "explosive":
            case "incendiary":
            case "nuke":
            case "silver":
            case "tungsten":
            default:
                return 7f;
        }
    }

    public void addAmmo(ItemStack offhand, ItemStack mainHand, int slot, PlayerEntity entity) {
        if(offhand.getItem() instanceof AbstractAmmo){
            AbstractAmmo ammo = (AbstractAmmo) offhand.getItem();
            if (((AbstractGunItem)(mainHand.getItem())).compatibleSize().equals(ammo.getSize())){
                if ((hasAmmo(mainHand) && (ammo.getType()).equals(getAmmoType(mainHand)))||(!hasAmmo(mainHand))){
                    int n_load = Math.min(offhand.getCount(), this.maxAmmo() - getAmmoCount(mainHand));
                    offhand.shrink(n_load);
                    addAmmoNBT(mainHand, n_load, ammo.getType());
                    entity.getCooldownTracker().setCooldown(mainHand.getItem(), getLoadTime());
                }
            }
        }
    }

    public abstract int maxAmmo();

    public abstract int getLoadTime();

    @Nonnull
    public abstract String compatibleSize();

    public abstract SoundEvent getSoundEvent();

    public abstract float getSpeedModifier();

    public abstract double getDamageModifier();

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
