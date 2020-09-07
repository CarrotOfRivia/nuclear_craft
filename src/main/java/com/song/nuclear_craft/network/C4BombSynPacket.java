package com.song.nuclear_craft.network;

import com.song.nuclear_craft.blocks.tileentity.C4BombTileEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class C4BombSynPacket {
    public double x;
    public double y;
    public double z;
    public String inputPanel;

    public C4BombSynPacket(BlockPos pos, String inputPanel){
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.inputPanel = inputPanel;
    }

    public C4BombSynPacket(final PacketBuffer packetBuffer){
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.inputPanel = packetBuffer.readString();
    }

    public void encode(final PacketBuffer packetBuffer){
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeString(this.inputPanel);
    }

    public static void handle(C4BombSynPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            NetworkEvent.Context context = ctx.get();
            INetHandler handler = context.getNetworkManager().getNetHandler();
            if (handler instanceof ClientPlayNetHandler){
                ClientWorld world = ((ClientPlayNetHandler) handler).getWorld();
                TileEntity entity = world.getTileEntity(new BlockPos(packet.x, packet.y, packet.z));
                if (entity instanceof C4BombTileEntity){
                    ((C4BombTileEntity) entity).inputPanel = packet.inputPanel;
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
