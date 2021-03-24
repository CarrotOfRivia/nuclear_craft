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
    public static final GunConfigurable USP_CONFIG = new GunConfigurable();
    public static final GunConfigurable AWP_CONFIG = new GunConfigurable();
    public static final GunConfigurable BARRETT_CONFIG = new GunConfigurable();
    public static final GunConfigurable M4A4_CONFIG = new GunConfigurable();

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
        }
        CONFIG_BUILDER.pop();

        // ------------------------ Shotguns Ammo Speed and Damage -------------------------- //
        CONFIG_BUILDER.comment("Shotguns Ammo Speed, Damage, bird shots").push("shotgun_ammo");
        for (AmmoSize ammoSize: AmmoPossibleCombination.SHOTGUN_AMMO.getAmmoSizes()){
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
        USP_CONFIG.setDamageModify(CONFIG_BUILDER.comment("USP damage modify").defineInRange("usp_damage_modify", 1.0, 0, 999999));
        USP_CONFIG.setSpeedModify(CONFIG_BUILDER.comment("USP speed modify").defineInRange("usp_speed_modify", 1.0, 0, 999999));
        AWP_CONFIG.setDamageModify(CONFIG_BUILDER.comment("AWP damage modify").defineInRange("awp_damage_modify", 3.0, 0, 999999));
        AWP_CONFIG.setSpeedModify(CONFIG_BUILDER.comment("AWP speed modify").defineInRange("awp_speed_modify", 2.0, 0, 999999));
        BARRETT_CONFIG.setDamageModify(CONFIG_BUILDER.comment("BARRETT damage modify").defineInRange("barrett_damage_modify", 5.0, 0, 999999));
        BARRETT_CONFIG.setSpeedModify(CONFIG_BUILDER.comment("BARRETT speed modify").defineInRange("barrett_speed_modify", 4.0, 0, 999999));
        M4A4_CONFIG.setDamageModify(CONFIG_BUILDER.comment("M4A4 damage modify").defineInRange("m4a4_damage_modify", 1.3, 0, 999999));
        M4A4_CONFIG.setSpeedModify(CONFIG_BUILDER.comment("M4A4 speed modify").defineInRange("m4a4_speed_modify", 1.35, 0, 999999));

        CONFIG_BUILDER.pop();

        CONFIG_BUILDER.comment("Tradings-Rockets").push("tradings");
        LEVEL_MAP.put("nuclear_craft:rocket_launcher", CONFIG_BUILDER.defineInRange("rocket_launcher_level", 1, 1, 64));
        PRICE1_MAP.put("nuclear_craft:rocket_launcher", CONFIG_BUILDER.define("rocket_launcher_price1", "minecraft:diamond"));
        PRICE1_MIN.put("nuclear_craft:rocket_launcher", CONFIG_BUILDER.defineInRange("rocket_launcher_price1_min", 30, 1, 64));
        PRICE1_MAX.put("nuclear_craft:rocket_launcher", CONFIG_BUILDER.defineInRange("rocket_launcher_price1_max", 40, 1, 64));
        PRICE2_MAP.put("nuclear_craft:rocket_launcher", CONFIG_BUILDER.define("rocket_launcher_price2", "null"));
        PRICE2_MIN.put("nuclear_craft:rocket_launcher", CONFIG_BUILDER.defineInRange("rocket_launcher_price2_min", 1, 1, 64));
        PRICE2_MAX.put("nuclear_craft:rocket_launcher", CONFIG_BUILDER.defineInRange("rocket_launcher_price2_max", 1, 1, 64));

        LEVEL_MAP.put("nuclear_craft:incendiary_rocket", CONFIG_BUILDER.defineInRange("incendiary_rocket_level", 1, 1, 64));
        PRICE1_MAP.put("nuclear_craft:incendiary_rocket", CONFIG_BUILDER.define("incendiary_rocket_price1", "minecraft:diamond"));
        PRICE1_MIN.put("nuclear_craft:incendiary_rocket", CONFIG_BUILDER.defineInRange("incendiary_rocket_price1_min", 1, 1, 64));
        PRICE1_MAX.put("nuclear_craft:incendiary_rocket", CONFIG_BUILDER.defineInRange("incendiary_rocket_price1_max", 2, 1, 64));
        PRICE2_MAP.put("nuclear_craft:incendiary_rocket", CONFIG_BUILDER.define("incendiary_rocket_price2", "null"));
        PRICE2_MIN.put("nuclear_craft:incendiary_rocket", CONFIG_BUILDER.defineInRange("incendiary_rocket_price2_min", 1, 1, 64));
        PRICE2_MAX.put("nuclear_craft:incendiary_rocket", CONFIG_BUILDER.defineInRange("incendiary_rocket_price2_max", 1, 1, 64));

        LEVEL_MAP.put("nuclear_craft:smoke_rocket", CONFIG_BUILDER.defineInRange("smoke_rocket_level", 2, 1, 64));
        PRICE1_MAP.put("nuclear_craft:smoke_rocket", CONFIG_BUILDER.define("smoke_rocket_price1", "minecraft:diamond"));
        PRICE1_MIN.put("nuclear_craft:smoke_rocket", CONFIG_BUILDER.defineInRange("smoke_rocket_price1_min", 2, 1, 64));
        PRICE1_MAX.put("nuclear_craft:smoke_rocket", CONFIG_BUILDER.defineInRange("smoke_rocket_price1_max", 3, 1, 64));
        PRICE2_MAP.put("nuclear_craft:smoke_rocket", CONFIG_BUILDER.define("smoke_rocket_price2", "null"));
        PRICE2_MIN.put("nuclear_craft:smoke_rocket", CONFIG_BUILDER.defineInRange("smoke_rocket_price2_min", 1, 1, 64));
        PRICE2_MAX.put("nuclear_craft:smoke_rocket", CONFIG_BUILDER.defineInRange("smoke_rocket_price2_max", 1, 1, 64));

        LEVEL_MAP.put("nuclear_craft:high_explosive_rocket", CONFIG_BUILDER.defineInRange("high_explosive_rocket_level", 3, 1, 64));
        PRICE1_MAP.put("nuclear_craft:high_explosive_rocket", CONFIG_BUILDER.define("high_explosive_rocket_price1", "minecraft:diamond"));
        PRICE1_MIN.put("nuclear_craft:high_explosive_rocket", CONFIG_BUILDER.defineInRange("high_explosive_rocket_price1_min", 3, 1, 64));
        PRICE1_MAX.put("nuclear_craft:high_explosive_rocket", CONFIG_BUILDER.defineInRange("high_explosive_rocket_price1_max", 4, 1, 64));
        PRICE2_MAP.put("nuclear_craft:high_explosive_rocket", CONFIG_BUILDER.define("high_explosive_rocket_price2", "null"));
        PRICE2_MIN.put("nuclear_craft:high_explosive_rocket", CONFIG_BUILDER.defineInRange("high_explosive_rocket_price2_min", 1, 1, 64));
        PRICE2_MAX.put("nuclear_craft:high_explosive_rocket", CONFIG_BUILDER.defineInRange("high_explosive_rocket_price2_max", 1, 1, 64));

        LEVEL_MAP.put("nuclear_craft:atomic_bomb_rocket", CONFIG_BUILDER.defineInRange("atomic_bomb_rocket_level", 4, 1, 64));
        PRICE1_MAP.put("nuclear_craft:atomic_bomb_rocket", CONFIG_BUILDER.define("atomic_bomb_rocket_price1", "minecraft:diamond"));
        PRICE1_MIN.put("nuclear_craft:atomic_bomb_rocket", CONFIG_BUILDER.defineInRange("atomic_bomb_rocket_price1_min", 40, 1, 64));
        PRICE1_MAX.put("nuclear_craft:atomic_bomb_rocket", CONFIG_BUILDER.defineInRange("atomic_bomb_rocket_price1_max", 50, 1, 64));
        PRICE2_MAP.put("nuclear_craft:atomic_bomb_rocket", CONFIG_BUILDER.define("atomic_bomb_rocket_price2", "null"));
        PRICE2_MIN.put("nuclear_craft:atomic_bomb_rocket", CONFIG_BUILDER.defineInRange("atomic_bomb_rocket_price2_min", 1, 1, 64));
        PRICE2_MAX.put("nuclear_craft:atomic_bomb_rocket", CONFIG_BUILDER.defineInRange("atomic_bomb_rocket_price2_max", 1, 1, 64));

        LEVEL_MAP.put("nuclear_craft:water_drop_rocket", CONFIG_BUILDER.defineInRange("water_drop_rocket_level", 4, 1, 64));
        PRICE1_MAP.put("nuclear_craft:water_drop_rocket", CONFIG_BUILDER.define("water_drop_rocket_price1", "minecraft:nether_star"));
        PRICE1_MIN.put("nuclear_craft:water_drop_rocket", CONFIG_BUILDER.defineInRange("water_drop_rocket_price1_min", 40, 1, 64));
        PRICE1_MAX.put("nuclear_craft:water_drop_rocket", CONFIG_BUILDER.defineInRange("water_drop_rocket_price1_max", 50, 1, 64));
        PRICE2_MAP.put("nuclear_craft:water_drop_rocket", CONFIG_BUILDER.define("water_drop_rocket_price2", "null"));
        PRICE2_MIN.put("nuclear_craft:water_drop_rocket", CONFIG_BUILDER.defineInRange("water_drop_rocket_price2_min", 1, 1, 64));
        PRICE2_MAX.put("nuclear_craft:water_drop_rocket", CONFIG_BUILDER.defineInRange("water_drop_rocket_price2_max", 1, 1, 64));
        CONFIG_BUILDER.pop();

        COMMON = CONFIG_BUILDER.build();
    }
}
