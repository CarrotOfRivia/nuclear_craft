package com.song.nuclear_craft.entities;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.AbstractAmmo;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import com.song.nuclear_craft.items.ItemList;
import com.song.nuclear_craft.misc.ConfigCommon;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.FMLPlayMessages;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nonnull;

public class AbstractAmmoEntity extends ThrowableItemProjectile {
    private double energy;
    private double initSpeed;
//    private Vector3d initVec;
    private double gravity = 0.03f;
    protected double baseDamage = 30;
    private int age = 0;
    private double bulletSize;
    private final IntOpenHashSet piercedEntities=new IntOpenHashSet(100);;
    private boolean isMyImpact=false;
    private double initEnergy;
    // BlockPos to store init motion
    private static final EntityDataAccessor<Float> CURRENT_MOTION_X = SynchedEntityData.defineId(ThrowableItemProjectile.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> CURRENT_MOTION_Y = SynchedEntityData.defineId(ThrowableItemProjectile.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> CURRENT_MOTION_Z = SynchedEntityData.defineId(ThrowableItemProjectile.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> BULLET_SIZE = SynchedEntityData.defineId(ThrowableItemProjectile.class, EntityDataSerializers.FLOAT);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CURRENT_MOTION_X, 0.f);
        this.entityData.define(CURRENT_MOTION_Y, 0.f);
        this.entityData.define(CURRENT_MOTION_Z, 0.f);
        this.entityData.define(BULLET_SIZE, 0.f);
    }

    public AbstractAmmoEntity(EntityType<? extends AbstractAmmoEntity> type, Level world){
        super(type, world);
    }

//    @OnlyIn(Dist.CLIENT)
    public AbstractAmmoEntity(FMLPlayMessages.SpawnEntity entity, Level world){
        this(EntityRegister.BULLET_ENTITY.get(), world);
        this.setDeltaMovement(entity.getVelX(), entity.getVelY(), entity.getVelZ());
        this.lerpHeadTo(entity.getYaw(), entity.getPitch());
        this.setPos(entity.getPosX(), entity.getPosY(), entity.getPosZ());
    }

    public AbstractAmmoEntity(double x, double y, double z, Level world, ItemStack itemStack, Player shooter){
        super(EntityRegister.BULLET_ENTITY.get(), x, y, z, world);
        this.setItem(itemStack);
        this.setOwner(shooter);
        this.bulletSize = ((AbstractAmmo)itemStack.getItem()).getSize().getSize();
        this.entityData.set(BULLET_SIZE, (float) bulletSize);
        this.setBaseDamage(((AbstractAmmo)itemStack.getItem()).getBaseDamage());
    }

    public void setGravity(double gravity){
        this.gravity = gravity;
    }

    public void setBaseDamage(double baseDamage) {
        this.baseDamage = baseDamage;
    }

    public double getBaseDamage(){
        return baseDamage;
    }

    @Override
    public void tick() {

        double kEBefore = -1;
        if (age == 0){
            // init: speed small-> big for better performance
            initSpeed = this.getDeltaMovement().length();
            this.bulletSize = this.entityData.get(BULLET_SIZE);
//            initVec = this.getMotion();
            this.energy = getEnergy(initSpeed);
            initEnergy = this.energy;
//                this.setMotion(initVec.mul(0.01, 0.01, 0.01));
        }

        // Syn motion, default forge won't work for high speed objects
        if (level.isClientSide){
            this.setDeltaMovement(this.entityData.get(CURRENT_MOTION_X), this.entityData.get(CURRENT_MOTION_Y), this.entityData.get(CURRENT_MOTION_Z));
        }
        else{
            Vec3 vector3d = this.getDeltaMovement();
            this.entityData.set(CURRENT_MOTION_X, (float)vector3d.x);
            this.entityData.set(CURRENT_MOTION_Y, (float)vector3d.y);
            this.entityData.set(CURRENT_MOTION_Z, (float)vector3d.z);
        }

        isMyImpact=true;
        if (!level.isClientSide){
            HitResult raytraceresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            while (!this.isRemoved()&&raytraceresult!=null){
                raytraceresult = ProjectileUtil.getHitResult(this, this::canHitEntity);

                if (raytraceresult != null && raytraceresult.getType() == HitResult.Type.ENTITY) {
                    Entity entity = ((EntityHitResult)raytraceresult).getEntity();
                    Entity indirect = this.getOwner();
                    if (entity instanceof Player && indirect instanceof Player && !((Player)indirect).canHarmPlayer((Player)entity)) {
                        raytraceresult = null;
                    }
                }

                if (raytraceresult != null && raytraceresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                    this.onHit(raytraceresult);
                }
                if(raytraceresult != null && raytraceresult.getType() == HitResult.Type.MISS){
                    break;
                }
            }
        }
        isMyImpact=false;

        super.tick();

        if(this.energy <= 0){
            this.setRemoved(RemovalReason.KILLED);
        }
        this.setDeltaMovement(this.getDeltaMovement().add(0, -gravity, 0));

        this.age++;
        Vec3 vector3d = this.getDeltaMovement();
        float f = (float) vector3d.horizontalDistanceSqr();
        this.setYRot((float)(Mth.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI)));
        this.setXRot((float)(Mth.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI)));

