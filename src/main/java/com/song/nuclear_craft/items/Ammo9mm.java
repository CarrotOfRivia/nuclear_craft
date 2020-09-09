package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;

public class Ammo9mm extends AbstractAmmo{
    public static final Size size= Size.S_9MM;
    public Ammo9mm() {
        super(new Properties().group(NuclearCraft.ITEM_GROUP));
    }
}
