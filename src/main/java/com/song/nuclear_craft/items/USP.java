package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.misc.Config;

import javax.annotation.Nonnull;

public class USP extends AbstractGunItem{

    public USP() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public int maxAmmo() {
        return 15;
    }

    @Override
    public int getLoadTime() {
        return 40;
    }

    @Nonnull
    @Override
    public AmmoSize compatibleSize() {
        return AmmoSize.SIZE_9MM;
    }

    @Override
    public String getShootActionString() {
        return "usp";
    }
    @Override
    public float getSpeedModifier() {
        return Config.USP_CONFIG.getSpeedModify().get().floatValue();
    }

    @Override
    public double getDamageModifier() {
        return Config.USP_CONFIG.getDamageModify().get();
    }

    @Override
    public String getReloadSound() {
        return "de_reload_empty";
    }

    @Override
    public int getCoolDown() {
        return 7;
    }

    @Override
    protected double getGunSoundDist() {
        return 10;
    }
}
