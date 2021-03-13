package com.song.nuclear_craft.entities.rocket_entities;

import com.song.nuclear_craft.entities.ExplosionUtils;
import com.song.nuclear_craft.entities.NukeExplosionHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AtomicBombRocketEntity extends FireworkRocketEntity {
    private LivingEntity boostedEntity;
    public AtomicBombRocketEntity(EntityType<? extends FireworkRocketEntity> p_i50164_1_, World p_i50164_2_) {
        super(p_i50164_1_, p_i50164_2_);
    }

    public AtomicBombRocketEntity(World worldIn, double x, double y, double z, ItemStack givenItem) {
        super(worldIn, x, y, z, givenItem);;
    }

    public AtomicBombRocketEntity(World p_i231581_1_, @Nullable Entity p_i231581_2_, double p_i231581_3_, double p_i231581_5_, double p_i231581_7_, ItemStack p_i231581_9_) {
        super(p_i231581_1_, p_i231581_2_, p_i231581_3_, p_i231581_5_, p_i231581_7_, p_i231581_9_);
    }

    public AtomicBombRocketEntity(World p_i47367_1_, ItemStack p_i47367_2_, LivingEntity boostedEntity) {
        super(p_i47367_1_, p_i47367_2_, boostedEntity);
        this.boostedEntity = boostedEntity;
    }

    public AtomicBombRocketEntity(World p_i50165_1_, ItemStack p_i50165_2_, double p_i50165_3_, double p_i50165_5_, double p_i50165_7_, boolean p_i50165_9_) {
        super(p_i50165_1_, p_i50165_2_, p_i50165_3_, p_i50165_5_, p_i50165_7_, p_i50165_9_);
    }

    public AtomicBombRocketEntity(World p_i231582_1_, ItemStack p_i231582_2_, Entity p_i231582_3_, double p_i231582_4_, double p_i231582_6_, double p_i231582_8_, boolean p_i231582_10_) {
        super(p_i231582_1_, p_i231582_2_, p_i231582_3_, p_i231582_4_, p_i231582_6_, p_i231582_8_, p_i231582_10_);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        this.explode();
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult blockRayTraceResult) {
        this.explode();
    }

    private void explode(){
        this.remove();
        if (! this.world.isRemote) {
//            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->AtomicBombEntity.nukeExplode(world, this.boostedEntity, this.getPosX(), this.getPosY(), this.getPosZ(), AtomicBombEntity.NUKE_RADIUS, true));
//            ExplosionUtils.oldNukeExplode(world, this.boostedEntity, this.getPosX(), this.getPosY(), this.getPosZ(), ExplosionUtils.NUKE_RADIUS, true);
            this.world.addEntity(new NukeExplosionHandler(this.getPosX(), this.getPosY(), this.getPosZ(), this.world));
        }
    }
}
