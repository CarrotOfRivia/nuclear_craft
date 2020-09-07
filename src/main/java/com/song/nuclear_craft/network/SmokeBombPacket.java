package com.song.nuclear_craft.network;

import com.song.nuclear_craft.entities.AtomicBombEntity;
import com.song.nuclear_craft.entities.SmokeRocketEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SmokeBombPacket {
    public double x;
    public double y;
    public double z;

    public SmokeBombPacket(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public SmokeBombPacket(final PacketBuffer packetBuffer){
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
    }

    public void encode(final PacketBuffer packetBuffer){
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
    }

    public static void handle(SmokeBombPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            NetworkEvent.Context context = ctx.get();
            INetHandler handler = context.getNetworkManager().getNetHandler();
            if (handler instanceof ClientPlayNetHandler){
                ClientWorld world = ((ClientPlayNetHandler) handler).getWorld();
                SmokeRocketEntity.generateSmoke(world, packet.x, packet.y, packet.z);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
