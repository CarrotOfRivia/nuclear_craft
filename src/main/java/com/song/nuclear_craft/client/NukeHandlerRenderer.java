package com.song.nuclear_craft.client;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class NukeHandlerRenderer extends EntityRenderer<Entity> {
    private static final ResourceLocation DUMMY = new ResourceLocation("textures/entity/zombie/zombie.png");
    protected NukeHandlerRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity p_114482_) {
        return DUMMY;
    }
}
