package com.song.nuclear_craft.entities.AmmoEntities;

import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.misc.ConfigCommon;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;

public class AmmoTungstenEntity extends AbstractAmmoEntity {

    public AmmoTungstenEntity(EntityType<? extends AbstractAmmoEntity> type, Level world) {
        super(type, world);
    }

    public AmmoTungstenEntity(PlayMessages.SpawnEntity entity, Level world) {
        super(entity, world);
    }

    public AmmoTungstenEntity(double x, double y, double z, Level world, ItemStack itemStack, Player shooter) {
        super(x, y, z, world, itemStack, shooter);
    }

    @Override
    public double getBlockBreakThreshold() {
        return ConfigCommon.AMMO_TUNGSTEN_BLOCK_BREAK_THRESHOLD.get();
    }

    @Override
    public double getRicochetEnergyLoss() {
        return ConfigCommon.AMMO_TUNGSTEN_RICOCHET_LOSS.get();
    }
}
