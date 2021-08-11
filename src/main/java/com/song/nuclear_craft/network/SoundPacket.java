package com.song.nuclear_craft.network;

import com.song.nuclear_craft.client.SoundPlayMethods;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class SoundPacket {
    public double x;
    public double y;
    public double z;
    public String action;

    public SoundPacket(BlockPos pos, String action){
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.action = action;
    }

    public SoundPacket(final FriendlyByteBuf packetBuffer){
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.action = packetBuffer.readUtf(32767);
    }

    public void encode(final FriendlyByteBuf packetBuffer){
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeUtf(this.action);
    }

    public static void handle(SoundPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()-> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->{
            SoundPlayMethods.playSoundFromString(new BlockPos(packet.x, packet.y, packet.z), packet.action);
        }));
        ctx.get().setPacketHandled(true);
    }
}
