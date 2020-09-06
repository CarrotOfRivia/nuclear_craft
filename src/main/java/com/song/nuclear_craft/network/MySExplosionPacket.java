package com.song.nuclear_craft.network;

import com.song.nuclear_craft.entities.AtomicBombEntity;
import com.song.nuclear_craft.particles.ParticleList;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MySExplosionPacket {
    public double radius;
    public double x;
    public double y;
    public double z;

    public MySExplosionPacket(double radius, double x, double y, double z){
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MySExplosionPacket(final PacketBuffer packetBuffer){
        this.radius = packetBuffer.readDouble();
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
    }

    public void encode(final PacketBuffer packetBuffer){
        packetBuffer.writeDouble(this.radius);
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
    }

    public static void handle(MySExplosionPacket packet, Supplier<NetworkEvent.Context> ctx){
        NetworkEvent.Context context = ctx.get();
        INetHandler handler = context.getNetworkManager().getNetHandler();
        if (handler instanceof ClientPlayNetHandler){
            ClientWorld world = ((ClientPlayNetHandler) handler).getWorld();
            AtomicBombEntity.mushroomCloud(world, packet.x, packet.y, packet.z, packet.radius/2);
        }
    }

}
