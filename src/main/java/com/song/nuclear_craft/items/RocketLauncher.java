package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class RocketLauncher extends RocketLauncherWithAmmo {

    public RocketLauncher() {
        super(new Item.Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent(String.format("item.%s.rocket_launcher.tooltip", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY));
    }

    @Override
    protected void addAmmo(ItemStack ammo, ItemStack rocket, int itemSlot, Entity entityIn) {
        String ammoRegistryName = Objects.requireNonNull(ammo.getItem().getRegistryName()).toString();
        ItemStack itemStack;
        if(ammoRegistryName.equals(ItemList.ATOMIC_BOMB_ROCKET.getRegistryName().toString())){
            itemStack = new ItemStack(ItemList.ROCKET_LAUNCHER_ATOMIC_BOMB);
        }else if(ammoRegistryName.equals(ItemList.INCENDIARY_ROCKET.getRegistryName().toString())){
            itemStack = new ItemStack(ItemList.ROCKET_LAUNCHER_INCENDIARY);
        }else if(ammoRegistryName.equals(ItemList.SMOKE_ROCKET.getRegistryName().toString())){
            itemStack = new ItemStack(ItemList.ROCKET_LAUNCHER_SMOKE);
        }else if(ammoRegistryName.equals(ItemList.HIGH_EXPLOSIVE_ROCKET.getRegistryName().toString())){
            itemStack = new ItemStack(ItemList.ROCKET_LAUNCHER_HIGH_EXPLOSIVE);
        }
        else {
            return;
        }
        clearAmmo(itemStack);
        entityIn.replaceItemInInventory(itemSlot, itemStack);
        ((RocketLauncherWithAmmo)itemStack.getItem()).addAmmo(ammo, itemStack, itemSlot, entityIn);
    }
}
