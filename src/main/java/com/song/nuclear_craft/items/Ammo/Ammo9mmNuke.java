package com.song.nuclear_craft.items.Ammo;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.AbstractAmmo;

import javax.annotation.Nonnull;

public class Ammo9mmNuke extends AbstractAmmo {

    public Ammo9mmNuke() {
        super(new Properties().group(NuclearCraft.ITEM_GROUP));
    }

    public Ammo9mmNuke(String size) {
        super(new Properties().group(NuclearCraft.ITEM_GROUP), size);
    }

    @Override
    public String getSize() {
        return "9mm";
    }


    @Nonnull
    @Override
    public String getType() {
        return "nuke";
    }

    @Override
    public double getBaseDamage() {
        return 30;
    }

    @Override
    public float getBaseSpeed() {
        return 6;
    }

    @Override
    public double getGravity() {
        return 0.03;
    }
}
