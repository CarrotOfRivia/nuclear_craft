package com.song.nuclear_craft.items.guns;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.misc.ConfigCommon;

import javax.annotation.Nonnull;

import net.minecraft.world.item.Item.Properties;

public class Ak47 extends AbstractGunItem{
    public Ak47() {
        super(new Properties().stacksTo(1).tab(NuclearCraft.ITEM_GROUP));
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
        return ConfigCommon.AK47_CONFIG.getSpeedModify().get().floatValue();
    }

    @Override
    public double getDamageModifier() {
        return ConfigCommon.AK47_CONFIG.getDamageModify().get();
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
