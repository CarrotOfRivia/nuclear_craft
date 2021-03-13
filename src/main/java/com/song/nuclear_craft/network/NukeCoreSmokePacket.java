package com.song.nuclear_craft.network;

import com.song.nuclear_craft.entities.rocket_entities.SmokeRocketEntity;
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

public class NukeCoreSmokePacket {
    public double x;
    public double y;
    public double z;
    public double radius;

    public NukeCoreSmokePacket(double x, double y, double z, double radius){
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
    }

    public NukeCoreSmokePacket(final PacketBuffer packetBuffer){
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
        return 100;
    }

    public static void handle(NukeCoreSmokePacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()-> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
//            NetworkEvent.Context context = ctx.get();
//            INetHandler handler = context.getNetworkManager().getNetHandler();
            ClientWorld world = Minecraft.getInstance().world;
            if(world != null){
                Random random = new Random();
                for (int i=0; i<getNumParticles(); i++){
                    double phi = 2 * Math.PI * random.nextDouble();
                    double theta = Math.PI * random.nextDouble();
                    double this_x = packet.x + packet.radius*Math.sin(theta)*Math.cos(phi);
                    double this_z = packet.z + packet.radius*Math.sin(theta)*Math.sin(phi);
                    double this_y = packet.y + packet.radius*Math.cos(theta);
                    world.addParticle((IParticleData) ParticleRegister.RESTRICTED_HEIGHT_SMOKE_PARTICLE.get(), this_x, this_y, this_z, 0,0,0);
                }
            }
        }));
        ctx.get().setPacketHandled(true);
    }

}
