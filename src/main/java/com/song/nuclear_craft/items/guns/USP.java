package com.song.nuclear_craft.items.guns;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.guns.AbstractGunItem;
import com.song.nuclear_craft.misc.ConfigCommon;

import javax.annotation.Nonnull;

import net.minecraft.world.item.Item.Properties;

public class USP extends AbstractGunItem {

    public USP() {
        super(new Properties().stacksTo(1).tab(NuclearCraft.ITEM_GROUP));
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
        return ConfigCommon.USP_CONFIG.getSpeedModify().get().floatValue();
    }

    @Override
    public double getDamageModifier() {
        return ConfigCommon.USP_CONFIG.getDamageModify().get();
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
