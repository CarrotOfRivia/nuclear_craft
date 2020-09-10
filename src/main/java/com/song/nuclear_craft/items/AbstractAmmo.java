package com.song.nuclear_craft.items;

import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public abstract class AbstractAmmo extends Item {
    public AbstractAmmo(Properties properties) {
        super(properties);
    }

    public abstract String getSize();

    @Nonnull
    public abstract String getType();
}
