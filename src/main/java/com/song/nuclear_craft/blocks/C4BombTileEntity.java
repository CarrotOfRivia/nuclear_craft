package com.song.nuclear_craft.blocks;

import com.song.nuclear_craft.misc.SoundEventList;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class C4BombTileEntity extends TileEntity implements ITickableTileEntity {
    private static final int DEFAULT_FUSE_TIME = 800;
    protected boolean is_active;
    protected int fuse_age;
    protected int explode_time;

    protected int threshold1;
    protected int threshold2;

    public C4BombTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.is_active = false;
        this.fuse_age = 0;
        this.explode_time = DEFAULT_FUSE_TIME;
        this.threshold1 = (int) (this.explode_time * 0.33);
        this.threshold2 = (int) (this.explode_time * 0.66);
    }

    public C4BombTileEntity(TileEntityType<?> tileEntityTypeIn, int explode_time){
        this(tileEntityTypeIn);
        this.explode_time = explode_time;
    }

    public static C4BombTileEntity C4AtomicBombFactory(){
        return new C4BombTileEntity(TileEntityList.C4_ATOMIC_BOMB_TE_TYPE);
    }

    public static C4BombTileEntity C4SmokeFactory(){
        return new C4BombTileEntity(TileEntityList.C4_SMOKE_TE_TYPE);
    }

    public static C4BombTileEntity C4IncendiaryFactory(){
        return new C4BombTileEntity(TileEntityList.C4_INCENDIARY_TE_TYPE);
    }

    public static C4BombTileEntity C4HighExplosiveFactory(){
        return new C4BombTileEntity(TileEntityList.C4_HIGH_EXPLOSIVE_TE_TYPE);
    }

    private int getBeepInterval(){
        if (fuse_age > explode_time-40){
            return 2;
        }
        else if(fuse_age > threshold2){
            return 8;
        }
        else if(fuse_age > threshold1){
            return 15;
        }
        else {
            return 20;
        }
    }

    @Override
    public void tick() {
        if (is_active){
            assert world != null;
            if (!world.isRemote) {
                fuse_age++;
                this.markDirty();

                if (fuse_age % getBeepInterval() == 0) {
                    world.playSound(null, this.getPos(), SoundEventList.C4_BEEP, SoundCategory.BLOCKS, 2f, 1.0f);
                }

                if (fuse_age >= explode_time) {
                    C4Bomb block = (C4Bomb) this.getBlockState().getBlock();
                    World world = this.world;
                    BlockPos pos = this.getPos();
                    this.remove();
                    world.removeBlock(pos, false);
                    block.explode(world, pos.getX(), pos.getY(), pos.getZ());
                }
            }
        }
    }

    public void setActive(){
        this.is_active = true;
    }

}
