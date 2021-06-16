package com.song.nuclear_craft.client;

import com.song.nuclear_craft.misc.SoundEventList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoundPlayMethods {
    public static void c4Beep(BlockPos pos){
        // CLIENT SIDE ONLY
        World world = Minecraft.getInstance().world;
        assert world != null;
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.C4_BEEP, SoundCategory.BLOCKS, 1f, 1.0f, false);
    }

    public static void activeSound(BlockPos pos){
        World world = Minecraft.getInstance().world;
        assert world != null;
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.BOMB_PLANTED, SoundCategory.PLAYERS, 1f, 1.0f, true);
    }

    public static void playLoadSound(BlockPos pos){
        // Rocket launcher load sound
        World world = Minecraft.getInstance().world;
        assert world != null;
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.ROCKET_LOAD, SoundCategory.PLAYERS, 1f, 1.0f, true);
    }

    public static void playSoundFromString(BlockPos pos, String name){
        World world = Minecraft.getInstance().world;
        assert world != null;
        if(name==null){
            return;
        }
        switch (name){
            case "c4_beep":
                SoundPlayMethods.c4Beep(new BlockPos(pos.getX(), pos.getY(), pos.getZ())); break;
            case "c4_activate":
                SoundPlayMethods.activeSound(new BlockPos(pos.getX(), pos.getY(), pos.getZ())); break;
            case "rocket_load":
                SoundPlayMethods.playLoadSound(new BlockPos(pos.getX(), pos.getY(), pos.getZ())); break;
            case "desert_eagle":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.DESERT_EAGLE, SoundCategory.PLAYERS, 0.25f, 1.0f, false); break;
            case "no_ammo":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.NO_AMMO, SoundCategory.PLAYERS, 1f, 1.0f, false); break;
            case "de_reload_empty":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.DE_RELOAD_EMPTY, SoundCategory.PLAYERS, 1f, 1.0f, false); break;
            case "glock":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.GLOCK, SoundCategory.PLAYERS, 0.25f, 1.0f, false); break;
            case "fn57":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.FN57, SoundCategory.PLAYERS, 0.4f, 1.0f, false); break;
            case "usp":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.USP, SoundCategory.PLAYERS, 0.25f, 1.0f, false); break;
            case "ak47":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.AK47, SoundCategory.PLAYERS, 0.25f, 1.0f, false); break;
            case "ak47_reload":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.AK47_RELOAD, SoundCategory.PLAYERS, 1f, 1.0f, false); break;
            case "awp":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.AWP, SoundCategory.PLAYERS, 0.25f, 1.0f, false); break;
            case "awp_reload":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.AWP_RELOAD, SoundCategory.PLAYERS, 1f, 1.0f, false); break;
            case "barrett":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.BARRETT, SoundCategory.PLAYERS, 1f, 1.0f, false); break;
            case "m4a4":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.M4A4, SoundCategory.PLAYERS, 1f, 1.0f, false); break;
            case "m4a4_reload":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.M4A4_RELOAD, SoundCategory.PLAYERS, 1f, 1.0f, false); break;
            case "xm1014":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.XM1014, SoundCategory.PLAYERS, 0.25f, 1.0f, false); break;
            case "xm1014_reload":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.XM1014_RELOAD, SoundCategory.PLAYERS, 1.5f, 1.0f, false); break;
            case "nova":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.NOVA, SoundCategory.PLAYERS, 0.25f, 1.0f, false); break;
            case "p90":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.P90, SoundCategory.PLAYERS, 1f, 1.0f, false); break;
            case "p90_reload":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.P90_RELOAD, SoundCategory.PLAYERS, 1f, 1.0f, false); break;
            case "defusing":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.DEFUSING, SoundCategory.PLAYERS, 5f, 1.0f, false); break;
            case "defused":
                world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventList.DEFUSED, SoundCategory.PLAYERS, 1f, 1.0f, false); break;
        }
    }
}
