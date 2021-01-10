package com.song.nuclear_craft.items.Ammo;

import java.util.ArrayList;
import java.util.Arrays;

public class AmmoPossibleCombination {
    public static final AmmoPossibleCombination RIFLE_AMMO = new AmmoPossibleCombination();
    public static final AmmoPossibleCombination SHOTGUN_AMMO = new AmmoPossibleCombination();
    private final ArrayList<AmmoType> ammoTypes = new ArrayList<>();
    private final ArrayList<AmmoSize> ammoSizes = new ArrayList<>();

    private AmmoPossibleCombination(){
    }

    private void addAmmoTypes(AmmoType... ammoTypes){
        this.ammoTypes.addAll(Arrays.asList(ammoTypes));
    }

    private void addAmmoSizes(AmmoSize... ammoSizes){
        this.ammoSizes.addAll(Arrays.asList(ammoSizes));
    }

    public ArrayList<AmmoSize> getAmmoSizes() {
        return ammoSizes;
    }

    public ArrayList<AmmoType> getAmmoTypes() {
        return ammoTypes;
    }

    static {
        RIFLE_AMMO.addAmmoSizes(AmmoSize.SIZE_9MM, AmmoSize.SIZE_127, AmmoSize.SIZE_556, AmmoSize.SIZE_762);
        RIFLE_AMMO.addAmmoTypes(AmmoType.ANTI_GRAVITY, AmmoType.EXPLOSIVE, AmmoType.INCENDIARY, AmmoType.NORMAL,
                AmmoType.NUKE, AmmoType.SILVER, AmmoType.TUNGSTEN);

        SHOTGUN_AMMO.addAmmoTypes(AmmoType.SHORT_GUN_NORMAL, AmmoType.SHORT_GUN_BLIGHT, AmmoType.SHORT_GUN_DESOLATOR);
        SHOTGUN_AMMO.addAmmoSizes(AmmoSize.SIZE_12_GA);
    }
}
