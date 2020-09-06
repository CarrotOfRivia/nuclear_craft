package com.song.nuclear_craft.blocks;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.BlockList;
import com.song.nuclear_craft.blocks.UraniumCentrifuge;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityList {
    public static TileEntityType<?> C4_ATOMIC_BOMB_TE_TYPE = TileEntityType.Builder.create(C4BombTileEntity::C4AtomicBombFactory, BlockList.C4_ATOMIC_BOMB).build(null);
    public static TileEntityType<?> C4_SMOKE_TE_TYPE = TileEntityType.Builder.create(C4BombTileEntity::C4SmokeFactory, BlockList.C4_SMOKE).build(null);
    public static TileEntityType<?> C4_INCENDIARY_TE_TYPE = TileEntityType.Builder.create(C4BombTileEntity::C4IncendiaryFactory, BlockList.C4_INCENDIARY).build(null);
    public static TileEntityType<?> C4_HIGH_EXPLOSIVE_TE_TYPE = TileEntityType.Builder.create(C4BombTileEntity::C4HighExplosiveFactory, BlockList.C4_HIGH_EXPLOSIVE).build(null);
    static {
        C4_ATOMIC_BOMB_TE_TYPE.setRegistryName(NuclearCraft.MODID, "c4_atomic_bomb_te_type");
        C4_SMOKE_TE_TYPE.setRegistryName(NuclearCraft.MODID, "c4_smoke_te_type");
        C4_INCENDIARY_TE_TYPE.setRegistryName(NuclearCraft.MODID, "c4_incendiary_te_type");
        C4_HIGH_EXPLOSIVE_TE_TYPE.setRegistryName(NuclearCraft.MODID, "c4_high_explosive_te_type");
    }
}
