package com.song.nuclear_craft.blocks.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class C4BombContainerScreen extends ContainerScreen<C4BombContainer> {
    public int i;
    public int j;

    private static final ResourceLocation C4_GUI_TEXTURES = new ResourceLocation(NuclearCraft.MODID, "textures/gui/container/c4_bomb_container.png");

    public C4BombContainerScreen(C4BombContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouse_x, int mouse_y) {
        i = (this.width - this.xSize) / 2;
        j = (this.height - this.ySize) / 2;
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bindTexture(C4_GUI_TEXTURES);
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
//        this.font.func_243248_b(matrixStack, this.title, 33, 28, 4210752);
        this.font.drawString(matrixStack, this.container.tileEntity.inputPanel+" s", 83f, 13f, 4210752);
        if (this.container.tileEntity.isActive()){
            this.font.drawString(matrixStack, new TranslationTextComponent("menu."+NuclearCraft.MODID+".c4_bomb.counter").getString()+this.container.tileEntity.getCounter()+" s", 33f, 33f, 4210752);
        }
    }
}
