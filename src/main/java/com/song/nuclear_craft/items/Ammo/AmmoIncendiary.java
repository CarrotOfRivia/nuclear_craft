//package com.song.nuclear_craft.items.Ammo;
//
//import com.song.nuclear_craft.NuclearCraft;
//import com.song.nuclear_craft.items.AbstractAmmo;
//
//import javax.annotation.Nonnull;
//
//public class AmmoIncendiary extends AbstractAmmo {
//    public AmmoIncendiary() {
//        super(new Properties().group(NuclearCraft.ITEM_GROUP));
//    }
//
//    public AmmoIncendiary(String size) {
//        super(new Properties().group(NuclearCraft.ITEM_GROUP), size);
//    }
//
//    @Nonnull
//    @Override
//    public AmmoType getType() {
//        return AmmoType.INCENDIARY;
//    }
//
//    @Override
//    public double getBaseDamage() {
//        return 20;
//    }
//
//    @Override
//    public float getBaseSpeed() {
//        return 5;
//    }
//
//    @Override
//    public double getGravity() {
//        return 0.03;
//    }
//}
