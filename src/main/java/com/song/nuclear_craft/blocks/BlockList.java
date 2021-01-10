package com.song.nuclear_craft.blocks;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockList {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NuclearCraft.MODID);

    // TO be removed:
    public static final Block ATOMIC_BOMB = new AtomicBomb().setRegistryName("atomic_bomb");
    public static final Block URANIUM_ORE = new UraniumOre().setRegistryName("uranium_ore");
    public static final Block URANIUM_CENTRIFUGE = new UraniumCentrifuge().setRegistryName("uranium_centrifuge");


    // True added blocks from here:
    public static final C4AtomicBomb C4_ATOMIC_BOMB = new C4AtomicBomb(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1f, 3f));
    public static final C4HighExplosive C4_HIGH_EXPLOSIVE = new C4HighExplosive(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1f, 3f));
    public static final C4Incendiary C4_INCENDIARY = new C4Incendiary(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1f, 3f));
    public static final C4Smoke C4_SMOKE = new C4Smoke(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(1f, 3f));

    public static final RegistryObject<Block> STATUE_OF_LIBERTY = BLOCKS.register("statue_of_liberty", ()-> new Statue(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> STATUE_OF_RIFLE_AMMO = BLOCKS.register("statue_of_rifle_ammo", ()-> new Statue(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> STATUE_OF_SHOTGUN_AMMO = BLOCKS.register("statue_of_shotgun_ammo", ()-> new Statue(AbstractBlock.Properties.create(Material.ROCK)));

    static {
        C4_ATOMIC_BOMB.setRegistryName(NuclearCraft.MODID, "c4_atomic_bomb");
        C4_HIGH_EXPLOSIVE.setRegistryName(NuclearCraft.MODID, "c4_high_explosive");
        C4_INCENDIARY.setRegistryName(NuclearCraft.MODID, "c4_incendiary");
        C4_SMOKE.setRegistryName(NuclearCraft.MODID, "c4_smoke");
    }
}
