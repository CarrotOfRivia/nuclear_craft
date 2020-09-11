package com.song.nuclear_craft.items;

import com.song.nuclear_craft.blocks.BlockList;
import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo9mm.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

import java.util.Objects;

public class ItemList {
    // deprecated:
    public static final Item ATOMIC_BOMB = new BlockItem(BlockList.ATOMIC_BOMB, new Item.Properties().group(NuclearCraft.ITEM_GROUP)).setRegistryName(BlockList.ATOMIC_BOMB.getRegistryName());
    public static final Item URANIUM_ORE = new BlockItem(BlockList.URANIUM_ORE, new Item.Properties().group(NuclearCraft.ITEM_GROUP)).setRegistryName(BlockList.URANIUM_ORE.getRegistryName());
    public static final Item URANIUM_CENTRIFUGE = new BlockItem(BlockList.URANIUM_CENTRIFUGE, new Item.Properties().group(NuclearCraft.ITEM_GROUP)).setRegistryName(BlockList.URANIUM_CENTRIFUGE.getRegistryName());

    // active:
    // Rockets
    public static final Item ATOMIC_BOMB_ROCKET = new Item(new Item.Properties().group(NuclearCraft.ITEM_GROUP).maxStackSize(1)).setRegistryName("atomic_bomb_rocket");
    public static final Item INCENDIARY_ROCKET = new Item(new Item.Properties().group(NuclearCraft.ITEM_GROUP)).setRegistryName("incendiary_rocket");
    public static final Item SMOKE_ROCKET = new Item(new Item.Properties().group(NuclearCraft.ITEM_GROUP)).setRegistryName("smoke_rocket");
    public static final Item HIGH_EXPLOSIVE_ROCKET = new Item(new Item.Properties().group(NuclearCraft.ITEM_GROUP)).setRegistryName("high_explosive_rocket");

    //Rocket Launchers
    public static final Item ROCKET_LAUNCHER = new RocketLauncher().setRegistryName("rocket_launcher");
    public static final Item ROCKET_LAUNCHER_ATOMIC_BOMB = new RocketLauncherAtomicBomb().setRegistryName("rocket_launcher_atomic_bomb");
    public static final Item ROCKET_LAUNCHER_SMOKE = new RocketLauncherSmoke().setRegistryName("rocket_launcher_smoke");
    public static final Item ROCKET_LAUNCHER_INCENDIARY = new RocketLauncherIncendiary().setRegistryName("rocket_launcher_incendiary");
    public static final Item ROCKET_LAUNCHER_HIGH_EXPLOSIVE = new RocketLauncherHighExplosive().setRegistryName("rocket_launcher_high_explosive");

    // Random items
    public static final Item ROCKET_CONTROL_UNIT = new Item(new Item.Properties().group(NuclearCraft.ITEM_GROUP)).setRegistryName("rocket_control_unit");
    public static final Item URANIUM_238 = new Item(new Item.Properties().group(NuclearCraft.ITEM_GROUP)).setRegistryName("uranium_238");
    public static final Item URANIUM_235 = new Item(new Item.Properties().group(NuclearCraft.ITEM_GROUP)).setRegistryName("uranium_235");

    // C4 Bombs
    public static final Item C4_ATOMIC_BOMB = new C4BombItem(BlockList.C4_ATOMIC_BOMB, new Item.Properties().group(NuclearCraft.ITEM_GROUP));
    public static final Item C4_HIGH_EXPLOSIVE = new C4BombItem(BlockList.C4_HIGH_EXPLOSIVE, new Item.Properties().group(NuclearCraft.ITEM_GROUP));
    public static final Item C4_INCENDIARY = new C4BombItem(BlockList.C4_INCENDIARY, new Item.Properties().group(NuclearCraft.ITEM_GROUP));
    public static final Item C4_SMOKE = new C4BombItem(BlockList.C4_SMOKE, new Item.Properties().group(NuclearCraft.ITEM_GROUP));

    // Guns and bullets
    public static final DesertEagle DESERT_EAGLE = new DesertEagle();
    public static final Ammo9mm AMMO_9MM = new Ammo9mm();
    public static final AmmoTest AMMO_TEST = new AmmoTest();
    public static final Ammo9mmAntiGravity AMMO_9MM_ANTI_GRAVITY = new Ammo9mmAntiGravity();
    public static final Ammo9mmExplosive AMMO_9MM_EXPLOSIVE = new Ammo9mmExplosive();
    public static final Ammo9mmIncendiary AMMO_9MM_INCENDIARY = new Ammo9mmIncendiary();
    public static final Ammo9mmNuke AMMO_9MM_NUKE = new Ammo9mmNuke();
    public static final Ammo9mmSilver AMMO_9MM_SILVER = new Ammo9mmSilver();
    public static final Ammo9mmTungsten AMMO_9MM_TUNGSTEN = new Ammo9mmTungsten();
    static {
        C4_ATOMIC_BOMB.setRegistryName(Objects.requireNonNull(BlockList.C4_ATOMIC_BOMB.getRegistryName()));
        C4_HIGH_EXPLOSIVE.setRegistryName(Objects.requireNonNull(BlockList.C4_HIGH_EXPLOSIVE.getRegistryName()));
        C4_INCENDIARY.setRegistryName(Objects.requireNonNull(BlockList.C4_INCENDIARY.getRegistryName()));
        C4_SMOKE.setRegistryName(Objects.requireNonNull(BlockList.C4_SMOKE.getRegistryName()));

        DESERT_EAGLE.setRegistryName(NuclearCraft.MODID, "desert_eagle");
        AMMO_9MM.setRegistryName(NuclearCraft.MODID, "ammo_9mm");
        AMMO_TEST.setRegistryName(NuclearCraft.MODID, "ammo_test");
        AMMO_9MM_ANTI_GRAVITY.setRegistryName(NuclearCraft.MODID, "ammo_9mm_anti_gravity");
        AMMO_9MM_EXPLOSIVE.setRegistryName(NuclearCraft.MODID, "ammo_9mm_explosive");
        AMMO_9MM_INCENDIARY.setRegistryName(NuclearCraft.MODID, "ammo_9mm_incendiary");
        AMMO_9MM_NUKE.setRegistryName(NuclearCraft.MODID, "ammo_9mm_nuke");
        AMMO_9MM_SILVER.setRegistryName(NuclearCraft.MODID, "ammo_9mm_silver");
        AMMO_9MM_TUNGSTEN.setRegistryName(NuclearCraft.MODID, "ammo_9mm_tungsten");
    }
}
