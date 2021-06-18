package com.song.nuclear_craft.misc;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import net.minecraft.util.ResourceLocation;

public class Util {
    public static ResourceLocation getResource(String s){
        return new ResourceLocation(NuclearCraft.MODID, s);
    }

    public static String getResourceString(String s){
        return getResource(s).toString();
    }

    public static String getAmmoRegisterString(AmmoSize size, AmmoType type){
        // return register id without mod id
        return "ammo_"+size.getRegisterString()+"_"+type.getRegisterString();
    }
}
