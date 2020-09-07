package com.song.nuclear_craft.blocks;

import com.song.nuclear_craft.blocks.tileentity.C4BombTileEntity;
import com.song.nuclear_craft.blocks.tileentity.TileEntityList;
import com.song.nuclear_craft.entities.IncendiaryRocketEntity;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class C4Incendiary extends C4Bomb{
    public static final int FUSE_TIME = 800;

    public C4Incendiary(Properties properties) {
        super(properties);
    }

    @Override
    public void explode(World world, double x, double y, double z) {
        IncendiaryRocketEntity.fireExplode(world, x, y, z);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new C4BombTileEntity(TileEntityList.C4_INCENDIARY_TE_TYPE, FUSE_TIME);
    }
}
