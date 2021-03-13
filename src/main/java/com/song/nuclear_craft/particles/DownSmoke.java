package com.song.nuclear_craft.particles;

import com.song.nuclear_craft.entities.NukeExplosionHandler;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

public class DownSmoke extends SpriteTexturedParticle {
    protected DownSmoke(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
    }

    protected DownSmoke(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
    }

    protected DownSmoke(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ, float scale) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.multiplyParticleScaleBy(scale);
        this.maxAge=200;
        this.particleGravity=0.5f;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }

    @Override
    public void tick() {
        super.tick();
        this.setColor(particleRed*0.99f, 0, 0);
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
            DownSmoke downSmoke = new DownSmoke(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, radius/3);
            downSmoke.selectSpriteRandomly(this.iAnimatedSprite);
            downSmoke.setColor(1.0F, .0F, .0F);
            return downSmoke;
        }
    }
}
