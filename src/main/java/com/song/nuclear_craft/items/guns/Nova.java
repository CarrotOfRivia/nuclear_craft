package com.song.nuclear_craft.items.guns;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import com.song.nuclear_craft.misc.ConfigCommon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

import net.minecraft.world.item.Item.Properties;

public class Nova extends AbstractGunItem{
    public Nova() {
        super(new Properties().stacksTo(1).tab(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public int getCoolDown() {
        return 20;
    }

    @Override
    public String getShootActionString() {
        return "nova";
    }

    @Override
    public int maxAmmo() {
        return 8;
    }

    @Override
    public int getLoadTime() {
        return 6;
    }

    @Nonnull
    @Override
    public AmmoSize compatibleSize() {
        return AmmoSize.SIZE_12_GA;
    }

    @Override
    public float getSpeedModifier() {
        return 1.1f;
    }

    @Override
    public double getDamageModifier() {
        return 1.2;
    }

    @Override
    public String getReloadSound() {
        return "xm1014_reload";
    }

    @Override
    protected int getBirdShotCount(AmmoType ammoType) {
        return ConfigCommon.BIRD_SHOT_COUNT_MAP.get(ammoType).get();
    }

    @Override
    protected float getInaccuracy(Level world, Player playerEntity) {
        return 3f;
    }

    @Override
    protected int getAmmoCountPerLoad() {
        return 1;
    }
}
