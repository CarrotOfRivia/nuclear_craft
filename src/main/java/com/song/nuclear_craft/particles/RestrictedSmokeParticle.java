package com.song.nuclear_craft.particles;

import com.song.nuclear_craft.entities.NukeExplosionHandler;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

public class RestrictedSmokeParticle extends SpriteTexturedParticle {
    private double yLim;
    private double xLim;

    private double xInit;
    private double yInit;
    private double zInit;

    private double xMotionInit;
    private double yMotionInit;
    private double zMotionInit;

    private boolean canChangeColor=false;

    protected RestrictedSmokeParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
    }

    protected RestrictedSmokeParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ, double xLim, double yLim, double scale, boolean canChangeColor) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.xInit = x;
        this.yInit = y;
        this.zInit = z;

        this.xLim = xLim;
        this.yLim = yLim;
        this.multiplyParticleScaleBy((float) scale);
        this.maxAge=3000;
        this.particleGravity=0;

        this.xMotionInit = motionX;
        this.yMotionInit = motionY;
        this.zMotionInit = motionZ;

        this.canChangeColor = canChangeColor;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.posY-yInit < yLim){
            this.motionX = xMotionInit;
            this.motionY = yMotionInit;
            this.motionZ = zMotionInit;
        }
        else {
            this.setExpired();
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

        if((this.posX-xInit)*(this.posX-xInit)+(this.posZ-zInit)*(this.posZ-zInit)>xLim*xLim){
            this.setExpired();
        }
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        return 15728880;
    }

    public static class MushroomFactory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite iAnimatedSprite;

        public MushroomFactory(IAnimatedSprite iAnimatedSprite){
            this.iAnimatedSprite = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            float radius = NukeExplosionHandler.getBlastRadius();
            RestrictedSmokeParticle nukeParticle = new RestrictedSmokeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, radius, 2*radius, radius/3, false);
            nukeParticle.selectSpriteRandomly(this.iAnimatedSprite);
            nukeParticle.setColor(1.0F, 1.0F, 1.0F);
            return nukeParticle;
        }
    }

    public static class RestrictedHeightFactory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite iAnimatedSprite;

        public RestrictedHeightFactory(IAnimatedSprite iAnimatedSprite){
            this.iAnimatedSprite = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            float radius = NukeExplosionHandler.getBlastRadius();
            RestrictedSmokeParticle nukeParticle = new RestrictedSmokeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, radius, 2*radius, radius/3, true);
            nukeParticle.selectSpriteRandomly(this.iAnimatedSprite);
            nukeParticle.setColor(1.0F, 1.0F, 1.0F);
            return nukeParticle;
        }
    }
}
