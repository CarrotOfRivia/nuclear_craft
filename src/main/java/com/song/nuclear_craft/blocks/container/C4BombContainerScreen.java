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

public class C4BombContainerScreen extends ContainerScreen<C4BombContainer> {

    private static final ResourceLocation C4_GUI_TEXTURES = new ResourceLocation(NuclearCraft.MODID, "textures/gui/container/c4_bomb_container.png");

    public C4BombContainerScreen(C4BombContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void func_230450_a_(MatrixStack matrixStack, float p_230450_2_, int mouse_x, int mouse_y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.field_230706_i_ != null;
        this.field_230706_i_.getTextureManager().bindTexture(C4_GUI_TEXTURES);
        int i = (this.field_230708_k_ - this.xSize) / 2;
        int j = (this.field_230709_l_ - this.ySize) / 2;
        this.func_238474_b_(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
        matrixStack.push();
        this.field_230712_o_.func_238421_b_(matrixStack, "00:00:40", i+53f, j+46f, 0x000000);
        matrixStack.pop();
    }

}
