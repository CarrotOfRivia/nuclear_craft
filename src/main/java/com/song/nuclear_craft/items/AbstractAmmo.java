package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractAmmo extends Item {
    public AbstractAmmo(Properties properties) {
        super(properties);
    }

    public abstract String getSize();

    @Nonnull
    public abstract String getType();

    public abstract double getBaseDamage();

    public abstract float getBaseSpeed();

    public abstract double getGravity();

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(String.format("item.%s.ammo.base_damage", NuclearCraft.MODID)).append(new StringTextComponent(" "+getBaseDamage())));
        tooltip.add(new TranslationTextComponent(String.format("item.%s.ammo.base_speed", NuclearCraft.MODID)).append(new StringTextComponent(" "+getBaseSpeed())));
        tooltip.add(new TranslationTextComponent(String.format("item.%s.ammo.gravity", NuclearCraft.MODID)).append(new StringTextComponent(" "+getGravity())));
    }
}
