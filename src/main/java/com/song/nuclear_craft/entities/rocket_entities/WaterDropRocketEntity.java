package com.song.nuclear_craft.entities.rocket_entities;

import com.song.nuclear_craft.entities.ExplosionUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;

import java.util.List;

public class WaterDropRocketEntity extends FireworkRocketEntity {
    private int meltCD = 7;
    private final int maxLife;
    private int age = 0;
    public WaterDropRocketEntity(Level world, ItemStack itemStack, Entity entity, double v, double v1, double v2, boolean b) {
        super(world, itemStack, entity, v, v1, v2, b);
        this.maxLife = 10000;
    }

    public WaterDropRocketEntity(Level world, ItemStack itemStack, Entity entity, double v, double v1, double v2, boolean b, int maxLife){
        super(world, itemStack, entity, v, v1, v2, b);
        this.maxLife = maxLife;
    }

    @Override
    protected void onHitEntity(EntityHitResult rayTraceResult) {
        Vec3 hitPoint = rayTraceResult.getLocation();
        meltDown(level, hitPoint.x, hitPoint.y, hitPoint.z);
        meltCD=2;
    }

    @Override
    protected void onHitBlock(BlockHitResult rayTraceResult) {
        Vec3 hitPoint = rayTraceResult.getLocation();
        meltDown(level, hitPoint.x, hitPoint.y, hitPoint.z);
        meltCD=2;
    }

    @Override
    public void tick() {
        super.tick();
        if(--meltCD == 0){
            meltDown(level, this.getX(), this.getY(), this.getZ());
            meltCD = 2;
        }

        if(!level.isClientSide){
            age ++;
            if(this.getY() > 300 && this.getDeltaMovement().y()>0){
                this.setRemoved(RemovalReason.KILLED);
            }
            if(this.age > maxLife){
                this.setRemoved(RemovalReason.KILLED);
            }
        }
    }

    private void meltDown(Level world, double x, double y, double z){
        if(! world.isClientSide){
            float radius = 10f;
            List<BlockPos> affectedBlockPositions = ExplosionUtils.getAffectedBlockPositions(world, x, y, z, radius, 3600002);
            for(BlockPos blockPos: affectedBlockPositions){
                if(blockPos.closerThan(new Vec3i(x, y, z), radius/4)){
                    world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                    continue;
                }
                BlockState blockState = world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if(block == Blocks.STONE){
                    world.setBlockAndUpdate(blockPos, Blocks.BLACKSTONE.defaultBlockState());
                }
                else if(blockState.is(BlockTags.LOGS)){
                    world.setBlockAndUpdate(blockPos, Blocks.COAL_BLOCK.defaultBlockState());
                }
                else if(block == Blocks.BEDROCK){
                    world.setBlockAndUpdate(blockPos, Blocks.OBSIDIAN.defaultBlockState());
                }
                else {
                    if(this.random.nextFloat()<0.95){
                        world.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                    }
                    else {
                        world.setBlockAndUpdate(blockPos, Blocks.LAVA.defaultBlockState().setValue(BlockStateProperties.LEVEL, 15));
                    }
                }
            }
        }
    }
}
