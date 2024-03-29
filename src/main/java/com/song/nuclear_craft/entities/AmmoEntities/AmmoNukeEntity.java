package com.song.nuclear_craft.entities.AmmoEntities;

import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.entities.ExplosionUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.PlayMessages;

public class AmmoNukeEntity extends AbstractAmmoEntity {

    public AmmoNukeEntity(EntityType<? extends AbstractAmmoEntity> type, Level world) {
        super(type, world);
    }

    public AmmoNukeEntity(PlayMessages.SpawnEntity entity, Level world) {
        super(entity, world);
    }

    public AmmoNukeEntity(double x, double y, double z, Level world, ItemStack itemStack, Player shooter) {
        super(x, y, z, world, itemStack, shooter);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if(result.getType()== HitResult.Type.ENTITY && ((EntityHitResult)result).getEntity() instanceof AbstractAmmoEntity){
            return;
        }
        teleportToHitPoint(result);
        if (!level.isClientSide){
            ExplosionUtils.oldNukeExplode(level, this, this.getX(), this.getY(), this.getZ(), 15, true, 20);
        }
        this.setRemoved(RemovalReason.KILLED);
    }
}
