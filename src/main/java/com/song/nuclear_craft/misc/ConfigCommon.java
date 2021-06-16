package com.song.nuclear_craft.misc;

import com.song.nuclear_craft.items.Ammo.AmmoPossibleCombination;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import com.song.nuclear_craft.items.GunConfigurable;
import com.song.nuclear_craft.items.ItemList;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.Map;

public class ConfigCommon {
    public static ForgeConfigSpec COMMON;

    public static ForgeConfigSpec.DoubleValue NUKE_RADIUS;
    public static ForgeConfigSpec.DoubleValue HIGH_EXPLOSIVE_RADIUS;
    public static ForgeConfigSpec.DoubleValue SMOKE_RADIUS;
    public static ForgeConfigSpec.IntValue INCENDIARY_COUNT;

    public static ForgeConfigSpec.IntValue HIGH_EXPLOSIVE_MAX_AMMO;
    public static ForgeConfigSpec.IntValue INCENDIARY_MAX_AMMO;
    public static ForgeConfigSpec.IntValue SMOKE_MAX_AMMO;
    public static ForgeConfigSpec.IntValue WATER_DROP_MAX_AMMO;

    public static ForgeConfigSpec.DoubleValue NUKE_BLAST_POWER;
    public static ForgeConfigSpec.DoubleValue HIGH_EXPLOSIVE_BLAST_POWER;

    public static ForgeConfigSpec.DoubleValue AMMO_ANTI_GRAVITY_RICOCHET_LOSS;
    public static ForgeConfigSpec.DoubleValue AMMO_NORMAL_RICOCHET_LOSS;
    public static ForgeConfigSpec.DoubleValue AMMO_SILVER_RICOCHET_LOSS;
    public static ForgeConfigSpec.DoubleValue AMMO_TUNGSTEN_RICOCHET_LOSS;

    public static final HashMap<AmmoSize, HashMap<AmmoType, ForgeConfigSpec.DoubleValue>> DAMAGE_MAP = new HashMap<>();
    public static final HashMap<AmmoSize, HashMap<AmmoType, ForgeConfigSpec.DoubleValue>> SPEED_MAP = new HashMap<>();
    public static final HashMap<AmmoSize, HashMap<AmmoType, ForgeConfigSpec.DoubleValue>> GRAVITY_MAP = new HashMap<>();

    public static final HashMap<AmmoType, ForgeConfigSpec.IntValue> BIRD_SHOT_COUNT_MAP = new HashMap<>();

    public static final GunConfigurable AK47_CONFIG = new GunConfigurable();
    public static final GunConfigurable DESERT_EAGLE_CONFIG = new GunConfigurable();
    public static final GunConfigurable GLOCK_CONFIG = new GunConfigurable();
    public static final GunConfigurable FN57_CONFIG = new GunConfigurable();
    public static final GunConfigurable USP_CONFIG = new GunConfigurable();
    public static final GunConfigurable AWP_CONFIG = new GunConfigurable();
    public static final GunConfigurable BARRETT_CONFIG = new GunConfigurable();
    public static final GunConfigurable M4A4_CONFIG = new GunConfigurable();
    public static final GunConfigurable P90_CONFIG = new GunConfigurable();

    public static final ForgeConfigSpec.DoubleValue AMMO_BLOCK_BREAK_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue AMMO_SILVER_BLOCK_BREAK_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue AMMO_TUNGSTEN_BLOCK_BREAK_THRESHOLD;

    public static final HashMap<String, ForgeConfigSpec.IntValue> LEVEL_MAP = new HashMap<>();
    public static final HashMap<String, ForgeConfigSpec.ConfigValue<String>> PRICE1_MAP = new HashMap<>();
    public static final HashMap<String, ForgeConfigSpec.ConfigValue<String>> PRICE2_MAP = new HashMap<>();
    public static final HashMap<String, ForgeConfigSpec.IntValue> PRICE1_MIN = new HashMap<>();
    public static final HashMap<String, ForgeConfigSpec.IntValue> PRICE2_MIN = new HashMap<>();
    public static final HashMap<String, ForgeConfigSpec.IntValue> PRICE1_MAX = new HashMap<>();
    public static final HashMap<String, ForgeConfigSpec.IntValue> PRICE2_MAX = new HashMap<>();

