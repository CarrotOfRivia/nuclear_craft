package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.rocket_entities.AtomicBombRocketEntity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RocketLauncherAtomicBomb extends RocketLauncherWithAmmo {
    private static final int MAX_AMMO=1;
    public RocketLauncherAtomicBomb() {
        super(new Item.Properties().stacksTo(1).tab(NuclearCraft.ITEM_GROUP));
        this.coolDown = 5;
    }

    @Override
    protected int getMAX_AMMO() {
        return MAX_AMMO;
    }

    @Override
    public Item getBoundedAmmo() {
        return ItemList.ATOMIC_BOMB_ROCKET.get();
    }

    @Override
    protected FireworkRocketEntity getEntity(Level worldIn, ItemStack toBeFired, Entity playerIn, double x, double y, double z, boolean p_i231582_10_) {
        return new AtomicBombRocketEntity(worldIn, toBeFired, playerIn, x, y, z, p_i231582_10_);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent(String.format("tooltip.%s.atomic_bomb_rocket.line0", NuclearCraft.MODID)).withStyle(ChatFormatting.GOLD));
    }
}
