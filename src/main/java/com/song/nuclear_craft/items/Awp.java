package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.SoundEventList;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nonnull;

public class Awp extends AbstractGunItem{
    public Awp(){
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public int getCoolDown() {
        return 40;
    }

    @Override
    public int maxAmmo() {
        return 10;
    }

    @Override
    public int getLoadTime() {
        return 64;
    }

    @Nonnull
    @Override
    public String compatibleSize() {
        return "7.62mm";
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEventList.AWP;
    }

    @Override
    public float getSpeedModifier() {
        return 2.0f;
    }

    @Override
    public double getDamageModifier() {
        return 3.0f;
    }

    @Override
    public SoundEvent getReloadSound() {
        return SoundEventList.AWP_RELOAD;
    }

    @Override
    public boolean canUseScope() {
        return true;
    }
}
