package com.song.nuclear_craft.items.defuse_kit;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.ConfigCommon;

public class DiamondDefuseKit extends DefuseKit{
    public DiamondDefuseKit() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP).maxDamage(150));
    }

    @Override
    public int getDefuseTick() {
        return ConfigCommon.DIAMOND_DEFUSE_KIT_TIME.get();
    }
}
