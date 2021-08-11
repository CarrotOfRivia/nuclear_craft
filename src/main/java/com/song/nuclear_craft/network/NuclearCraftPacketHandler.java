package com.song.nuclear_craft.network;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class NuclearCraftPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static int channel_id = 0;
    public static final SimpleChannel EXPLOSION_CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(NuclearCraft.MODID, "explosion"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    public static final SimpleChannel PARTICLE_CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(NuclearCraft.MODID, "smoke_bomb"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    public static final SimpleChannel C4_SETTING_CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(NuclearCraft.MODID, "c4_setting"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    public static final SimpleChannel KEY_BIND = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(NuclearCraft.MODID, "key_bind"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register(){
        EXPLOSION_CHANNEL.registerMessage(channel_id++, MySExplosionPacket.class, MySExplosionPacket::encode, MySExplosionPacket::new, MySExplosionPacket::handle);
        C4_SETTING_CHANNEL.registerMessage(channel_id++, C4BombSynPacket.class, C4BombSynPacket::encode, C4BombSynPacket::new, C4BombSynPacket::handle);
        C4_SETTING_CHANNEL.registerMessage(channel_id++, SoundPacket.class, SoundPacket::encode, SoundPacket::new, SoundPacket::handle);
        PARTICLE_CHANNEL.registerMessage(channel_id++, ParticlePacket.class, ParticlePacket::encode, ParticlePacket::new, ParticlePacket::handle);
        PARTICLE_CHANNEL.registerMessage(channel_id++, ShockWaveParticleChannel.class, ShockWaveParticleChannel::encode, ShockWaveParticleChannel::new, ShockWaveParticleChannel::handle);
        PARTICLE_CHANNEL.registerMessage(channel_id++, NukeRisingSmokePacket.class, NukeRisingSmokePacket::encode, NukeRisingSmokePacket::new, NukeRisingSmokePacket::handle);
        PARTICLE_CHANNEL.registerMessage(channel_id++, NukeMushroomCloudPacket.class, NukeMushroomCloudPacket::encode, NukeMushroomCloudPacket::new, NukeMushroomCloudPacket::handle);
        PARTICLE_CHANNEL.registerMessage(channel_id++, NukeDownSmokePacket.class, NukeDownSmokePacket::encode, NukeDownSmokePacket::new, NukeDownSmokePacket::handle);
        KEY_BIND.registerMessage(channel_id++, GunLoadingPacket.class, GunLoadingPacket::encode, GunLoadingPacket::new, GunLoadingPacket::handle);
        C4_SETTING_CHANNEL.registerMessage(channel_id++, C4BombSettingPacket.class, C4BombSettingPacket::encode, C4BombSettingPacket::new, C4BombSettingPacket::handle);
        C4_SETTING_CHANNEL.registerMessage(channel_id++, BombDefuseProgressPacket.class, BombDefuseProgressPacket::encode, BombDefuseProgressPacket::new, BombDefuseProgressPacket::handle);
    }

}
