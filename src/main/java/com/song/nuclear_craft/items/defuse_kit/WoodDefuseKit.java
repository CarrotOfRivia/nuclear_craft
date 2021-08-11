package com.song.nuclear_craft.items.defuse_kit;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.ConfigCommon;

public class WoodDefuseKit extends DefuseKit{
    public WoodDefuseKit() {
        super(new Properties().stacksTo(1).tab(NuclearCraft.ITEM_GROUP).durability(5));
    }

    @Override
    public int getDefuseTick() {
        return ConfigCommon.WOOD_DEFUSE_KIT_TIME.get();
    }
}
