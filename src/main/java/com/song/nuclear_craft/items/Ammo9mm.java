package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;

import javax.annotation.Nonnull;

public class Ammo9mm extends AbstractAmmo{
    public Ammo9mm() {
        super(new Properties().group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public String getSize() {
        return "9mm";
    }

    @Nonnull
    @Override
    public String getType() {
        return "normal";
    }
}
