package com.song.nuclear_craft.network;

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

public class NukeMushroomCloudPacket {
    public double x;
    public double y;
    public double z;
    public double radius;

    public NukeMushroomCloudPacket(double x, double y, double z, double radius){
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
    }

    public NukeMushroomCloudPacket(final PacketBuffer packetBuffer){
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
        return 20;
    }

    public static void handle(NukeMushroomCloudPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()-> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
//            NetworkEvent.Context context = ctx.get();
//            INetHandler handler = context.getNetworkManager().getNetHandler();
            ClientWorld world = Minecraft.getInstance().world;
            Random random = new Random();
            if(world != null){
                for (int i=0; i<getNumParticles(); i++){
                    double theta = 2 * Math.PI * random.nextDouble();
                    double xDelta = packet.radius*Math.cos(theta);
                    double zDelta = packet.radius*Math.sin(theta);
                    world.addParticle((IParticleData) ParticleRegister.RESTRICTED_HEIGHT_SMOKE_PARTICLE.get(), packet.x+xDelta, packet.y+2*packet.radius*random.nextDouble(), packet.z+zDelta,
                            xDelta/200,0,zDelta/200);
                }
            }
        }));
        ctx.get().setPacketHandled(true);
    }

}
