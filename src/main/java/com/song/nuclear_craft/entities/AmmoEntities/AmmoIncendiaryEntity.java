package com.song.nuclear_craft.entities.AmmoEntities;

import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.entities.rocket_entities.IncendiaryRocketEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.fmllegacy.network.FMLPlayMessages;

import javax.annotation.Nonnull;

public class AmmoIncendiaryEntity extends AbstractAmmoEntity {

    public AmmoIncendiaryEntity(EntityType<? extends AbstractAmmoEntity> type, Level world) {
        super(type, world);
    }

    public AmmoIncendiaryEntity(FMLPlayMessages.SpawnEntity entity, Level world) {
        super(entity, world);
    }

    public AmmoIncendiaryEntity(double x, double y, double z, Level world, ItemStack itemStack, Player shooter) {
        super(x, y, z, world, itemStack, shooter);
    }

    @Override
    protected void onHitEntity(@Nonnull EntityHitResult entityRayTraceResult) {
        super.onHitEntity(entityRayTraceResult);
        Entity entity = entityRayTraceResult.getEntity();
        entity.setSecondsOnFire(10);
    }

    @Override
    protected void onHitBlock(@Nonnull BlockHitResult blockRayTraceResult) {
        super.onHitBlock(blockRayTraceResult);
        if(!level.isClientSide){
            IncendiaryRocketEntity.fireExplode(level, this.getX(), this.getY(), this.getZ(), 5);
        }
        this.setRemoved(RemovalReason.KILLED);
    }
}
