package com.song.nuclear_craft.misc;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class Config {
    public static ForgeConfigSpec CONFIG;

    public static ForgeConfigSpec.DoubleValue NUKE_RADIUS;
    public static ForgeConfigSpec.DoubleValue HIGH_EXPLOSIVE_RADIUS;
    public static ForgeConfigSpec.DoubleValue SMOKE_RADIUS;
    public static ForgeConfigSpec.IntValue INCENDIARY_COUNT;

    public static ForgeConfigSpec.IntValue HIGH_EXPLOSIVE_MAX_AMMO;
    public static ForgeConfigSpec.IntValue INCENDIARY_MAX_AMMO;
    public static ForgeConfigSpec.IntValue SMOKE_MAX_AMMO;

    public static ForgeConfigSpec.DoubleValue NUKE_BLAST_POWER;
    public static ForgeConfigSpec.DoubleValue HIGH_EXPLOSIVE_BLAST_POWER;

    public static ForgeConfigSpec.DoubleValue AMMO_ANTI_GRAVITY_RICOCHET_LOSS;
    public static ForgeConfigSpec.DoubleValue AMMO_NORMAL_RICOCHET_LOSS;
    public static ForgeConfigSpec.DoubleValue AMMO_SILVER_RICOCHET_LOSS;
    public static ForgeConfigSpec.DoubleValue AMMO_TUNGSTEN_RICOCHET_LOSS;

    static {
        ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
        CONFIG_BUILDER.comment("atomic bomb settings").push("nuclear_craft");

        NUKE_RADIUS = CONFIG_BUILDER.comment("Atomic bomb explosion radius").defineInRange("nuke_radius", 80f, 0f, 256f);
        HIGH_EXPLOSIVE_RADIUS = CONFIG_BUILDER.comment("High explosive bomb radius").defineInRange("high_explosive", 10f, 0f, 80f);
        SMOKE_RADIUS = CONFIG_BUILDER.comment("Smoke bomb radius").defineInRange("smoke_bomb", 30f, 0f, 256f);
        INCENDIARY_COUNT = CONFIG_BUILDER.comment("Number of flames from incendiary").defineInRange("incendiary_bomb", 100, 0, 10000);

        HIGH_EXPLOSIVE_MAX_AMMO = CONFIG_BUILDER.comment("Max number of ammo of high explosive rocket launcher").defineInRange("high_explosive_ammo", 10, 0, 32767);
        INCENDIARY_MAX_AMMO = CONFIG_BUILDER.comment("Max number of ammo of incendiary rocket launcher").defineInRange("incendiary_ammo", 10, 0, 32767);
        SMOKE_MAX_AMMO = CONFIG_BUILDER.comment("Max number of ammo of smoke rocket launcher").defineInRange("smoke_ammo", 10, 0, 32767);

        NUKE_BLAST_POWER = CONFIG_BUILDER.comment("Atomic bomb explosion power(i.e. max blast resistance block to break) 3,600,000 for bedrock, 1200 for obsidian, 100 for water").defineInRange("nuke_blast_power", 1500d, 0d, 999999999d);
        HIGH_EXPLOSIVE_BLAST_POWER = CONFIG_BUILDER.comment("High explosive bomb explosion power(i.e. max blast resistance block to break) 3,600,000 for bedrock, 1200 for obsidian, 100 for water").defineInRange("high_explosive_blast_power", 101d, 0d, 999999999d);

        AMMO_ANTI_GRAVITY_RICOCHET_LOSS = CONFIG_BUILDER.comment("Energy loss of ammo (Anti-Gravity) after ricochet (bouncing on hard surfaces)")
                .defineInRange("ammo_anti_gravity_ricochet_loss",0.5d, 0d, 1d);
        AMMO_NORMAL_RICOCHET_LOSS = CONFIG_BUILDER.comment("Energy loss of ammo (Normal) after ricochet (bouncing on hard surfaces)")
                .defineInRange("ammo_normal_ricochet_loss",0.5d, 0d, 1d);
        AMMO_SILVER_RICOCHET_LOSS = CONFIG_BUILDER.comment("Energy loss of ammo (SILVER) after ricochet (bouncing on hard surfaces)")
                .defineInRange("ammo_silver_ricochet_loss",0.5d, 0d, 1d);
        AMMO_TUNGSTEN_RICOCHET_LOSS = CONFIG_BUILDER.comment("Energy loss of ammo (TUNGSTEN) after ricochet (bouncing on hard surfaces)")
                .defineInRange("ammo_tungsten_ricochet_loss",0.5d, 0d, 1d);

        CONFIG_BUILDER.pop();

        CONFIG = CONFIG_BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
    }
}
