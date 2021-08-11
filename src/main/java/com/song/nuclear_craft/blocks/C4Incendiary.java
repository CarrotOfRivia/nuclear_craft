package com.song.nuclear_craft.blocks;

import com.song.nuclear_craft.blocks.tileentity.C4BombTileEntity;
import com.song.nuclear_craft.blocks.tileentity.IncendiaryTileEntity;
import com.song.nuclear_craft.blocks.tileentity.TileEntityRegister;
import com.song.nuclear_craft.entities.rocket_entities.IncendiaryRocketEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class C4Incendiary extends C4Bomb{
    public static final int FUSE_TIME = 800;

    public C4Incendiary(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new IncendiaryTileEntity(blockPos, blockState);
    }

    @Override
    public void explode(Level world, double x, double y, double z) {
        y = IncendiaryRocketEntity.getValidY(world, x, y, z);
        IncendiaryRocketEntity.fireExplode(world, x, y, z);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, TileEntityRegister.C4_INCENDIARY_TE_TYPE.get(), C4BombTileEntity::tick);
    }

}
