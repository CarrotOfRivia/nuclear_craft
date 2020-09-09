package com.song.nuclear_craft.items;

import net.minecraft.item.Item;

public abstract class AbstractAmmo extends Item {
    public AbstractAmmo(Properties properties) {
        super(properties);
    }

    public enum Size {
        S_9MM,
        S_12_7_MM,
        S_7_62_MM,
        S_5_56_MM
    }
}
