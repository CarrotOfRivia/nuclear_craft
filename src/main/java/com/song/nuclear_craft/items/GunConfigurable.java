package com.song.nuclear_craft.items;

import net.minecraftforge.common.ForgeConfigSpec;

public class GunConfigurable {
    private ForgeConfigSpec.DoubleValue damageModify;
    private ForgeConfigSpec.DoubleValue speedModify;

    public GunConfigurable(){

    }

    public void setDamageModify(ForgeConfigSpec.DoubleValue damageModify) {
        this.damageModify = damageModify;
    }

    public void setSpeedModify(ForgeConfigSpec.DoubleValue speedModify) {
        this.speedModify = speedModify;
    }

    public ForgeConfigSpec.DoubleValue getDamageModify() {
        return damageModify;
    }

    public ForgeConfigSpec.DoubleValue getSpeedModify() {
        return speedModify;
    }
}
