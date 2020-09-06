package com.song.nuclear_craft.blocks.container;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class ContainerTypeList {
    public static final ContainerType<C4BombContainer> C4_BOMB_CONTAINER_TYPE = IForgeContainerType.create(C4BombContainer::new);

    static {
        C4_BOMB_CONTAINER_TYPE.setRegistryName(NuclearCraft.MODID, "c4_bomb_container_type");
    }
}
