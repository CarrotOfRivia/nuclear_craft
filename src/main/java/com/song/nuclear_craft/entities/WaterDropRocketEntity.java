package com.song.nuclear_craft.entities;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class WaterDropRocketEntity extends FireworkRocketEntity {
    public WaterDropRocketEntity(World p_i231582_1_, ItemStack p_i231582_2_, Entity p_i231582_3_, double p_i231582_4_, double p_i231582_6_, double p_i231582_8_, boolean p_i231582_10_) {
        super(p_i231582_1_, p_i231582_2_, p_i231582_3_, p_i231582_4_, p_i231582_6_, p_i231582_8_, p_i231582_10_);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult rayTraceResult) {
        Vector3d hitPoint = rayTraceResult.getHitVec();
        meltDown(world, hitPoint.x, hitPoint.y, hitPoint.z, 10f);
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult rayTraceResult) {
        Vector3d hitPoint = rayTraceResult.getHitVec();
        meltDown(world, hitPoint.x, hitPoint.y, hitPoint.z, 10f);
    }

    private void meltDown(World world, double x, double y, double z, float radius){
        if(! world.isRemote){
            List<BlockPos> affectedBlockPositions = AtomicBombEntity.getAffectedBlockPositions(world, x, y, z, radius, 3600002);
            for(BlockPos blockPos: affectedBlockPositions){
                world.setBlockState(blockPos, Blocks.LAVA.getDefaultState().with(BlockStateProperties.LEVEL_0_15, 15));
            }
        }
    }
}
