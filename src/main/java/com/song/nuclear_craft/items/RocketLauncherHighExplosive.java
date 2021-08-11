package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.rocket_entities.HighExplosiveRocketEntity;
import com.song.nuclear_craft.misc.ConfigCommon;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RocketLauncherHighExplosive extends RocketLauncherWithAmmo {

    public RocketLauncherHighExplosive() {
        super(new Item.Properties().stacksTo(1).tab(NuclearCraft.ITEM_GROUP));
        this.coolDown = 5;
    }

    @Override
    protected int getMAX_AMMO() {
        return ConfigCommon.HIGH_EXPLOSIVE_MAX_AMMO.get();
    }

    @Override
    public Item getBoundedAmmo() {
        return ItemList.HIGH_EXPLOSIVE_ROCKET.get();
    }

    @Override
    protected FireworkRocketEntity getEntity(Level worldIn, ItemStack toBeFired, Entity playerIn, double x, double y, double z, boolean p_i231582_10_) {
        return new HighExplosiveRocketEntity(worldIn, toBeFired, playerIn, x, y, z, p_i231582_10_);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent(String.format("tooltip.%s.high_explosive_rocket.line0", NuclearCraft.MODID)).withStyle(ChatFormatting.GOLD));
    }
}
