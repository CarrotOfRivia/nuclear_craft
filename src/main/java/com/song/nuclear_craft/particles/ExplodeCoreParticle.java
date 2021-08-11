package com.song.nuclear_craft.particles;

import com.song.nuclear_craft.entities.NukeExplosionHandler;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

import javax.annotation.Nullable;
import java.util.Random;

public class ExplodeCoreParticle extends TextureSheetParticle {
    private float scale1;
    private float scale2;
    private float growRate;
    private int maxSizeAge;
    private float shrinkRate;
    public ExplodeCoreParticle(ClientLevel world, double x, double y, double z) {
        super(world, x, y, z);
    }

    protected ExplodeCoreParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    protected ExplodeCoreParticle(ClientLevel world, double x, double y, double z, double x_speed, double y_speed, double z_speed, int age, int maxAge, int maxSizeAge, float scale1, float scale2, float scale3){
        super(world, x, y, z, x_speed, y_speed, z_speed);
        this.age = age;
        this.lifetime = maxAge;
        this.maxSizeAge = maxSizeAge;
        this.quadSize = scale1;

        this.scale1 = scale1;
        this.scale2 = scale2;

        this.growRate = (scale2-scale1)/(maxSizeAge);
        this.shrinkRate = (scale2-scale3)/(maxAge-maxSizeAge);
    }

    @Override
    public void tick() {
        this.xd=0;
        this.yd=0;
        this.zd=0;
        super.tick();
        if(age<=maxSizeAge){
            this.quadSize= scale1 +age*growRate;
            this.setColor(1f, 1f-1.f*age/maxSizeAge, 1f-1.f*age/maxSizeAge);
        }
        else {
            this.remove();

            // TODO remove these
            this.quadSize = scale2 - (age-maxSizeAge)*shrinkRate;
            float rgb = 1-0.8f*(age-maxSizeAge)/(lifetime-maxSizeAge);
            this.setColor(rgb, 0, 0);
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType>{
        private final SpriteSet iAnimatedSprite;
        private static final Random random = new Random();

        public Factory(SpriteSet iAnimatedSprite){
            this.iAnimatedSprite = iAnimatedSprite;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            float radius = NukeExplosionHandler.getStageOneTick();
            ExplodeCoreParticle particle = new ExplodeCoreParticle(worldIn, x, y, z, 0d, 0d, 0d, 0,
                    3000, NukeExplosionHandler.getStageOneTick()*3, 0.01f*radius, radius, 0.25f*radius);
            particle.pickSprite(this.iAnimatedSprite);
            particle.setColor(1.0F, 1.0F, 1.0F);
            return particle;
        }
    }
}
