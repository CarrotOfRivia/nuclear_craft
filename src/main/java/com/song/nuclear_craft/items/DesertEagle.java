package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.SoundEventList;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nonnull;

public class DesertEagle extends AbstractGunItem{

    public DesertEagle() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
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
    public SoundEvent getSoundEvent() {
        return SoundEventList.DESERT_EAGLE;
    }

    @Nonnull
    @Override
    public String compatibleSize() {
        return "9mm";
    }
}
