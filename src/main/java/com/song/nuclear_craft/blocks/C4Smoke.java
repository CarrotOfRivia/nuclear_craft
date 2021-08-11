package com.song.nuclear_craft.blocks;

import com.song.nuclear_craft.blocks.tileentity.C4BombTileEntity;
import com.song.nuclear_craft.blocks.tileentity.SmokeTileEntity;
import com.song.nuclear_craft.blocks.tileentity.TileEntityRegister;
import com.song.nuclear_craft.entities.rocket_entities.SmokeRocketEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class C4Smoke extends C4Bomb{
    public static final int FUSE_TIME = 800;

    public C4Smoke(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SmokeTileEntity(blockPos, blockState);
    }

    @Override
    public void explode(Level world, double x, double y, double z) {
        SmokeRocketEntity.sendSmokePacket(world, x, y, z);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, TileEntityRegister.C4_SMOKE_TE_TYPE.get(), C4BombTileEntity::tick);
    }

}
