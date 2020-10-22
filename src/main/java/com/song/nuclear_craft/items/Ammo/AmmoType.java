package com.song.nuclear_craft.items.Ammo;

import com.song.nuclear_craft.items.AbstractAmmo;
import com.song.nuclear_craft.items.ItemList;

import javax.annotation.Nonnull;
import java.util.HashMap;

public enum AmmoType {
    ANTI_GRAVITY("Anti-Gravity", "anti_gravity", 5, 25, 0),
    EXPLOSIVE("Explosive","explosive", 5, 20, 0.03f),
    INCENDIARY("Incendiary", "incendiary", 5, 20, 0.03f),
    NORMAL("Normal", "normal", 5, 27, 0.03f),
    NUKE("Nuke", "nuke", 6, 30, 0.03f),
    SILVER("Silver", "silver",7, 40, 0.03f),
    TUNGSTEN("Tungsten", "tungsten", 8, 50, 0.03f),
    SHORT_GUN_NORMAL("Normal", "short_gun_normal", 5, 7, 0.03f, 10),
    SHORT_GUN_BLIGHT("Blight", "short_gun_blight", 5, 7, 0.03f, 10)
    ;
    private final String description;
    private final String registerString;
    private final float speed;
    private final float damage;
    private final float gravity;
    private int birdShotCount =0;

    AmmoType(String description, String registerString, float speed, float damage, float gravity){
        this.description = description;
        this.registerString = registerString;
        this.speed = speed;
        this.damage = damage;
        this.gravity = gravity;
    }

    AmmoType(String description, String registerString, float speed, float damage, float gravity, int birdShotCount){
        this(description, registerString, speed, damage, gravity);
        this.birdShotCount = birdShotCount;

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

    public int getBirdShotCount(){
        return birdShotCount;
    }

    public static AbstractAmmo getBirdShotFromType(AmmoType ammoType){
        switch (ammoType){
            case SHORT_GUN_NORMAL:
            case SHORT_GUN_BLIGHT:
                return ItemList.BIRD_SHOT_12_GA.get();
            default:
                return null;
        }
    }
}
