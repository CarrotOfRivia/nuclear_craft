package com.song.nuclear_craft.blocks.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class HighExplosiveTileEntity extends C4BombTileEntity{


    public HighExplosiveTileEntity(BlockPos blockPos, BlockState blockState) {
        super(TileEntityRegister.C4_HIGH_EXPLOSIVE_TE_TYPE.get(), blockPos, blockState);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        return super.save(compound);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
    }
}
