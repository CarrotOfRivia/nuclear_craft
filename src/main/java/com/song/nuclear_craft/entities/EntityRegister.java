package com.song.nuclear_craft.entities;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegister {
//    public static final EntityType<? extends AtomicBombEntity> ATOMIC_BOMB_ENTITY = EntityType.Builder.<AtomicBombEntity>create(AtomicBombEntity::new, EntityClassification.MISC).build(new ResourceLocation(NuclearCraft.MODID, "atomic_bomb").toString());

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, NuclearCraft.MODID);
    // Bullets

    public static final RegistryObject<EntityType<AbstractAmmoEntity>> BULLET_ENTITY = ENTITIES.register("anti_gravity_ammo", ()->EntityType.Builder.<AbstractAmmoEntity>of(AbstractAmmoEntity::new, MobCategory.MISC).
            sized(0.01f, 0.01f).setCustomClientFactory(AbstractAmmoEntity::new).build(new ResourceLocation(NuclearCraft.MODID, "anti_gravity_ammo").toString()));
//    public static final EntityType<? extends AbstractAmmoEntity> BULLET_ENTITY = EntityType.Builder.<AbstractAmmoEntity>create(AbstractAmmoEntity::new, EntityClassification.MISC).
//            size(0.1f, 0.1f).clientTrackingRange(4).updateInterval(10).setCustomClientFactory(AbstractAmmoEntity::new).build(null);

    public static final RegistryObject<EntityType<NukeExplosionHandler>> NUKE_EXPLOSION_HANDLER_TYPE = ENTITIES.register("nuke_explosion_handler", ()->EntityType.Builder.<NukeExplosionHandler>of(NukeExplosionHandler::new, MobCategory.MISC).
            sized(0.01f, 0.01f).build(new ResourceLocation(NuclearCraft.MODID, "nuke_explosion_handler").toString()));


}
