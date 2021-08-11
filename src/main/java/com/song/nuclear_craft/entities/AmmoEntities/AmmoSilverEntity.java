package com.song.nuclear_craft.entities.AmmoEntities;

import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.misc.ConfigCommon;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.FMLPlayMessages;

public class AmmoSilverEntity extends AbstractAmmoEntity {

    public AmmoSilverEntity(EntityType<? extends AbstractAmmoEntity> type, Level world) {
        super(type, world);
    }

    public AmmoSilverEntity(FMLPlayMessages.SpawnEntity entity, Level world) {
        super(entity, world);
    }

    public AmmoSilverEntity(double x, double y, double z, Level world, ItemStack itemStack, Player shooter) {
        super(x, y, z, world, itemStack, shooter);
    }

    @Override
    public double getBlockBreakThreshold() {
        return ConfigCommon.AMMO_SILVER_BLOCK_BREAK_THRESHOLD.get();
    }

    @Override
    public double getRicochetEnergyLoss() {
        return ConfigCommon.AMMO_SILVER_RICOCHET_LOSS.get();
    }
}
