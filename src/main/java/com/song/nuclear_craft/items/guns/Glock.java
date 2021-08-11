package com.song.nuclear_craft.items.guns;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.misc.ConfigCommon;

import javax.annotation.Nonnull;

import net.minecraft.world.item.Item.Properties;

public class Glock extends AbstractGunItem{
    public Glock() {
        super(new Properties().stacksTo(1).tab(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public int getCoolDown() {
        return 7;
    }

    @Override
    public int maxAmmo() {
        return 17;
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
        return "glock";
    }

    @Override
    public float getSpeedModifier() {
        return ConfigCommon.GLOCK_CONFIG.getSpeedModify().get().floatValue();
    }

    @Override
    public double getDamageModifier() {
        return ConfigCommon.GLOCK_CONFIG.getDamageModify().get();
    }

    @Override
    public String getReloadSound() {
        return "de_reload_empty";
    }
}
