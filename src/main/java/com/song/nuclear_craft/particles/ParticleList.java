package com.song.nuclear_craft.particles;

import net.minecraft.particles.BasicParticleType;

public class ParticleList {

    public static BasicParticleType NUKE_PARTICLE_SMOKE = new BasicParticleType(true);
    public static BasicParticleType NUKE_PARTICLE_FIRE = new BasicParticleType(true);
    public static BasicParticleType BIG_SMOKE = new BasicParticleType(true);
    static {
        NUKE_PARTICLE_SMOKE.setRegistryName("nuke_particle_smoke");
        NUKE_PARTICLE_FIRE.setRegistryName("nuke_particle_fire");
        BIG_SMOKE.setRegistryName("big_smoke");
    }
}
