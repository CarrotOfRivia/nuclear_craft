package com.song.nuclear_craft.network;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.RuntimeDistCleaner;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NuclearCraftPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static int channel_id = 0;
    public static final SimpleChannel EXPLOSION_CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(NuclearCraft.MODID, "explosion"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    public static final SimpleChannel SMOKE_BOMB_CHANNEL = NetworkRegistry.newSimpleChannel(
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
        System.out.println("channel id: -------"+channel_id);
        EXPLOSION_CHANNEL.registerMessage(channel_id++, MySExplosionPacket.class, MySExplosionPacket::encode, MySExplosionPacket::new, MySExplosionPacket::handle);
        System.out.println("channel id: -------"+channel_id);
        C4_SETTING_CHANNEL.registerMessage(channel_id++, C4BombSynPacket.class, C4BombSynPacket::encode, C4BombSynPacket::new, C4BombSynPacket::handle);
        System.out.println("channel id: -------"+channel_id);
        SMOKE_BOMB_CHANNEL.registerMessage(channel_id++, SmokeBombPacket.class, SmokeBombPacket::encode, SmokeBombPacket::new, SmokeBombPacket::handle);
        System.out.println("channel id: -------"+channel_id);
        KEY_BIND.registerMessage(channel_id++, GunLoadingPacket.class, GunLoadingPacket::encode, GunLoadingPacket::new, GunLoadingPacket::handle);
        System.out.println("channel id: -------"+channel_id);
        C4_SETTING_CHANNEL.registerMessage(channel_id++, C4BombSettingPacket.class, C4BombSettingPacket::encode, C4BombSettingPacket::new, C4BombSettingPacket::handle);
        System.out.println("channel id: -------"+channel_id);
    }

}
