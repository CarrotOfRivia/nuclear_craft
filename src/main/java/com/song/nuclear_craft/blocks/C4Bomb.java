package com.song.nuclear_craft.blocks;

import com.song.nuclear_craft.blocks.tileentity.C4BombTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public abstract class C4Bomb extends HorizontalFaceBlock {
    protected static final VoxelShape FLOOR_H_X = Block.makeCuboidShape(4.125, 0, 1, 12, 4, 15);
    protected static final VoxelShape FLOOR_H_Z = Block.makeCuboidShape(1, 0, 4.125, 15, 4, 12);
    protected static final VoxelShape CEIL_H_X = Block.makeCuboidShape(4.125, 12, 1, 12, 16, 15);
    protected static final VoxelShape CEIL_H_Z = Block.makeCuboidShape(1, 12, 4.125, 15, 16, 12);

    protected static final VoxelShape WALL_E = Block.makeCuboidShape(0, 4.125, 1, 4, 12, 15);
    protected static final VoxelShape WALL_W = Block.makeCuboidShape(12, 4.125, 1, 16, 12, 15);
    protected static final VoxelShape WALL_S = Block.makeCuboidShape(1, 4.125, 0, 15, 12, 4);
    protected static final VoxelShape WALL_N = Block.makeCuboidShape(1, 4.125, 12, 15, 12, 16);

    public C4Bomb(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(FACE, AttachFace.WALL));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if(worldIn.isBlockPowered(pos)){
            TileEntity entity = worldIn.getTileEntity(pos);
            if (entity instanceof C4BombTileEntity){
                ((C4BombTileEntity) entity).activate();
            }
        }
    }

    public void explode(World world, double x, double y, double z){

    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(HORIZONTAL_FACING);
        switch((AttachFace)state.get(FACE)) {
            case FLOOR:
                if (direction.getAxis() == Direction.Axis.X) {
                    return FLOOR_H_X;
                }

                return FLOOR_H_Z;
            case WALL:
                switch(direction) {
                    case EAST:
                        return WALL_E;
                    case WEST:
                        return WALL_W;
                    case SOUTH:
                        return WALL_S;
                    case NORTH:
                    default:
                        return WALL_N;
                }
            case CEILING:
            default:
                if (direction.getAxis() == Direction.Axis.X) {
                    return CEIL_H_X;
                } else {
                    return CEIL_H_Z;
                }
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, FACE);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isRemote){
            this.neighborChanged(state, worldIn, pos, this, pos, false);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof C4BombTileEntity) {
                ((C4BombTileEntity) tileentity).synToClient();
                NetworkHooks.openGui((ServerPlayerEntity) player, (C4BombTileEntity)tileentity, pos);
            }
            return ActionResultType.CONSUME;
        }
    }
}
