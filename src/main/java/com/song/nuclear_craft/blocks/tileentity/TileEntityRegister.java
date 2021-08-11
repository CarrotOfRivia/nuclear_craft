package com.song.nuclear_craft.blocks.tileentity;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.blocks.BlockList;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, NuclearCraft.MODID);

    public static final RegistryObject<BlockEntityType<AtomicBombTileEntity>> C4_ATOMIC_BOMB_TE_TYPE = TILE_ENTITY_TYPES.register("c4_atomic_bomb_te_type", ()->BlockEntityType.Builder.of(AtomicBombTileEntity::new, BlockList.C4_ATOMIC_BOMB).build(null));
    public static final RegistryObject<BlockEntityType<SmokeTileEntity>> C4_SMOKE_TE_TYPE = TILE_ENTITY_TYPES.register("c4_smoke_te_type", ()->BlockEntityType.Builder.of(SmokeTileEntity::new, BlockList.C4_SMOKE).build(null));
    public static final RegistryObject<BlockEntityType<IncendiaryTileEntity>> C4_INCENDIARY_TE_TYPE = TILE_ENTITY_TYPES.register("c4_incendiary_te_type", ()->BlockEntityType.Builder.of(IncendiaryTileEntity::new, BlockList.C4_INCENDIARY).build(null));
    public static final RegistryObject<BlockEntityType<HighExplosiveTileEntity>> C4_HIGH_EXPLOSIVE_TE_TYPE = TILE_ENTITY_TYPES.register("c4_high_explosive_te_type", ()->BlockEntityType.Builder.of(HighExplosiveTileEntity::new, BlockList.C4_HIGH_EXPLOSIVE).build(null));

}
