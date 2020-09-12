package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.SoundEventList;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nonnull;

public class Glock extends AbstractGunItem{
    public Glock() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
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
    public String compatibleSize() {
        return "9mm";
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEventList.GLOCK;
    }

    @Override
    public float getSpeedModifier() {
        return 1.0f;
    }

    @Override
    public double getDamageModifier() {
        return 1.1f;
    }

    @Override
    public SoundEvent getReloadSound() {
        return SoundEventList.DE_RELOAD_EMPTY;
    }
}
