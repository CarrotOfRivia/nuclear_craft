package com.song.nuclear_craft.blocks.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class HighExplosiveTileEntity extends C4BombTileEntity{
    public HighExplosiveTileEntity() {
        super(TileEntityRegister.C4_HIGH_EXPLOSIVE_TE_TYPE);
    }

    public HighExplosiveTileEntity(TileEntityType<?> tileEntityTypeIn, int explode_time) {
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
