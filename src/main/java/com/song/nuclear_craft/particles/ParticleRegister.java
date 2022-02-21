package com.song.nuclear_craft.particles;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleRegister {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, NuclearCraft.MODID);

    public static final RegistryObject<ParticleType<SimpleParticleType>> NUKE_PARTICLE_SMOKE = PARTICLES.register("nuke_particle_smoke", ()->new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SimpleParticleType>> NUKE_PARTICLE_FIRE = PARTICLES.register("nuke_particle_fire", ()->new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SimpleParticleType>> BIG_SMOKE = PARTICLES.register("big_smoke", ()->new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SimpleParticleType>> RESTRICTED_HEIGHT_SMOKE_PARTICLE = PARTICLES.register("restricted_height_smoke_particle", ()->new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SimpleParticleType>> MUSHROOM_SMOKE_PARTICLE = PARTICLES.register("mushroom_smoke_particle", ()->new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<SimpleParticleType>> SHOCK_WAVE = PARTICLES.register("down_smoke", ()->new SimpleParticleType(true));

    public static final RegistryObject<ParticleType<SimpleParticleType>> EXPLODE_CORE = PARTICLES.register("explode_core", ()->new SimpleParticleType(true));
//
//    public static BasicParticleType NUKE_PARTICLE_FIRE = new BasicParticleType(true);
//    public static BasicParticleType BIG_SMOKE = new BasicParticleType(true);
//    static {
//        NUKE_PARTICLE_FIRE.setRegistryName("nuke_particle_fire");
//        BIG_SMOKE.setRegistryName("big_smoke");
//    }
}
