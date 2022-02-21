package com.song.nuclear_craft.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.container.C4BombContainerScreen;
import com.song.nuclear_craft.client.ScopeZoomGui;
import com.song.nuclear_craft.items.guns.AbstractGunItem;
import com.song.nuclear_craft.misc.SoundEventList;
import com.song.nuclear_craft.network.C4BombSettingPacket;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NuclearCraft.MODID, value = Dist.CLIENT)
public class ClientEventForgeSubscriber {

    private static final ResourceLocation BUTTON_X = new ResourceLocation(NuclearCraft.MODID, "textures/gui/button/button_x.png");
    private static final ResourceLocation BUTTON_SKELETON = new ResourceLocation(NuclearCraft.MODID, "textures/gui/button/button_skeleton.png");
    public static KeyMapping gunReload;
    public static KeyMapping zoom;

    private static int zoomState = 0;
    private static int prevZoomState = 0;
    private static double mouseSensitivityBefore = Minecraft.getInstance().options.sensitivity;
    private static final float fovBefore = 1f;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderWorldLastEvent(final RenderLevelLastEvent event){
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        MultiBufferSource.BufferSource renderTypeBuffer = Minecraft.getInstance().renderBuffers().bufferSource();
        PoseStack matrixStack = event.getPoseStack();
        matrixStack.pushPose();
        VertexConsumer builder = renderTypeBuffer.getBuffer(RenderType.translucent());
        builder.color(255, 0f, 0f, 255);
        renderTypeBuffer.endBatch(RenderType.translucent());
        matrixStack.popPose();
    }

    @SubscribeEvent
    public void onInitGuiEvent(final ScreenEvent.InitScreenEvent event){
        Screen gui = event.getScreen();
        if (gui instanceof C4BombContainerScreen){
            int i = (gui.width - ((C4BombContainerScreen) gui).getXSize()) / 2;
            int j = (gui.height - ((C4BombContainerScreen) gui).getYSize()) / 2;
            int input_id = 1;
            for (int row=0; row<=2; row++){
                for(int col=0; col<=3; col++){
                    int finalInput_id = input_id;
                    event.addListener(new Button(i+14+col*30, j+57+row*30, 20, 20,
                            new TextComponent(""+input_id), button-> {
                        NuclearCraftPacketHandler.C4_SETTING_CHANNEL.sendToServer(new C4BombSettingPacket(((C4BombContainerScreen) gui).getMenu().tileEntity.getBlockPos(), "add_"+ finalInput_id));
                    }));
                    input_id++;
                    if (row==2) {
                        row++;
                        break;
                    }
                }
            }
            event.addListener(new Button(i+44, j+117, 20, 20,
                    new TextComponent("0"), button-> {
                NuclearCraftPacketHandler.C4_SETTING_CHANNEL.sendToServer(new C4BombSettingPacket(((C4BombContainerScreen) gui).getMenu().tileEntity.getBlockPos(), "add_"+ 0));
            }));
            event.addListener(new ImageButton(i+74, j+117, 20, 20, 0, 0, 2, BUTTON_X, 20, 20,
                    button-> {
                NuclearCraftPacketHandler.C4_SETTING_CHANNEL.sendToServer(new C4BombSettingPacket(((C4BombContainerScreen) gui).getMenu().tileEntity.getBlockPos(), "delete"));
            }));
            event.addListener(new ImageButton(i+104, j+117, 20, 20, 0, 0, 2, BUTTON_SKELETON, 20, 20,
                    button-> {
                NuclearCraftPacketHandler.C4_SETTING_CHANNEL.sendToServer(new C4BombSettingPacket(((C4BombContainerScreen) gui).getMenu().tileEntity.getBlockPos(), "activate"));
            }));
        }
    }

    @SubscribeEvent
    public void onFovUpdate(final FOVModifierEvent event){
        Player entity = event.getEntity();
        Item item = entity.getMainHandItem().getItem();
        if(zoom.consumeClick()){
            if(item instanceof AbstractGunItem && (((AbstractGunItem) item).canUseScope())) {
                zoomState = (zoomState+1)%3;
                entity.playSound(SoundEventList.ZOOM, 1f, 1f);
            }
        }
        else if(!(item instanceof AbstractGunItem && (((AbstractGunItem) item).canUseScope()))){
            zoomState = 0;
        }


        switch (zoomState){
            case 0:
            default:
                if(prevZoomState != 0){
                    Minecraft.getInstance().options.sensitivity = mouseSensitivityBefore;
                }
                else{
                    mouseSensitivityBefore = Minecraft.getInstance().options.sensitivity;
                }
                prevZoomState = 0;
                break;
            case 1:
                event.setNewfov(0.33f * fovBefore);
                Minecraft.getInstance().options.sensitivity = 0.33*mouseSensitivityBefore;
                prevZoomState = 1;
                break;
            case 2:
                event.setNewfov(0.1f * fovBefore);
                Minecraft.getInstance().options.sensitivity = 0.1*mouseSensitivityBefore;
                prevZoomState = 2;
                break;
        }

    }

    @SubscribeEvent
    public void onOverlayRender(final RenderGameOverlayEvent event){
        if(zoomState>0){
            Window window = event.getWindow();
            new ScopeZoomGui(Minecraft.getInstance()).drawGuiContainerBackgroundLayer(event.getMatrixStack(), window.getX(), window.getY(), window.getGuiScaledWidth(), window.getGuiScaledHeight());
        }
    }

    @SubscribeEvent
    public void onPlayerRender(final RenderPlayerEvent event){
        Player playerEntity = event.getPlayer();
        if(playerEntity.getMainHandItem().getItem() instanceof AbstractGunItem){
            if(event.isCancelable()){
                // TODO do something to render correct pose
                event.getRenderer().getModel().rightArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
                event.getRenderer().getModel().rightArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }
        }
    }

    @SubscribeEvent
    public void onHandRender(final RenderHandEvent event){
        if(zoomState>0){
            event.setCanceled(true);
        }
    }

}
