package com.song.nuclear_craft.villagers;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoPossibleCombination;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import com.song.nuclear_craft.items.ItemList;
import net.minecraft.item.Items;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NuclearCraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TradesRegistration {

    @SubscribeEvent
    public static void registerTrades(VillagerTradesEvent event){
        if (event.getType() == ProfessionTypes.RIFLE_AMMO_SELLER_PROFESSION.get()){
            for (AmmoSize ammoSize: AmmoPossibleCombination.RIFLE_AMMO.getAmmoSizes()){
                event.getTrades().get(1).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.IRON_INGOT, 1,1).setPrice2(Items.GUNPOWDER, 1,1).
                        setForSale(ItemList.AMMO_REGISTRIES_TYPE.get(ammoSize).get(AmmoType.NORMAL).get(), 6, 10).build()));
                event.getTrades().get(1).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.IRON_INGOT, 1,1).setPrice2(Items.GUNPOWDER, 2,2).
                        setForSale(ItemList.AMMO_REGISTRIES_TYPE.get(ammoSize).get(AmmoType.INCENDIARY).get(), 6, 10).build()));

                event.getTrades().get(2).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.IRON_INGOT, 1,1).setPrice2(Items.GUNPOWDER, 2,2).
                        setForSale(ItemList.AMMO_REGISTRIES_TYPE.get(ammoSize).get(AmmoType.ANTI_GRAVITY).get(), 4, 8).build()));
                event.getTrades().get(2).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.IRON_INGOT, 1,1).setPrice2(Items.GUNPOWDER, 2,2).
                        setForSale(ItemList.AMMO_REGISTRIES_TYPE.get(ammoSize).get(AmmoType.SILVER).get(), 4, 8).build()));

                event.getTrades().get(3).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.IRON_INGOT, 2,2).setPrice2(Items.GUNPOWDER, 10,10).
                        setForSale(ItemList.AMMO_REGISTRIES_TYPE.get(ammoSize).get(AmmoType.EXPLOSIVE).get(), 4, 6).build()));

                event.getTrades().get(4).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.IRON_INGOT, 5,5).setPrice2(Items.GUNPOWDER, 2,2).
                        setForSale(ItemList.AMMO_REGISTRIES_TYPE.get(ammoSize).get(AmmoType.TUNGSTEN).get(), 4, 6).build()));

                event.getTrades().get(5).add((new RandomTradeBuilder(1, 25, 0.05F).setPrice(Items.NETHER_STAR, 1,1).setPrice2(Items.DIAMOND_BLOCK, 1,1).
                        setForSale(ItemList.AMMO_REGISTRIES_TYPE.get(ammoSize).get(AmmoType.NUKE).get(), 32, 32).build()));
            }
        }

        if (event.getType() == ProfessionTypes.SHOTGUN_AMMO_SELLER_PROFESSION.get()){
            for (AmmoSize ammoSize: AmmoPossibleCombination.SHOTGUN_AMMO.getAmmoSizes()){
                event.getTrades().get(1).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.IRON_INGOT, 1,1).setPrice2(Items.GUNPOWDER, 1,1).
                        setForSale(ItemList.AMMO_REGISTRIES_TYPE.get(ammoSize).get(AmmoType.SHORT_GUN_NORMAL).get(), 6, 10).build()));

                event.getTrades().get(2).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.IRON_INGOT, 1,1).setPrice2(Items.GUNPOWDER, 2,2).
                        setForSale(ItemList.AMMO_REGISTRIES_TYPE.get(ammoSize).get(AmmoType.SHORT_GUN_BLIGHT).get(), 8, 8).build()));

                event.getTrades().get(3).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.IRON_INGOT, 5,5).setPrice2(Items.GUNPOWDER, 2,2).
                        setForSale(ItemList.AMMO_REGISTRIES_TYPE.get(ammoSize).get(AmmoType.SHORT_GUN_DESOLATOR).get(), 4, 4).build()));
            }
        }

        if (event.getType() == ProfessionTypes.GUN_SELLER_PROFESSION.get()){
            for (AmmoSize ammoSize: AmmoPossibleCombination.SHOTGUN_AMMO.getAmmoSizes()){
                event.getTrades().get(1).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.DIAMOND, 10,10).
                        setForSale(ItemList.GLOCK.get(), 1, 1).build()));
                event.getTrades().get(1).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.DIAMOND, 10,10).
                        setForSale(ItemList.USP.get(), 1, 1).build()));

                event.getTrades().get(2).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.DIAMOND, 20,30).
                        setForSale(ItemList.DESERT_EAGLE.get(), 1, 1).build()));
                event.getTrades().get(2).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.DIAMOND, 20,30).
                        setForSale(ItemList.NOVA.get(), 1, 1).build()));

                event.getTrades().get(3).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.DIAMOND, 30,40).
                        setForSale(ItemList.M4A4.get(), 1, 1).build()));
                event.getTrades().get(3).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.DIAMOND, 30,40).
                        setForSale(ItemList.XM1014.get(), 1, 1).build()));

                event.getTrades().get(4).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.DIAMOND, 40,50).
                        setForSale(ItemList.AK47.get(), 1, 1).build()));
                event.getTrades().get(4).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.DIAMOND, 40,50).
                        setForSale(ItemList.AWP.get(), 1, 1).build()));

                event.getTrades().get(4).add((new RandomTradeBuilder(64, 25, 0.05F).setPrice(Items.DIAMOND, 50,64).
                        setForSale(ItemList.BARRETT.get(), 1, 1).build()));
            }
        }
    }
}
