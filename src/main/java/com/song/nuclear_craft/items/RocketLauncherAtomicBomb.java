package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.AtomicBombRocketEntity;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RocketLauncherAtomicBomb extends Item {
    public RocketLauncherAtomicBomb() {
        super(new Item.Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = new ItemStack(ItemList.ATOMIC_BOMB_ROCKET);
        itemstack.getOrCreateChildTag("Fireworks").putByte("Flight", (byte) 127);
        if (!worldIn.isRemote){
            AtomicBombRocketEntity atomicBombRocketEntity = new AtomicBombRocketEntity(worldIn, itemstack, playerIn, playerIn.getPosX(), playerIn.getPosYEye() - (double)0.15F, playerIn.getPosZ(), true);
            Vector3d vec3d = playerIn.getLookVec();
            atomicBombRocketEntity.shoot(vec3d.x, vec3d.y, vec3d.z, 5f, 0);
            worldIn.addEntity(atomicBombRocketEntity);
            worldIn.playSound((PlayerEntity)null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.f);
        }
        if (playerIn.isCreative()){
            return ActionResult.func_233538_a_(new ItemStack(this), worldIn.isRemote());
        }
        else {
            return ActionResult.func_233538_a_(new ItemStack(ItemList.ROCKET_LAUNCHER), worldIn.isRemote());
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent(String.format("item.%s.rocket_launcher_atomic_bomb.tooltip", NuclearCraft.MODID)).func_240699_a_(TextFormatting.GOLD));
    }
}
