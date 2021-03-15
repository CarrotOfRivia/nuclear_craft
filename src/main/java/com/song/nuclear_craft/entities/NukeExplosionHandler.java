package com.song.nuclear_craft.entities;

import com.song.nuclear_craft.misc.ConfigCommon;
import com.song.nuclear_craft.network.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.song.nuclear_craft.entities.ExplosionUtils.Y_SHORTEN;

public class NukeExplosionHandler extends Entity {
    private int age=0;
    /**
     * Using an entity to handle nuke explosions
     * */
    public NukeExplosionHandler(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public NukeExplosionHandler(double x, double y, double z, World world){
        super(EntityRegister.NUKE_EXPLOSION_HANDLER_TYPE.get(), world);
        this.setPosition(x, y, z);

        this.onSpawn();
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.age = compound.getInt("age");
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("age", this.age);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public void move(MoverType typeIn, Vector3d pos) {
        // We do not want it to change location
    }

    private void onSpawn(){
        // TODO: add radiation, brightness and fire
        if(! this.world.isRemote()) {

            // Applying vision effect
            for (PlayerEntity player : world.getPlayers()) {
                if (getDistanceSquare(player) < getVisionEffectDist()) {
                    player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 1600, 0));
                }
            }

            NuclearCraftPacketHandler.PARTICLE_CHANNEL.send(PacketDistributor.ALL.noArg(), new ParticlePacket(this.getPosX(), this.getPosY(), this.getPosZ(), "nuke_core"));
        }
    }

    private double getDistanceSquare(PlayerEntity player){
        return player.getDistance(this);
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

    public void effectNeighbor(){
        if(! this.world.isRemote()){
            float multiplier = 2.0f;
            int effectiveHeight=15;
            int effectRadius = (int) (multiplier*getBlastRadius());
            for (int dx = -effectRadius; dx<=effectRadius; dx++){
                int yMax = (int) Math.sqrt(effectRadius*effectRadius-dx*dx);
                for(int dz = -yMax; dz<=yMax; dz++){
                    int y = this.world.getHeight(Heightmap.Type.WORLD_SURFACE, (int)this.getPosX()+dx, (int)this.getPosZ()+dz);
                    BlockPos interested = new BlockPos((int)this.getPosX()+dx, y-1, (int)this.getPosZ()+dz);
                    if(dx*dx+dz*dz < getBlastRadius()*getBlastRadius()){
                        if(rand.nextFloat()<0.5){
                            world.setBlockState(interested, Blocks.NETHERRACK.getDefaultState());
                            if(rand.nextFloat()<0.2){
                                world.setBlockState(interested.offset(Direction.UP), Blocks.FIRE.getDefaultState());
                            }
                        }
                        if(rand.nextFloat()<0.05){
                            world.setBlockState(interested, Blocks.LAVA.getDefaultState());
                        }
                    }
                    else if(y-this.getPosY()<effectiveHeight || y-this.getPosY()>-effectiveHeight){
                        while (y-this.getPosY()>-effectiveHeight){
                            BlockPos interested1 = new BlockPos((int)this.getPosX()+dx, y-1, (int)this.getPosZ()+dz);
                            BlockState blockState = world.getBlockState(interested);
                            world.destroyBlock(interested1, false);
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


    @Override
    public void tick() {
        super.tick();
        if(! world.isRemote()){
            if(age == getStageOneTick()){
                ExplosionUtils.oldNukeExplode(this.world, null, this.getPosX(), this.getPosY(), this.getPosZ(), getBlastRadius(), false, ConfigCommon.NUKE_BLAST_POWER.get());
            }
            if(age == getStageTwoTick()){
                effectNeighbor();
//                NuclearCraftPacketHandler.PARTICLE_CHANNEL.send(PacketDistributor.ALL.noArg(), new NukeCoreSmokePacket(this.getPosX(), this.getPosY(), this.getPosZ(), 0.25*getBlastRadius()));
            }

            if(age>=getStageTwoTick() && age%10==0){
                NuclearCraftPacketHandler.PARTICLE_CHANNEL.send(PacketDistributor.ALL.noArg(), new NukeRisingSmokePacket(this.getPosX(), this.getPosY()-getBlastRadius()/Y_SHORTEN+1, this.getPosZ(), 0.1*getBlastRadius()));
//                NuclearCraftPacketHandler.PARTICLE_CHANNEL.send(PacketDistributor.ALL.noArg(), new NukeDownSmokePacket(this.getPosX(), this.getPosY(), this.getPosZ(), getBlastRadius()));
            }

            if(age>=getStageThreeTick() && age%10==0){
                NuclearCraftPacketHandler.PARTICLE_CHANNEL.send(PacketDistributor.ALL.noArg(),new NukeMushroomCloudPacket(this.getPosX(), this.getPosY()+1.75*getBlastRadius()-getBlastRadius()/Y_SHORTEN+1, this.getPosZ(), 0.25*getBlastRadius()));
            }

            age++;
            if(age >= 3000){
                this.remove();
            }
        }
    }
}
