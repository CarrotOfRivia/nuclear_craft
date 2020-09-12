package com.song.nuclear_craft.items.Ammo9mm;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.AbstractAmmo;

import javax.annotation.Nonnull;

public class Ammo762Nuke extends AbstractAmmo {

    public Ammo762Nuke() {
        super(new Properties().group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public String getSize() {
        return "7.62mm";
    }

    @Nonnull
    @Override
    public String getType() {
        return "nuke";
    }

    @Override
    public double getBaseDamage() {
        return 25;
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
