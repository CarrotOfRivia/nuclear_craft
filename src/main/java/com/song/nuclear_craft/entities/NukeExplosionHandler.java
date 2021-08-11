package com.song.nuclear_craft.entities;

import com.song.nuclear_craft.effects.EffectRegister;
import com.song.nuclear_craft.misc.ConfigCommon;
import com.song.nuclear_craft.network.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import static com.song.nuclear_craft.entities.ExplosionUtils.Y_SHORTEN;

public class NukeExplosionHandler extends Entity {
    private int age=0;
    /**
     * Using an entity to handle nuke explosions
     * */
    public NukeExplosionHandler(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    public NukeExplosionHandler(double x, double y, double z, Level world){
        super(EntityRegister.NUKE_EXPLOSION_HANDLER_TYPE.get(), world);
        this.setPos(x, y, z);

        this.onSpawn();
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.age = compound.getInt("age");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("age", this.age);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public void move(MoverType typeIn, Vec3 pos) {
        // We do not want it to change location
    }

    private void onSpawn(){
        // TODO: add radiation, brightness and fire
        if(! this.level.isClientSide()) {

            // Applying vision effect
            for (Player player : level.players()) {
                if (getDistanceSquare(player) < getVisionEffectDist()) {
                    player.addEffect(new MobEffectInstance(EffectRegister.RADIOACTIVE.get(), 1600, 1));
                }
            }

            NuclearCraftPacketHandler.PARTICLE_CHANNEL.send(PacketDistributor.ALL.noArg(), new ParticlePacket(this.getX(), this.getY(), this.getZ(), "nuke_core"));
        }
    }

    private double getDistanceSquare(Player player){
        return player.distanceTo(this);
    }

    private static double getVisionEffectDist(){
        return 400.;
    }

    public static float getBlastRadius(){
        return ConfigCommon.NUKE_RADIUS.get().floatValue();
    }

    public static int getStageOneTick(){
        return 20;
    }

    public static int getStageTwoTick(){
        return 80;
    }

    public static int getStageThreeTick(){
        return 560;
    }

    public void shockWave(){
        if(! this.level.isClientSide()){
            float multiplier = 2.0f;
            int effectiveHeight=15;
            int effectRadius = (int) (multiplier*getBlastRadius());
            for (int dx = -effectRadius; dx<=effectRadius; dx++){
                int yMax = (int) Math.sqrt(effectRadius*effectRadius-dx*dx);
                for(int dz = -yMax; dz<=yMax; dz++){
                    int y = this.level.getHeight(Heightmap.Types.WORLD_SURFACE, (int)this.getX()+dx, (int)this.getZ()+dz);
                    BlockPos interested = new BlockPos((int)this.getX()+dx, y-1, (int)this.getZ()+dz);
                    if(y-this.getY()<effectiveHeight || y-this.getY()>-effectiveHeight){
                        while (y-this.getY()>-effectiveHeight){
                            BlockPos interested1 = new BlockPos((int)this.getX()+dx, y-1, (int)this.getZ()+dz);
                            BlockState blockState = level.getBlockState(interested);
                            level.destroyBlock(interested1, false);
                            if(blockState.getMaterial() != Material.WOOD && blockState.getMaterial() != Material.LEAVES){
                                break;
                            }
                            y--;
                        }
                    }
                }
            }
        }
    }

    public void effectCore(){
        if(! this.level.isClientSide()){
            float multiplier = 2.0f;
            int effectiveHeight=15;
            int effectRadius = (int) (multiplier*getBlastRadius());
            for (int dx = -effectRadius; dx<=effectRadius; dx++){
                int yMax = (int) Math.sqrt(effectRadius*effectRadius-dx*dx);
                for(int dz = -yMax; dz<=yMax; dz++){
                    int y = this.level.getHeight(Heightmap.Types.WORLD_SURFACE, (int)this.getX()+dx, (int)this.getZ()+dz);
                    BlockPos interested = new BlockPos((int)this.getX()+dx, y-1, (int)this.getZ()+dz);
                    if(dx*dx+dz*dz < getBlastRadius()*getBlastRadius()){
                        if(random.nextFloat()<0.5){
                            level.setBlockAndUpdate(interested, Blocks.NETHERRACK.defaultBlockState());
                            if(random.nextFloat()<0.2){
                                level.setBlockAndUpdate(interested.relative(Direction.UP), Blocks.FIRE.defaultBlockState());
                            }
                        }
                        if(random.nextFloat()<0.05){
                            level.setBlockAndUpdate(interested, Blocks.LAVA.defaultBlockState());
                        }
                    }
                }
            }
        }
    }


    @Override
    public void tick() {
        super.tick();
        if(! level.isClientSide()){
            if(age == getStageOneTick()){
                ExplosionUtils.oldNukeExplode(this.level, null, this.getX(), this.getY(), this.getZ(), getBlastRadius(), false, ConfigCommon.NUKE_BLAST_POWER.get());
                effectCore();
            }
            if(age == getStageTwoTick() - 15){
                NuclearCraftPacketHandler.PARTICLE_CHANNEL.send(PacketDistributor.ALL.noArg(), new ShockWaveParticleChannel(this.getX(), this.getY(), this.getZ(), getBlastRadius()));
            }
            if(age == getStageTwoTick() + 10){
                shockWave();
            }

            if(age>=getStageTwoTick() && age%10==0){
                NuclearCraftPacketHandler.PARTICLE_CHANNEL.send(PacketDistributor.ALL.noArg(), new NukeRisingSmokePacket(this.getX(), this.getY()-getBlastRadius()/Y_SHORTEN+1, this.getZ(), 0.1*getBlastRadius()));
//                NuclearCraftPacketHandler.PARTICLE_CHANNEL.send(PacketDistributor.ALL.noArg(), new NukeDownSmokePacket(this.getPosX(), this.getPosY(), this.getPosZ(), getBlastRadius()));
            }

            if(age>=getStageThreeTick() && age%10==0){
                NuclearCraftPacketHandler.PARTICLE_CHANNEL.send(PacketDistributor.ALL.noArg(),new NukeMushroomCloudPacket(this.getX(), this.getY()+1.75*getBlastRadius()-getBlastRadius()/Y_SHORTEN+1, this.getZ(), 0.25*getBlastRadius()));
            }

            age++;
            if(age >= 3000){
                this.setRemoved(RemovalReason.KILLED);
            }
        }
    }
}
