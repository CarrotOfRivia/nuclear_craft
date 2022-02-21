package com.song.nuclear_craft.blocks.container;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;

public class ContainerTypeList {
    public static final MenuType<C4BombContainer> C4_BOMB_CONTAINER_TYPE = IForgeMenuType.create(C4BombContainer::new);

    static {
        C4_BOMB_CONTAINER_TYPE.setRegistryName(NuclearCraft.MODID, "c4_bomb_container_type");
    }
}
