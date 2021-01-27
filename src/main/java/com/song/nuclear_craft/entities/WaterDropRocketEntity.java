package com.song.nuclear_craft.entities;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

import java.util.List;

public class WaterDropRocketEntity extends FireworkRocketEntity {
    private int meltCD = 7;
    private final int maxLife;
    private int age = 0;
    public WaterDropRocketEntity(World world, ItemStack itemStack, Entity entity, double v, double v1, double v2, boolean b) {
        super(world, itemStack, entity, v, v1, v2, b);
        this.maxLife = 10000;
    }

    public WaterDropRocketEntity(World world, ItemStack itemStack, Entity entity, double v, double v1, double v2, boolean b, int maxLife){
        super(world, itemStack, entity, v, v1, v2, b);
        this.maxLife = maxLife;
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
            age ++;
            if(this.getPosY() > 300 && this.getMotion().getY()>0){
                this.remove();
            }
            if(this.age > maxLife){
                this.remove();
            }
        }
    }

    private void meltDown(World world, double x, double y, double z){
        if(! world.isRemote){
            float radius = 10f;
            List<BlockPos> affectedBlockPositions = AtomicBombEntity.getAffectedBlockPositions(world, x, y, z, radius, 3600002);
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
