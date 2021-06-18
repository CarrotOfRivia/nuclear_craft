package com.song.nuclear_craft.items.guns;

import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.items.AbstractAmmo;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import com.song.nuclear_craft.network.SoundPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;

public abstract class AbstractMachineGunItem extends AbstractGunItem{
    // you can shoot multiple bullets per tick
    public AbstractMachineGunItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack heldItemStack = playerIn.getHeldItem(handIn);
        if (heldItemStack.getItem() instanceof AbstractGunItem){
            AbstractGunItem gunItem = (AbstractGunItem) heldItemStack.getItem();
            if((!worldIn.isRemote)&&(hasAmmo(heldItemStack))){
                AmmoType ammoType = getAmmoType(heldItemStack);
                assert ammoType != null;
                AmmoSize ammoSize = compatibleSize();
                if(gunItem.getCoolDown()>0){
                    playerIn.getCooldownTracker().setCooldown(heldItemStack.getItem(), gunItem.getCoolDown());
                }
                AbstractAmmo ammoItem = gunItem.getAmmoItem(ammoType, ammoSize);
                ItemStack toBeFired = new ItemStack(ammoItem);
                Vector3d lookVec = playerIn.getLookVec();
                BlockPos playerPos = playerIn.getPosition();
                for(int i = 0; i< getNumShoots(); i++){
                    AbstractAmmoEntity entity = getAmmoEntity(gunItem, playerIn, lookVec, toBeFired, worldIn, ammoItem, ammoType, ammoSize);
                    entity.shoot(lookVec.x, lookVec.y, lookVec.z, ammoItem.getBaseSpeed()*getSpeedModifier()*(1+0.01f*random.nextFloat()), getInaccuracy(worldIn, playerIn));
                    // one tick to avoid shooting yourself
                    entity.tick();
                    worldIn.addEntity(entity);

                    shrinkAmmoNBT(heldItemStack);
                }
                NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(
                        playerPos.getX(), playerPos.getY(), playerPos.getZ(), getGunSoundDist(), playerIn.world.getDimensionKey())),
                        new SoundPacket(playerPos, getShootActionString()));
                return ActionResult.func_233538_a_(heldItemStack, worldIn.isRemote());
            }
            else if(! worldIn.isRemote){
                BlockPos pos = playerIn.getPosition();
                NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), 4, playerIn.world.getDimensionKey())),
                        new SoundPacket(pos, "no_ammo"));
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    protected abstract int getNumShoots();
}
