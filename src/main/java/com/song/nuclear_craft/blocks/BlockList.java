package com.song.nuclear_craft.blocks;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockList {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NuclearCraft.MODID);


    // True added blocks from here:
    public static final C4AtomicBomb C4_ATOMIC_BOMB = new C4AtomicBomb(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F));
    public static final C4HighExplosive C4_HIGH_EXPLOSIVE = new C4HighExplosive(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F));
    public static final C4Incendiary C4_INCENDIARY = new C4Incendiary(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F));
    public static final C4Smoke C4_SMOKE = new C4Smoke(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F));

    public static final RegistryObject<Block> STATUE_OF_LIBERTY = BLOCKS.register("statue_of_liberty", ()-> new Statue(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> STATUE_OF_RIFLE_AMMO = BLOCKS.register("statue_of_rifle_ammo", ()-> new Statue(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> STATUE_OF_SHOTGUN_AMMO = BLOCKS.register("statue_of_shotgun_ammo", ()-> new Statue(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> STATUE_OF_ROCKET = BLOCKS.register("statue_of_rocket", ()-> new Statue(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> STATUE_OF_EXPLOSIVE = BLOCKS.register("statue_of_explosive", ()-> new Statue(BlockBehaviour.Properties.of(Material.STONE)));

    static {
        C4_ATOMIC_BOMB.setRegistryName(NuclearCraft.MODID, "c4_atomic_bomb");
        C4_HIGH_EXPLOSIVE.setRegistryName(NuclearCraft.MODID, "c4_high_explosive");
        C4_INCENDIARY.setRegistryName(NuclearCraft.MODID, "c4_incendiary");
        C4_SMOKE.setRegistryName(NuclearCraft.MODID, "c4_smoke");
    }
}
