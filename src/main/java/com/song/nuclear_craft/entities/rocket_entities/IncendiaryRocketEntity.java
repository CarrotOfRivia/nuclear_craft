package com.song.nuclear_craft.entities.rocket_entities;

import com.song.nuclear_craft.misc.Config;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class IncendiaryRocketEntity extends FireworkRocketEntity {
    public static int N_FIRE_BALLS = Config.INCENDIARY_COUNT.get();
    public IncendiaryRocketEntity(EntityType<? extends FireworkRocketEntity> p_i50164_1_, World p_i50164_2_) {
        super(p_i50164_1_, p_i50164_2_);
    }

    public IncendiaryRocketEntity(World worldIn, double x, double y, double z, ItemStack givenItem) {
        super(worldIn, x, y, z, givenItem);
    }

    public IncendiaryRocketEntity(World p_i231581_1_, @Nullable Entity p_i231581_2_, double p_i231581_3_, double p_i231581_5_, double p_i231581_7_, ItemStack p_i231581_9_) {
        super(p_i231581_1_, p_i231581_2_, p_i231581_3_, p_i231581_5_, p_i231581_7_, p_i231581_9_);
    }

    public IncendiaryRocketEntity(World p_i47367_1_, ItemStack p_i47367_2_, LivingEntity p_i47367_3_) {
        super(p_i47367_1_, p_i47367_2_, p_i47367_3_);
    }

    public IncendiaryRocketEntity(World p_i50165_1_, ItemStack p_i50165_2_, double p_i50165_3_, double p_i50165_5_, double p_i50165_7_, boolean p_i50165_9_) {
        super(p_i50165_1_, p_i50165_2_, p_i50165_3_, p_i50165_5_, p_i50165_7_, p_i50165_9_);
    }

    public IncendiaryRocketEntity(World p_i231582_1_, ItemStack p_i231582_2_, Entity p_i231582_3_, double p_i231582_4_, double p_i231582_6_, double p_i231582_8_, boolean p_i231582_10_) {
        super(p_i231582_1_, p_i231582_2_, p_i231582_3_, p_i231582_4_, p_i231582_6_, p_i231582_8_, p_i231582_10_);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        this.remove();
        if (! world.isRemote){
            double validY = getValidY(world, this.getPosX(), this.getPosY(), this.getPosZ());
            fireExplode(world, this.getPosX(), validY, this.getPosZ());
        }
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult blockRayTraceResult) {
        this.remove();
        if(! world.isRemote){
            double validY = getValidY(world, this.getPosX(), this.getPosY(), this.getPosZ());
            fireExplode(world, this.getPosX(), validY, this.getPosZ());
        }
    }

    public static void fireExplode(World world, double x, double y, double z) {
        fireExplode(world, x, y, z, N_FIRE_BALLS);
    }

    public static void fireExplode(World world, double x, double y, double z, int maxBalls){
        Random random = new Random();
        world.playSound(null, x, y, z, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.BLOCKS, 10f, 0.3f);
        for(int i=0; i<maxBalls; i++){
            double r = random.nextDouble() + 5;
            double theta = random.nextDouble() * 3.14 * 2;
            double phi = random.nextDouble() * 1.36 + 1.76;
            world.addEntity(new SmallFireballEntity(world, x, y, z, r*Math.cos(theta)*Math.sin(phi), r*Math.cos(phi), r*Math.sin(theta)*Math.sin(phi)));
        }
    }

    public static double getValidY(World world, double x, double y, double z){
        for (double deltaY=5; deltaY > -5; deltaY--){
            if (world.getBlockState(new BlockPos(x, y+deltaY, z)) == Blocks.AIR.getDefaultState()){
                return y+deltaY;
            }
        }
        return y+5;
    }
}
