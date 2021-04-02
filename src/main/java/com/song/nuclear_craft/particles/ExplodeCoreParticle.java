package com.song.nuclear_craft.particles;

import com.song.nuclear_craft.entities.NukeExplosionHandler;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

import javax.annotation.Nullable;
import java.util.Random;

public class ExplodeCoreParticle extends SpriteTexturedParticle {
    private float scale1;
    private float scale2;
    private float growRate;
    private int maxSizeAge;
    private float shrinkRate;
    public ExplodeCoreParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
    }

    protected ExplodeCoreParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        return 15728880;
    }

    protected ExplodeCoreParticle(ClientWorld world, double x, double y, double z, double x_speed, double y_speed, double z_speed, int age, int maxAge, int maxSizeAge, float scale1, float scale2, float scale3){
        super(world, x, y, z, x_speed, y_speed, z_speed);
        this.age = age;
        this.maxAge = maxAge;
        this.maxSizeAge = maxSizeAge;
        this.particleScale = scale1;

        this.scale1 = scale1;
        this.scale2 = scale2;

        this.growRate = (scale2-scale1)/(maxSizeAge);
        this.shrinkRate = (scale2-scale3)/(maxAge-maxSizeAge);
    }

    @Override
    public void tick() {
        this.motionX=0;
        this.motionY=0;
        this.motionZ=0;
        super.tick();
        if(age<=maxSizeAge){
            this.particleScale= scale1 +age*growRate;
            this.setColor(1f, 1f-1.f*age/maxSizeAge, 1f-1.f*age/maxSizeAge);
        }
        else {
            this.setExpired();

            // TODO remove these
            this.particleScale = scale2 - (age-maxSizeAge)*shrinkRate;
            float rgb = 1-0.8f*(age-maxSizeAge)/(maxAge-maxSizeAge);
            this.setColor(rgb, 0, 0);
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements IParticleFactory<BasicParticleType>{
        private final IAnimatedSprite iAnimatedSprite;
        private static final Random random = new Random();

        public Factory(IAnimatedSprite iAnimatedSprite){
            this.iAnimatedSprite = iAnimatedSprite;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            float radius = NukeExplosionHandler.getStageOneTick();
            ExplodeCoreParticle particle = new ExplodeCoreParticle(worldIn, x, y, z, 0d, 0d, 0d, 0,
                    3000, NukeExplosionHandler.getStageOneTick()*3, 0.01f*radius, radius, 0.25f*radius);
            particle.selectSpriteRandomly(this.iAnimatedSprite);
            particle.setColor(1.0F, 1.0F, 1.0F);
            return particle;
        }
    }
}
