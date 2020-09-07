package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.AtomicBombRocketEntity;
import com.song.nuclear_craft.entities.IncendiaryRocketEntity;
import com.song.nuclear_craft.misc.Config;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class RocketLauncherIncendiary extends RocketLauncherWithAmmo{

    public RocketLauncherIncendiary() {
        super(new Item.Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
        this.MAX_AMMO = Config.INCENDIARY_MAX_AMMO.get();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack toBeFired = new ItemStack(ItemList.INCENDIARY_ROCKET);
        toBeFired.getOrCreateChildTag("Fireworks").putByte("Flight", (byte) 127);

        ItemStack thisItemStack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote){
            IncendiaryRocketEntity incendiaryRocketEntity = new IncendiaryRocketEntity(worldIn, toBeFired, playerIn, playerIn.getPosX(), playerIn.getPosYEye() - (double)0.15F, playerIn.getPosZ(), true);
            Vector3d vec3d = playerIn.getLookVec();
            incendiaryRocketEntity.shoot(vec3d.x, vec3d.y, vec3d.z, 5f, 0);
            worldIn.addEntity(incendiaryRocketEntity);
            worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.f);
//            System.out.println("entity added");
        }
        if (playerIn.isCreative()){
            return ActionResult.func_233538_a_(new ItemStack(this), worldIn.isRemote());
        }
        else {
            return afterFire(worldIn, thisItemStack);
        }
    }

}