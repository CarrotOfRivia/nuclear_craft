package com.song.nuclear_craft.items.defuse_kit;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.ConfigCommon;

import net.minecraft.world.item.Item.Properties;

public class IronDefuseKit extends DefuseKit{
    public IronDefuseKit() {
        super(new Properties().stacksTo(1).tab(NuclearCraft.ITEM_GROUP).durability(20));
    }

    @Override
    public int getDefuseTick() {
        return ConfigCommon.IRON_DEFUSE_KIT_TIME.get();
    }
}
