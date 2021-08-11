package com.song.nuclear_craft.entities.rocket_entities;

import com.song.nuclear_craft.misc.ConfigCommon;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Random;

public class IncendiaryRocketEntity extends FireworkRocketEntity {
    public static int N_FIRE_BALLS = ConfigCommon.INCENDIARY_COUNT.get();
    public IncendiaryRocketEntity(EntityType<? extends FireworkRocketEntity> p_i50164_1_, Level p_i50164_2_) {
        super(p_i50164_1_, p_i50164_2_);
    }

    public IncendiaryRocketEntity(Level worldIn, double x, double y, double z, ItemStack givenItem) {
        super(worldIn, x, y, z, givenItem);
    }

    public IncendiaryRocketEntity(Level p_i231581_1_, @Nullable Entity p_i231581_2_, double p_i231581_3_, double p_i231581_5_, double p_i231581_7_, ItemStack p_i231581_9_) {
        super(p_i231581_1_, p_i231581_2_, p_i231581_3_, p_i231581_5_, p_i231581_7_, p_i231581_9_);
    }

    public IncendiaryRocketEntity(Level p_i47367_1_, ItemStack p_i47367_2_, LivingEntity p_i47367_3_) {
        super(p_i47367_1_, p_i47367_2_, p_i47367_3_);
    }

    public IncendiaryRocketEntity(Level p_i50165_1_, ItemStack p_i50165_2_, double p_i50165_3_, double p_i50165_5_, double p_i50165_7_, boolean p_i50165_9_) {
        super(p_i50165_1_, p_i50165_2_, p_i50165_3_, p_i50165_5_, p_i50165_7_, p_i50165_9_);
    }

    public IncendiaryRocketEntity(Level p_i231582_1_, ItemStack p_i231582_2_, Entity p_i231582_3_, double p_i231582_4_, double p_i231582_6_, double p_i231582_8_, boolean p_i231582_10_) {
        super(p_i231582_1_, p_i231582_2_, p_i231582_3_, p_i231582_4_, p_i231582_6_, p_i231582_8_, p_i231582_10_);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityRayTraceResult) {
        this.setRemoved(RemovalReason.KILLED);
        if (! level.isClientSide){
            double validY = getValidY(level, this.getX(), this.getY(), this.getZ());
            fireExplode(level, this.getX(), validY, this.getZ());
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockRayTraceResult) {
        this.setRemoved(RemovalReason.KILLED);
        if(! level.isClientSide){
            double validY = getValidY(level, this.getX(), this.getY(), this.getZ());
            fireExplode(level, this.getX(), validY, this.getZ());
        }
    }

    public static void fireExplode(Level world, double x, double y, double z) {
        fireExplode(world, x, y, z, N_FIRE_BALLS);
    }

    public static void fireExplode(Level world, double x, double y, double z, int maxBalls){
        Random random = new Random();
        world.playSound(null, x, y, z, SoundEvents.BLAZE_SHOOT, SoundSource.BLOCKS, 10f, 0.3f);
        for(int i=0; i<maxBalls; i++){
            double r = random.nextDouble() + 5;
            double theta = random.nextDouble() * 3.14 * 2;
            double phi = random.nextDouble() * 1.36 + 1.76;
            world.addFreshEntity(new SmallFireball(world, x, y, z, r*Math.cos(theta)*Math.sin(phi), r*Math.cos(phi), r*Math.sin(theta)*Math.sin(phi)));
        }
    }

    public static double getValidY(Level world, double x, double y, double z){
        for (double deltaY=5; deltaY > -5; deltaY--){
            if (world.getBlockState(new BlockPos(x, y+deltaY, z)) == Blocks.AIR.defaultBlockState()){
                return y+deltaY;
            }
        }
        return y+5;
    }
}
