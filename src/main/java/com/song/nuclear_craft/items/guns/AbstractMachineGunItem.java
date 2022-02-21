package com.song.nuclear_craft.items.guns;

import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.items.AbstractAmmo;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import com.song.nuclear_craft.network.SoundPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;

public abstract class AbstractMachineGunItem extends AbstractGunItem{
    // you can shoot multiple bullets per tick
    public AbstractMachineGunItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level worldIn, Player playerIn, @Nonnull InteractionHand handIn) {
        ItemStack heldItemStack = playerIn.getItemInHand(handIn);
        if (heldItemStack.getItem() instanceof AbstractGunItem){
            AbstractGunItem gunItem = (AbstractGunItem) heldItemStack.getItem();
            if((!worldIn.isClientSide)&&(hasAmmo(heldItemStack))){
                AmmoType ammoType = getAmmoType(heldItemStack);
                assert ammoType != null;
                AmmoSize ammoSize = compatibleSize();
                if(gunItem.getCoolDown()>0){
                    playerIn.getCooldowns().addCooldown(heldItemStack.getItem(), gunItem.getCoolDown());
                }
                AbstractAmmo ammoItem = gunItem.getAmmoItem(ammoType, ammoSize);
                ItemStack toBeFired = new ItemStack(ammoItem);
                Vec3 lookVec = playerIn.getLookAngle();
                BlockPos playerPos = playerIn.blockPosition();
                for(int i = 0; i< getNumShoots(); i++){
                    AbstractAmmoEntity entity = getAmmoEntity(gunItem, playerIn, lookVec, toBeFired, worldIn, ammoItem, ammoType, ammoSize);
                    entity.shoot(lookVec.x, lookVec.y, lookVec.z, ammoItem.getBaseSpeed()*getSpeedModifier()*(1+0.01f*random.nextFloat()), getInaccuracy(worldIn, playerIn));
                    // one tick to avoid shooting yourself
                    entity.tick();
                    worldIn.addFreshEntity(entity);

                    shrinkAmmoNBT(heldItemStack);
                }
                NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(
                        playerPos.getX(), playerPos.getY(), playerPos.getZ(), getGunSoundDist(), playerIn.level.dimension())),
                        new SoundPacket(playerPos, getShootActionString()));
                return InteractionResultHolder.sidedSuccess(heldItemStack, worldIn.isClientSide());
            }
            else if(! worldIn.isClientSide){
                BlockPos pos = playerIn.blockPosition();
                NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), 4, playerIn.level.dimension())),
                        new SoundPacket(pos, "no_ammo"));
            }
        }
        return super.use(worldIn, playerIn, handIn);
    }

    protected abstract int getNumShoots();
}
