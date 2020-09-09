package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.item.Item;

public class DesertEagle extends AbstractGunItem{

    public DesertEagle() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
        coolDown=5;
    }

    @Override
    public Item getAmmo() {
        return ItemList.AMMO_9MM;
    }
}
