package com.song.nuclear_craft.entities.AmmoEntities;

import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.FMLPlayMessages;

public class TestAmmoEntity extends AbstractAmmoEntity {


    public TestAmmoEntity(EntityType<? extends AbstractAmmoEntity> type, Level world) {
        super(type, world);
    }

    public TestAmmoEntity(FMLPlayMessages.SpawnEntity entity, Level world) {
        super(entity, world);
    }

    public TestAmmoEntity(double x, double y, double z, Level world, ItemStack itemStack, Player shooter) {
        super(x, y, z, world, itemStack, shooter);
    }

    @Override
    protected double getEnergy(double speed) {
        return 20f;
    }
}
