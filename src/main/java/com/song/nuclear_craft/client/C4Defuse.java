package com.song.nuclear_craft.client;

import com.song.nuclear_craft.network.BombDefuseProgressPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;

public class C4Defuse {
    public static void handleDefuseProgress(BombDefuseProgressPacket packet){
        if(packet.currentTick >= 0){
            float defuseSecond = packet.defuseTick/20f;
            float currentSecond = packet.currentTick/20f;
            Minecraft.getInstance().gui.setOverlayMessage(new TextComponent(String.format("%.2f s / %.2f s", currentSecond, defuseSecond)), false);
        }
        else{
            Minecraft.getInstance().gui.setOverlayMessage(new TextComponent("---------------------------"), false);
        }
    }
}
