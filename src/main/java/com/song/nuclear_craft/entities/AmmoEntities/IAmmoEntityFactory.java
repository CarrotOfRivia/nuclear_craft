package com.song.nuclear_craft.entities.AmmoEntities;

import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IAmmoEntityFactory {
    AbstractAmmoEntity create(double x, double y, double z, World world, ItemStack toBeFired, PlayerEntity shooter);

    static IAmmoEntityFactory getAmmoEntityFactory(AmmoType ammoType){
        switch (ammoType){
            case NUKE:
                return AmmoNukeEntity::new;
            case ANTI_GRAVITY:
                return AmmoAntiGravityEntity::new;
            case SILVER:
                return AmmoSilverEntity::new;
            case TUNGSTEN:
                return AmmoTungstenEntity::new;
            case EXPLOSIVE:
                return AmmoExplosiveEntity::new;
            case INCENDIARY:
                return AmmoIncendiaryEntity::new;
            case SHORT_GUN_NORMAL:
                return AmmoBirdShotNormalEntity::new;
            case SHORT_GUN_BLIGHT:
                return AmmoBirdShotBlighEntity::new;
            case SHORT_GUN_DESOLATOR:
                return AmmoBirdShotDesolatorEntity::new;
            case NORMAL:
            default:
                return AmmoNormalEntity::new;
        }
    }
}
