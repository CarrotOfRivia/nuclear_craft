package com.song.nuclear_craft.particles;

import com.song.nuclear_craft.entities.NukeExplosionHandler;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.gen.Heightmap;

public class ShockWaveParticle extends SpriteTexturedParticle {
    private double xMotionInit;
    private double yMotionInit;
    private double zMotionInit;
    protected ShockWaveParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
    }

    protected ShockWaveParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
    }

    protected ShockWaveParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ, float scale) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.multiplyParticleScaleBy(scale);
        this.maxAge=25;
//        this.particleGravity=0.5f;

        this.xMotionInit = motionX;
        this.yMotionInit = motionY;
        this.zMotionInit = motionZ;

        this.canCollide = false;
    }

    @Override
    public void tick() {
        super.tick();
        this.motionX = xMotionInit;
        this.motionY = yMotionInit;
        this.motionZ = zMotionInit;
//        this.setColor(particleRed*0.99f, 0, 0);
        this.posY = this.world.getHeight(Heightmap.Type.WORLD_SURFACE, (int)this.posX, (int)this.posZ) + 3;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements IParticleFactory<BasicParticleType>{
        private final IAnimatedSprite iAnimatedSprite;

        public Factory(IAnimatedSprite iAnimatedSprite){
            this.iAnimatedSprite = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            float radius = NukeExplosionHandler.getBlastRadius();
            ShockWaveParticle downSmoke = new ShockWaveParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, radius/2);
            downSmoke.selectSpriteRandomly(this.iAnimatedSprite);
            return downSmoke;
        }
    }
}
