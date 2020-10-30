package com.song.nuclear_craft.entities.AmmoEntities;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;

public class AmmoBirdShotBlighEntity extends AmmoBirdShotNormalEntity{
    // TODO maybe a better way of generating uuid
    private static final UUID uuid = UUID.nameUUIDFromBytes((NuclearCraft.MODID+".blight_armor_reduction").getBytes());
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
            AttributeModifier armorReductionModifier = new AttributeModifier(uuid, "Blight Armor modifier", -7, AttributeModifier.Operation.ADDITION);
            Objects.requireNonNull(((LivingEntity) entity).getAttributeManager().createInstanceIfAbsent(Attributes.ARMOR)).applyNonPersistentModifier(armorReductionModifier);
            super.onEntityHit(entityRayTraceResult);
            Objects.requireNonNull(((LivingEntity) entity).getAttributeManager().createInstanceIfAbsent(Attributes.ARMOR)).removeModifier(armorReductionModifier);
        }
        else {
            super.onEntityHit(entityRayTraceResult);
        }
    }
}
