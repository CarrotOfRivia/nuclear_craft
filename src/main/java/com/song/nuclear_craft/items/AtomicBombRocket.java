package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.rocket_entities.AtomicBombRocketEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

@Deprecated
public class AtomicBombRocket extends FireworkRocketItem {
    public AtomicBombRocket() {
        super(new Item.Properties().stacksTo(1).tab(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (!world.isClientSide) {
            ItemStack itemstack = context.getItemInHand();
            Vec3 vector3d = context.getClickLocation();
            Direction direction = context.getClickedFace();
            AtomicBombRocketEntity atomicBombRocketEntity = new AtomicBombRocketEntity(world, context.getPlayer(), vector3d.x + (double)direction.getStepX() * 0.15D, vector3d.y + (double)direction.getStepY() * 0.15D, vector3d.z + (double)direction.getStepZ() * 0.15D, itemstack);
            world.addFreshEntity(atomicBombRocketEntity);
            itemstack.shrink(1);
        }

        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (playerIn.isFallFlying()) {
            ItemStack itemstack = playerIn.getItemInHand(handIn);
            if (!worldIn.isClientSide) {
                worldIn.addFreshEntity(new AtomicBombRocketEntity(worldIn, itemstack, playerIn));
                if (!playerIn.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            }

            return InteractionResultHolder.sidedSuccess(playerIn.getItemInHand(handIn), worldIn.isClientSide());
        } else {
            return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
        }
    }
}
