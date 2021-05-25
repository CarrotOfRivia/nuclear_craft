package com.song.nuclear_craft.items.defuse_kit;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.ConfigCommon;

public class NetheriteDefuseKit extends DefuseKit{
    public NetheriteDefuseKit() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP).maxDamage(500));
    }

    @Override
    public int getDefuseTick() {
        return ConfigCommon.NETHERITE_DEFUSE_KIT_TIME.get();
    }
}
