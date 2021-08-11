package com.song.nuclear_craft.entities.containers;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import javax.annotation.Nullable;
import java.awt.*;

public class UraniumCentrifugeContainer extends AbstractContainerMenu {

    protected UraniumCentrifugeContainer(@Nullable MenuType<?> type, int id) {
        super(type, id);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }
}
