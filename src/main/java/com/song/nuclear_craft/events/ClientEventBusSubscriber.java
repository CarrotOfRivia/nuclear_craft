package com.song.nuclear_craft.events;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = NuclearCraft.MODID, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public void clientSetup(FMLClientSetupEvent event){

//        RenderingRegistry.registerEntityRenderingHandler(EntityList.ATOMIC_BOMB_ENTITY, AtomicBombRenderer::new);
    }

}
