package com.song.nuclear_craft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class ShockWaveEntity extends ArmorStandEntity {
    private int life=0;
    private int radius;
    private final int maxLife;

    public ShockWaveEntity(World worldIn, double posX, double posY, double posZ, int maxLife, int radius) {
        super(EntityType.ARMOR_STAND, worldIn);
        this.setPosition(posX, posY, posZ);
        this.maxLife = maxLife;
        this.hasNoGravity();
        this.radius = radius;
    }

    public ShockWaveEntity(World world, double x, double y, double z, int maxLife) {
        this(world, x, y, z, maxLife, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.life>this.maxLife){
            this.remove();
        }

        if (! this.world.isRemote() && (life % 10 ==0)){
            int maxStep = radius*6;
            int maxDy = 3;
            for(int step=0; step<maxStep; step++){
                double theta = 2*Math.PI*step/maxStep;
                double x = this.getPosX() + Math.cos(theta) * radius;
                double z = this.getPosZ() + Math.sin(theta) * radius;
                for (int dy=-maxDy; dy<=maxDy; dy++){
                    this.world.destroyBlock(new BlockPos(x, dy+this.getPosY(), z), true);
                }
            }
            radius++;
        }

        life ++;
    }
}
