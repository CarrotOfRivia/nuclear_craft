package com.song.nuclear_craft.entities;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.entity.projectile.ProjectileEntity;

public class EntityList {
    public static final EntityType<? extends AtomicBombEntity> ATOMIC_BOMB_ENTITY = EntityType.Builder.<AtomicBombEntity>create(AtomicBombEntity::new, EntityClassification.MISC).build("atomic_bomb");

    // Bullets
//    public static final EntityType<? extends EggEntity> BULLET_ENTITY = EntityType.Builder.<EggEntity>create(AbstractAmmoEntity::new, EntityClassification.MISC).
//            size(0.5f, 0.5f).func_233606_a_(4).func_233608_b_(10).setCustomClientFactory(AbstractAmmoEntity::new).build("bullet_entity");

    static {
        ATOMIC_BOMB_ENTITY.setRegistryName(NuclearCraft.MODID, "atomic_bomb");
//        BULLET_ENTITY.setRegistryName(NuclearCraft.MODID, "bullet_entity");
    }
}
