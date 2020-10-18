//package com.song.nuclear_craft.items.Ammo;
//
//import com.song.nuclear_craft.NuclearCraft;
//import com.song.nuclear_craft.items.AbstractAmmo;
//
//import javax.annotation.Nonnull;
//
//public class AmmoNuke extends AbstractAmmo {
//
//    public AmmoNuke() {
//        super(new Properties().group(NuclearCraft.ITEM_GROUP));
//    }
//
//    public AmmoNuke(String size) {
//        super(new Properties().group(NuclearCraft.ITEM_GROUP), size);
//    }
//
//
//    @Nonnull
//    @Override
//    public AmmoType getType() {
//        return AmmoType.NUKE;
//    }
//
//    @Override
//    public double getBaseDamage() {
//        return 30;
//    }
//
//    @Override
//    public float getBaseSpeed() {
//        return 6;
//    }
//
//    @Override
//    public double getGravity() {
//        return 0.03;
//    }
//}
