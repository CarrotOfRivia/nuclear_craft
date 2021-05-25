package com.song.nuclear_craft.items.defuse_kit;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.ConfigCommon;
import net.minecraft.item.ItemTier;

public class WoodDefuseKit extends DefuseKit{
    public WoodDefuseKit() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP).maxDamage(5));
    }

    @Override
    public int getDefuseTick() {
        return ConfigCommon.WOOD_DEFUSE_KIT_TIME.get();
    }
}
