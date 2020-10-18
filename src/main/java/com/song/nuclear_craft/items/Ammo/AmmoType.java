package com.song.nuclear_craft.items.Ammo;

import javax.annotation.Nonnull;

public enum AmmoType {
    ANTI_GRAVITY("Anti-Gravity", "anti_gravity", 5, 25, 0),
    EXPLOSIVE("Explosive","explosive", 5, 20, 0.03f),
    INCENDIARY("Incendiary", "incendiary", 5, 20, 0.03f),
    NORMAL("Normal", "normal", 5, 27, 0.03f),
    NUKE("Nuke", "nuke", 6, 30, 0.03f),
    SILVER("Silver", "silver",7, 40, 0.03f),
//    TEST("Test", "test", 1, 30, 0f),
    TUNGSTEN("Tungsten", "tungsten", 8, 50, 0.03f)
    ;
    private final String description;
    private final String registerString;
    private final float speed;
    private final float damage;
    private final float gravity;
    AmmoType(String description, String registerString, float speed, float damage, float gravity){
        this.description = description;
        this.registerString = registerString;
        this.speed = speed;
        this.damage = damage;
        this.gravity = gravity;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    public String getRegisterString() {
        return registerString;
    }

    public float getDamage() {
        return damage;
    }

    public float getSpeed() {
        return speed;
    }

    public float getGravity() {
        return gravity;
    }
}
