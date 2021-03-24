package com.song.nuclear_craft.villagers;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoPossibleCombination;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import com.song.nuclear_craft.items.ItemList;
import com.song.nuclear_craft.misc.ConfigCommon;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = NuclearCraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TradesRegistration {

    @SubscribeEvent
    public static void registerTrades(VillagerTradesEvent event){
        if (event.getType() == ProfessionTypes.RIFLE_AMMO_SELLER_PROFESSION.get()){
            for (AmmoSize ammoSize: AmmoPossibleCombination.RIFLE_AMMO.getAmmoSizes()) {
                for (AmmoType ammoType : AmmoPossibleCombination.RIFLE_AMMO.getAmmoTypes()) {
                    Item item = ItemList.AMMO_REGISTRIES_TYPE.get(ammoSize).get(ammoType).get();
                    String key = Objects.requireNonNull(item.getRegistryName()).toString();
                    String p1 = ConfigCommon.PRICE1_MAP.get(key).get();
                    String p2 = ConfigCommon.PRICE2_MAP.get(key).get();
                    if ((!"null".equals(p1) || (!"null".equals(p2)))) {
                        event.getTrades().get((int) ConfigCommon.LEVEL_MAP.get(key).get()).
                                add((new RandomTradeBuilder(64, 25, 0.05F).
                                        setPrice(ForgeRegistries.ITEMS.getValue(new ResourceLocation(p1)), ConfigCommon.PRICE1_MIN.get(key).get(), ConfigCommon.PRICE1_MAX.get(key).get()).
                                        setPrice2(ForgeRegistries.ITEMS.getValue(new ResourceLocation(p2)), ConfigCommon.PRICE2_MIN.get(key).get(), ConfigCommon.PRICE2_MAX.get(key).get()).
                                        setForSale(item, 6, 10).build()));
                    }
                }
            }
        }

        if (event.getType() == ProfessionTypes.SHOTGUN_AMMO_SELLER_PROFESSION.get()){
            for (AmmoSize ammoSize: AmmoPossibleCombination.SHOTGUN_AMMO.getAmmoSizes()) {
                for (AmmoType ammoType : AmmoPossibleCombination.SHOTGUN_AMMO.getAmmoTypes()) {
                    Item item = ItemList.AMMO_REGISTRIES_TYPE.get(ammoSize).get(ammoType).get();
                    String key = Objects.requireNonNull(item.getRegistryName()).toString();
                    String p1 = ConfigCommon.PRICE1_MAP.get(key).get();
                    String p2 = ConfigCommon.PRICE2_MAP.get(key).get();
                    if ((!"null".equals(p1) || (!"null".equals(p2)))) {
                        event.getTrades().get((int) ConfigCommon.LEVEL_MAP.get(key).get()).
                                add((new RandomTradeBuilder(64, 25, 0.05F).
                                        setPrice(ForgeRegistries.ITEMS.getValue(new ResourceLocation(p1)), ConfigCommon.PRICE1_MIN.get(key).get(), ConfigCommon.PRICE1_MAX.get(key).get()).
                                        setPrice2(ForgeRegistries.ITEMS.getValue(new ResourceLocation(p2)), ConfigCommon.PRICE2_MIN.get(key).get(), ConfigCommon.PRICE2_MAX.get(key).get()).
                                        setForSale(item, 6, 10).build()));
                    }
                }
            }
        }

        if (event.getType() == ProfessionTypes.GUN_SELLER_PROFESSION.get()){
            for (Item item: Arrays.asList(ItemList.GLOCK.get(), ItemList.USP.get(), ItemList.DESERT_EAGLE.get(), ItemList.NOVA.get(), ItemList.M4A4.get(), ItemList.XM1014.get(),
                    ItemList.AK47.get(), ItemList.AWP.get(), ItemList.BARRETT.get())){
                String key = Objects.requireNonNull(item.getRegistryName()).toString();
                String p1 = ConfigCommon.PRICE1_MAP.get(key).get();
                String p2 = ConfigCommon.PRICE2_MAP.get(key).get();
                if ((!"null".equals(p1)||(!"null".equals(p2)))){
                    event.getTrades().get((int)ConfigCommon.LEVEL_MAP.get(key).get()).
                            add((new RandomTradeBuilder(64, 25, 0.05F).
                                    setPrice(ForgeRegistries.ITEMS.getValue(new ResourceLocation(p1)), ConfigCommon.PRICE1_MIN.get(key).get(),ConfigCommon.PRICE1_MAX.get(key).get()).
                                    setPrice2(ForgeRegistries.ITEMS.getValue(new ResourceLocation(p2)), ConfigCommon.PRICE2_MIN.get(key).get(),ConfigCommon.PRICE2_MAX.get(key).get()).
                                    setForSale(item, 1, 1).build()));
                }
            }
        }

        if (event.getType() == ProfessionTypes.ROCKET_MASTER_PROFESSION.get()){
            for (Item item: Arrays.asList(ItemList.ROCKET_LAUNCHER.get(), ItemList.INCENDIARY_ROCKET.get(), ItemList.SMOKE_ROCKET.get(),
                    ItemList.HIGH_EXPLOSIVE_ROCKET.get(), ItemList.ATOMIC_BOMB_ROCKET.get(), ItemList.WATER_DROP_ROCKET.get())){
                String key = Objects.requireNonNull(item.getRegistryName()).toString();
                String p1 = ConfigCommon.PRICE1_MAP.get(key).get();
                String p2 = ConfigCommon.PRICE2_MAP.get(key).get();
                if ((!"null".equals(p1)||(!"null".equals(p2)))){
                    event.getTrades().get((int)ConfigCommon.LEVEL_MAP.get(key).get()).
                            add((new RandomTradeBuilder(64, 25, 0.05F).
                                    setPrice(ForgeRegistries.ITEMS.getValue(new ResourceLocation(p1)), ConfigCommon.PRICE1_MIN.get(key).get(),ConfigCommon.PRICE1_MAX.get(key).get()).
                                    setPrice2(ForgeRegistries.ITEMS.getValue(new ResourceLocation(p2)), ConfigCommon.PRICE2_MIN.get(key).get(),ConfigCommon.PRICE2_MAX.get(key).get()).
                                    setForSale(item, 1, 1).build()));
                }
            }
        }

        if (event.getType() == ProfessionTypes.EXPLOSIVE_MASTER_PROFESSION.get()){
            for (Item item: Arrays.asList(ItemList.C4_INCENDIARY, ItemList.C4_SMOKE, ItemList.C4_HIGH_EXPLOSIVE, ItemList.C4_ATOMIC_BOMB)){
                String key = Objects.requireNonNull(item.getRegistryName()).toString();
                String p1 = ConfigCommon.PRICE1_MAP.get(key).get();
                String p2 = ConfigCommon.PRICE2_MAP.get(key).get();
                if ((!"null".equals(p1)||(!"null".equals(p2)))){
                    event.getTrades().get((int)ConfigCommon.LEVEL_MAP.get(key).get()).
                            add((new RandomTradeBuilder(64, 25, 0.05F).
                                    setPrice(ForgeRegistries.ITEMS.getValue(new ResourceLocation(p1)), ConfigCommon.PRICE1_MIN.get(key).get(),ConfigCommon.PRICE1_MAX.get(key).get()).
                                    setPrice2(ForgeRegistries.ITEMS.getValue(new ResourceLocation(p2)), ConfigCommon.PRICE2_MIN.get(key).get(),ConfigCommon.PRICE2_MAX.get(key).get()).
                                    setForSale(item, 1, 1).build()));
                }
            }
        }

    }
}
