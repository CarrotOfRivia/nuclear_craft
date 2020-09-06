package com.song.nuclear_craft.entities;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.TNTEntity;

public class EntityList {
    public static final EntityType<? extends AtomicBombEntity> ATOMIC_BOMB_ENTITY = (EntityType<? extends AtomicBombEntity>) EntityType.Builder.<AtomicBombEntity>create(AtomicBombEntity::new, EntityClassification.MISC).build("atomic_bomb").setRegistryName(NuclearCraft.MODID, "atomic_bomb");
}
