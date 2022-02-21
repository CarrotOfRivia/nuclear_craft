package com.song.nuclear_craft.effects;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectRegister {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, NuclearCraft.MODID);

    public static final RegistryObject<MobEffect> RADIOACTIVE = EFFECTS.register("radioactive", Radioactive::new);
}
