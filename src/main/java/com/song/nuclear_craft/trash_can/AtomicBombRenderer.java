//package com.song.nuclear_craft.trash_can;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import com.song.nuclear_craft.NuclearCraft;
//import com.song.nuclear_craft.blocks.BlockList;
//import com.song.nuclear_craft.entities.ExplosionUtils;
//import net.minecraft.client.renderer.IRenderTypeBuffer;
//import net.minecraft.client.renderer.entity.EntityRenderer;
//import net.minecraft.client.renderer.entity.EntityRendererManager;
//import net.minecraft.client.renderer.entity.TNTMinecartRenderer;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.vector.Vector3f;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//@OnlyIn(Dist.CLIENT)
//public class AtomicBombRenderer extends EntityRenderer<ExplosionUtils> {
//    public AtomicBombRenderer(EntityRendererManager renderManager) {
//        super(renderManager);
//    }
//
//    @Override
//    public ResourceLocation getEntityTexture(ExplosionUtils entity) {
//        return new ResourceLocation(NuclearCraft.MODID, "block/atomic_bomb");
//    }
//
//    @Override
//    public void render(ExplosionUtils entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
//        matrixStackIn.push();
//        matrixStackIn.translate(0.0D, 0.5D, 0.0D);
//        if ((float)entityIn.getFuse() - partialTicks + 1.0F < 10.0F) {
//            float f = 1.0F - ((float)entityIn.getFuse() - partialTicks + 1.0F) / 10.0F;
//            f = MathHelper.clamp(f, 0.0F, 1.0F);
//            f = f * f;
//            f = f * f;
//            float f1 = 1.0F + f * 0.3F;
//            matrixStackIn.scale(f1, f1, f1);
//        }
//
//        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-90.0F));
//        matrixStackIn.translate(-0.5D, -0.5D, 0.5D);
//        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
//        TNTMinecartRenderer.renderTntFlash(BlockList.ATOMIC_BOMB.getDefaultState(), matrixStackIn, bufferIn, packedLightIn, entityIn.getFuse() / 5 % 2 == 0);
//        matrixStackIn.pop();
//        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
//    }
//}
