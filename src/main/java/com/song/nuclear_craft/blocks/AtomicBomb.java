package com.song.nuclear_craft.blocks;

import com.song.nuclear_craft.entities.AtomicBombEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AtomicBomb extends TNTBlock {

    public AtomicBomb() {
        super(AbstractBlock.Properties.create(Material.TNT).zeroHardnessAndResistance().sound(SoundType.PLANT));
    }

    @Override
    public void catchFire(BlockState state, World worldIn, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        if (!worldIn.isRemote) {
            TNTEntity tntentity = new AtomicBombEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, igniter);
            worldIn.addEntity(tntentity);
            worldIn.playSound((PlayerEntity)null, tntentity.getPosX(), tntentity.getPosY(), tntentity.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }
}
