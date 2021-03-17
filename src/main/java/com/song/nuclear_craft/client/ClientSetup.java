package com.song.nuclear_craft.client;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.container.C4BombContainerScreen;
import com.song.nuclear_craft.blocks.container.ContainerTypeList;
import com.song.nuclear_craft.entities.EntityRegister;
import com.song.nuclear_craft.events.ClientEventBusSubscriber;
import com.song.nuclear_craft.events.ClientEventForgeSubscriber;
import com.song.nuclear_craft.misc.ConfigClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class ClientSetup {
    public static void clientSetup(final FMLClientSetupEvent event){
        // do something that can only be done on the client
        ClientEventForgeSubscriber.gunReload = new KeyBinding("key."+ NuclearCraft.MODID+".load_ammo", GLFW.GLFW_KEY_R, "key."+NuclearCraft.MODID+".categories"); // keyBinding is a static variable
        ClientEventForgeSubscriber.zoom = new KeyBinding("key."+NuclearCraft.MODID+".zoom", GLFW.GLFW_KEY_Z, "key."+NuclearCraft.MODID+".categories");
        ClientRegistry.registerKeyBinding(ClientEventForgeSubscriber.gunReload);
        ClientRegistry.registerKeyBinding(ClientEventForgeSubscriber.zoom);

        ScreenManager.registerFactory(ContainerTypeList.C4_BOMB_CONTAINER_TYPE, C4BombContainerScreen::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.BULLET_ENTITY.get(),
                renderManager -> new SpriteRenderer<>(renderManager,  Minecraft.getInstance().getItemRenderer())
        );

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigClient.CONFIG);

        MinecraftForge.EVENT_BUS.register(new ClientEventForgeSubscriber());
        MinecraftForge.EVENT_BUS.register(new ClientEventBusSubscriber());
    }
}
