package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.SoundEventList;

import javax.annotation.Nonnull;

public class DesertEagle extends AbstractGunItem{

    public DesertEagle() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public int getCoolDown() {
        return 10;
    }

    @Override
    public int maxAmmo() {
        return 7;
    }

    @Override
    public int getLoadTime() {
        return 40;
    }

    @Override
    public String getShootActionString() {
        return "desert_eagle";
    }

    @Override
    public float getSpeedModifier() {
        return 1.4f;
    }

    @Override
    public double getDamageModifier() {
        return 1.4;
    }

    @Override
    public String getReloadSound() {
        return "de_reload_empty";
    }

    @Nonnull
    @Override
    public String compatibleSize() {
        return "9mm";
    }

    @Override
    protected double getGunSoundDist() {
        return 45;
    }
}