        // remove aged bullets which are not removed by other means
        if(this.age >= 1000){
            this.setRemoved(RemovalReason.KILLED);
        }
    }

    @Override
    protected void onHitEntity(@Nonnull EntityHitResult entityRayTraceResult) {
        Entity entity = entityRayTraceResult.getEntity();
        this.piercedEntities.add(entity.getId());

        if(entity instanceof ItemEntity || entity instanceof AbstractAmmoEntity){
            // bullets do not destroy item
            // bullets do not destroy themselves (for short guns)
            return;
        }

        if(entity instanceof LivingEntity){
            // for proper shotgun and machine gun behaviour
            entity.invulnerableTime=0;
        }

        double damage = this.baseDamage * getEnergy(this.getDeltaMovement().length()) / this.initEnergy;
        // get shooter
        DamageSource damageSource = new IndirectEntityDamageSource(new ResourceLocation(NuclearCraft.MODID, "bullet").toString(), this, this.getOwner()).setProjectile();
        boolean result = entity.hurt(damageSource, (float) damage);

        if (result){
            this.energy -= 30;
        }
        else {
            this.energy -= 10;
        }
    }

    @Override
    protected void onHit(HitResult result) {
        double kEBefore = this.energy;
        // do this hack to remove impact in super.tick()
        if(isMyImpact&&!level.isClientSide){
            super.onHit(result);
        }
        if(this.energy<=0){
            this.setRemoved(RemovalReason.KILLED);
        }
        double factor = Math.sqrt(this.energy / kEBefore);
        this.setDeltaMovement(this.getDeltaMovement().multiply(factor, factor, factor));
    }

    @Override
    protected boolean canHitEntity(Entity p_230298_1_) {
        return super.canHitEntity(p_230298_1_)&&((this.piercedEntities == null || !this.piercedEntities.contains(p_230298_1_.getId())));
    }

    @Override
    protected void onHitBlock(@Nonnull BlockHitResult blockRayTraceResult) {
        Block block = level.getBlockState(blockRayTraceResult.getBlockPos()).getBlock();
        double blastResist = block.getExplosionResistance();
        if(blastResist>getBlockBreakThreshold()+1e-3){
            // ricochet
            Direction blockDirection = blockRayTraceResult.getDirection();
            this.ricochetSpeed(blockDirection);
            teleportToHitPoint(blockRayTraceResult);
            this.energy -= this.initEnergy * getRicochetEnergyLoss();
        }
        else {
            // destroy
            level.destroyBlock(blockRayTraceResult.getBlockPos(), true);
            this.energy -= getEnergyLoss(blastResist);
        }
        this.piercedEntities.clear();
    }

    protected void teleportToHitPoint(HitResult rayTraceResult){
        // necessary for proper ricochet behaviour
        Vec3 hitResult = rayTraceResult.getLocation();
        this.setPos(hitResult.x, hitResult.y, hitResult.z);
    }

    public double getRicochetEnergyLoss(){
        return 0.5d;
    }

    public double getBlockBreakThreshold(){
        return ConfigCommon.AMMO_BLOCK_BREAK_THRESHOLD.get();
    }

    public double getEnergyLoss(double blastResist){
        return 25 + 10 * (blastResist-2);
    }

    private void ricochetSpeed(Direction direction){
        switch (direction){
            case UP:
            case DOWN:
                this.setDeltaMovement(this.getDeltaMovement().multiply(1, -1, 1));
                break;
            case EAST:
            case WEST:
                this.setDeltaMovement(this.getDeltaMovement().multiply(-1, 1, 1));
                break;
            case NORTH:
            case SOUTH:
                this.setDeltaMovement(this.getDeltaMovement().multiply(1, 1, -1));
                break;
            default:break;
        }
    }

    protected double getEnergy(double speed) {
        return 0.5*speed*speed*(this.bulletSize/9);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemList.AMMO_REGISTRIES_TYPE.get(AmmoSize.SIZE_9MM).get(AmmoType.NORMAL).get();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
