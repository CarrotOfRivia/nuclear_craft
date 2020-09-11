package com.song.nuclear_craft.misc;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.container.C4BombContainerScreen;
import com.song.nuclear_craft.blocks.container.ContainerTypeList;
import com.song.nuclear_craft.entities.EntityList;
import com.song.nuclear_craft.entities.renderers.AtomicBombRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = NuclearCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void clientSetup(FMLClientSetupEvent event){
        ScreenManager.registerFactory(ContainerTypeList.C4_BOMB_CONTAINER_TYPE, C4BombContainerScreen::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityList.BULLET_ENTITY,
                renderManager -> new SpriteRenderer<>(renderManager,  Minecraft.getInstance().getItemRenderer())
                );

        RenderingRegistry.registerEntityRenderingHandler(EntityList.ATOMIC_BOMB_ENTITY, AtomicBombRenderer::new);
    }

}
