package com.song.nuclear_craft.effects;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectRegister {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, NuclearCraft.MODID);

    public static final RegistryObject<Effect> RADIOACTIVE = EFFECTS.register("radioactive", Radioactive::new);
}
