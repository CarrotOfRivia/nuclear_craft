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
    public static final RegistryObject<DesertEagle> DESERT_EAGLE = ITEMS.register("desert_eagle", DesertEagle::new);
    public static final RegistryObject<Glock> GLOCK = ITEMS.register("glock", Glock::new);
    public static final RegistryObject<USP> USP = ITEMS.register("usp", USP::new);
    public static final RegistryObject<Ak47> AK47 = ITEMS.register("ak47", Ak47::new);
    public static final RegistryObject<Awp> AWP = ITEMS.register("awp", Awp::new);
    public static final RegistryObject<Barrett> BARRETT = ITEMS.register("barrett", Barrett::new);
    public static final RegistryObject<M4A4> M4A4 = ITEMS.register("m4a4", M4A4::new);

    public static final RegistryObject<AbstractAmmo> AMMO_9MM = ITEMS.register("ammo_9mm", Ammo9mmNormal::new);
    public static final RegistryObject<AbstractAmmo> AMMO_9MM_ANTI_GRAVITY = ITEMS.register("ammo_9mm_anti_gravity", Ammo9mmAntiGravity::new);
    public static final RegistryObject<AbstractAmmo> AMMO_TEST = ITEMS.register("ammo_test", AmmoTest::new);
    public static final RegistryObject<AbstractAmmo> AMMO_9MM_EXPLOSIVE = ITEMS.register("ammo_9mm_explosive", Ammo9mmExplosive::new);
    public static final RegistryObject<AbstractAmmo> AMMO_9MM_INCENDIARY = ITEMS.register("ammo_9mm_incendiary", Ammo9mmIncendiary::new);
    public static final RegistryObject<AbstractAmmo> AMMO_9MM_NUKE = ITEMS.register("ammo_9mm_nuke", Ammo9mmNuke::new);
    public static final RegistryObject<AbstractAmmo> AMMO_9MM_SILVER = ITEMS.register("ammo_9mm_silver", Ammo9mmSilver::new);
    public static final RegistryObject<AbstractAmmo> AMMO_9MM_TUNGSTEN = ITEMS.register("ammo_9mm_tungsten", Ammo9mmTungsten::new);

    public static final RegistryObject<AbstractAmmo> AMMO_762_NORMAL = ITEMS.register("ammo_762_normal", Ammo762Normal::new);
    public static final RegistryObject<AbstractAmmo> AMMO_762_ANTI_GRAVITY = ITEMS.register("ammo_762_anti_gravity", Ammo762AntiGravity::new);
    public static final RegistryObject<AbstractAmmo> AMMO_762_EXPLOSIVE = ITEMS.register("ammo_762_explosive", Ammo762Explosive::new);
    public static final RegistryObject<AbstractAmmo> AMMO_762_INCENDIARY = ITEMS.register("ammo_762_incendiary", Ammo762Incendiary::new);
    public static final RegistryObject<AbstractAmmo> AMMO_762_NUKE = ITEMS.register("ammo_762_nuke", Ammo762Nuke::new);
    public static final RegistryObject<AbstractAmmo> AMMO_762_SILVER = ITEMS.register("ammo_762_silver", Ammo762Silver::new);
    public static final RegistryObject<AbstractAmmo> AMMO_762_TUNGSTEN = ITEMS.register("ammo_762_tungsten", Ammo762Tungsten::new);

    public static final RegistryObject<AbstractAmmo> AMMO_127_NORMAL = ITEMS.register("ammo_127_normal", Ammo127Normal::new);
    public static final RegistryObject<AbstractAmmo> AMMO_127_ANTI_GRAVITY = ITEMS.register("ammo_127_anti_gravity", Ammo127AntiGravity::new);
    public static final RegistryObject<AbstractAmmo> AMMO_127_EXPLOSIVE = ITEMS.register("ammo_127_explosive", Ammo127Explosive::new);
    public static final RegistryObject<AbstractAmmo> AMMO_127_INCENDIARY = ITEMS.register("ammo_127_incendiary", Ammo127Incendiary::new);
    public static final RegistryObject<AbstractAmmo> AMMO_127_NUKE = ITEMS.register("ammo_127_nuke", Ammo127Nuke::new);
    public static final RegistryObject<AbstractAmmo> AMMO_127_SILVER = ITEMS.register("ammo_127_silver", Ammo127Silver::new);
    public static final RegistryObject<AbstractAmmo> AMMO_127_TUNGSTEN = ITEMS.register("ammo_127_tungsten", Ammo127Tungsten::new);

    public static final RegistryObject<AbstractAmmo> AMMO_556_NORMAL = ITEMS.register("ammo_556_normal", Ammo556Normal::new);
    public static final RegistryObject<AbstractAmmo> AMMO_556_ANTI_GRAVITY = ITEMS.register("ammo_556_anti_gravity", Ammo556AntiGravity::new);
    public static final RegistryObject<AbstractAmmo> AMMO_556_EXPLOSIVE = ITEMS.register("ammo_556_explosive", Ammo556Explosive::new);
    public static final RegistryObject<AbstractAmmo> AMMO_556_INCENDIARY = ITEMS.register("ammo_556_incendiary", Ammo556Incendiary::new);
    public static final RegistryObject<AbstractAmmo> AMMO_556_NUKE = ITEMS.register("ammo_556_nuke", Ammo556Nuke::new);
    public static final RegistryObject<AbstractAmmo> AMMO_556_SILVER = ITEMS.register("ammo_556_silver", Ammo556Silver::new);
    public static final RegistryObject<AbstractAmmo> AMMO_556_TUNGSTEN = ITEMS.register("ammo_556_tungsten", Ammo556Tungsten::new);

    static {
        C4_ATOMIC_BOMB.setRegistryName(Objects.requireNonNull(BlockList.C4_ATOMIC_BOMB.getRegistryName()));
        C4_HIGH_EXPLOSIVE.setRegistryName(Objects.requireNonNull(BlockList.C4_HIGH_EXPLOSIVE.getRegistryName()));
        C4_INCENDIARY.setRegistryName(Objects.requireNonNull(BlockList.C4_INCENDIARY.getRegistryName()));
        C4_SMOKE.setRegistryName(Objects.requireNonNull(BlockList.C4_SMOKE.getRegistryName()));

    }
}
