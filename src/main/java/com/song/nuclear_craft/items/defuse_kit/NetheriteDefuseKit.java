package com.song.nuclear_craft.items.defuse_kit;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.ConfigCommon;

import net.minecraft.world.item.Item.Properties;

public class NetheriteDefuseKit extends DefuseKit{
    public NetheriteDefuseKit() {
        super(new Properties().stacksTo(1).tab(NuclearCraft.ITEM_GROUP).durability(500));
    }

    @Override
    public int getDefuseTick() {
        return ConfigCommon.NETHERITE_DEFUSE_KIT_TIME.get();
    }
}
