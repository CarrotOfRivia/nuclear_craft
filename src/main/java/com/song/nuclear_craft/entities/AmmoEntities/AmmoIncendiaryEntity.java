package com.song.nuclear_craft.entities.AmmoEntities;

import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.entities.IncendiaryRocketEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nonnull;

public class AmmoIncendiaryEntity extends AbstractAmmoEntity {

    public AmmoIncendiaryEntity(EntityType<? extends AbstractAmmoEntity> type, World world) {
        super(type, world);
    }

    public AmmoIncendiaryEntity(FMLPlayMessages.SpawnEntity entity, World world) {
        super(entity, world);
    }

    public AmmoIncendiaryEntity(double x, double y, double z, World world, ItemStack itemStack, PlayerEntity shooter) {
        super(x, y, z, world, itemStack, shooter);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        super.onEntityHit(entityRayTraceResult);
        Entity entity = entityRayTraceResult.getEntity();
        entity.setFire(10);
    }

    @Override
    protected void func_230299_a_(@Nonnull BlockRayTraceResult blockRayTraceResult) {
        super.func_230299_a_(blockRayTraceResult);
        if(!world.isRemote){
            IncendiaryRocketEntity.fireExplode(world, this.getPosX(), this.getPosY(), this.getPosZ(), 5);
        }
        this.remove();
    }
}
