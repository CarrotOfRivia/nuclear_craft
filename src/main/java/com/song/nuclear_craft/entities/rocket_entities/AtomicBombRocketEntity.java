package com.song.nuclear_craft.entities.rocket_entities;

import com.song.nuclear_craft.entities.ExplosionUtils;
import com.song.nuclear_craft.entities.NukeExplosionHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class AtomicBombRocketEntity extends FireworkRocketEntity {
    private LivingEntity boostedEntity;
    public AtomicBombRocketEntity(EntityType<? extends FireworkRocketEntity> p_i50164_1_, Level p_i50164_2_) {
        super(p_i50164_1_, p_i50164_2_);
    }

    public AtomicBombRocketEntity(Level worldIn, double x, double y, double z, ItemStack givenItem) {
        super(worldIn, x, y, z, givenItem);;
    }

    public AtomicBombRocketEntity(Level p_i231581_1_, @Nullable Entity p_i231581_2_, double p_i231581_3_, double p_i231581_5_, double p_i231581_7_, ItemStack p_i231581_9_) {
        super(p_i231581_1_, p_i231581_2_, p_i231581_3_, p_i231581_5_, p_i231581_7_, p_i231581_9_);
    }

    public AtomicBombRocketEntity(Level p_i47367_1_, ItemStack p_i47367_2_, LivingEntity boostedEntity) {
        super(p_i47367_1_, p_i47367_2_, boostedEntity);
        this.boostedEntity = boostedEntity;
    }

    public AtomicBombRocketEntity(Level p_i50165_1_, ItemStack p_i50165_2_, double p_i50165_3_, double p_i50165_5_, double p_i50165_7_, boolean p_i50165_9_) {
        super(p_i50165_1_, p_i50165_2_, p_i50165_3_, p_i50165_5_, p_i50165_7_, p_i50165_9_);
    }

    public AtomicBombRocketEntity(Level p_i231582_1_, ItemStack p_i231582_2_, Entity p_i231582_3_, double p_i231582_4_, double p_i231582_6_, double p_i231582_8_, boolean p_i231582_10_) {
        super(p_i231582_1_, p_i231582_2_, p_i231582_3_, p_i231582_4_, p_i231582_6_, p_i231582_8_, p_i231582_10_);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityRayTraceResult) {
        this.explode();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockRayTraceResult) {
        this.explode();
    }

    private void explode(){
        this.setRemoved(RemovalReason.KILLED);
        if (! this.level.isClientSide) {
//            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->AtomicBombEntity.nukeExplode(world, this.boostedEntity, this.getPosX(), this.getPosY(), this.getPosZ(), AtomicBombEntity.NUKE_RADIUS, true));
//            ExplosionUtils.oldNukeExplode(world, this.boostedEntity, this.getPosX(), this.getPosY(), this.getPosZ(), ExplosionUtils.NUKE_RADIUS, true);
            this.level.addFreshEntity(new NukeExplosionHandler(this.getX(), this.getY(), this.getZ(), this.level));
        }
    }
}
