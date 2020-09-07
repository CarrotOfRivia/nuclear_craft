package com.song.nuclear_craft.blocks.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class IncendiaryTileEntity extends C4BombTileEntity{
    public IncendiaryTileEntity() {
        super(TileEntityList.C4_INCENDIARY_TE_TYPE);
    }

    public IncendiaryTileEntity(TileEntityType<?> tileEntityTypeIn, int explode_time) {
        super(tileEntityTypeIn, explode_time);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
    }
}
