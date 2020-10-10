package com.song.nuclear_craft.items.Ammo;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.AbstractAmmo;

import javax.annotation.Nonnull;

public class Ammo9mmExplosive extends AbstractAmmo {
    public Ammo9mmExplosive() {
        super(new Properties().group(NuclearCraft.ITEM_GROUP));
    }

    public Ammo9mmExplosive(String size) {
        super(new Properties().group(NuclearCraft.ITEM_GROUP), size);
    }

    @Override
    public String getSize() {
        return "9mm";
    }

        @Nonnull
    @Override
    public String getType() {
        return "explosive";
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
