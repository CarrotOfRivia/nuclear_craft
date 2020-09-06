package com.song.nuclear_craft.blocks;

import com.song.nuclear_craft.entities.AtomicBombEntity;
import com.song.nuclear_craft.entities.HighExplosiveRocketEntity;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class C4HighExplosive extends C4Bomb{
    public static final int FUSE_TIME = 800;

    public C4HighExplosive(Properties properties) {
        super(properties);
    }

    @Override
    public void explode(World world, double x, double y, double z) {
        AtomicBombEntity.nukeExplode(world, null, x, y, z, HighExplosiveRocketEntity.HIGH_EXPLOSIVE_RADIUS, false, HighExplosiveRocketEntity.MAX_BLAST_POWER);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new C4BombTileEntity(TileEntityList.C4_HIGH_EXPLOSIVE_TE_TYPE, FUSE_TIME);
    }
}
