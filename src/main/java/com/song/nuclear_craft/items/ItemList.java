package com.song.nuclear_craft.items;

import com.song.nuclear_craft.blocks.BlockList;
import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumMap;
import java.util.HashMap;
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

    public static final HashMap<AmmoSize, HashMap<AmmoType, RegistryObject<AbstractAmmo>>> AMMO_REGISTRIES_TYPE = new HashMap<>();

    static {
        for (AmmoSize ammoSize : AmmoSize.values()) {
            HashMap<AmmoType, RegistryObject<AbstractAmmo>> tmp = new HashMap<>();
            for (AmmoType ammoType: AmmoType.values()){
                tmp.put(ammoType, ITEMS.register("ammo_"+ammoSize.getRegisterString()+"_"+ammoType.getRegisterString(), ()->new AbstractAmmo(new Item.Properties().group(NuclearCraft.AMMO_ITEM_GROUP), ammoSize, ammoType)));
            }
            AMMO_REGISTRIES_TYPE.put(ammoSize, tmp);
        }
    }

    static {
        C4_ATOMIC_BOMB.setRegistryName(Objects.requireNonNull(BlockList.C4_ATOMIC_BOMB.getRegistryName()));
        C4_HIGH_EXPLOSIVE.setRegistryName(Objects.requireNonNull(BlockList.C4_HIGH_EXPLOSIVE.getRegistryName()));
        C4_INCENDIARY.setRegistryName(Objects.requireNonNull(BlockList.C4_INCENDIARY.getRegistryName()));
        C4_SMOKE.setRegistryName(Objects.requireNonNull(BlockList.C4_SMOKE.getRegistryName()));

    }
}
