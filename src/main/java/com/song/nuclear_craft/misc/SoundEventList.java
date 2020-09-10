package com.song.nuclear_craft.misc;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundEventList {
    public static SoundEvent BOMB_PLANTED = new SoundEvent(new ResourceLocation(NuclearCraft.MODID, "bomb_planted"));
    public static SoundEvent C4_BEEP = new SoundEvent(new ResourceLocation(NuclearCraft.MODID, "c4_beep"));
    public static SoundEvent LOADING = new SoundEvent(new ResourceLocation(NuclearCraft.MODID, "loading"));
    public static SoundEvent DESERT_EAGLE = new SoundEvent(new ResourceLocation(NuclearCraft.MODID, "desert_eagle"));
}
