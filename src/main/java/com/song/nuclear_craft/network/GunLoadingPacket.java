package com.song.nuclear_craft.network;

import com.song.nuclear_craft.items.guns.AbstractGunItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class GunLoadingPacket {
    public int slot;

    public GunLoadingPacket(int slot){
        this.slot = slot;
    }

    public GunLoadingPacket(final PacketBuffer packetBuffer){
        this.slot = packetBuffer.readInt();
    }

    public void encode(final PacketBuffer packetBuffer){
        packetBuffer.writeInt(this.slot);
    }

    public static void handle(GunLoadingPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()-> {
            NetworkEvent.Context context = ctx.get();
            INetHandler handler = context.getNetworkManager().getNetHandler();
            if (handler instanceof ServerPlayNetHandler){
                ServerWorld world = (ServerWorld) ((ServerPlayNetHandler) handler).player.world;
                ServerPlayerEntity playerEntity = ((ServerPlayNetHandler) handler).player;
                AbstractGunItem.tryLoadAmmo(world, playerEntity, packet.slot);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
