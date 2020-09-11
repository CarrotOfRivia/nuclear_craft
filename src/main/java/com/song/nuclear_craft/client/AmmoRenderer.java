package com.song.nuclear_craft.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.items.AbstractAmmo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;

public class AmmoRenderer extends SpriteRenderer<AbstractAmmoEntity> {
    public AmmoRenderer(EntityRendererManager p_i226035_1_, ItemRenderer p_i226035_2_, float scale, boolean p_i226035_4_) {
        super(p_i226035_1_, p_i226035_2_, scale, p_i226035_4_);
    }

    public AmmoRenderer(EntityRendererManager renderManagerIn, ItemRenderer itemRendererIn) {
        this(renderManagerIn, itemRendererIn, 1.0f, true);
    }

    @Override
    public void render(AbstractAmmoEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
