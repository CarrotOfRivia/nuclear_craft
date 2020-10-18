package com.song.nuclear_craft.entities.AmmoEntities;

import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.entities.AtomicBombEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class AmmoNukeEntity extends AbstractAmmoEntity {

    public AmmoNukeEntity(EntityType<? extends AbstractAmmoEntity> type, World world) {
        super(type, world);
    }

    public AmmoNukeEntity(FMLPlayMessages.SpawnEntity entity, World world) {
        super(entity, world);
    }

    public AmmoNukeEntity(double x, double y, double z, World world, ItemStack itemStack, PlayerEntity shooter) {
        super(x, y, z, world, itemStack, shooter);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        teleportToHitPoint(result);
        if (!world.isRemote){
            AtomicBombEntity.nukeExplode(world, this, this.getPosX(), this.getPosY(), this.getPosZ(), 15, true, 20);
        }
        this.remove();
    }
}
