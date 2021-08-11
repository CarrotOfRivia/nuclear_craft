package com.song.nuclear_craft.items.guns;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.misc.ConfigCommon;

import javax.annotation.Nonnull;

import net.minecraft.world.item.Item.Properties;

public class Barrett extends AbstractGunItem{
    public Barrett(){
        super(new Properties().stacksTo(1).tab(NuclearCraft.ITEM_GROUP));
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
        return ConfigCommon.BARRETT_CONFIG.getSpeedModify().get().floatValue();
    }

    @Override
    public double getDamageModifier() {
        return ConfigCommon.BARRETT_CONFIG.getDamageModify().get();
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
    protected double getGunSoundDist() {
        return 70;
    }
}
