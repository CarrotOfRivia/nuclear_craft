package com.song.nuclear_craft.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class Radioactive extends Effect{
    public static final DamageSource RADIOACTIVE = (new DamageSource("radioactive")).setDamageBypassesArmor().setMagicDamage();
    public Radioactive() {
        super(EffectType.HARMFUL, 0);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.attackEntityFrom(RADIOACTIVE, 1.0F*amplifier);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int j = 25 >> amplifier;
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }
}
