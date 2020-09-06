package com.song.nuclear_craft.blocks.container;

import com.song.nuclear_craft.blocks.BlockList;
import com.song.nuclear_craft.blocks.C4BombTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Objects;

public class C4BombContainer extends Container {
    public final C4BombTileEntity tileEntity;
//    private final IWorldPosCallable canInteractWithCallable;

    public C4BombContainer(int windowId, PlayerInventory playerInventory, C4BombTileEntity tileEntity){
        super(ContainerTypeList.C4_BOMB_CONTAINER_TYPE, windowId);
        this.tileEntity = tileEntity;
//        this.canInteractWithCallable = IWorldPosCallable.of(Objects.requireNonNull(tileEntity.getWorld()), tileEntity.getPos());
    }

    public C4BombContainer(int windowId, PlayerInventory playerInventory, PacketBuffer data){
        // TODO data is null
        this(windowId, playerInventory, getTEntity(playerInventory, data));
    }

    public static C4BombTileEntity getTEntity(PlayerInventory playerInventory, PacketBuffer data){
        Objects.requireNonNull(playerInventory, "playerInv cannot be null");
        if(data == null){
            return null;
        }
        BlockPos blockPos = data.readBlockPos();
        TileEntity entity = playerInventory.player.world.getTileEntity(blockPos);
        if (!(entity instanceof C4BombTileEntity)){
            return (C4BombTileEntity)entity;
        }else {
            throw new IllegalStateException("tile entity is not correct at" + blockPos);
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        // TODO
//        return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockList.C4_ATOMIC_BOMB);
        return true;
    }
}
