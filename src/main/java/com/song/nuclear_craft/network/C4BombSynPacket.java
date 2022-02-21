package com.song.nuclear_craft.network;

import com.song.nuclear_craft.blocks.tileentity.C4BombTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C4BombSynPacket {
    public double x;
    public double y;
    public double z;
    public String inputPanel;
    public int fuse_age;
    public int explode_time;
    public boolean is_active;

    public C4BombSynPacket(BlockPos pos, String inputPanel, int fuse_age, int explode_time, boolean is_active){
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.inputPanel = inputPanel;
        this.fuse_age = fuse_age;
        this.explode_time = explode_time;
        this.is_active = is_active;
    }

    public C4BombSynPacket(final FriendlyByteBuf packetBuffer){
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.inputPanel = packetBuffer.readUtf();
        this.fuse_age = packetBuffer.readInt();
        this.explode_time = packetBuffer.readInt();
        this.is_active = packetBuffer.readBoolean();
    }

    public void encode(final FriendlyByteBuf packetBuffer){
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeUtf(this.inputPanel);
        packetBuffer.writeInt(fuse_age);
        packetBuffer.writeInt(explode_time);
        packetBuffer.writeBoolean(is_active);
    }

    public static void handle(C4BombSynPacket packet, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()-> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
//            NetworkEvent.Context context = ctx.get();
//            INetHandler handler = context.getNetworkManager().getNetHandler();
            BlockEntity entity = Minecraft.getInstance().level.getBlockEntity(new BlockPos(packet.x, packet.y, packet.z));
            if (entity instanceof C4BombTileEntity){
                ((C4BombTileEntity) entity).setAttr(packet.inputPanel, packet.fuse_age, packet.explode_time, packet.is_active);
            }

        }));
        ctx.get().setPacketHandled(true);
    }
}
