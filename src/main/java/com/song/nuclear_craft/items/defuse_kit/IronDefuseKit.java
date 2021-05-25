package com.song.nuclear_craft.items.defuse_kit;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.ConfigCommon;

public class IronDefuseKit extends DefuseKit{
    public IronDefuseKit() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP).maxDamage(20));
    }

    @Override
    public int getDefuseTick() {
        return ConfigCommon.IRON_DEFUSE_KIT_TIME.get();
    }
}
