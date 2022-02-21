package com.song.nuclear_craft.network;

import com.song.nuclear_craft.entities.NukeExplosionHandler;
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

public class ShockWaveParticleChannel {
    public double x;
    public double y;
    public double z;
    public double radius;

    public ShockWaveParticleChannel(double x, double y, double z, double radius){
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
    }

    public ShockWaveParticleChannel(final FriendlyByteBuf packetBuffer){
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
        return (int) (10* NukeExplosionHandler.getBlastRadius());
    }

    public static void handle(ShockWaveParticleChannel packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()-> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
//            NetworkEvent.Context context = ctx.get();
//            INetHandler handler = context.getNetworkManager().getNetHandler();
            ClientLevel world = Minecraft.getInstance().level;
            if(world != null){
                Random random = new Random();
                for (int i=0; i<getNumParticles(); i++){
                    double theta = 2 * Math.PI * i / getNumParticles();
                    double this_x = packet.radius*Math.sin(theta);
                    double this_z = packet.radius*Math.cos(theta);
                    world.addParticle((ParticleOptions) ParticleRegister.SHOCK_WAVE.get(), packet.x+this_x, packet.y, packet.z+this_z, this_x/25,0,this_z/25);
                }
            }
        }));
        ctx.get().setPacketHandled(true);
    }

}
