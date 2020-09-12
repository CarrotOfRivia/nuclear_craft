package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.SoundEventList;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nonnull;

public class Ak47 extends AbstractGunItem{
    public Ak47() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
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
        return 60;
    }

    @Nonnull
    @Override
    public String compatibleSize() {
        return "7.62mm";
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEventList.AK47;
    }

    @Override
    public float getSpeedModifier() {
        return 1.5f;
    }

    @Override
    public double getDamageModifier() {
        return 1.7d;
    }

    @Override
    public SoundEvent getReloadSound() {
        return SoundEventList.DE_RELOAD_EMPTY;
    }
}
