package com.song.nuclear_craft.trash_can;

import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class UraniumOre extends OreBlock {
    public UraniumOre() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(3.0F, 3.0F));
    }
}
