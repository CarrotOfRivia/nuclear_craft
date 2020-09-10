package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.misc.SoundEventList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvent;

public class DesertEagle extends AbstractGunItem{
    private static final int loadTime = 40;

    public DesertEagle() {
        super(new Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public Item getAmmo() {
        return ItemList.AMMO_9MM;
//        return Items.EGG;
    }

    @Override
    public int getCoolDown() {
        return 10;
    }

    @Override
    public void addAmmo(ItemStack offhand, ItemStack mainHand, int slot, PlayerEntity entity) {
        if(offhand.getItem() instanceof AbstractAmmo){
            AbstractAmmo ammo = (AbstractAmmo) offhand.getItem();
            if ("9mm".equals(ammo.getSize())){
                if ((hasAmmo(mainHand) && (ammo.getType()).equals(getAmmoType(mainHand)))||(!hasAmmo(mainHand))){
                    int n_load = Math.min(offhand.getCount(), this.maxAmmo() - getAmmoCount(mainHand));
                    offhand.shrink(n_load);
                    addAmmoNBT(mainHand, n_load, ammo.getType());
                    entity.getCooldownTracker().setCooldown(mainHand.getItem(), loadTime);
                }
            }
        }
    }

    @Override
    public int maxAmmo() {
        return 7;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEventList.DESERT_EAGLE;
    }

    @Override
    public float getVelocity() {
        return 7f;
    }
}
