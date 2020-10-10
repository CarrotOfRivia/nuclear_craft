package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;

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
        return 0;
    }

    @Nonnull
    @Override
    public String compatibleSize() {
        return "5.56mm";
    }

    @Override
    public float getSpeedModifier() {
        return 1.35f;
    }

    @Override
    public double getDamageModifier() {
        return 1.3f;
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
