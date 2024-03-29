package com.song.nuclear_craft.villagers;

import com.google.common.collect.ImmutableSet;
import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ProfessionTypes {
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSION = DeferredRegister.create(ForgeRegistries.PROFESSIONS, NuclearCraft.MODID);

    public static final RegistryObject<VillagerProfession> RIFLE_AMMO_SELLER_PROFESSION = VILLAGER_PROFESSION.register(("rifle_ammo_seller"),
            ()->new VillagerProfession(Util.getResourceString("rifle_ammo_seller"), PointOfInterestTypes.RIFLE_AMMO_SELLER.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH));
    public static final RegistryObject<VillagerProfession> SHOTGUN_AMMO_SELLER_PROFESSION = VILLAGER_PROFESSION.register(("shotgun_ammo_seller"),
            ()->new VillagerProfession(Util.getResourceString("shotgun_ammo_seller"), PointOfInterestTypes.SHOTGUN_AMMO_SELLER.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH));
    public static final RegistryObject<VillagerProfession> GUN_SELLER_PROFESSION = VILLAGER_PROFESSION.register(("gun_seller"),
            ()->new VillagerProfession(Util.getResourceString("gun_seller"), PointOfInterestTypes.GUN_SELLER.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH));
    public static final RegistryObject<VillagerProfession> ROCKET_MASTER_PROFESSION = VILLAGER_PROFESSION.register(("rocket_master"),
            ()->new VillagerProfession(Util.getResourceString("rocket_master"), PointOfInterestTypes.ROCKET_MASTER.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH));
    public static final RegistryObject<VillagerProfession> EXPLOSIVE_MASTER_PROFESSION = VILLAGER_PROFESSION.register(("explosive_master"),
            ()->new VillagerProfession(Util.getResourceString("explosive_master"), PointOfInterestTypes.EXPLOSIVE_MASTER.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_WEAPONSMITH));
}
