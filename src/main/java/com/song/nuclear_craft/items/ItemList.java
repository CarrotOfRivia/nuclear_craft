package com.song.nuclear_craft.items;

import com.song.nuclear_craft.blocks.BlockList;
import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.*;
import com.song.nuclear_craft.items.defuse_kit.*;
import com.song.nuclear_craft.items.guns.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Objects;

public class ItemList {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NuclearCraft.MODID);

    // active:
    public static final RegistryObject<Item> DEBUG_STICK = ITEMS.register("debug_stick", DebugStick::new);

    // Rockets
    public static final RegistryObject<Item> ATOMIC_BOMB_ROCKET = ITEMS.register("atomic_bomb_rocket", ()->new Rocket(new Item.Properties().group(NuclearCraft.ITEM_GROUP).maxStackSize(1), 1));
    public static final RegistryObject<Item> INCENDIARY_ROCKET = ITEMS.register("incendiary_rocket", ()->new Rocket(new Item.Properties().group(NuclearCraft.ITEM_GROUP), 1));
    public static final RegistryObject<Item> SMOKE_ROCKET = ITEMS.register("smoke_rocket", ()->new Rocket(new Item.Properties().group(NuclearCraft.ITEM_GROUP), 1));
    public static final RegistryObject<Item> HIGH_EXPLOSIVE_ROCKET = ITEMS.register("high_explosive_rocket", ()->new Rocket(new Item.Properties().group(NuclearCraft.ITEM_GROUP), 1));
    public static final RegistryObject<Item> WATER_DROP_ROCKET = ITEMS.register("water_drop_rocket", ()->new Rocket(new Item.Properties().group(NuclearCraft.ITEM_GROUP), 1));

    //Rocket Launchers
    public static final RegistryObject<RocketLauncher> ROCKET_LAUNCHER = ITEMS.register("rocket_launcher", RocketLauncher::new);
    public static final RegistryObject<RocketLauncherAtomicBomb> ROCKET_LAUNCHER_ATOMIC_BOMB = ITEMS.register("rocket_launcher_atomic_bomb", RocketLauncherAtomicBomb::new);
    public static final RegistryObject<RocketLauncherSmoke> ROCKET_LAUNCHER_SMOKE = ITEMS.register("rocket_launcher_smoke", RocketLauncherSmoke::new);
    public static final RegistryObject<RocketLauncherIncendiary> ROCKET_LAUNCHER_INCENDIARY = ITEMS.register("rocket_launcher_incendiary", RocketLauncherIncendiary::new);
    public static final RegistryObject<RocketLauncherHighExplosive> ROCKET_LAUNCHER_HIGH_EXPLOSIVE = ITEMS.register("rocket_launcher_high_explosive", RocketLauncherHighExplosive::new);
    public static final RegistryObject<RocketLauncherWaterDrop> ROCKET_LAUNCHER_WATER_DROP = ITEMS.register("rocket_launcher_water_drop", RocketLauncherWaterDrop::new);

    // C4 Bombs
    public static final Item C4_ATOMIC_BOMB = new C4BombItem(BlockList.C4_ATOMIC_BOMB, new Item.Properties().group(NuclearCraft.ITEM_GROUP));
    public static final Item C4_HIGH_EXPLOSIVE = new C4BombItem(BlockList.C4_HIGH_EXPLOSIVE, new Item.Properties().group(NuclearCraft.ITEM_GROUP));
    public static final Item C4_INCENDIARY = new C4BombItem(BlockList.C4_INCENDIARY, new Item.Properties().group(NuclearCraft.ITEM_GROUP));
    public static final Item C4_SMOKE = new C4BombItem(BlockList.C4_SMOKE, new Item.Properties().group(NuclearCraft.ITEM_GROUP));

    // Statues
    public static final RegistryObject<Item> STATUE_OF_LIBERTY = ITEMS.register("statue_of_liberty", ()->new BlockItem(BlockList.STATUE_OF_LIBERTY.get(), new Item.Properties().group(NuclearCraft.ITEM_GROUP)));
    public static final RegistryObject<Item> STATUE_OF_RIFLE_AMMO = ITEMS.register("statue_of_rifle_ammo", ()->new BlockItem(BlockList.STATUE_OF_RIFLE_AMMO.get(), new Item.Properties().group(NuclearCraft.ITEM_GROUP)));
    public static final RegistryObject<Item> STATUE_OF_SHOTGUN_AMMO = ITEMS.register("statue_of_shotgun_ammo", ()->new BlockItem(BlockList.STATUE_OF_SHOTGUN_AMMO.get(), new Item.Properties().group(NuclearCraft.ITEM_GROUP)));
    public static final RegistryObject<Item> STATUE_OF_ROCKET = ITEMS.register("statue_of_rocket", ()->new BlockItem(BlockList.STATUE_OF_ROCKET.get(), new Item.Properties().group(NuclearCraft.ITEM_GROUP)));
    public static final RegistryObject<Item> STATUE_OF_EXPLOSIVE = ITEMS.register("statue_of_explosive", ()->new BlockItem(BlockList.STATUE_OF_EXPLOSIVE.get(), new Item.Properties().group(NuclearCraft.ITEM_GROUP)));

    // Guns and bullets
    public static final RegistryObject<DesertEagle> DESERT_EAGLE = ITEMS.register("desert_eagle", DesertEagle::new);
    public static final RegistryObject<Glock> GLOCK = ITEMS.register("glock", Glock::new);
    public static final RegistryObject<USP> USP = ITEMS.register("usp", USP::new);
    public static final RegistryObject<Ak47> AK47 = ITEMS.register("ak47", Ak47::new);
    public static final RegistryObject<Awp> AWP = ITEMS.register("awp", Awp::new);
    public static final RegistryObject<Barrett> BARRETT = ITEMS.register("barrett", Barrett::new);
    public static final RegistryObject<com.song.nuclear_craft.items.guns.M4A4> M4A4 = ITEMS.register("m4a4", M4A4::new);
    public static final RegistryObject<XM1014> XM1014 = ITEMS.register("xm1014", XM1014::new);
    public static final RegistryObject<Nova> NOVA = ITEMS.register("nova", Nova::new);
    public static final RegistryObject<P90> P90 = ITEMS.register("p90", P90::new);

    //Defuse Kit
    public static final RegistryObject<DefuseKit> WOOD_DEFUSE_KIT = ITEMS.register("wood_defuse_kit", WoodDefuseKit::new);
    public static final RegistryObject<DefuseKit> IRON_DEFUSE_KIT = ITEMS.register("iron_defuse_kit", IronDefuseKit::new);
    public static final RegistryObject<DefuseKit> GOLD_DEFUSE_KIT = ITEMS.register("gold_defuse_kit", GoldDefuseKit::new);
    public static final RegistryObject<DefuseKit> DIAMOND_DEFUSE_KIT = ITEMS.register("diamond_defuse_kit", DiamondDefuseKit::new);
    public static final RegistryObject<DefuseKit> NETHERITE_DEFUSE_KIT = ITEMS.register("netherite_defuse_kit", NetheriteDefuseKit::new);


    public static final HashMap<AmmoSize, HashMap<AmmoType, RegistryObject<AbstractAmmo>>> AMMO_REGISTRIES_TYPE = new HashMap<>();
    public static final HashMap<AmmoType, RegistryObject<AbstractAmmo>> BIRD_SHOT_MAP = new HashMap<>();
    static {
        // Register Rifle Ammo
        for (AmmoSize ammoSize : AmmoPossibleCombination.RIFLE_AMMO.getAmmoSizes()) {
            HashMap<AmmoType, RegistryObject<AbstractAmmo>> tmp = new HashMap<>();
            for (AmmoType ammoType: AmmoPossibleCombination.RIFLE_AMMO.getAmmoTypes()){
                tmp.put(ammoType, ITEMS.register("ammo_"+ammoSize.getRegisterString()+"_"+ammoType.getRegisterString(), ()->new AbstractAmmo(new Item.Properties().group(NuclearCraft.AMMO_ITEM_GROUP), ammoSize, ammoType)));
            }
            AMMO_REGISTRIES_TYPE.put(ammoSize, tmp);
        }
        // Register Short Gun Ammo
        for (AmmoSize ammoSize : AmmoPossibleCombination.SHOTGUN_AMMO.getAmmoSizes()) {
            HashMap<AmmoType, RegistryObject<AbstractAmmo>> tmp = new HashMap<>();
            for (AmmoType ammoType: AmmoPossibleCombination.SHOTGUN_AMMO.getAmmoTypes()){
                tmp.put(ammoType, ITEMS.register("ammo_"+ammoSize.getRegisterString()+"_"+ammoType.getRegisterString(),
                        ()->new AbstractAmmo(new Item.Properties().group(NuclearCraft.AMMO_ITEM_GROUP), ammoSize, ammoType, 1)));
            }
            AMMO_REGISTRIES_TYPE.put(ammoSize, tmp);
        }
        // Register Short Gun Bird Shots
        for (AmmoType ammoType: AmmoPossibleCombination.SHOTGUN_AMMO.getAmmoTypes()){
            BIRD_SHOT_MAP.put(ammoType, ITEMS.register("bird_shot_12_ga_"+ammoType.getRegisterString(), ()->new AbstractAmmo(new Item.Properties(), AmmoSize.SIZE_12_GA, ammoType)));
        }
    }

    static {
        C4_ATOMIC_BOMB.setRegistryName(Objects.requireNonNull(BlockList.C4_ATOMIC_BOMB.getRegistryName()));
        C4_HIGH_EXPLOSIVE.setRegistryName(Objects.requireNonNull(BlockList.C4_HIGH_EXPLOSIVE.getRegistryName()));
        C4_INCENDIARY.setRegistryName(Objects.requireNonNull(BlockList.C4_INCENDIARY.getRegistryName()));
        C4_SMOKE.setRegistryName(Objects.requireNonNull(BlockList.C4_SMOKE.getRegistryName()));
    }
}