    public static final ForgeConfigSpec.IntValue WOOD_DEFUSE_KIT_TIME;
    public static final ForgeConfigSpec.IntValue IRON_DEFUSE_KIT_TIME;
    public static final ForgeConfigSpec.IntValue GOLD_DEFUSE_KIT_TIME;
    public static final ForgeConfigSpec.IntValue DIAMOND_DEFUSE_KIT_TIME;
    public static final ForgeConfigSpec.IntValue NETHERITE_DEFUSE_KIT_TIME;


    static {
        ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();

        CONFIG_BUILDER.comment("Ammo destroying block settings").push("ammo_break_block");
        AMMO_BLOCK_BREAK_THRESHOLD = CONFIG_BUILDER.comment("Normal ammo can break blocks with blast resist lower than this value").defineInRange("ammo_block_break_threshold", 3d, -10, 99999);
        AMMO_SILVER_BLOCK_BREAK_THRESHOLD = CONFIG_BUILDER.comment("Silver ammo can break blocks with blast resist lower than this value").defineInRange("ammo_silver_block_break_threshold", 6d, -10, 99999);
        AMMO_TUNGSTEN_BLOCK_BREAK_THRESHOLD = CONFIG_BUILDER.comment("Tungsten ammo can break blocks with blast resist lower than this value").defineInRange("ammo_tungsten_block_break_threshold", 6d, -10, 99999);
        CONFIG_BUILDER.pop();

        CONFIG_BUILDER.comment("Rocket ammo number settings").push("rocket_ammo_number");
        HIGH_EXPLOSIVE_MAX_AMMO = CONFIG_BUILDER.comment("Max number of ammo of high explosive rocket launcher").defineInRange("high_explosive_ammo", 1, 0, 32767);
        INCENDIARY_MAX_AMMO = CONFIG_BUILDER.comment("Max number of ammo of incendiary rocket launcher").defineInRange("incendiary_ammo", 1, 0, 32767);
        SMOKE_MAX_AMMO = CONFIG_BUILDER.comment("Max number of ammo of smoke rocket launcher").defineInRange("smoke_ammo", 1, 0, 32767);
        WATER_DROP_MAX_AMMO = CONFIG_BUILDER.comment("Max number of ammo of water drop rocket launcher").defineInRange("water_drop_ammo", 1, 0, 32767);
        CONFIG_BUILDER.pop();

        CONFIG_BUILDER.comment("Explosion settings").push("explosion");
        NUKE_RADIUS = CONFIG_BUILDER.comment("Atomic bomb explosion radius").defineInRange("nuke_radius", 50f, 0f, 256f);
        HIGH_EXPLOSIVE_RADIUS = CONFIG_BUILDER.comment("High explosive bomb radius").defineInRange("high_explosive", 10f, 0f, 80f);
        SMOKE_RADIUS = CONFIG_BUILDER.comment("Smoke bomb radius").defineInRange("smoke_bomb", 30f, 0f, 256f);

        NUKE_BLAST_POWER = CONFIG_BUILDER.comment("Atomic bomb explosion power(i.e. max blast resistance block to break) 3,600,000 for bedrock, 1200 for obsidian, 100 for water").defineInRange("nuke_blast_power", 1500d, 0d, 999999999d);
        HIGH_EXPLOSIVE_BLAST_POWER = CONFIG_BUILDER.comment("High explosive bomb explosion power(i.e. max blast resistance block to break) 3,600,000 for bedrock, 1200 for obsidian, 100 for water").defineInRange("high_explosive_blast_power", 101d, 0d, 999999999d);
        CONFIG_BUILDER.pop();

        CONFIG_BUILDER.comment("Ricochet, gravity, incendiary fire count").push("misc");
        INCENDIARY_COUNT = CONFIG_BUILDER.comment("Number of flames from incendiary").defineInRange("incendiary_bomb", 100, 0, 10000);
        AMMO_ANTI_GRAVITY_RICOCHET_LOSS = CONFIG_BUILDER.comment("Energy loss of ammo (Anti-Gravity) after ricochet (bouncing on hard surfaces)")
                .defineInRange("ammo_anti_gravity_ricochet_loss",0.5d, 0d, 1d);
        AMMO_NORMAL_RICOCHET_LOSS = CONFIG_BUILDER.comment("Energy loss of ammo (Normal) after ricochet (bouncing on hard surfaces)")
                .defineInRange("ammo_normal_ricochet_loss",0.5d, 0d, 1d);
        AMMO_SILVER_RICOCHET_LOSS = CONFIG_BUILDER.comment("Energy loss of ammo (SILVER) after ricochet (bouncing on hard surfaces)")
                .defineInRange("ammo_silver_ricochet_loss",0.5d, 0d, 1d);
        AMMO_TUNGSTEN_RICOCHET_LOSS = CONFIG_BUILDER.comment("Energy loss of ammo (TUNGSTEN) after ricochet (bouncing on hard surfaces)")
                .defineInRange("ammo_tungsten_ricochet_loss",0.5d, 0d, 1d);
        CONFIG_BUILDER.pop();

        // ------------------------ Rifle Ammo Speed and Damage -------------------------- //
        CONFIG_BUILDER.comment("Rifle Ammo Speed and Damage").push("rifle_ammo");
        for (AmmoSize ammoSize: AmmoPossibleCombination.RIFLE_AMMO.getAmmoSizes()){
            CONFIG_BUILDER.push(ammoSize.getRegisterString());
            HashMap<AmmoType, ForgeConfigSpec.DoubleValue> thisDamageMap = new HashMap<>();
            HashMap<AmmoType, ForgeConfigSpec.DoubleValue> thisSpeedMap = new HashMap<>();
            HashMap<AmmoType, ForgeConfigSpec.DoubleValue> thisGravityMap = new HashMap<>();
            for (AmmoType ammoType: AmmoPossibleCombination.RIFLE_AMMO.getAmmoTypes()){
                thisDamageMap.put(ammoType, CONFIG_BUILDER.comment("Base damage, base speed(m/tick) and gravity(m/tick^2) of ammo: "+ammoType.getDescription()+" "+ammoSize.getDescription())
                        .defineInRange("damage_"+ammoType.getRegisterString()+"_"+ammoSize.getRegisterString(), ammoType.getDamage()*ammoSize.getDamageModify(), 0, 999999));
                thisSpeedMap.put(ammoType, CONFIG_BUILDER.defineInRange("speed_"+ammoType.getRegisterString()+"_"+ammoSize.getRegisterString(), ammoType.getSpeed()*ammoSize.getSpeedModify(), 0, 999999));
                thisGravityMap.put(ammoType, CONFIG_BUILDER.defineInRange("gravity_"+ammoType.getRegisterString()+"_"+ammoSize.getRegisterString(), ammoType.getGravity(), 0, 999999));
            }
            DAMAGE_MAP.put(ammoSize, thisDamageMap);
            SPEED_MAP.put(ammoSize, thisSpeedMap);
            GRAVITY_MAP.put(ammoSize, thisGravityMap);
            CONFIG_BUILDER.pop();
        }
        CONFIG_BUILDER.pop();

        // ------------------------ Shotguns Ammo Speed and Damage -------------------------- //
        CONFIG_BUILDER.comment("Shotguns Ammo Speed, Damage, bird shots").push("shotgun_ammo");
        for (AmmoSize ammoSize: AmmoPossibleCombination.SHOTGUN_AMMO.getAmmoSizes()){
            CONFIG_BUILDER.push(ammoSize.getRegisterString());
            HashMap<AmmoType, ForgeConfigSpec.DoubleValue> thisDamageMap = new HashMap<>();
            HashMap<AmmoType, ForgeConfigSpec.DoubleValue> thisSpeedMap = new HashMap<>();
            HashMap<AmmoType, ForgeConfigSpec.DoubleValue> thisGravityMap = new HashMap<>();
            for (AmmoType ammoType: AmmoPossibleCombination.SHOTGUN_AMMO.getAmmoTypes()){
                thisDamageMap.put(ammoType, CONFIG_BUILDER.comment("Base damage, base speed(m/tick) and gravity(m/tick^2) of ammo: "+ammoType.getDescription()+" "+ammoSize.getDescription())
                        .defineInRange("damage_"+ammoType.getRegisterString()+"_"+ammoSize.getRegisterString(), ammoType.getDamage()*ammoSize.getDamageModify(), 0, 999999));
                thisSpeedMap.put(ammoType, CONFIG_BUILDER.defineInRange("speed_"+ammoType.getRegisterString()+"_"+ammoSize.getRegisterString(), ammoType.getSpeed()*ammoSize.getSpeedModify(), 0, 999999));
                thisGravityMap.put(ammoType, CONFIG_BUILDER.defineInRange("gravity_"+ammoType.getRegisterString()+"_"+ammoSize.getRegisterString(), ammoType.getGravity(), 0, 999999));
            }
            DAMAGE_MAP.put(ammoSize, thisDamageMap);
            SPEED_MAP.put(ammoSize, thisSpeedMap);
            GRAVITY_MAP.put(ammoSize, thisGravityMap);
            CONFIG_BUILDER.pop();
        }

        // -----------------------Short Guns Ammo Shoot Count ------------------------------//
        for (AmmoType ammoType: AmmoPossibleCombination.SHOTGUN_AMMO.getAmmoTypes()) {
            BIRD_SHOT_COUNT_MAP.put(ammoType, CONFIG_BUILDER.comment("Number of birdshots in short gun ammo: "+ammoType.getDescription()).defineInRange("n_birdshot_"+ammoType.getRegisterString(), ammoType.getBirdShotCount(), 0, 999999));
        }
        CONFIG_BUILDER.pop();

        // -------------------- Gun Modifiers -----------------------------//
        CONFIG_BUILDER.comment("Gun Modifiers").push("gun_modifiers");
        AK47_CONFIG.setDamageModify(CONFIG_BUILDER.comment("AK47 damage modify").defineInRange("ak47_damage_modify", 1.7, 0, 999999));
        AK47_CONFIG.setSpeedModify(CONFIG_BUILDER.comment("AK47 speed modify").defineInRange("ak47_speed_modify", 1.5, 0, 999999));
        DESERT_EAGLE_CONFIG.setDamageModify(CONFIG_BUILDER.comment("DESERT_EAGLE damage modify").defineInRange("desert_eagle_damage_modify", 1.4, 0, 999999));
        DESERT_EAGLE_CONFIG.setSpeedModify(CONFIG_BUILDER.comment("DESERT_EAGLE speed modify").defineInRange("desert_eagle_speed_modify", 1.4, 0, 999999));
        GLOCK_CONFIG.setDamageModify(CONFIG_BUILDER.comment("GLOCK damage modify").defineInRange("glock_damage_modify", 1.1, 0, 999999));
        GLOCK_CONFIG.setSpeedModify(CONFIG_BUILDER.comment("GLOCK speed modify").defineInRange("glock_speed_modify", 1.0, 0, 999999));
        FN57_CONFIG.setDamageModify(CONFIG_BUILDER.comment("FN57 damage modify").defineInRange("fn57_damage_modify", 1.1, 0, 999999));
        FN57_CONFIG.setSpeedModify(CONFIG_BUILDER.comment("FN57 speed modify").defineInRange("fn57_speed_modify", 1.1, 0, 999999));
        USP_CONFIG.setDamageModify(CONFIG_BUILDER.comment("USP damage modify").defineInRange("usp_damage_modify", 1.0, 0, 999999));
        USP_CONFIG.setSpeedModify(CONFIG_BUILDER.comment("USP speed modify").defineInRange("usp_speed_modify", 1.0, 0, 999999));
        AWP_CONFIG.setDamageModify(CONFIG_BUILDER.comment("AWP damage modify").defineInRange("awp_damage_modify", 3.0, 0, 999999));
        AWP_CONFIG.setSpeedModify(CONFIG_BUILDER.comment("AWP speed modify").defineInRange("awp_speed_modify", 2.0, 0, 999999));
        BARRETT_CONFIG.setDamageModify(CONFIG_BUILDER.comment("BARRETT damage modify").defineInRange("barrett_damage_modify", 5.0, 0, 999999));
        BARRETT_CONFIG.setSpeedModify(CONFIG_BUILDER.comment("BARRETT speed modify").defineInRange("barrett_speed_modify", 4.0, 0, 999999));
        M4A4_CONFIG.setDamageModify(CONFIG_BUILDER.comment("M4A4 damage modify").defineInRange("m4a4_damage_modify", 1.3, 0, 999999));
        M4A4_CONFIG.setSpeedModify(CONFIG_BUILDER.comment("M4A4 speed modify").defineInRange("m4a4_speed_modify", 1.35, 0, 999999));
        P90_CONFIG.setDamageModify(CONFIG_BUILDER.comment("P90 damage modify").defineInRange("P90_damage_modify", 1.3, 0, 999999));
        P90_CONFIG.setSpeedModify(CONFIG_BUILDER.comment("P90 speed modify").defineInRange("P90_speed_modify", 1.35, 0, 999999));

        CONFIG_BUILDER.pop();

        CONFIG_BUILDER.comment("Tradings").push("tradings");
        CONFIG_BUILDER.comment("ROCKET_MASTER_PROFESSION: Rockets.").push("rocket_master_profession");
        addTrader("rocket_launcher", 1, "minecraft:diamond", 30, 40, "null", 1, 1, CONFIG_BUILDER);
        addTrader("incendiary_rocket", 1, "minecraft:diamond", 1, 2, "null", 1, 1, CONFIG_BUILDER);
        addTrader("smoke_rocket", 2, "minecraft:diamond", 2, 3, "null", 1, 1, CONFIG_BUILDER);
        addTrader("high_explosive_rocket", 3, "minecraft:diamond", 3, 4, "null", 1, 1, CONFIG_BUILDER);
        addTrader("atomic_bomb_rocket", 4, "minecraft:diamond", 40, 50, "null", 1, 1, CONFIG_BUILDER);
        addTrader("water_drop_rocket", 4, "minecraft:nether_star", 40, 50, "null", 1, 1, CONFIG_BUILDER);
        CONFIG_BUILDER.pop();

        CONFIG_BUILDER.comment("EXPLOSIVE_MASTER_PROFESSION: C4 explosives").push("explosive_master_profession");
        addTrader("c4_incendiary", 1, "minecraft:diamond", 2, 3, "null", 1, 1, CONFIG_BUILDER);
        addTrader("wood_defuse_kit", 1, "minecraft:wood", 2, 3, "null", 1, 1, CONFIG_BUILDER);
        addTrader("c4_smoke", 2, "minecraft:diamond", 4, 5, "null", 1, 1, CONFIG_BUILDER);
        addTrader("iron_defuse_kit", 2, "minecraft:iron_ingot", 5, 8, "null", 1, 1, CONFIG_BUILDER);
        addTrader("c4_high_explosive", 3, "minecraft:diamond", 5, 10, "null", 1, 1, CONFIG_BUILDER);
        addTrader("gold_defuse_kit", 3, "minecraft:gold_ingot", 5, 8, "null", 1, 1, CONFIG_BUILDER);
        addTrader("c4_atomic_bomb", 4, "minecraft:diamond", 40, 50, "null", 1, 1, CONFIG_BUILDER);
        addTrader("diamond_defuse_kit", 4, "minecraft:diamond", 5, 8, "null", 1, 1, CONFIG_BUILDER);
        addTrader("netherite_defuse_kit", 5, "minecraft:netherite_ingot", 5, 8, "null", 1, 1, CONFIG_BUILDER);
        CONFIG_BUILDER.pop();

        CONFIG_BUILDER.comment("GUN_SELLER_PROFESSION: guns").push("gun_seller_profession");
        addTrader("glock", 1, "minecraft:diamond", 10, 10, "null", 1, 1, CONFIG_BUILDER);
        addTrader("fn57", 1, "minecraft:diamond", 10, 10, "null", 1, 1, CONFIG_BUILDER);
        addTrader("usp", 1, "minecraft:diamond", 10, 10, "null", 1, 1, CONFIG_BUILDER);
        addTrader("desert_eagle", 2, "minecraft:diamond", 20, 30, "null", 1, 1, CONFIG_BUILDER);
        addTrader("nova", 2, "minecraft:diamond", 20, 30, "null", 1, 1, CONFIG_BUILDER);
        addTrader("m4a4", 3, "minecraft:diamond", 30, 40, "null", 1, 1, CONFIG_BUILDER);
        addTrader("xm1014", 3, "minecraft:diamond", 30, 40, "null", 1, 1, CONFIG_BUILDER);
        addTrader("ak47", 4, "minecraft:diamond", 40, 50, "null", 1, 1, CONFIG_BUILDER);
        addTrader("awp", 4, "minecraft:diamond", 40, 50, "null", 1, 1, CONFIG_BUILDER);
        addTrader("barrett", 5, "minecraft:diamond", 50, 64, "null", 1, 1, CONFIG_BUILDER);
        addTrader("p90", 3, "minecraft:diamond", 30, 50, "null", 1, 1, CONFIG_BUILDER);
        CONFIG_BUILDER.pop();

        CONFIG_BUILDER.comment("SHOTGUN_AMMO_SELLER_PROFESSION: Shotgun ammo").push("shotgun_ammo_seller_profession");
        for (AmmoSize ammoSize: AmmoPossibleCombination.SHOTGUN_AMMO.getAmmoSizes()){
            addTrader("ammo_"+ammoSize.getRegisterString()+"_"+AmmoType.SHORT_GUN_NORMAL.getRegisterString(), 1, "minecraft:iron_ingot", 1, 1, "minecraft:gunpowder", 1, 1, CONFIG_BUILDER);
            addTrader("ammo_"+ammoSize.getRegisterString()+"_"+AmmoType.SHORT_GUN_BLIGHT.getRegisterString(), 2, "minecraft:iron_ingot", 1, 1, "minecraft:gunpowder", 2, 2, CONFIG_BUILDER);
            addTrader("ammo_"+ammoSize.getRegisterString()+"_"+AmmoType.SHORT_GUN_DESOLATOR.getRegisterString(), 3, "minecraft:iron_ingot", 5, 10, "minecraft:gunpowder", 2, 4, CONFIG_BUILDER);
        }
        CONFIG_BUILDER.pop();

        CONFIG_BUILDER.comment("RIFLE_AMMO_SELLER_PROFESSION: Rifle ammo").push("rifle_ammo_seller_profession");
        for (AmmoSize ammoSize: AmmoPossibleCombination.RIFLE_AMMO.getAmmoSizes()){
            addTrader("ammo_"+ammoSize.getRegisterString()+"_"+AmmoType.NORMAL.getRegisterString(), 1, "minecraft:iron_ingot", 1, 1, "minecraft:gunpowder", 1, 1, CONFIG_BUILDER);
            addTrader("ammo_"+ammoSize.getRegisterString()+"_"+AmmoType.INCENDIARY.getRegisterString(), 1, "minecraft:iron_ingot", 1, 1, "minecraft:gunpowder", 2, 2, CONFIG_BUILDER);

            addTrader("ammo_"+ammoSize.getRegisterString()+"_"+AmmoType.ANTI_GRAVITY.getRegisterString(), 2, "minecraft:iron_ingot", 1, 1, "minecraft:gunpowder", 2, 2, CONFIG_BUILDER);
            addTrader("ammo_"+ammoSize.getRegisterString()+"_"+AmmoType.SILVER.getRegisterString(), 2, "minecraft:iron_ingot", 1, 1, "minecraft:gunpowder", 2, 2, CONFIG_BUILDER);

            addTrader("ammo_"+ammoSize.getRegisterString()+"_"+AmmoType.EXPLOSIVE.getRegisterString(), 3, "minecraft:iron_ingot", 2, 2, "minecraft:gunpowder", 10, 10, CONFIG_BUILDER);

            addTrader("ammo_"+ammoSize.getRegisterString()+"_"+AmmoType.TUNGSTEN.getRegisterString(), 4, "minecraft:iron_ingot", 5, 5, "minecraft:gunpowder", 2, 2, CONFIG_BUILDER);

            addTrader("ammo_"+ammoSize.getRegisterString()+"_"+AmmoType.NUKE.getRegisterString(), 5, "minecraft:nether_star", 1, 1, "minecraft:gunpowder", 1, 1, CONFIG_BUILDER);
        }
        CONFIG_BUILDER.pop();
        CONFIG_BUILDER.pop();

        CONFIG_BUILDER.comment("Defuse Time (in ticks, 20ticks = 1 second)").push("defuse_time");
        WOOD_DEFUSE_KIT_TIME = CONFIG_BUILDER.defineInRange("wood_defuse_kit_time", 400, 1, 999999999);
        IRON_DEFUSE_KIT_TIME = CONFIG_BUILDER.defineInRange("iron_defuse_kit_time", 200, 1, 999999999);
        GOLD_DEFUSE_KIT_TIME = CONFIG_BUILDER.defineInRange("gold_defuse_kit_time", 60, 1, 999999999);
        DIAMOND_DEFUSE_KIT_TIME = CONFIG_BUILDER.defineInRange("diamond_defuse_kit_time", 150, 1, 999999999);
        NETHERITE_DEFUSE_KIT_TIME = CONFIG_BUILDER.defineInRange("netherite_defuse_kit_time", 100, 1, 999999999);
        CONFIG_BUILDER.pop();

        COMMON = CONFIG_BUILDER.build();
    }

    private static void addTrader(String id, int level, String price1, int price1Min, int price1Max, String price2, int price2Min, int price2Max, ForgeConfigSpec.Builder builder){
        builder.push(id);
        LEVEL_MAP.put("nuclear_craft:"+id, builder.defineInRange(id+"_level", level, 1, 64));
        PRICE1_MAP.put("nuclear_craft:"+id, builder.define(id+"_price1", price1));
        PRICE1_MIN.put("nuclear_craft:"+id, builder.defineInRange(id+"_price1_min", price1Min, 1, 64));
        PRICE1_MAX.put("nuclear_craft:"+id, builder.defineInRange(id+"_price1_max", price1Max, 1, 64));
        PRICE2_MAP.put("nuclear_craft:"+id, builder.define(id+"_price2", price2));
        PRICE2_MIN.put("nuclear_craft:"+id, builder.defineInRange(id+"_price2_min", price2Min, 1, 64));
        PRICE2_MAX.put("nuclear_craft:"+id, builder.defineInRange(id+"_price2_max", price2Max, 1, 64));
        builder.pop();
    }
}
