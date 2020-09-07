package com.song.nuclear_craft.misc;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.container.C4BombContainer;
import com.song.nuclear_craft.blocks.container.C4BombContainerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.gui.screen.ModListScreen;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NuclearCraft.MODID, value = Dist.CLIENT)
public class ClientEventForgeSubscriber {

        @SubscribeEvent
    public static void onInitGuiEvent(final GuiScreenEvent.InitGuiEvent event){
        Screen gui = event.getGui();
        if (gui instanceof C4BombContainerScreen){
            int i = (gui.width - ((C4BombContainerScreen) gui).getXSize()) / 2;
            int j = (gui.height - ((C4BombContainerScreen) gui).getYSize()) / 2;
            int input_id = 1;
            for (int row=0; row<=1; row++){
                for(int col=0; col<=3; col++){
                    event.addWidget(new Button(i+14+col*30, j+57+row*30, 20, 20,
                            new StringTextComponent(""+input_id), button-> {}));
                    input_id++;
                }
            }
            event.addWidget(new Button(i+14, j+121, 20, 20,
                    new StringTextComponent("9"), button-> {}));
            event.addWidget(new Button(i+44, j+121, 35, 20,
                    new StringTextComponent(""), button-> {}));
            event.addWidget(new Button(i+89, j+121, 35, 20,
                    new StringTextComponent(""), button-> {}));
        }
    }
}
