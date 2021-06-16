package com.song.nuclear_craft.items.guns;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.misc.ConfigCommon;

import javax.annotation.Nonnull;

public class FN57 extends AbstractGunItem{
    public FN57() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public int getCoolDown() {
        return 7;
    }

    @Override
    public String getShootActionString() {
        return "fn57";
    }

    @Override
    public int maxAmmo() {
        return 20;
    }

    @Override
    public int getLoadTime() {
        return 40;
    }

    @Nonnull
    @Override
    public AmmoSize compatibleSize() {
        return AmmoSize.SIZE_570;
    }

    @Override
    public float getSpeedModifier() {
        return ConfigCommon.FN57_CONFIG.getSpeedModify().get().floatValue();
    }

    @Override
    public double getDamageModifier() {
        return ConfigCommon.FN57_CONFIG.getDamageModify().get();
    }

    @Override
    public String getReloadSound() {
        return "de_reload_empty";
    }
}
