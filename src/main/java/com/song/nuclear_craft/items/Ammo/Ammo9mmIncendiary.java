package com.song.nuclear_craft.items.Ammo;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.AbstractAmmo;

import javax.annotation.Nonnull;

public class Ammo9mmIncendiary extends AbstractAmmo {
    public Ammo9mmIncendiary() {
        super(new Properties().group(NuclearCraft.ITEM_GROUP));
        setSize("9mm");
    }

    @Nonnull
    @Override
    public String getType() {
        return "incendiary";
    }

    @Override
    public double getBaseDamage() {
        return 20;
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
