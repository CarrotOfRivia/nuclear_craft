package com.song.nuclear_craft.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.util.ResourceLocation;

public class ScopeZoomGui extends AbstractGui implements IRenderable {
    private static final ResourceLocation ZOOM_TEXTURE = new ResourceLocation(NuclearCraft.MODID, "textures/gui/container/scope_zoom.png");
    public Minecraft mc;

    public ScopeZoomGui(Minecraft mc){
        this.mc = mc;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        drawGuiContainerBackgroundLayer(matrixStack, 0, 0, 0);
    }

    public void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, int i, int j, int xSize, int ySize) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.mc != null;
        this.mc.getTextureManager().bindTexture(ZOOM_TEXTURE);
        blit(matrixStack, 0, 0, this.getBlitOffset(), 0, 0, xSize, ySize, ySize, xSize);
    }
}
