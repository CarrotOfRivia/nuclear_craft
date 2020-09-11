package com.song.nuclear_craft.entities;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.AmmoEntities.AmmoAntiGravityEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public class EntityList {
    public static final EntityType<? extends AtomicBombEntity> ATOMIC_BOMB_ENTITY = EntityType.Builder.<AtomicBombEntity>create(AtomicBombEntity::new, EntityClassification.MISC).build(new ResourceLocation(NuclearCraft.MODID, "atomic_bomb").toString());

    // Bullets
    public static final EntityType<? extends AbstractAmmoEntity> BULLET_ENTITY = EntityType.Builder.<AbstractAmmoEntity>create(AbstractAmmoEntity::new, EntityClassification.MISC).
            size(0.1f, 0.1f).func_233606_a_(4).func_233608_b_(10).setCustomClientFactory(AbstractAmmoEntity::new).build(new ResourceLocation(NuclearCraft.MODID, "anti_gravity_ammo").toString());

    static {
        ATOMIC_BOMB_ENTITY.setRegistryName(NuclearCraft.MODID, "atomic_bomb");
        BULLET_ENTITY.setRegistryName(NuclearCraft.MODID, "anti_gravity_ammo");
    }
}
