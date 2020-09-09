package com.song.nuclear_craft.items;

import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.entities.Ammo9MMEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class AbstractGunItem extends Item {
    protected int coolDown;
    public AbstractGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack heldItemStack = playerIn.getHeldItem(handIn);
        if(coolDown>0){
            playerIn.getCooldownTracker().setCooldown(heldItemStack.getItem(), coolDown);
        }

        if(!worldIn.isRemote && heldItemStack.getItem() instanceof AbstractGunItem){
            ItemStack toBeFired = new ItemStack(((AbstractGunItem) heldItemStack.getItem()).getAmmo());
            toBeFired.getOrCreateChildTag("Fireworks").putByte("Flight", (byte) 127);
            AbstractAmmoEntity entity = new AbstractAmmoEntity(playerIn.getPosX(), playerIn.getPosYEye() - (double)0.15F, playerIn.getPosZ(), worldIn, toBeFired);
            entity.setSilent(true);
            Vector3d vec3d = playerIn.getLookVec();
            entity.shoot(vec3d.x, vec3d.y, vec3d.z, 10f, 0);
            worldIn.addEntity(entity);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public abstract Item getAmmo();
}
