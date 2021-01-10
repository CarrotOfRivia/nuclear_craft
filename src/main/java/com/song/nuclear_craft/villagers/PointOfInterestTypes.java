package com.song.nuclear_craft.villagers;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.BlockList;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.Util;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PointOfInterestTypes {
    public static final DeferredRegister<PointOfInterestType> POINT_OF_INTEREST_TYPE = DeferredRegister.create(ForgeRegistries.POI_TYPES, NuclearCraft.MODID);

    public static final RegistryObject<PointOfInterestType> RIFLE_AMMO_SELLER = POINT_OF_INTEREST_TYPE.register("rifle_ammo_seller",
            ()-> new PointOfInterestType("rifle_ammo_seller", PointOfInterestType.getAllStates(BlockList.STATUE_OF_RIFLE_AMMO.get()), 1, 1));
    public static final RegistryObject<PointOfInterestType> SHOTGUN_AMMO_SELLER = POINT_OF_INTEREST_TYPE.register("shotgun_ammo_seller",
            ()-> new PointOfInterestType("shotgun_ammo_seller", PointOfInterestType.getAllStates(BlockList.STATUE_OF_SHOTGUN_AMMO.get()), 1, 1));
    public static final RegistryObject<PointOfInterestType> GUN_SELLER = POINT_OF_INTEREST_TYPE.register("gun_seller",
            ()-> new PointOfInterestType("gun_seller", PointOfInterestType.getAllStates(BlockList.STATUE_OF_LIBERTY.get()), 1, 1));
}
