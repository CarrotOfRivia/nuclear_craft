package com.song.nuclear_craft.network;

import com.song.nuclear_craft.client.SoundPlayMethods;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

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

    public SoundPacket(final PacketBuffer packetBuffer){
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.action = packetBuffer.readString(32767);
    }

    public void encode(final PacketBuffer packetBuffer){
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeString(this.action);
    }

    public static void handle(SoundPacket packet, Supplier<NetworkEvent.Context> ctx){
        //TODO
        ctx.get().enqueueWork(()-> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()->()->{
            switch (packet.action){
                case "c4_beep":
                    SoundPlayMethods.c4Beep(new BlockPos(packet.x, packet.y, packet.z)); break;
                case "c4_activate":
                    SoundPlayMethods.activeSound(new BlockPos(packet.x, packet.y, packet.z)); break;
                case "rocket_load":
                    SoundPlayMethods.playLoadSound(new BlockPos(packet.x, packet.y, packet.z)); break;
                default:
                    SoundPlayMethods.playSoundFromString(new BlockPos(packet.x, packet.y, packet.z), packet.action); break;
            }
        }));
        ctx.get().setPacketHandled(true);
    }
}
