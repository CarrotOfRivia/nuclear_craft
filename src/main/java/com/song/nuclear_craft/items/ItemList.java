package com.song.nuclear_craft.items;

import com.song.nuclear_craft.blocks.BlockList;
import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class ItemList {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NuclearCraft.MODID);

//    public static final RegistryObject<Item> BLUEPRINT_AMMO_9MM = ITEMS.register("blueprint_ammo_9mm", ()->new Item(new Item.Properties().group(NuclearCraft.ITEM_GROUP)));
//    public static final RegistryObject<Item> BLUEPRINT_AMMO_762 = ITEMS.register("blueprint_ammo_762", ()->new Item(new Item.Properties().group(NuclearCraft.ITEM_GROUP)));
//    public static final RegistryObject<Item> BLUEPRINT_AMMO_127 = ITEMS.register("blueprint_ammo_127", ()->new Item(new Item.Properties().group(NuclearCraft.ITEM_GROUP)));

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

    // C4 Bombs
    public static final Item C4_ATOMIC_BOMB = new C4BombItem(BlockList.C4_ATOMIC_BOMB, new Item.Properties().group(NuclearCraft.ITEM_GROUP));
    public static final Item C4_HIGH_EXPLOSIVE = new C4BombItem(BlockList.C4_HIGH_EXPLOSIVE, new Item.Properties().group(NuclearCraft.ITEM_GROUP));
    public static final Item C4_INCENDIARY = new C4BombItem(BlockList.C4_INCENDIARY, new Item.Properties().group(NuclearCraft.ITEM_GROUP));
    public static final Item C4_SMOKE = new C4BombItem(BlockList.C4_SMOKE, new Item.Properties().group(NuclearCraft.ITEM_GROUP));

    // Guns and bullets
    public static final DesertEagle DESERT_EAGLE = new DesertEagle();
    public static final Glock GLOCK = new Glock();
    public static final USP USP = new USP();
    public static final Ak47 AK47 = new Ak47();
    public static final Awp AWP = new Awp();
    public static final Barrett BARRETT = new Barrett();

    public static final Ammo9mmNormal AMMO_9MM = new Ammo9mmNormal();
    public static final AmmoTest AMMO_TEST = new AmmoTest();
    public static final Ammo9mmAntiGravity AMMO_9MM_ANTI_GRAVITY = new Ammo9mmAntiGravity();
    public static final Ammo9mmExplosive AMMO_9MM_EXPLOSIVE = new Ammo9mmExplosive();
    public static final Ammo9mmIncendiary AMMO_9MM_INCENDIARY = new Ammo9mmIncendiary();
    public static final Ammo9mmNuke AMMO_9MM_NUKE = new Ammo9mmNuke();
    public static final Ammo9mmSilver AMMO_9MM_SILVER = new Ammo9mmSilver();
    public static final Ammo9mmTungsten AMMO_9MM_TUNGSTEN = new Ammo9mmTungsten();

    public static final Ammo762Normal AMMO_762_NORMAL = new Ammo762Normal();
    public static final Ammo762AntiGravity AMMO_762_ANTI_GRAVITY = new Ammo762AntiGravity();
    public static final Ammo762Explosive AMMO_762_EXPLOSIVE = new Ammo762Explosive();
    public static final Ammo762Incendiary AMMO_762_INCENDIARY = new Ammo762Incendiary();
    public static final Ammo762Nuke AMMO_762_NUKE = new Ammo762Nuke();
    public static final Ammo762Silver AMMO_762_SILVER = new Ammo762Silver();
    public static final Ammo762Tungsten AMMO_762_TUNGSTEN = new Ammo762Tungsten();

    public static final Ammo127Normal AMMO_127_NORMAL = new Ammo127Normal();
    public static final Ammo127AntiGravity AMMO_127_ANTI_GRAVITY = new Ammo127AntiGravity();
    public static final Ammo127Explosive AMMO_127_EXPLOSIVE = new Ammo127Explosive();
    public static final Ammo127Incendiary AMMO_127_INCENDIARY = new Ammo127Incendiary();
    public static final Ammo127Nuke AMMO_127_NUKE = new Ammo127Nuke();
    public static final Ammo127Silver AMMO_127_SILVER = new Ammo127Silver();
    public static final Ammo127Tungsten AMMO_127_TUNGSTEN = new Ammo127Tungsten();
    static {
        C4_ATOMIC_BOMB.setRegistryName(Objects.requireNonNull(BlockList.C4_ATOMIC_BOMB.getRegistryName()));
        C4_HIGH_EXPLOSIVE.setRegistryName(Objects.requireNonNull(BlockList.C4_HIGH_EXPLOSIVE.getRegistryName()));
        C4_INCENDIARY.setRegistryName(Objects.requireNonNull(BlockList.C4_INCENDIARY.getRegistryName()));
        C4_SMOKE.setRegistryName(Objects.requireNonNull(BlockList.C4_SMOKE.getRegistryName()));

        DESERT_EAGLE.setRegistryName(NuclearCraft.MODID, "desert_eagle");
        GLOCK.setRegistryName(NuclearCraft.MODID, "glock");
        USP.setRegistryName(NuclearCraft.MODID, "usp");
        AK47.setRegistryName(NuclearCraft.MODID, "ak47");
        AWP.setRegistryName(NuclearCraft.MODID, "awp");
        BARRETT.setRegistryName(NuclearCraft.MODID, "barrett");
        // 9mm
        AMMO_9MM.setRegistryName(NuclearCraft.MODID, "ammo_9mm");
        AMMO_TEST.setRegistryName(NuclearCraft.MODID, "ammo_test");
        AMMO_9MM_ANTI_GRAVITY.setRegistryName(NuclearCraft.MODID, "ammo_9mm_anti_gravity");
        AMMO_9MM_EXPLOSIVE.setRegistryName(NuclearCraft.MODID, "ammo_9mm_explosive");
        AMMO_9MM_INCENDIARY.setRegistryName(NuclearCraft.MODID, "ammo_9mm_incendiary");
        AMMO_9MM_NUKE.setRegistryName(NuclearCraft.MODID, "ammo_9mm_nuke");
        AMMO_9MM_SILVER.setRegistryName(NuclearCraft.MODID, "ammo_9mm_silver");
        AMMO_9MM_TUNGSTEN.setRegistryName(NuclearCraft.MODID, "ammo_9mm_tungsten");
        // 7.62mm
        AMMO_762_NORMAL.setRegistryName(NuclearCraft.MODID, "ammo_762_normal");
        AMMO_762_ANTI_GRAVITY.setRegistryName(NuclearCraft.MODID, "ammo_762_anti_gravity");
        AMMO_762_EXPLOSIVE.setRegistryName(NuclearCraft.MODID, "ammo_762_explosive");
        AMMO_762_INCENDIARY.setRegistryName(NuclearCraft.MODID, "ammo_762_incendiary");
        AMMO_762_NUKE.setRegistryName(NuclearCraft.MODID, "ammo_762_nuke");
        AMMO_762_SILVER.setRegistryName(NuclearCraft.MODID, "ammo_762_silver");
        AMMO_762_TUNGSTEN.setRegistryName(NuclearCraft.MODID, "ammo_762_tungsten");

        // 12.7mm
        AMMO_127_NORMAL.setRegistryName(NuclearCraft.MODID, "ammo_127_normal");
        AMMO_127_ANTI_GRAVITY.setRegistryName(NuclearCraft.MODID, "ammo_127_anti_gravity");
        AMMO_127_EXPLOSIVE.setRegistryName(NuclearCraft.MODID, "ammo_127_explosive");
        AMMO_127_INCENDIARY.setRegistryName(NuclearCraft.MODID, "ammo_127_incendiary");
        AMMO_127_NUKE.setRegistryName(NuclearCraft.MODID, "ammo_127_nuke");
        AMMO_127_SILVER.setRegistryName(NuclearCraft.MODID, "ammo_127_silver");
        AMMO_127_TUNGSTEN.setRegistryName(NuclearCraft.MODID, "ammo_127_tungsten");
    }
}
