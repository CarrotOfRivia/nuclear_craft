package com.song.nuclear_craft.events;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.container.C4BombContainerScreen;
import com.song.nuclear_craft.blocks.container.ContainerTypeList;
import com.song.nuclear_craft.entities.EntityRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = NuclearCraft.MODID, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public void clientSetup(FMLClientSetupEvent event){

//        RenderingRegistry.registerEntityRenderingHandler(EntityList.ATOMIC_BOMB_ENTITY, AtomicBombRenderer::new);
    }

}
