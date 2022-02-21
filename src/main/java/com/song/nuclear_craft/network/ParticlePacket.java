package com.song.nuclear_craft.network;

import com.song.nuclear_craft.entities.rocket_entities.SmokeRocketEntity;
import com.song.nuclear_craft.particles.ParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ParticlePacket {
    public double x;
    public double y;
    public double z;
    public String action;

    public ParticlePacket(double x, double y, double z, String action){
        this.x = x;
        this.y = y;
        this.z = z;
        this.action = action;
    }

    public ParticlePacket(final FriendlyByteBuf packetBuffer){
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.action = packetBuffer.readUtf();
    }

    public void encode(final FriendlyByteBuf packetBuffer){
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeUtf(this.action);
    }

    public static void handle(ParticlePacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
//            NetworkEvent.Context context = ctx.get();
//            INetHandler handler = context.getNetworkManager().getNetHandler();
            switch (packet.action){
                case "smoke_bomb":
                    SmokeRocketEntity.generateSmoke(packet.x, packet.y, packet.z); break;
                case "nuke_core":
                    if (Minecraft.getInstance().level != null){
                        Minecraft.getInstance().level.addParticle((ParticleOptions) ParticleRegister.EXPLODE_CORE.get(), packet.x, packet.y, packet.z, 0, 0,0);
                    }
                    break;
                default:
                    break;
            }

        }));
        ctx.get().setPacketHandled(true);
    }

}
