package com.song.nuclear_craft.entities.AmmoEntities;

import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AmmoNormalEntity extends AbstractAmmoEntity {
    public AmmoNormalEntity(double x, double y, double z, World worldIn, ItemStack itemStack, PlayerEntity shooter) {
        super(x, y, z, worldIn, itemStack, shooter);
    }
}
