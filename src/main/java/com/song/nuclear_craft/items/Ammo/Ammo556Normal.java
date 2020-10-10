package com.song.nuclear_craft.items.Ammo;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.AbstractAmmo;

import javax.annotation.Nonnull;

public class Ammo556Normal extends AbstractAmmo {
    public Ammo556Normal() {
        super(new Properties().group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public String getSize() {
        return "5.56mm";
    }

    @Nonnull
    @Override
    public String getType() {
        return "normal";
    }

    @Override
    public double getBaseDamage() {
        return 22;
    }

    @Override
    public float getBaseSpeed() {
        return 8.5f;
    }

    @Override
    public double getGravity() {
        return 0.03;
    }
}
