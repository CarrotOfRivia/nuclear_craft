package com.song.nuclear_craft.entities.AmmoEntities;

import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nonnull;

public class AmmoBirdShotBlighEntity extends AmmoBirdShotNormalEntity{
    public AmmoBirdShotBlighEntity(EntityType<? extends AbstractAmmoEntity> type, World world) {
        super(type, world);
    }

    public AmmoBirdShotBlighEntity(FMLPlayMessages.SpawnEntity entity, World world) {
        super(entity, world);
    }

    public AmmoBirdShotBlighEntity(double x, double y, double z, World world, ItemStack itemStack, PlayerEntity shooter) {
        super(x, y, z, world, itemStack, shooter);
    }

    @Override
    protected void onEntityHit(@Nonnull EntityRayTraceResult entityRayTraceResult) {
        Entity entity = entityRayTraceResult.getEntity();
        if(entity instanceof LivingEntity){
//            TODO Do something
//            ((LivingEntity) entity).getAttributeManager().createInstanceIfAbsent(Attributes.ARMOR).applyNonPersistentModifier();
        }
        super.onEntityHit(entityRayTraceResult);
    }
}
