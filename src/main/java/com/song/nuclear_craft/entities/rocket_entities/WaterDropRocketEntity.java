package com.song.nuclear_craft.entities.rocket_entities;

import com.song.nuclear_craft.entities.ExplosionUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

import java.util.List;

public class WaterDropRocketEntity extends FireworkRocketEntity {
    private int meltCD = 7;
    public WaterDropRocketEntity(World p_i231582_1_, ItemStack p_i231582_2_, Entity p_i231582_3_, double p_i231582_4_, double p_i231582_6_, double p_i231582_8_, boolean p_i231582_10_) {
        super(p_i231582_1_, p_i231582_2_, p_i231582_3_, p_i231582_4_, p_i231582_6_, p_i231582_8_, p_i231582_10_);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult rayTraceResult) {
        Vector3d hitPoint = rayTraceResult.getHitVec();
        meltDown(world, hitPoint.x, hitPoint.y, hitPoint.z);
        meltCD=2;
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult rayTraceResult) {
        Vector3d hitPoint = rayTraceResult.getHitVec();
        meltDown(world, hitPoint.x, hitPoint.y, hitPoint.z);
        meltCD=2;
    }

    @Override
    public void tick() {
        super.tick();
        if(--meltCD == 0){
            meltDown(world, this.getPosX(), this.getPosY(), this.getPosZ());
            meltCD = 2;
        }

        if(!world.isRemote){
            if(this.getPosY() > 300 && this.getMotion().getY()>0){
                this.remove();
            }
        }
    }

    private void meltDown(World world, double x, double y, double z){
        if(! world.isRemote){
            float radius = 10f;
            List<BlockPos> affectedBlockPositions = ExplosionUtils.getAffectedBlockPositions(world, x, y, z, radius, 3600002);
            for(BlockPos blockPos: affectedBlockPositions){
                if(blockPos.withinDistance(new Vector3i(x, y, z), radius/4)){
                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                    continue;
                }
                Block block = world.getBlockState(blockPos).getBlock();
                if(block == Blocks.STONE){
                    world.setBlockState(blockPos, Blocks.BLACKSTONE.getDefaultState());
                }
                else if(BlockTags.LOGS.contains(block)){
                    world.setBlockState(blockPos, Blocks.COAL_BLOCK.getDefaultState());
                }
                else if(block == Blocks.BEDROCK){
                    world.setBlockState(blockPos, Blocks.OBSIDIAN.getDefaultState());
                }
                else {
                    if(this.rand.nextFloat()<0.95){
                        world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                    }
                    else {
                        world.setBlockState(blockPos, Blocks.LAVA.getDefaultState().with(BlockStateProperties.LEVEL_0_15, 15));
                    }
                }
            }
        }
    }
}
