package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class C4BombItem extends BlockItem {
    public C4BombItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent(String.format("item.%s.c4_bomb.tooltip1", NuclearCraft.MODID)).func_240699_a_(TextFormatting.GRAY));
        tooltip.add(new TranslationTextComponent(String.format("item.%s.c4_bomb.tooltip2", NuclearCraft.MODID)).func_240699_a_(TextFormatting.GRAY));
    }
}
