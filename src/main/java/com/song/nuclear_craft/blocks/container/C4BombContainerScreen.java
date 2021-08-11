package com.song.nuclear_craft.blocks.container;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class C4BombContainerScreen extends AbstractContainerScreen<C4BombContainer> {
    public int i;
    public int j;

    private static final ResourceLocation C4_GUI_TEXTURES = new ResourceLocation(NuclearCraft.MODID, "textures/gui/container/c4_bomb_container.png");

    public C4BombContainerScreen(C4BombContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouse_x, int mouse_y) {
        i = (this.width - this.imageWidth) / 2;
        j = (this.height - this.imageHeight) / 2;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        assert this.minecraft != null;
//        this.minecraft.getTextureManager().bindForSetup(C4_GUI_TEXTURES);
        RenderSystem.setShaderTexture(0, C4_GUI_TEXTURES);
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int x, int y) {
//        this.font.draw(matrixStack, this.title, 33, 28, 4210752);
        this.font.draw(matrixStack, this.menu.tileEntity.inputPanel+" s", 83f, 13f, 4210752);
        if (this.menu.tileEntity.isActive()){
            this.font.draw(matrixStack, new TranslatableComponent("menu."+NuclearCraft.MODID+".c4_bomb.counter").getString()+this.menu.tileEntity.getCounter()+" s", 33f, 33f, 4210752);
        }
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
