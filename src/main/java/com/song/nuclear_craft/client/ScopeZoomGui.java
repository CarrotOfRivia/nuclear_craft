package com.song.nuclear_craft.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.CarvedPumpkinBlock;

public class ScopeZoomGui extends GuiComponent implements Widget {
    private static final ResourceLocation ZOOM_TEXTURE = new ResourceLocation(NuclearCraft.MODID, "textures/gui/container/scope_zoom.png");
    public Minecraft mc;

    public ScopeZoomGui(Minecraft mc){
        this.mc = mc;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        drawGuiContainerBackgroundLayer(matrixStack, 0, 0, 0);
    }

    public void drawGuiContainerBackgroundLayer(PoseStack matrixStack, int i, int j, int xSize, int ySize) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.mc != null;
//        this.mc.getTextureManager().bindForSetup(ZOOM_TEXTURE);
        RenderSystem.setShaderTexture(0, ZOOM_TEXTURE);
        blit(matrixStack, 0, 0, this.getBlitOffset(), 0, 0, xSize, ySize, xSize, ySize);
    }
}
