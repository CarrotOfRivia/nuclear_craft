package com.song.nuclear_craft.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.container.C4BombContainerScreen;
import com.song.nuclear_craft.client.ScopeZoomGui;
import com.song.nuclear_craft.items.guns.AbstractGunItem;
import com.song.nuclear_craft.misc.SoundEventList;
import com.song.nuclear_craft.network.C4BombSettingPacket;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NuclearCraft.MODID, value = Dist.CLIENT)
public class ClientEventForgeSubscriber {

    private static final ResourceLocation BUTTON_X = new ResourceLocation(NuclearCraft.MODID, "textures/gui/button/button_x.png");
    private static final ResourceLocation BUTTON_SKELETON = new ResourceLocation(NuclearCraft.MODID, "textures/gui/button/button_skeleton.png");
    public static KeyBinding gunReload;
    public static KeyBinding zoom;

    private static int zoomState = 0;
    private static int prevZoomState = 0;
    private static double mouseSensitivityBefore = Minecraft.getInstance().gameSettings.mouseSensitivity;
    private static final float fovBefore = 1f;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderWorldLastEvent(final RenderWorldLastEvent event){
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        IRenderTypeBuffer.Impl renderTypeBuffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        MatrixStack matrixStack = event.getMatrixStack();
        matrixStack.push();
        IVertexBuilder builder = renderTypeBuffer.getBuffer(RenderType.getTranslucent());
        builder.color(255, 0f, 0f, 255);
        renderTypeBuffer.finish(RenderType.getTranslucent());
        matrixStack.pop();
    }

    @SubscribeEvent
    public void onInitGuiEvent(final GuiScreenEvent.InitGuiEvent event){
        Screen gui = event.getGui();
        if (gui instanceof C4BombContainerScreen){
            int i = (gui.width - ((C4BombContainerScreen) gui).getXSize()) / 2;
            int j = (gui.height - ((C4BombContainerScreen) gui).getYSize()) / 2;
            int input_id = 1;
            for (int row=0; row<=2; row++){
                for(int col=0; col<=3; col++){
                    int finalInput_id = input_id;
                    event.addWidget(new Button(i+14+col*30, j+57+row*30, 20, 20,
                            new StringTextComponent(""+input_id), button-> {
                        NuclearCraftPacketHandler.C4_SETTING_CHANNEL.sendToServer(new C4BombSettingPacket(((C4BombContainerScreen) gui).getContainer().tileEntity.getPos(), "add_"+ finalInput_id));
                    }));
                    input_id++;
                    if (row==2) {
                        row++;
                        break;
                    }
                }
            }
            event.addWidget(new Button(i+44, j+117, 20, 20,
                    new StringTextComponent("0"), button-> {
                NuclearCraftPacketHandler.C4_SETTING_CHANNEL.sendToServer(new C4BombSettingPacket(((C4BombContainerScreen) gui).getContainer().tileEntity.getPos(), "add_"+ 0));
            }));
            event.addWidget(new ImageButton(i+74, j+117, 20, 20, 0, 0, 2, BUTTON_X, 20, 20,
                    button-> {
                NuclearCraftPacketHandler.C4_SETTING_CHANNEL.sendToServer(new C4BombSettingPacket(((C4BombContainerScreen) gui).getContainer().tileEntity.getPos(), "delete"));
            }));
            event.addWidget(new ImageButton(i+104, j+117, 20, 20, 0, 0, 2, BUTTON_SKELETON, 20, 20,
                    button-> {
                NuclearCraftPacketHandler.C4_SETTING_CHANNEL.sendToServer(new C4BombSettingPacket(((C4BombContainerScreen) gui).getContainer().tileEntity.getPos(), "activate"));
            }));
        }
    }

    @SubscribeEvent
    public void onFovUpdate(final FOVUpdateEvent event){
        PlayerEntity entity = event.getEntity();
        Item item = entity.getHeldItemMainhand().getItem();
        if(zoom.isPressed()){
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
                    Minecraft.getInstance().gameSettings.mouseSensitivity = mouseSensitivityBefore;
                }
                else{
                    mouseSensitivityBefore = Minecraft.getInstance().gameSettings.mouseSensitivity;
                }
                prevZoomState = 0;
                break;
            case 1:
                event.setNewfov(0.33f * fovBefore);
                Minecraft.getInstance().gameSettings.mouseSensitivity = 0.33*mouseSensitivityBefore;
                prevZoomState = 1;
                break;
            case 2:
                event.setNewfov(0.1f * fovBefore);
                Minecraft.getInstance().gameSettings.mouseSensitivity = 0.1*mouseSensitivityBefore;
                prevZoomState = 2;
                break;
        }

    }

    @SubscribeEvent
    public void onOverlayRender(final RenderGameOverlayEvent event){
        if(zoomState>0){
            MainWindow window = event.getWindow();
            new ScopeZoomGui(Minecraft.getInstance()).drawGuiContainerBackgroundLayer(event.getMatrixStack(), window.getWindowX(), window.getWindowY(), window.getScaledWidth(), window.getScaledHeight());
        }
    }

    @SubscribeEvent
    public void onPlayerRender(final RenderPlayerEvent event){
        PlayerEntity playerEntity = event.getPlayer();
        if(playerEntity.getHeldItemMainhand().getItem() instanceof AbstractGunItem){
            if(event.isCancelable()){
                // TODO do something to render correct pose
                event.getRenderer().getEntityModel().rightArmPose = BipedModel.ArmPose.CROSSBOW_HOLD;
                event.getRenderer().getEntityModel().rightArmPose = BipedModel.ArmPose.CROSSBOW_HOLD;
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
