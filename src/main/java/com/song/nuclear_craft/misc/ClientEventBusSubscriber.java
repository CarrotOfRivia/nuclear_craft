package com.song.nuclear_craft.misc;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.container.C4BombContainerScreen;
import com.song.nuclear_craft.blocks.container.ContainerTypeList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.client.gui.screen.ModListScreen;

@Mod.EventBusSubscriber(modid = NuclearCraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        ScreenManager.registerFactory(ContainerTypeList.C4_BOMB_CONTAINER_TYPE, C4BombContainerScreen::new);
    }

}
