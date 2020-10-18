package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.misc.Config;
import com.song.nuclear_craft.misc.SoundEventList;

import javax.annotation.Nonnull;

public class Barrett extends AbstractGunItem{
    public Barrett(){
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public int getCoolDown() {
        return 40;
    }

    @Override
    public int maxAmmo() {
        return 5;
    }

    @Override
    public int getLoadTime() {
        return 64;
    }

    @Nonnull
    @Override
    public AmmoSize compatibleSize() {
        return AmmoSize.SIZE_127;
    }

    @Override
    public String getShootActionString() {
        return "barrett";
    }

    @Override
    public float getSpeedModifier() {
        return Config.BARRETT_CONFIG.getSpeedModify().get().floatValue();
    }

    @Override
    public double getDamageModifier() {
        return Config.BARRETT_CONFIG.getDamageModify().get();
    }

    @Override
    public String getReloadSound() {
        return "awp_reload";
    }

    @Override
    public boolean canUseScope() {
        return true;
    }

    @Override
    public float getSoundVolume() {
        return 0.5f;
    }

    @Override
    protected double getGunSoundDist() {
        return 70;
    }
}
