package com.song.nuclear_craft.items.guns;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.guns.AbstractGunItem;
import com.song.nuclear_craft.misc.ConfigCommon;

import javax.annotation.Nonnull;

import net.minecraft.world.item.Item.Properties;

public class DesertEagle extends AbstractGunItem {

    public DesertEagle() {
        super(new Properties().stacksTo(1).tab(NuclearCraft.ITEM_GROUP));
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
        return ConfigCommon.DESERT_EAGLE_CONFIG.getSpeedModify().get().floatValue();
    }

    @Override
    public double getDamageModifier() {
        return ConfigCommon.DESERT_EAGLE_CONFIG.getDamageModify().get();
    }

    @Override
    public String getReloadSound() {
        return "de_reload_empty";
    }

    @Nonnull
    @Override
    public AmmoSize compatibleSize() {
        return AmmoSize.SIZE_9MM;
    }

    @Override
    protected double getGunSoundDist() {
        return 45;
    }
}
