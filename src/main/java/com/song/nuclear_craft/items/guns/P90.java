package com.song.nuclear_craft.items.guns;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import com.song.nuclear_craft.misc.ConfigCommon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.Random;

import net.minecraft.world.item.Item.Properties;

public class P90 extends AbstractMachineGunItem{
    public P90() {
        super(new Properties().stacksTo(1).tab(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public int getCoolDown() {
        return 0;
    }

    @Override
    public String getShootActionString() {
        return "p90";
    }

    @Override
    public int maxAmmo() {
        return 50;
    }

    @Override
    public int getLoadTime() {
        return 64;
    }

    @Nonnull
    @Override
    public AmmoSize compatibleSize() {
        return AmmoSize.SIZE_570;
    }

    @Override
    public float getSpeedModifier() {
        return ConfigCommon.P90_CONFIG.getSpeedModify().get().floatValue();
    }

    @Override
    public double getDamageModifier() {
        return ConfigCommon.P90_CONFIG.getDamageModify().get();
    }

    @Override
    public String getReloadSound() {
        return "p90_reload";
    }

    @Override
    protected int getNumShoots() {
        return 2;
    }

    @Override
    protected float getInaccuracy(Level world, Player playerEntity) {
        return 0.5f;
    }
}
