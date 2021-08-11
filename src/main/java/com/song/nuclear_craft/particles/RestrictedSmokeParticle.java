package com.song.nuclear_craft.particles;

import com.song.nuclear_craft.entities.NukeExplosionHandler;
import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

public class RestrictedSmokeParticle extends TextureSheetParticle {
    private double yLim;
    private double xLim;

    private double xInit;
    private double yInit;
    private double zInit;

    private double xMotionInit;
    private double yMotionInit;
    private double zMotionInit;

    private boolean canChangeColor=false;

    protected RestrictedSmokeParticle(ClientLevel world, double x, double y, double z) {
        super(world, x, y, z);
    }

    protected RestrictedSmokeParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ, double xLim, double yLim, double scale, boolean canChangeColor) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.xInit = x;
        this.yInit = y;
        this.zInit = z;

        this.xLim = xLim;
        this.yLim = yLim;
        this.scale((float) scale);
        this.lifetime=3000;
        this.gravity=0;

        this.xMotionInit = motionX;
        this.yMotionInit = motionY;
        this.zMotionInit = motionZ;

        this.canChangeColor = canChangeColor;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.y-yInit < yLim){
            this.xd = xMotionInit;
            this.yd = yMotionInit;
            this.zd = zMotionInit;
        }
        else {
            this.remove();
        }

        if (canChangeColor){
            float factor = 480f;
            if (this.age > factor){
                this.setColor(1f, 1f, 1f);
            }
            else {
                this.setColor(1f, this.age/480f, this.age/480f);
            }
        }

        if((this.x-xInit)*(this.x-xInit)+(this.z-zInit)*(this.z-zInit)>xLim*xLim){
            this.remove();
        }
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    public static class MushroomFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet iAnimatedSprite;

        public MushroomFactory(SpriteSet iAnimatedSprite){
            this.iAnimatedSprite = iAnimatedSprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            float radius = NukeExplosionHandler.getBlastRadius();
            RestrictedSmokeParticle nukeParticle = new RestrictedSmokeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, radius, 2*radius, radius/3, false);
            nukeParticle.pickSprite(this.iAnimatedSprite);
            nukeParticle.setColor(1.0F, 1.0F, 1.0F);
            return nukeParticle;
        }
    }

    public static class RestrictedHeightFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet iAnimatedSprite;

        public RestrictedHeightFactory(SpriteSet iAnimatedSprite){
            this.iAnimatedSprite = iAnimatedSprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            float radius = NukeExplosionHandler.getBlastRadius();
            RestrictedSmokeParticle nukeParticle = new RestrictedSmokeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, radius, 2*radius, radius/3, true);
            nukeParticle.pickSprite(this.iAnimatedSprite);
            nukeParticle.setColor(1.0F, 1.0F, 1.0F);
            return nukeParticle;
        }
    }
}
