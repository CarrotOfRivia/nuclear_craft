package com.song.nuclear_craft.entities;

import com.song.nuclear_craft.items.ItemList;
import com.sun.scenario.animation.AnimationPulse;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class AbstractAmmoEntity extends ProjectileItemEntity {
    private double kineticE;
    private double initSpeed;
    private Vector3d initVec;
    private double gravity = 0.03f;
    private int age = 0;
    // BlockPos to store init motion
    private static final DataParameter<Float> CURRENT_MOTION_X = EntityDataManager.createKey(ProjectileItemEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> CURRENT_MOTION_Y = EntityDataManager.createKey(ProjectileItemEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> CURRENT_MOTION_Z = EntityDataManager.createKey(ProjectileItemEntity.class, DataSerializers.FLOAT);

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(CURRENT_MOTION_X, 0.f);
        this.dataManager.register(CURRENT_MOTION_Y, 0.f);
        this.dataManager.register(CURRENT_MOTION_Z, 0.f);
    }

    public AbstractAmmoEntity(EntityType<? extends AbstractAmmoEntity> type, World world){
        super(type, world);
    }

    @OnlyIn(Dist.CLIENT)
    public AbstractAmmoEntity(FMLPlayMessages.SpawnEntity entity, World world){
        this(EntityList.BULLET_ENTITY, world);
        this.setMotion(entity.getVelX(), entity.getVelY(), entity.getVelZ());
        this.setHeadRotation(entity.getYaw(), entity.getPitch());
        this.setPosition(entity.getPosX(), entity.getPosY(), entity.getPosZ());
    }

    public AbstractAmmoEntity(double x, double y, double z, World world, ItemStack itemStack, PlayerEntity shooter){
        super(EntityList.BULLET_ENTITY, x, y, z, world);
        this.setItem(itemStack);
    }

    public void setGravity(float gravity){
        this.gravity = gravity;
    }


    @Override
    public void tick() {

        double kEBefore = -1;
        if (age == 0){
            // init: speed small-> big for better performance
            initSpeed = this.getMotion().length();
            initVec = this.getMotion();
            this.kineticE = getKineticE(initSpeed);
//                this.setMotion(initVec.mul(0.01, 0.01, 0.01));
        }else {
            kEBefore = this.kineticE;
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

        super.tick();

        if(this.kineticE <= 0){
            this.remove();
        }

        if (this.age > 5){
            double factor = Math.sqrt(this.kineticE/kEBefore);
            this.setMotion(this.getMotion().mul(factor, factor, factor));
            this.setMotion(this.getMotion().add(0, -gravity, 0));
        }
        this.age++;
        Vector3d vector3d = this.getMotion();
        float f = MathHelper.sqrt(horizontalMag(vector3d));
        this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
//             this.setRotation((float) (180*Math.atan(vector3d.y/vector3d.x)/Math.PI), (float) (180*Math.atan(vector3d.x/vector3d.z)/Math.PI));
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        Entity entity = entityRayTraceResult.getEntity();
        if (entity instanceof LivingEntity){
            ((LivingEntity) entity).setHealth(0f);
            this.kineticE -= 2;
        }
        else {
            this.kineticE -= 1;
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
    }

    @Override
    protected void func_230299_a_(@Nonnull BlockRayTraceResult blockRayTraceResult) {
        if(! world.isRemote){
            Block block = world.getBlockState(blockRayTraceResult.getPos()).getBlock();
            double blastResist = block.getExplosionResistance();
            if(blastResist>3){
                // ricochet
                Direction blockDirection = blockRayTraceResult.getFace();
                this.ricochet(blockDirection);
                this.kineticE -= 1;
            }
            else if(blastResist >=2){
                // destroy
                world.destroyBlock(blockRayTraceResult.getPos(), true);
                this.kineticE -= 3;
            }
            else {
                // destroy
                world.destroyBlock(blockRayTraceResult.getPos(), true);
                this.kineticE -= 1;
            }

        }
    }

    private void ricochet(Direction direction){
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

    protected double getKineticE(double initSpeed){
        return 0.1*initSpeed*initSpeed;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemList.AMMO_9MM;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
