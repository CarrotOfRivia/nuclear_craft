package com.song.nuclear_craft.misc;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigClient {
    public static ForgeConfigSpec CONFIG;

    public static ForgeConfigSpec.BooleanValue RENDER_MUSHROOM_CLOUD;

    static {
        ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
        CONFIG_BUILDER.comment("atomic bomb client side settings").push("nuclear_craft");

        RENDER_MUSHROOM_CLOUD = CONFIG_BUILDER.comment("Render mushroom cloud by nukes").define("render_mushroom_cloud", true);

        CONFIG_BUILDER.pop();

        CONFIG = CONFIG_BUILDER.build();
    }
}
