package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.AtomicBombRocketEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

@Deprecated
public class AtomicBombRocket extends FireworkRocketItem {
    public AtomicBombRocket() {
        super(new Item.Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (!world.isRemote) {
            ItemStack itemstack = context.getItem();
            Vector3d vector3d = context.getHitVec();
            Direction direction = context.getFace();
            AtomicBombRocketEntity atomicBombRocketEntity = new AtomicBombRocketEntity(world, context.getPlayer(), vector3d.x + (double)direction.getXOffset() * 0.15D, vector3d.y + (double)direction.getYOffset() * 0.15D, vector3d.z + (double)direction.getZOffset() * 0.15D, itemstack);
            world.addEntity(atomicBombRocketEntity);
            itemstack.shrink(1);
        }

        return ActionResultType.func_233537_a_(world.isRemote);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (playerIn.isElytraFlying()) {
            ItemStack itemstack = playerIn.getHeldItem(handIn);
            if (!worldIn.isRemote) {
                worldIn.addEntity(new AtomicBombRocketEntity(worldIn, itemstack, playerIn));
                if (!playerIn.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
            }

            return ActionResult.func_233538_a_(playerIn.getHeldItem(handIn), worldIn.isRemote());
        } else {
            return ActionResult.resultPass(playerIn.getHeldItem(handIn));
        }
    }
}
