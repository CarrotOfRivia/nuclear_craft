package com.song.nuclear_craft.entities.rocket_entities;

import com.song.nuclear_craft.misc.ConfigCommon;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import com.song.nuclear_craft.network.ParticlePacket;
import com.song.nuclear_craft.particles.ParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Random;

public class SmokeRocketEntity extends FireworkRocketEntity {
    public static double SMOKE_RADIUS = ConfigCommon.SMOKE_RADIUS.get().floatValue();
    public SmokeRocketEntity(EntityType<? extends FireworkRocketEntity> p_i50164_1_, Level p_i50164_2_) {
        super(p_i50164_1_, p_i50164_2_);
    }

    public SmokeRocketEntity(Level worldIn, double x, double y, double z, ItemStack givenItem) {
        super(worldIn, x, y, z, givenItem);
    }

    public SmokeRocketEntity(Level p_i231581_1_, @Nullable Entity p_i231581_2_, double p_i231581_3_, double p_i231581_5_, double p_i231581_7_, ItemStack p_i231581_9_) {
        super(p_i231581_1_, p_i231581_2_, p_i231581_3_, p_i231581_5_, p_i231581_7_, p_i231581_9_);
    }

    public SmokeRocketEntity(Level p_i47367_1_, ItemStack p_i47367_2_, LivingEntity p_i47367_3_) {
        super(p_i47367_1_, p_i47367_2_, p_i47367_3_);
    }

    public SmokeRocketEntity(Level p_i50165_1_, ItemStack p_i50165_2_, double p_i50165_3_, double p_i50165_5_, double p_i50165_7_, boolean p_i50165_9_) {
        super(p_i50165_1_, p_i50165_2_, p_i50165_3_, p_i50165_5_, p_i50165_7_, p_i50165_9_);
    }

    public SmokeRocketEntity(Level p_i231582_1_, ItemStack p_i231582_2_, Entity p_i231582_3_, double p_i231582_4_, double p_i231582_6_, double p_i231582_8_, boolean p_i231582_10_) {
        super(p_i231582_1_, p_i231582_2_, p_i231582_3_, p_i231582_4_, p_i231582_6_, p_i231582_8_, p_i231582_10_);
    }

    @Override
    protected void onHitEntity(EntityHitResult p_213868_1_) {
        this.explode();
    }

    @Override
    protected void onHitBlock(BlockHitResult p_230299_1_) {
        this.explode();
    }

    private void explode(){
        this.setRemoved(RemovalReason.KILLED);
        if(!level.isClientSide){
            sendSmokePacket(level, this.getX(), this.getY(), this.getZ());
        }
    }

    public static void sendSmokePacket(Level world, double x, double y, double z){
        world.playSound(null, x, y, z, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 20, 1f);
        for(Player playerEntity : world.players()){
            ServerPlayer serverPlayerEntity = (ServerPlayer) playerEntity;
            NuclearCraftPacketHandler.PARTICLE_CHANNEL.send(PacketDistributor.PLAYER.with(()->serverPlayerEntity), new ParticlePacket(x, y, z, "smoke_bomb"));
        }
    }

    public static void generateSmoke(double x, double y, double z){
        // client side
        ClientLevel world = Minecraft.getInstance().level;
        Random random = new Random();
        for(double deltaX = -SMOKE_RADIUS; deltaX <= SMOKE_RADIUS; deltaX +=3){
            for(double deltaY = -SMOKE_RADIUS; deltaY <= SMOKE_RADIUS; deltaY +=3){
                for(double deltaZ = -SMOKE_RADIUS; deltaZ <= SMOKE_RADIUS; deltaZ +=3){
                    if ((deltaX*deltaX+deltaY*deltaY+deltaZ*deltaZ < SMOKE_RADIUS*SMOKE_RADIUS)&&(random.nextDouble()>0.7)&&(world.getBlockState(new BlockPos(x+deltaX, y+deltaY, z+deltaZ)))== Blocks.AIR.defaultBlockState()){
                        world.addParticle((ParticleOptions) ParticleRegister.BIG_SMOKE.get(), x+deltaX, y+deltaY, z+deltaZ, 0.03*(random.nextDouble()-0.5), 0.03*(random.nextDouble()-0.5), 0.03*(random.nextDouble()-0.5));
                    }
                }
            }
        }
    }
}
