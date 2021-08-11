package com.song.nuclear_craft.network;

import com.song.nuclear_craft.blocks.tileentity.C4BombTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class C4BombSettingPacket {
    public double x;
    public double y;
    public double z;
    public String action;

    public C4BombSettingPacket(BlockPos pos, String action){
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.action = action;
    }

    public C4BombSettingPacket(final FriendlyByteBuf packetBuffer){
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.action = packetBuffer.readUtf(32767);
    }

    public void encode(final FriendlyByteBuf packetBuffer){
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeUtf(this.action);
    }

    public static void handle(C4BombSettingPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()-> {
            NetworkEvent.Context context = ctx.get();
            PacketListener handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl){
                ServerLevel world = (ServerLevel) ((ServerGamePacketListenerImpl) handler).player.level;
                BlockEntity entity = world.getBlockEntity(new BlockPos(packet.x, packet.y, packet.z));
                if (!(entity instanceof C4BombTileEntity)){
                    throw new IllegalArgumentException("entity should be C4bomb, but not: "+entity);
                }
                switch (packet.action.split("_")[0]){
                    case "add":
                        ((C4BombTileEntity) entity).addNum(Integer.parseInt(packet.action.split("_")[1]));
                        break;
                    case "delete":
                        ((C4BombTileEntity) entity).delete();
                        break;
                    case "activate":
                        ((C4BombTileEntity) entity).activate(); break;
                    default:
                        throw new IllegalArgumentException("not recognized argument: "+packet.action);
                }
            }
            ctx.get().setPacketHandled(true);
        });
    }
}
