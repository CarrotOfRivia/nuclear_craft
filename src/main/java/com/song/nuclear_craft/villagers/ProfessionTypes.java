package com.song.nuclear_craft.villagers;

import com.google.common.collect.ImmutableSet;
import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ProfessionTypes {
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSION = DeferredRegister.create(ForgeRegistries.PROFESSIONS, NuclearCraft.MODID);

    public static final RegistryObject<VillagerProfession> RIFLE_AMMO_SELLER_PROFESSION = VILLAGER_PROFESSION.register("rifle_ammo_seller",
            ()->new VillagerProfession("rifle_ammo_seller", PointOfInterestTypes.RIFLE_AMMO_SELLER.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_WEAPONSMITH));
    public static final RegistryObject<VillagerProfession> SHOTGUN_AMMO_SELLER_PROFESSION = VILLAGER_PROFESSION.register("shotgun_ammo_seller",
            ()->new VillagerProfession("shotgun_ammo_seller", PointOfInterestTypes.SHOTGUN_AMMO_SELLER.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_WEAPONSMITH));
    public static final RegistryObject<VillagerProfession> GUN_SELLER_PROFESSION = VILLAGER_PROFESSION.register("gun_seller",
            ()->new VillagerProfession("gun_seller", PointOfInterestTypes.GUN_SELLER.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_WEAPONSMITH));
    public static final RegistryObject<VillagerProfession> ROCKET_MASTER_PROFESSION = VILLAGER_PROFESSION.register("rocket_master",
            ()->new VillagerProfession("rocket_master", PointOfInterestTypes.ROCKET_MASTER.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_WEAPONSMITH));
    public static final RegistryObject<VillagerProfession> EXPLOSIVE_MASTER_PROFESSION = VILLAGER_PROFESSION.register("explosive_master",
            ()->new VillagerProfession("explosive_master", PointOfInterestTypes.EXPLOSIVE_MASTER.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_WEAPONSMITH));
}
