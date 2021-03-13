package com.song.nuclear_craft.particles;

import com.song.nuclear_craft.entities.ExplosionUtils;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;


@OnlyIn(Dist.CLIENT)
public class BigSmokeParticle extends SpriteTexturedParticle {


    protected BigSmokeParticle(ClientWorld world, double x, double y, double z) {
        this(world, x, y, z, 0d, 0d, 0d);
    }

    protected BigSmokeParticle(ClientWorld world, double x, double y, double z, double x_speed, double y_speed, double z_speed) {
        super(world, x, y, z, x_speed, y_speed, z_speed);
    }

    protected BigSmokeParticle(ClientWorld world, double x, double y, double z, double x_speed, double y_speed, double z_speed, int age, int maxAge, float avg_scale){
        super(world, x, y, z, x_speed, y_speed, z_speed);
        this.age = age;
        this.maxAge = maxAge;
        this.particleScale = avg_scale * (4.5f + new Random().nextFloat()) / (5f);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class NukeParticleFactory implements IParticleFactory<BasicParticleType>{
        private final IAnimatedSprite iAnimatedSprite;

        public NukeParticleFactory(IAnimatedSprite iAnimatedSprite){
            this.iAnimatedSprite = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            BigSmokeParticle nukeParticle = new BigSmokeParticle(worldIn, x, y + 0.5D, z, 0d, 0.07d, 0d, 0, 100, 4f * ExplosionUtils.NUKE_RADIUS / 80);
            nukeParticle.selectSpriteRandomly(this.iAnimatedSprite);
            nukeParticle.setColor(1.0F, 1.0F, 1.0F);
            return nukeParticle;
        }
    }

    public static class BigSmokeFactory implements IParticleFactory<BasicParticleType>{
        private final IAnimatedSprite iAnimatedSprite;
        private static final Random random = new Random();

        public BigSmokeFactory(IAnimatedSprite iAnimatedSprite){
            this.iAnimatedSprite = iAnimatedSprite;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            BigSmokeParticle bigParticle = new BigSmokeParticle(worldIn, x, y + 0.5D, z, xSpeed, ySpeed, zSpeed, 0, 700+(int)(300*random.nextFloat()), 4f);
            bigParticle.selectSpriteRandomly(this.iAnimatedSprite);
            bigParticle.setColor(1.0F, 1.0F, 1.0F);
            return bigParticle;
        }
    }
}
