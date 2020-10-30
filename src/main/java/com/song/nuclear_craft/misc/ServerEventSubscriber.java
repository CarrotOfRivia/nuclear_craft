package com.song.nuclear_craft.misc;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NuclearCraft.MODID, value = Dist.CLIENT)
public class ServerEventSubscriber {

    @SubscribeEvent
    public static void onEntityDamaged(final LivingDamageEvent event){

    }
}
