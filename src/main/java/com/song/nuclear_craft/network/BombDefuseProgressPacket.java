package com.song.nuclear_craft.network;

import com.song.nuclear_craft.client.C4Defuse;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class BombDefuseProgressPacket {
    public double x;
    public double y;
    public double z;
    public int currentTick;
    public int defuseTick;

    public BombDefuseProgressPacket(BlockPos pos, int currentTick, int defuseTick){
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.currentTick = currentTick;
        this.defuseTick = defuseTick;
    }

    public BombDefuseProgressPacket(final FriendlyByteBuf packetBuffer){
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.currentTick = packetBuffer.readInt();
        this.defuseTick = packetBuffer.readInt();
    }

    public void encode(final FriendlyByteBuf packetBuffer){
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeInt(currentTick);
        packetBuffer.writeInt(defuseTick);
    }

    public static void handle(BombDefuseProgressPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()-> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            C4Defuse.handleDefuseProgress(packet);
        }));
        ctx.get().setPacketHandled(true);
    }
}
