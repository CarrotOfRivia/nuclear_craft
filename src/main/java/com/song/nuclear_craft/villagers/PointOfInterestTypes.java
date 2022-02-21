package com.song.nuclear_craft.villagers;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.BlockList;
import com.song.nuclear_craft.misc.Util;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PointOfInterestTypes {
    public static final DeferredRegister<PoiType> POINT_OF_INTEREST_TYPE = DeferredRegister.create(ForgeRegistries.POI_TYPES, NuclearCraft.MODID);

    public static final RegistryObject<PoiType> RIFLE_AMMO_SELLER = POINT_OF_INTEREST_TYPE.register(("rifle_ammo_seller"),
            ()-> new PoiType(Util.getResourceString("rifle_ammo_seller"), PoiType.getBlockStates(BlockList.STATUE_OF_RIFLE_AMMO.get()), 1, 1));
    public static final RegistryObject<PoiType> SHOTGUN_AMMO_SELLER = POINT_OF_INTEREST_TYPE.register(("shotgun_ammo_seller"),
            ()-> new PoiType(Util.getResourceString("shotgun_ammo_seller"), PoiType.getBlockStates(BlockList.STATUE_OF_SHOTGUN_AMMO.get()), 1, 1));
    public static final RegistryObject<PoiType> GUN_SELLER = POINT_OF_INTEREST_TYPE.register(("gun_seller"),
            ()-> new PoiType(Util.getResourceString("gun_seller"), PoiType.getBlockStates(BlockList.STATUE_OF_LIBERTY.get()), 1, 1));
    public static final RegistryObject<PoiType> ROCKET_MASTER = POINT_OF_INTEREST_TYPE.register(("rocket_master"),
            ()-> new PoiType(Util.getResourceString("rocket_master"), PoiType.getBlockStates(BlockList.STATUE_OF_ROCKET.get()), 1, 1));
    public static final RegistryObject<PoiType> EXPLOSIVE_MASTER = POINT_OF_INTEREST_TYPE.register(("explosive_master"),
            ()-> new PoiType(Util.getResourceString("explosive_master"), PoiType.getBlockStates(BlockList.STATUE_OF_EXPLOSIVE.get()), 1, 1));
}
