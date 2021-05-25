package com.song.nuclear_craft.items.defuse_kit;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.ConfigCommon;

public class GoldDefuseKit extends DefuseKit{
    public GoldDefuseKit() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP).maxDamage(1));
    }

    @Override
    public int getDefuseTick() {
        return ConfigCommon.GOLD_DEFUSE_KIT_TIME.get();
    }
}
