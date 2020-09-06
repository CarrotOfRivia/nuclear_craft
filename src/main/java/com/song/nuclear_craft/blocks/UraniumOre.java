package com.song.nuclear_craft.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.biome.Biomes;

public class UraniumOre extends OreBlock {
    public UraniumOre() {
        super(AbstractBlock.Properties.create(Material.ROCK).func_235861_h_().hardnessAndResistance(3.0F, 3.0F));
    }
}
