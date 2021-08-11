//package com.song.nuclear_craft.blocks;
//
//import com.song.nuclear_craft.entities.UraniumCentrifugeTEntity;
//import net.minecraft.world.level.block.state.BlockBehaviour;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.material.Material;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.MenuProvider;
//import net.minecraft.tileentity.FurnaceTileEntity;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.phys.BlockHitResult;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.Level;
//
//import javax.annotation.Nullable;
//
//@Deprecated
//public class UraniumCentrifuge extends Block {
//    public UraniumCentrifuge() {
//        super(BlockBehaviour.Properties.of(Material.STONE).strength(3.0F, 3.0F));
//    }
//
//    @Override
//    public boolean hasTileEntity(BlockState state) {
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
//        return new UraniumCentrifugeTEntity();
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
//        if(worldIn.isClientSide){
//            return InteractionResult.SUCCESS;
//        }
//        else{
//            BlockEntity tileentity = worldIn.getBlockEntity(pos);
//            if (tileentity instanceof UraniumCentrifugeTEntity){
//                player.openMenu((MenuProvider)tileentity);
//            }
//            return InteractionResult.CONSUME;
//        }
//    }
//}
