package com.song.nuclear_craft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class AbstractAmmoEntity extends FireworkRocketEntity {
    private static final DataParameter<ItemStack> ITEM_STACK_DATA = EntityDataManager.createKey(ProjectileItemEntity.class, DataSerializers.ITEMSTACK);


    public AbstractAmmoEntity(double x, double y, double z, World world, ItemStack itemStack){
        super(world, itemStack, x, y, z, true);
    }


    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        Entity entity = entityRayTraceResult.getEntity();
        if (entity instanceof LivingEntity){
            ((LivingEntity) entity).setHealth(0f);
        }
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult blockRayTraceResult) {
        Direction blockDirection = blockRayTraceResult.getFace();
    }
}
