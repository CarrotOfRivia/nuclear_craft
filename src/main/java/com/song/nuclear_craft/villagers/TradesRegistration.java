package com.song.nuclear_craft.villagers;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.ConfigCommon;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mod.EventBusSubscriber(modid = NuclearCraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TradesRegistration {
    @SubscribeEvent
    public static void registerTrades(VillagerTradesEvent event){
        HashMap<String, List<NCTradingRecipe>> TYPE_TRADING_MAP=new HashMap<>();
        for(NCTradingRecipe recipe: ConfigCommon.NCTradings){
            if(recipe.getItem1()==null && recipe.getItem2() == null){
                continue;
            }
            String key  = recipe.getStringProfession();
            if (!TYPE_TRADING_MAP.containsKey(key)) {
                TYPE_TRADING_MAP.put(key, new ArrayList<>());
            }
            TYPE_TRADING_MAP.get(key).add(recipe);
        }

        if(TYPE_TRADING_MAP.containsKey(event.getType().toString())){
            for (NCTradingRecipe recipe: TYPE_TRADING_MAP.get(event.getType().toString())){
                event.getTrades().get((int)recipe.level.get()).add(
                        new RandomTradeBuilder(64, 25, 0.05f)
                                .setPrice(recipe.getItem1(), recipe.price1Min.get(), recipe.price1Max.get())
                                .setPrice2(recipe.getItem2(), recipe.price2Min.get(), recipe.price2Max.get())
                                .setForSale(recipe.getOutput(), recipe.outputMin.get(), recipe.outputMax.get())
                                .build());
            }
        }
    }
}
