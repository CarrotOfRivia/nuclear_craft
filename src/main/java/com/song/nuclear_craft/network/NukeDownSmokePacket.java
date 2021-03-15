package com.song.nuclear_craft.network;

import com.song.nuclear_craft.misc.ConfigClient;
import com.song.nuclear_craft.particles.ParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

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

    public NukeDownSmokePacket(final PacketBuffer packetBuffer){
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.radius = packetBuffer.readDouble();
    }

    public void encode(final PacketBuffer packetBuffer){
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
            ClientWorld world = Minecraft.getInstance().world;
            Random random = new Random();
            if(ConfigClient.RENDER_MUSHROOM_CLOUD.get() && world != null){
                for (int i=0; i<getNumParticles(); i++){
                    double theta = 2*Math.PI*random.nextDouble();
                    double speedModifier = 2*random.nextDouble();
                    world.addParticle((IParticleData) ParticleRegister.DOWN_SMOKE.get(), packet.x, packet.y, packet.z,
                            speedModifier*packet.radius*Math.cos(theta)/20,0,speedModifier*packet.radius*Math.sin(theta)/20);
                }
            }
        }));
        ctx.get().setPacketHandled(true);
    }

}
