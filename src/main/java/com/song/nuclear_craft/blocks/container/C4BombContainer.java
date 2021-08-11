package com.song.nuclear_craft.blocks.container;

import com.song.nuclear_craft.blocks.tileentity.C4BombTileEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;

import java.util.Objects;

public class C4BombContainer extends AbstractContainerMenu {
    public final C4BombTileEntity tileEntity;
//    private final IWorldPosCallable canInteractWithCallable;

    public C4BombContainer(int windowId, Inventory playerInventory, C4BombTileEntity tileEntity){
        super(ContainerTypeList.C4_BOMB_CONTAINER_TYPE, windowId);
        this.tileEntity = tileEntity;
//        this.canInteractWithCallable = IWorldPosCallable.of(Objects.requireNonNull(tileEntity.getWorld()), tileEntity.getPos());
    }

    public C4BombContainer(int windowId, Inventory playerInventory, FriendlyByteBuf data){
        this(windowId, playerInventory, getTEntity(playerInventory, data));
    }

    public static C4BombTileEntity getTEntity(Inventory playerInventory, FriendlyByteBuf data){
        Objects.requireNonNull(playerInventory, "playerInv cannot be null");
        if(data == null){
            return null;
        }
        BlockPos blockPos = data.readBlockPos();
        BlockEntity entity = playerInventory.player.level.getBlockEntity(blockPos);
        if (entity instanceof C4BombTileEntity){
            return (C4BombTileEntity)entity;
        }else {
            throw new IllegalStateException("tile entity is not correct at" + blockPos);
        }
    }

    @Override
    public boolean stillValid(Player playerIn) {
        // TODO
//        return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockList.C4_ATOMIC_BOMB);
        return true;
    }
}
