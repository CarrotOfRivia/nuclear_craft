package com.song.nuclear_craft.entities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Ammo9MMEntity extends AbstractAmmoEntity{
    public Ammo9MMEntity(double x, double y, double z, World worldIn, ItemStack itemStack, PlayerEntity shooter) {
        super(x, y, z, worldIn, itemStack, shooter);
    }
}
