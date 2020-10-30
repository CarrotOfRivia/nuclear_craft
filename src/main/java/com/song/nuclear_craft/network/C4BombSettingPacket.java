package com.song.nuclear_craft.network;

import com.song.nuclear_craft.blocks.tileentity.C4BombTileEntity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

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

    public C4BombSettingPacket(final PacketBuffer packetBuffer){
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.action = packetBuffer.readString(32767);
    }

    public void encode(final PacketBuffer packetBuffer){
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeString(this.action);
    }

    public static void handle(C4BombSettingPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()-> {
            NetworkEvent.Context context = ctx.get();
            INetHandler handler = context.getNetworkManager().getNetHandler();
            if (handler instanceof ServerPlayNetHandler){
                ServerWorld world = (ServerWorld) ((ServerPlayNetHandler) handler).player.world;
                TileEntity entity = world.getTileEntity(new BlockPos(packet.x, packet.y, packet.z));
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
