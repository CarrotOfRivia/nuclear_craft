package com.song.nuclear_craft.items.Ammo;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.AbstractAmmo;

import javax.annotation.Nonnull;

public class Ammo762Normal extends AbstractAmmo {
    public Ammo762Normal() {
        super(new Properties().group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public String getSize() {
        return "7.62mm";
    }

    @Nonnull
    @Override
    public String getType() {
        return "normal";
    }

    @Override
    public double getBaseDamage() {
        return 25;
    }

    @Override
    public float getBaseSpeed() {
        return 7;
    }

    @Override
    public double getGravity() {
        return 0.03;
    }
}
