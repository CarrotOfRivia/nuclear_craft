package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.SoundEventList;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nonnull;

public class USP extends AbstractGunItem{

    public USP() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
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
    public String compatibleSize() {
        return "9mm";
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEventList.USP;
    }

    @Override
    public float getSpeedModifier() {
        return 1.0f;
    }

    @Override
    public double getDamageModifier() {
        return 1.0f;
    }

    @Override
    public SoundEvent getReloadSound() {
        return SoundEventList.DE_RELOAD_EMPTY;
    }

    @Override
    public float getSoundVolume() {
        return 0.1f;
    }

    @Override
    public int getCoolDown() {
        return 7;
    }
}
