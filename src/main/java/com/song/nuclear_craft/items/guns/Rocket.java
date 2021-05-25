package com.song.nuclear_craft.items.guns;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class Rocket extends Item {
    private final int tooltipLines;
    public Rocket(Properties properties, int tooltipLines) {
        super(properties);
        this.tooltipLines = tooltipLines;
    }

    public Rocket(Properties properties){
        this(properties, 0);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        for(int l=0; l<this.tooltipLines; l++){
            tooltip.add(new TranslationTextComponent(String.format("tooltip.%s.%s.line%d", NuclearCraft.MODID,
                    Objects.requireNonNull(this.getRegistryName()).getPath(), l)));
        }
        tooltip.add(new TranslationTextComponent(String.format("tooltip.%s.rocket_load.line0", NuclearCraft.MODID)));
        tooltip.add(new TranslationTextComponent(String.format("tooltip.%s.rocket_load.line1", NuclearCraft.MODID)));
    }
}
