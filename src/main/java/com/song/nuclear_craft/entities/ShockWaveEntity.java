package com.song.nuclear_craft.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;

public class ShockWaveEntity extends ArmorStand {
    private int life=0;
    private int radius;
    private final int maxLife;

    public ShockWaveEntity(Level worldIn, double posX, double posY, double posZ, int maxLife, int radius) {
        super(EntityType.ARMOR_STAND, worldIn);
        this.setPos(posX, posY, posZ);
        this.maxLife = maxLife;
        this.isNoGravity();
        this.radius = radius;
    }

    public ShockWaveEntity(Level world, double x, double y, double z, int maxLife) {
        this(world, x, y, z, maxLife, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.life>this.maxLife){
            this.setRemoved(RemovalReason.KILLED);
        }

        if (! this.level.isClientSide() && (life % 10 ==0)){
            int maxStep = radius*6;
            int maxDy = 3;
            for(int step=0; step<maxStep; step++){
                double theta = 2*Math.PI*step/maxStep;
                double x = this.getX() + Math.cos(theta) * radius;
                double z = this.getZ() + Math.sin(theta) * radius;
                for (int dy=-maxDy; dy<=maxDy; dy++){
                    this.level.destroyBlock(new BlockPos(x, dy+this.getY(), z), true);
                }
            }
            radius++;
        }

        life ++;
    }
}
