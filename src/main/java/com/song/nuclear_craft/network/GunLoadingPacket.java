package com.song.nuclear_craft.network;

import com.song.nuclear_craft.items.guns.AbstractGunItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GunLoadingPacket {
    public int slot;

    public GunLoadingPacket(int slot){
        this.slot = slot;
    }

    public GunLoadingPacket(final FriendlyByteBuf packetBuffer){
        this.slot = packetBuffer.readInt();
    }

    public void encode(final FriendlyByteBuf packetBuffer){
        packetBuffer.writeInt(this.slot);
    }

    public static void handle(GunLoadingPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()-> {
            NetworkEvent.Context context = ctx.get();
            PacketListener handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl){
                ServerLevel world = (ServerLevel) ((ServerGamePacketListenerImpl) handler).player.level;
                ServerPlayer playerEntity = ((ServerGamePacketListenerImpl) handler).player;
                AbstractGunItem.tryLoadAmmo(world, playerEntity, packet.slot);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
