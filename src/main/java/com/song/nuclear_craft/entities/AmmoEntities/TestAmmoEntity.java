package com.song.nuclear_craft.entities.AmmoEntities;

import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class TestAmmoEntity extends AbstractAmmoEntity {


    public TestAmmoEntity(EntityType<? extends AbstractAmmoEntity> type, World world) {
        super(type, world);
    }

    public TestAmmoEntity(FMLPlayMessages.SpawnEntity entity, World world) {
        super(entity, world);
    }

    public TestAmmoEntity(double x, double y, double z, World world, ItemStack itemStack, PlayerEntity shooter) {
        super(x, y, z, world, itemStack, shooter);
    }

    @Override
    protected double getEnergy(double speed) {
        return 20f;
    }
}
