package com.song.nuclear_craft.network;

import com.song.nuclear_craft.misc.ConfigClient;
import com.song.nuclear_craft.particles.ParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class NukeRisingSmokePacket {
    public double x;
    public double y;
    public double z;
    public double radius;

    public NukeRisingSmokePacket(double x, double y, double z, double radius){
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
    }

    public NukeRisingSmokePacket(final FriendlyByteBuf packetBuffer){
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.radius = packetBuffer.readDouble();
    }

    public void encode(final FriendlyByteBuf packetBuffer){
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeDouble(this.radius);
    }

    public static int getNumParticles(){
        return 8;
    }

    public static void handle(NukeRisingSmokePacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()-> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
//            NetworkEvent.Context context = ctx.get();
//            INetHandler handler = context.getNetworkManager().getNetHandler();
            ClientLevel world = Minecraft.getInstance().level;
            Random random = new Random();
            if(ConfigClient.RENDER_MUSHROOM_CLOUD.get() && world != null){
                for (int i=0; i<getNumParticles(); i++){
                    double theta = 2*Math.PI*(i+0.2*random.nextDouble())/(getNumParticles());
                    double this_x = packet.x + packet.radius*Math.cos(theta);
                    double this_z = packet.z + packet.radius*Math.sin(theta);
                    world.addParticle((ParticleOptions) ParticleRegister.RESTRICTED_HEIGHT_SMOKE_PARTICLE.get(), this_x, packet.y, this_z, packet.radius*Math.cos(theta)/320,packet.radius/32,packet.radius*Math.sin(theta)/320);
                }
            }
        }));
        ctx.get().setPacketHandled(true);
    }

}
