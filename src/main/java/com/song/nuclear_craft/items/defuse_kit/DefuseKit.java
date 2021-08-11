package com.song.nuclear_craft.items.defuse_kit;

import net.minecraft.world.item.Item;

public abstract class DefuseKit extends Item {


    public DefuseKit(Properties builder) {
        super(builder);
    }

    public abstract int getDefuseTick();
}
