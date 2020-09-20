package com.song.nuclear_craft.items.Ammo;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.AbstractAmmo;

import javax.annotation.Nonnull;

public class Ammo127Nuke extends AbstractAmmo {

    public Ammo127Nuke() {
        super(new Properties().group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public String getSize() {
        return "12.7mm";
    }
    @Nonnull
    @Override
    public String getType() {
        return "nuke";
    }

    @Override
    public double getBaseDamage() {
        return 50;
    }

    @Override
    public float getBaseSpeed() {
        return 9;
    }

    @Override
    public double getGravity() {
        return 0.03;
    }
}
