package com.song.nuclear_craft.items.Ammo;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.AbstractAmmo;

import javax.annotation.Nonnull;

public class Ammo9mmNormal extends AbstractAmmo {
    public Ammo9mmNormal() {
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

    @Override
    public double getBaseDamage() {
        return 30;
    }

    @Override
    public float getBaseSpeed() {
        return 5;
    }

    @Override
    public double getGravity() {
        return 0.03;
    }
}
