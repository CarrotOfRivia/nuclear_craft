package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class C4BombItem extends BlockItem {
    public C4BombItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TranslatableComponent(String.format("item.%s.c4_bomb.tooltip1", NuclearCraft.MODID)).withStyle(ChatFormatting.GRAY));
        tooltip.add(new TranslatableComponent(String.format("item.%s.c4_bomb.tooltip2", NuclearCraft.MODID)).withStyle(ChatFormatting.GRAY));
    }
}
