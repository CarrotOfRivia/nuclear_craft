package com.song.nuclear_craft.entities;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.AbstractAmmo;
import com.song.nuclear_craft.items.ItemList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class AbstractAmmoEntity extends ProjectileItemEntity {
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
    private static final DataParameter<Float> CURRENT_MOTION_X = EntityDataManager.createKey(ProjectileItemEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> CURRENT_MOTION_Y = EntityDataManager.createKey(ProjectileItemEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> CURRENT_MOTION_Z = EntityDataManager.createKey(ProjectileItemEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> BULLET_SIZE = EntityDataManager.createKey(ProjectileItemEntity.class, DataSerializers.FLOAT);

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(CURRENT_MOTION_X, 0.f);
        this.dataManager.register(CURRENT_MOTION_Y, 0.f);
        this.dataManager.register(CURRENT_MOTION_Z, 0.f);
        this.dataManager.register(BULLET_SIZE, 0.f);
    }

    public AbstractAmmoEntity(EntityType<? extends AbstractAmmoEntity> type, World world){
        super(type, world);
    }

//    @OnlyIn(Dist.CLIENT)
    public AbstractAmmoEntity(FMLPlayMessages.SpawnEntity entity, World world){
        this(EntityList.BULLET_ENTITY.get(), world);
        this.setMotion(entity.getVelX(), entity.getVelY(), entity.getVelZ());
        this.setHeadRotation(entity.getYaw(), entity.getPitch());
        this.setPosition(entity.getPosX(), entity.getPosY(), entity.getPosZ());
    }

    public AbstractAmmoEntity(double x, double y, double z, World world, ItemStack itemStack, PlayerEntity shooter){
        super(EntityList.BULLET_ENTITY.get(), x, y, z, world);
        this.setItem(itemStack);
        this.setShooter(shooter);
        this.bulletSize = getSizeFromString(((AbstractAmmo)itemStack.getItem()).getSize());
        this.dataManager.set(BULLET_SIZE, (float) bulletSize);
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
            initSpeed = this.getMotion().length();
            this.bulletSize = this.dataManager.get(BULLET_SIZE);
//            initVec = this.getMotion();
            this.energy = getEnergy(initSpeed);
            initEnergy = this.energy;
//                this.setMotion(initVec.mul(0.01, 0.01, 0.01));
        }

        // Syn motion, default forge won't work for high speed objects
        if (world.isRemote){
            this.setMotion(this.dataManager.get(CURRENT_MOTION_X), this.dataManager.get(CURRENT_MOTION_Y), this.dataManager.get(CURRENT_MOTION_Z));
        }
        else{
            Vector3d vector3d = this.getMotion();
            this.dataManager.set(CURRENT_MOTION_X, (float)vector3d.x);
            this.dataManager.set(CURRENT_MOTION_Y, (float)vector3d.y);
            this.dataManager.set(CURRENT_MOTION_Z, (float)vector3d.z);
        }

        isMyImpact=true;
        if (!world.isRemote){
            RayTraceResult raytraceresult = ProjectileHelper.func_234618_a_(this, this::func_230298_a_);
            while (!this.removed&&raytraceresult!=null){
                raytraceresult = ProjectileHelper.func_234618_a_(this, this::func_230298_a_);

                if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                    Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
                    Entity indirect = this.func_234616_v_();
                    if (entity instanceof PlayerEntity && indirect instanceof PlayerEntity && !((PlayerEntity)indirect).canAttackPlayer((PlayerEntity)entity)) {
                        raytraceresult = null;
                    }
                }

                if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                    this.onImpact(raytraceresult);
                }
                if(raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.MISS){
                    break;
                }
            }
        }
        isMyImpact=false;

        super.tick();

        if(this.energy <= 0){
            this.remove();
        }
        this.setMotion(this.getMotion().add(0, -gravity, 0));

        this.age++;
        Vector3d vector3d = this.getMotion();
        float f = MathHelper.sqrt(horizontalMag(vector3d));
        this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));

        // remove aged bullets which are not removed by other means
        if(this.age >= 1000){
            this.remove();
        }
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        Entity entity = entityRayTraceResult.getEntity();
        this.piercedEntities.add(entity.getEntityId());

        if(entity instanceof ItemEntity){
            // bullets do not destroy item
            return;
        }
        double damage = this.baseDamage * getEnergy(this.getMotion().length()) / this.initEnergy;
        // get shooter
        DamageSource damageSource = new IndirectEntityDamageSource(new ResourceLocation(NuclearCraft.MODID, "bullet").toString(), this, this.func_234616_v_()).setProjectile();
        entity.attackEntityFrom(damageSource, (float) damage);

        if (entity instanceof LivingEntity){
            this.energy -= 30;
        }
        else {
            this.energy -= 10;
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        double kEBefore = this.energy;
        // do hack to remove impact in super.tick()
        if(isMyImpact&&!world.isRemote){
            super.onImpact(result);
        }
        if(this.energy<=0){
            this.remove();
        }
        double factor = Math.sqrt(this.energy / kEBefore);
        this.setMotion(this.getMotion().mul(factor, factor, factor));
    }

    @Override
    protected boolean func_230298_a_(Entity p_230298_1_) {
        return super.func_230298_a_(p_230298_1_)||((this.piercedEntities == null || !this.piercedEntities.contains(p_230298_1_.getEntityId())));
    }

    @Override
    protected void func_230299_a_(@Nonnull BlockRayTraceResult blockRayTraceResult) {
        if(true){
            Block block = world.getBlockState(blockRayTraceResult.getPos()).getBlock();
            double blastResist = block.getExplosionResistance();
            if(blastResist>getBlockBreakThreshold()+1e-3){
                // ricochet
                Direction blockDirection = blockRayTraceResult.getFace();
                this.ricochetSpeed(blockDirection);
                Vector3d hitResult = blockRayTraceResult.getHitVec();
                this.setPosition(hitResult.x, hitResult.y, hitResult.z);
                this.energy -= this.initEnergy * getRicochetEnergyLoss();
            }
            else {
                // destroy
                world.destroyBlock(blockRayTraceResult.getPos(), true);
                this.energy -= getEnergyLoss(blastResist);
            }
            this.piercedEntities.clear();
        }
    }

    public double getRicochetEnergyLoss(){
        return 0.5d;
    }

    public double getBlockBreakThreshold(){
        return 3d;
    }

    public double getEnergyLoss(double blastResist){
        return 25 + 10 * (blastResist-2);
    }

    private void ricochetSpeed(Direction direction){
        switch (direction){
            case UP:
            case DOWN:
                this.setMotion(this.getMotion().mul(1, -1, 1));
                break;
            case EAST:
            case WEST:
                this.setMotion(this.getMotion().mul(-1, 1, 1));
                break;
            case NORTH:
            case SOUTH:
                this.setMotion(this.getMotion().mul(1, 1, -1));
                break;
            default:break;
        }
    }

    protected double getEnergy(double speed) {
        return 0.5*speed*speed*(this.bulletSize/9);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemList.AMMO_9MM.get();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static double getSizeFromString(String size){
        switch (size){
            case "12.7mm":
                return 12.7;
            case "5.56mm":
                return 5.56;
            case "7.62mm":
                return 7.62;
            case "9mm":
                return 9;
            default:
                throw new ValueException("Unrecognized size: "+size);
        }
    }
}
