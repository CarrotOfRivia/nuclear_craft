package com.song.nuclear_craft.client;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.container.C4BombContainerScreen;
import com.song.nuclear_craft.blocks.container.ContainerTypeList;
import com.song.nuclear_craft.entities.EntityRegister;
import com.song.nuclear_craft.events.ClientEventBusSubscriber;
import com.song.nuclear_craft.events.ClientEventForgeSubscriber;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class ClientSetup {
    public static void clientSetup(final FMLClientSetupEvent event){
        // do something that can only be done on the client
        ClientEventForgeSubscriber.gunReload = new KeyMapping("key."+ NuclearCraft.MODID+".load_ammo", GLFW.GLFW_KEY_R, "key."+NuclearCraft.MODID+".categories"); // keyBinding is a static variable
        ClientEventForgeSubscriber.zoom = new KeyMapping("key."+NuclearCraft.MODID+".zoom", GLFW.GLFW_KEY_Z, "key."+NuclearCraft.MODID+".categories");
        ClientRegistry.registerKeyBinding(ClientEventForgeSubscriber.gunReload);
        ClientRegistry.registerKeyBinding(ClientEventForgeSubscriber.zoom);

        MenuScreens.register(ContainerTypeList.C4_BOMB_CONTAINER_TYPE, C4BombContainerScreen::new);

//        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.BULLET_ENTITY.get(),
//                renderManager -> new ThrownItemRenderer<>(renderManager,  Minecraft.getInstance().getItemRenderer())
//        );

        EntityRenderers.register(EntityRegister.BULLET_ENTITY.get(), ThrownItemRenderer::new);
        EntityRenderers.register(EntityRegister.NUKE_EXPLOSION_HANDLER_TYPE.get(), NukeHandlerRenderer::new);

        MinecraftForge.EVENT_BUS.register(new ClientEventForgeSubscriber());
        MinecraftForge.EVENT_BUS.register(new ClientEventBusSubscriber());
    }
}
