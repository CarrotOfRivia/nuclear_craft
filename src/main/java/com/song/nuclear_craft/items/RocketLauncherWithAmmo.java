package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.ClientEventForgeSubscriber;
import com.song.nuclear_craft.misc.SoundEventList;
import com.song.nuclear_craft.network.GunLoadingPacket;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import com.song.nuclear_craft.network.SoundPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.List;

public abstract class RocketLauncherWithAmmo extends Item {
    protected int MAX_AMMO = 10;
    protected Item BONDED_AMMO=null;
    protected int coolDown = 0;
    public RocketLauncherWithAmmo(Properties properties) {
        super(properties);
    }

    protected int getAmmoCount(ItemStack itemStack){
        CompoundNBT compoundnbt = itemStack.getOrCreateTag();
        if(! compoundnbt.contains("ammo")){
            compoundnbt.putInt("ammo", this.MAX_AMMO);
        }
        return compoundnbt.getInt("ammo");
    }

    protected void addAmmoCount(ItemStack itemStack, int n){
        CompoundNBT compoundnbt = itemStack.getOrCreateTag();
        assert compoundnbt.contains("ammo");
        compoundnbt.putInt("ammo", n+compoundnbt.getInt("ammo"));
    }

    public void clearAmmo(ItemStack itemStack){
        addAmmoCount(itemStack, -getAmmoCount(itemStack));
    }

    protected ActionResult<ItemStack>  afterFire(World worldIn, ItemStack thisItemStack){
        CompoundNBT compoundnbt = thisItemStack.getOrCreateTag();
        if(! compoundnbt.contains("ammo")){
            compoundnbt.putInt("ammo", this.MAX_AMMO);
        }
        if(!worldIn.isRemote){
            int n_ammo = compoundnbt.getInt("ammo");
            n_ammo --;
            compoundnbt.putInt("ammo", n_ammo);
        }
        if (compoundnbt.getInt("ammo") <= 0){
            return ActionResult.func_233538_a_(new ItemStack(ItemList.ROCKET_LAUNCHER), worldIn.isRemote());
        }
        else {
            return ActionResult.func_233538_a_(thisItemStack, worldIn.isRemote());
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        int n_ammo = getAmmoCount(stack);
        tooltip.add(new TranslationTextComponent(String.format("item.%s.rocket_launcher.ammo_left", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY));
        tooltip.add(new StringTextComponent(""+n_ammo).mergeStyle(TextFormatting.GOLD));
    }

    protected void addAmmo(ItemStack ammo, ItemStack rocket, int itemSlot, Entity entityIn){
        if (BONDED_AMMO != null){
            if(ammo.getItem().getRegistryName().equals(BONDED_AMMO.getRegistryName())){
                int n_ammo = getAmmoCount(rocket);
                if (n_ammo<MAX_AMMO){
                    int n_loaded = Math.min(MAX_AMMO-n_ammo, ammo.getCount());
                    ammo.shrink(n_loaded);
                    addAmmoCount(rocket, n_loaded);
                    BlockPos pos = entityIn.getPosition();
                    NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), 20, entityIn.world.getDimensionKey())),
                            new SoundPacket(pos, "rocket_load"));
                    ((PlayerEntity) entityIn).getCooldownTracker().setCooldown(rocket.getItem(), 20);
                }
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        // loading ammo
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if(isSelected && worldIn.isRemote){
            if(ClientEventForgeSubscriber.gunReload.isPressed() && (entityIn instanceof PlayerEntity)){
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

    protected void enterCD(PlayerEntity playerEntity){
        playerEntity.getCooldownTracker().setCooldown(this, coolDown);
    }
}
