package com.song.nuclear_craft.items.Ammo;

public enum  AmmoSize {
    SIZE_9MM("9mm", "9mm", 9, 1f, 1f),
    SIZE_127("12.7mm", "127", 12.7f, 1.4f, 2f),
    SIZE_762("7.62mm", "762", 7.62f, 1.3f, 0.8f),
    SIZE_556("5.56mm", "556", 5.56f, 1.4f, 0.7f),
    SIZE_570("5.70mm", "570", 5.70f, 1f, 1f),
    SIZE_12_GA("12 gauge", "12_ga", 18.53f, 1f, 1f);
    ;
    private final String registerString;
    private final float size;
    private final float speedModify;
    private final float damageModify;
    private final String description;

    AmmoSize(String description, String registerString, float size, float speedModify, float damageModify){
        this.registerString = registerString;
        this.size = size;
        this.speedModify = speedModify;
        this.damageModify = damageModify;
        this.description = description;
    }

    public String getRegisterString() {
        return registerString;
    }

    public float getSize() {
        // Size is only used to calculate init energy for penetrating power
        return size;
    }

    public float getDamageModify() {
        return damageModify;
    }

    public float getSpeedModify() {
        return speedModify;
    }

    public String getDescription() {
        return description;
    }
}
