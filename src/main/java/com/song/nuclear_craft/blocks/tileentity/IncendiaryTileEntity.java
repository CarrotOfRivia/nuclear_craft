package com.song.nuclear_craft.blocks.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class IncendiaryTileEntity extends C4BombTileEntity{
    public IncendiaryTileEntity(BlockPos blockPos, BlockState blockState) {
        super(TileEntityRegister.C4_INCENDIARY_TE_TYPE.get(), blockPos, blockState);
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
