package com.song.nuclear_craft.particles;

import com.song.nuclear_craft.entities.NukeExplosionHandler;
import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.levelgen.Heightmap;

public class ShockWaveParticle extends TextureSheetParticle {
    private double xMotionInit;
    private double yMotionInit;
    private double zMotionInit;
    protected ShockWaveParticle(ClientLevel world, double x, double y, double z) {
        super(world, x, y, z);
    }

    protected ShockWaveParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
    }

    protected ShockWaveParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ, float scale) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.scale(scale);
        this.lifetime=25;
//        this.particleGravity=0.5f;

        this.xMotionInit = motionX;
        this.yMotionInit = motionY;
        this.zMotionInit = motionZ;

        this.hasPhysics = false;
    }

    @Override
    public void tick() {
        super.tick();
        this.xd = xMotionInit;
        this.yd = yMotionInit;
        this.zd = zMotionInit;
//        this.setColor(particleRed*0.99f, 0, 0);
        this.y = this.level.getHeight(Heightmap.Types.WORLD_SURFACE, (int)this.x, (int)this.z) + 3;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType>{
        private final SpriteSet iAnimatedSprite;

        public Factory(SpriteSet iAnimatedSprite){
            this.iAnimatedSprite = iAnimatedSprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            float radius = NukeExplosionHandler.getBlastRadius();
            ShockWaveParticle downSmoke = new ShockWaveParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, radius/2);
            downSmoke.pickSprite(this.iAnimatedSprite);
            downSmoke.setColor(74/256f, 82/256f, 76/256f);
            return downSmoke;
        }
    }
}
