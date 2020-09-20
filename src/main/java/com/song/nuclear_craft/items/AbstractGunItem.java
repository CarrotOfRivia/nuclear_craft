package com.song.nuclear_craft.items;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.entities.AmmoEntities.*;
import com.song.nuclear_craft.misc.SoundEventList;
import net.minecraft.client.Minecraft;
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
import java.util.Objects;

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
                AbstractAmmo ammoItem = gunItem.getAmmoItem(ammoType, ammoSize);
                ItemStack toBeFired = new ItemStack(ammoItem);
                AbstractAmmoEntity entity = gunItem.getAmmoEntity(playerIn.getPosX(), playerIn.getPosYEye() - (double)0.15F, playerIn.getPosZ(), worldIn, toBeFired, playerIn, ammoType, ammoSize);
                worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), getSoundEvent(), SoundCategory.PLAYERS, getSoundVolume(), 1f);
                entity.setSilent(true);
                // handle it myself
                entity.setNoGravity(true);
                entity.setGravity(ammoItem.getGravity());
                Vector3d vec3d = playerIn.getLookVec();
                entity.shoot(vec3d.x, vec3d.y, vec3d.z, ammoItem.getBaseSpeed()*getSpeedModifier(), 0);
                entity.setBaseDamage(getDamageModifier()*entity.getBaseDamage());
                worldIn.addEntity(entity);
                shrinkAmmoNBT(heldItemStack);
                return ActionResult.func_233538_a_(heldItemStack, worldIn.isRemote());
            }
            else if(! worldIn.isRemote){
                worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEventList.NO_AMMO, SoundCategory.PLAYERS, 1f, 1f);
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
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

    public float getSoundVolume(){
        return 0.2f;
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

    public AbstractAmmo getAmmoItem(String ammoType, String ammoSize) {
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


    public abstract int getCoolDown();

    public AbstractAmmoEntity getAmmoEntity(double x, double y, double z, World world, ItemStack toBeFired, PlayerEntity shooter, String ammoType, String ammoSize) {
        // TODO
        switch (ammoType){
            case "test":
                return new TestAmmoEntity(x, y, z, world, toBeFired, shooter);
            case "anti_gravity":
                return new AmmoAntiGravityEntity(x, y, z, world, toBeFired, shooter);
            case "explosive":
                return new AmmoExplosiveEntity(x, y, z, world, toBeFired, shooter);
            case "incendiary":
                return new AmmoIncendiaryEntity(x, y, z, world, toBeFired, shooter);
            case "nuke":
                return new AmmoNukeEntity(x, y, z, world, toBeFired, shooter);
            case "silver":
                return new AmmoSilverEntity(x, y, z, world, toBeFired, shooter);
            case "tungsten":
                return new AmmoTungstenEntity(x, y, z, world, toBeFired, shooter);
            case "normal":
            default:
                return new AmmoNormalEntity(x, y, z, world, toBeFired, shooter);
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
                    if(!entity.world.isRemote){
                        entity.world.playSound(null, entity.getPosX(), entity.getPosY(), entity.getPosZ(), getReloadSound(), SoundCategory.PLAYERS, 1f, 1f);
                    }
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

    public abstract SoundEvent getReloadSound();

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        // loading ammo
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if(isSelected){
            if(!worldIn.isRemote && NuclearCraft.gunReload.isPressed() && (entityIn instanceof PlayerEntity)){
                ItemStack itemStackMain = ((PlayerEntity) entityIn).getHeldItemMainhand();
                if ((itemStackMain.getItem() instanceof AbstractGunItem)&&!(((PlayerEntity) entityIn).getCooldownTracker().hasCooldown(itemStackMain.getItem()))){
                    ItemStack itemStackOff = ((PlayerEntity) entityIn).getHeldItemOffhand();
                    ((AbstractGunItem) itemStackMain.getItem()).addAmmo(itemStackOff, itemStackMain, itemSlot, (PlayerEntity) entityIn);
                }
            }
            if (worldIn.isRemote&&((getCoolDown()<=0)||(Minecraft.getInstance().ingameGUI.getTicks()%getCoolDown()==0))){
                Minecraft.getInstance().ingameGUI.setOverlayMessage(new TranslationTextComponent(String.format("item.%s.guns.ammo_left", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY).
                        append(new StringTextComponent(" "+getAmmoCount(stack)).mergeStyle(TextFormatting.GOLD)), false);
            }
        }

    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(hasAmmo(stack)){
            int n_ammo = getAmmoCount(stack);
            AbstractAmmo ammo = getAmmoItem(Objects.requireNonNull(getAmmoType(stack)), compatibleSize());
            tooltip.add(new TranslationTextComponent(String.format("item.%s.guns.ammo_left", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY).
                    append(new StringTextComponent(" "+n_ammo).mergeStyle(TextFormatting.GOLD)));
            tooltip.add(new TranslationTextComponent(String.format("item.%s.guns.ammo_type", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY).
                    append(new TranslationTextComponent(String.format("item.%s.%s", NuclearCraft.MODID, Objects.requireNonNull(ammo.getRegistryName()).getPath())).mergeStyle(TextFormatting.GOLD)));
        }
        tooltip.add(new TranslationTextComponent(String.format("item.%s.guns.compatible_ammo_size", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY).append(new StringTextComponent(" "+compatibleSize())));
        tooltip.add(new TranslationTextComponent(String.format("item.%s.guns.damage_modifier", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY).append(new StringTextComponent(" "+getDamageModifier())));
        tooltip.add(new TranslationTextComponent(String.format("item.%s.guns.speed_modifier", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY).append(new StringTextComponent(" "+getSpeedModifier())));
        if(canUseScope()){
            tooltip.add(new TranslationTextComponent(String.format("item.%s.guns.use_scope", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY).append(new StringTextComponent(" Z")));
        }
    }

    public boolean canUseScope(){
        return false;
    }
}
