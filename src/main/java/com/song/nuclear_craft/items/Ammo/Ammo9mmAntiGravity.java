package com.song.nuclear_craft.items.Ammo;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.AbstractAmmo;

import javax.annotation.Nonnull;

public class Ammo9mmAntiGravity extends AbstractAmmo {
    public Ammo9mmAntiGravity() {
        super(new Properties().group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public String getSize() {
        return "9mm";
    }

    @Nonnull
    @Override
    public String getType() {
        return "anti_gravity";
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
        return 0;
    }
}
