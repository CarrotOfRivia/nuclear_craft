package com.song.nuclear_craft.blocks;

import com.song.nuclear_craft.blocks.tileentity.C4BombTileEntity;
import com.song.nuclear_craft.blocks.tileentity.TileEntityRegister;
import com.song.nuclear_craft.items.defuse_kit.DefuseKit;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import com.song.nuclear_craft.network.SoundPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class C4Bomb extends FaceAttachedHorizontalDirectionalBlock implements EntityBlock {
    protected static final VoxelShape FLOOR_H_X = Block.box(4.125, 0, 1, 12, 4, 15);
    protected static final VoxelShape FLOOR_H_Z = Block.box(1, 0, 4.125, 15, 4, 12);
    protected static final VoxelShape CEIL_H_X = Block.box(4.125, 12, 1, 12, 16, 15);
    protected static final VoxelShape CEIL_H_Z = Block.box(1, 12, 4.125, 15, 16, 12);

    protected static final VoxelShape WALL_E = Block.box(0, 4.125, 1, 4, 12, 15);
    protected static final VoxelShape WALL_W = Block.box(12, 4.125, 1, 16, 12, 15);
    protected static final VoxelShape WALL_S = Block.box(1, 4.125, 0, 15, 12, 4);
    protected static final VoxelShape WALL_N = Block.box(1, 4.125, 12, 15, 12, 16);

    public C4Bomb(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL));
    }


    @Nullable
    @Override
    public abstract BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState);

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if(worldIn.hasNeighborSignal(pos)){
            BlockEntity entity = worldIn.getBlockEntity(pos);
            if (entity instanceof C4BombTileEntity){
                ((C4BombTileEntity) entity).activate();
            }
        }
    }

    public void explode(Level world, double x, double y, double z){

    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        switch((AttachFace)state.getValue(FACE)) {
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FACE);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isClientSide){
            this.neighborChanged(state, worldIn, pos, this, pos, false);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if(itemStack.getItem() instanceof DefuseKit){
            if(! worldIn.isClientSide()){
                C4BombTileEntity c4BombTileEntity = (C4BombTileEntity)worldIn.getBlockEntity(pos);
                if(c4BombTileEntity != null && c4BombTileEntity.currentDefuseStatus<0){
                    c4BombTileEntity.currentDefuseStatus = 0;
                    c4BombTileEntity.defuseTime = ((DefuseKit)(itemStack.getItem())).getDefuseTick();
                    c4BombTileEntity.defusingEntityID = player.getId();
                    c4BombTileEntity.defusingTool = Objects.requireNonNull(itemStack.getItem().getRegistryName()).toString();
                    NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), 21, worldIn.dimension())),
                            new SoundPacket(pos, "defusing"));
                }
            }
            return InteractionResult.PASS;
        }
        else {
            if (worldIn.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                BlockEntity tileentity = worldIn.getBlockEntity(pos);
                if (tileentity instanceof C4BombTileEntity) {
                    ((C4BombTileEntity) tileentity).synToClient();
                    NetworkHooks.openGui((ServerPlayer) player, (C4BombTileEntity)tileentity, pos);
                }
                return InteractionResult.CONSUME;
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return true;
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }

}
