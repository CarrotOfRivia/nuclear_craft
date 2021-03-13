package com.song.nuclear_craft.blocks.tileentity;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.BlockList;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityRegister {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, NuclearCraft.MODID);

    public static TileEntityType<?> C4_ATOMIC_BOMB_TE_TYPE = TileEntityType.Builder.create(AtomicBombTileEntity::new, BlockList.C4_ATOMIC_BOMB).build(null);
    public static TileEntityType<?> C4_SMOKE_TE_TYPE = TileEntityType.Builder.create(SmokeTileEntity::new, BlockList.C4_SMOKE).build(null);
    public static TileEntityType<?> C4_INCENDIARY_TE_TYPE = TileEntityType.Builder.create(IncendiaryTileEntity::new, BlockList.C4_INCENDIARY).build(null);
    public static TileEntityType<?> C4_HIGH_EXPLOSIVE_TE_TYPE = TileEntityType.Builder.create(HighExplosiveTileEntity::new, BlockList.C4_HIGH_EXPLOSIVE).build(null);
    static {
        C4_ATOMIC_BOMB_TE_TYPE.setRegistryName(NuclearCraft.MODID, "c4_atomic_bomb_te_type");
        C4_SMOKE_TE_TYPE.setRegistryName(NuclearCraft.MODID, "c4_smoke_te_type");
        C4_INCENDIARY_TE_TYPE.setRegistryName(NuclearCraft.MODID, "c4_incendiary_te_type");
        C4_HIGH_EXPLOSIVE_TE_TYPE.setRegistryName(NuclearCraft.MODID, "c4_high_explosive_te_type");
    }
}
