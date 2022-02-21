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

public class NukeDownSmokePacket {
    public double x;
    public double y;
    public double z;
    public double radius;

    public NukeDownSmokePacket(double x, double y, double z, double radius){
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
    }

    public NukeDownSmokePacket(final FriendlyByteBuf packetBuffer){
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

    public static void handle(NukeDownSmokePacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()-> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
//            NetworkEvent.Context context = ctx.get();
//            INetHandler handler = context.getNetworkManager().getNetHandler();
            ClientLevel world = Minecraft.getInstance().level;
            Random random = new Random();
            if(ConfigClient.RENDER_MUSHROOM_CLOUD.get() && world != null){
                for (int i=0; i<getNumParticles(); i++){
                    double theta = 2*Math.PI*random.nextDouble();
                    double speedModifier = 2*random.nextDouble();
                    world.addParticle((ParticleOptions) ParticleRegister.SHOCK_WAVE.get(), packet.x, packet.y, packet.z,
                            speedModifier*packet.radius*Math.cos(theta)/25,0,speedModifier*packet.radius*Math.sin(theta)/25);
                }
            }
        }));
        ctx.get().setPacketHandled(true);
    }

}
