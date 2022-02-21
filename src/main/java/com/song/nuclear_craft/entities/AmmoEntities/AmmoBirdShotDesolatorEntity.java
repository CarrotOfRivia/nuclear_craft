package com.song.nuclear_craft.entities.AmmoEntities;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;

public class AmmoBirdShotDesolatorEntity extends AmmoBirdShotNormalEntity{
    // TODO maybe a better way of generating uuid
    private static final UUID uuid = UUID.nameUUIDFromBytes((NuclearCraft.MODID+".blight_armor_reduction").getBytes());
    public AmmoBirdShotDesolatorEntity(EntityType<? extends AbstractAmmoEntity> type, Level world) {
        super(type, world);
    }

    public AmmoBirdShotDesolatorEntity(PlayMessages.SpawnEntity entity, Level world) {
        super(entity, world);
    }

    public AmmoBirdShotDesolatorEntity(double x, double y, double z, Level world, ItemStack itemStack, Player shooter) {
        super(x, y, z, world, itemStack, shooter);
    }

    @Override
    protected void onHitEntity(@Nonnull EntityHitResult entityRayTraceResult) {
        Entity entity = entityRayTraceResult.getEntity();
        if(entity instanceof LivingEntity){
            AttributeModifier armorReductionModifier = new AttributeModifier(uuid, "Blight Armor modifier", -14, AttributeModifier.Operation.ADDITION);
            Objects.requireNonNull(((LivingEntity) entity).getAttributes().getInstance(Attributes.ARMOR)).addTransientModifier(armorReductionModifier);
            super.onHitEntity(entityRayTraceResult);
            Objects.requireNonNull(((LivingEntity) entity).getAttributes().getInstance(Attributes.ARMOR)).removeModifier(armorReductionModifier);
        }
        else {
            super.onHitEntity(entityRayTraceResult);
        }
    }
}
