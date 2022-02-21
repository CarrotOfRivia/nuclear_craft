package com.song.nuclear_craft.network;

import com.song.nuclear_craft.entities.ExplosionUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

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

    public MySExplosionPacket(final FriendlyByteBuf packetBuffer){
        this.radius = packetBuffer.readDouble();
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
    }

    public void encode(final FriendlyByteBuf packetBuffer){
        packetBuffer.writeDouble(this.radius);
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
    }

    public static void handle(MySExplosionPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, ()->()->{
//            NetworkEvent.Context context = ctx.get();
//            INetHandler handler = context.getNetworkManager().getNetHandler();
            ExplosionUtils.mushroomCloud(packet.x, packet.y, packet.z, packet.radius/2);
        }));
        ctx.get().setPacketHandled(true);
    }

}
