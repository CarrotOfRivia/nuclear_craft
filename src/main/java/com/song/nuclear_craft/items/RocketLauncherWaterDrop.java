package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.IncendiaryRocketEntity;
import com.song.nuclear_craft.entities.WaterDropRocketEntity;
import com.song.nuclear_craft.misc.Config;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RocketLauncherWaterDrop extends RocketLauncherWithAmmo{
    private static final int MAX_AMMO = Config.INCENDIARY_MAX_AMMO.get();

    public RocketLauncherWaterDrop() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
        this.coolDown = 5;
    }

    @Override
    public Item getBoundedAmmo() {
        return ItemList.WATER_DROP_ROCKET.get();
    }

    @Override
    protected int getMAX_AMMO() {
        return Config.WATER_DROP_MAX_AMMO.get();
    }

    @Override
    protected FireworkRocketEntity getEntity(World worldIn, ItemStack toBeFired, Entity playerIn, double x, double y, double z, boolean p_i231582_10_) {
        return new WaterDropRocketEntity(worldIn, toBeFired, playerIn, x, y, z, p_i231582_10_);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(String.format("tooltip.%s.water_drop_rocket.line0", NuclearCraft.MODID)).mergeStyle(TextFormatting.GOLD));
    }

}
