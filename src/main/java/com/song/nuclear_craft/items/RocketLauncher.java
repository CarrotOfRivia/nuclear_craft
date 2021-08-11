package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class RocketLauncher extends RocketLauncherWithAmmo {

    public RocketLauncher() {
        super(new Item.Properties().stacksTo(1).tab(NuclearCraft.ITEM_GROUP));
    }

    @Override
    protected FireworkRocketEntity getEntity(Level worldIn, ItemStack toBeFired, Entity playerIn, double x, double y, double z, boolean p_i231582_10_) {
        return null;
    }

    @Override
    public Item getBoundedAmmo() {
        return null;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, @Nonnull Player playerIn, @Nonnull InteractionHand handIn) {
        return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TranslatableComponent(String.format("item.%s.rocket_launcher.tooltip", NuclearCraft.MODID)).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void addAmmo(ItemStack ammo, ItemStack rocket, int itemSlot, Entity entityIn) {
        String ammoRegistryName = Objects.requireNonNull(ammo.getItem().getRegistryName()).toString();
        ItemStack itemStack;
        if(ammo.getItem() == ItemList.ATOMIC_BOMB_ROCKET.get()){
            itemStack = new ItemStack(ItemList.ROCKET_LAUNCHER_ATOMIC_BOMB.get());
        }
        else if(ammo.getItem() == ItemList.INCENDIARY_ROCKET.get()){
            itemStack = new ItemStack(ItemList.ROCKET_LAUNCHER_INCENDIARY.get());
        }
        else if(ammo.getItem() == ItemList.SMOKE_ROCKET.get()){
            itemStack = new ItemStack(ItemList.ROCKET_LAUNCHER_SMOKE.get());
        }
        else if(ammo.getItem() == ItemList.HIGH_EXPLOSIVE_ROCKET.get()){
            itemStack = new ItemStack(ItemList.ROCKET_LAUNCHER_HIGH_EXPLOSIVE.get());
        }
        else if(ammo.getItem() == ItemList.WATER_DROP_ROCKET.get()){
            itemStack = new ItemStack(ItemList.ROCKET_LAUNCHER_WATER_DROP.get());
        }
        else {
            return;
        }
        clearAmmo(itemStack);
        entityIn.getSlot(itemSlot).set(itemStack);
        ((RocketLauncherWithAmmo)itemStack.getItem()).addAmmo(ammo, itemStack, itemSlot, entityIn);
    }
}
