package com.song.nuclear_craft.blocks;

import com.song.nuclear_craft.entities.UraniumCentrifugeTEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@Deprecated
public class UraniumCentrifuge extends Block {
    public UraniumCentrifuge() {
        super(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new UraniumCentrifugeTEntity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(worldIn.isRemote){
            return ActionResultType.SUCCESS;
        }
        else{
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof UraniumCentrifugeTEntity){
                player.openContainer((INamedContainerProvider)tileentity);
                // TODO add stat
            }
            return ActionResultType.CONSUME;
        }
    }
}
