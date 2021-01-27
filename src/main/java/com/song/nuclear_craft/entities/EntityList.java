package com.song.nuclear_craft.entities;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.AmmoEntities.AmmoAntiGravityEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityList {
//    public static final EntityType<? extends AtomicBombEntity> ATOMIC_BOMB_ENTITY = EntityType.Builder.<AtomicBombEntity>create(AtomicBombEntity::new, EntityClassification.MISC).build(new ResourceLocation(NuclearCraft.MODID, "atomic_bomb").toString());

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, NuclearCraft.MODID);
    // Bullets

    public static final RegistryObject<EntityType<AbstractAmmoEntity>> BULLET_ENTITY = ENTITIES.register("anti_gravity_ammo", ()->EntityType.Builder.<AbstractAmmoEntity>create(AbstractAmmoEntity::new, EntityClassification.MISC).
            size(0.1f, 0.1f).setCustomClientFactory(AbstractAmmoEntity::new).build(new ResourceLocation(NuclearCraft.MODID, "anti_gravity_ammo").toString()));

}
