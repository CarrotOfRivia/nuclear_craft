package com.song.nuclear_craft.items.guns;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.misc.ConfigCommon;

import javax.annotation.Nonnull;

public class M4A4 extends AbstractGunItem{
    public M4A4(){
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public int getCoolDown() {
        return 0;
    }

    @Override
    public String getShootActionString() {
        return "m4a4";
    }

    @Override
    public int maxAmmo() {
        return 30;
    }

    @Override
    public int getLoadTime() {
        return 60;
    }

    @Nonnull
    @Override
    public AmmoSize compatibleSize() {
        return AmmoSize.SIZE_556;
    }

    @Override
    public float getSpeedModifier() {
        return ConfigCommon.M4A4_CONFIG.getSpeedModify().get().floatValue();
    }

    @Override
    public double getDamageModifier() {
        return ConfigCommon.M4A4_CONFIG.getDamageModify().get();
    }

    @Override
    public String getReloadSound() {
        return "m4a4_reload";
    }

    @Override
    protected double getGunSoundDist() {
        return 26;
    }
}
