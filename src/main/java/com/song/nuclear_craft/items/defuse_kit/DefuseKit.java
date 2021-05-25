package com.song.nuclear_craft.items.defuse_kit;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.TieredItem;

public abstract class DefuseKit extends Item {


    public DefuseKit(Properties builder) {
        super(builder);
    }

    public abstract int getDefuseTick();
}
