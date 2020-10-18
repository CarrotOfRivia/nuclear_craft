package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.misc.Config;
import com.song.nuclear_craft.misc.SoundEventList;

import javax.annotation.Nonnull;

public class Ak47 extends AbstractGunItem{
    public Ak47() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public int getCoolDown() {
        return 0;
    }

    @Override
    public int maxAmmo() {
        return 30;
    }

    @Override
    public int getLoadTime() {
        return 64;
    }

    @Nonnull
    @Override
    public AmmoSize compatibleSize() {
        return AmmoSize.SIZE_762;
    }

    @Override
    public String getShootActionString() {
        return "ak47";
    }

    @Override
    public float getSpeedModifier() {
        return Config.AK47_CONFIG.getSpeedModify().get().floatValue();
    }

    @Override
    public double getDamageModifier() {
        return Config.AK47_CONFIG.getDamageModify().get();
    }

    @Override
    public String getReloadSound() {
        return "ak47_reload";
    }

    @Override
    protected double getGunSoundDist() {
        return 30;
    }
}
