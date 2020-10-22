package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.AtomicBombRocketEntity;
import com.song.nuclear_craft.entities.HighExplosiveRocketEntity;
import com.song.nuclear_craft.misc.SoundEventList;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class RocketLauncherAtomicBomb extends RocketLauncherWithAmmo {
    private static final int MAX_AMMO=1;
    public RocketLauncherAtomicBomb() {
        super(new Item.Properties().maxStackSize(1).group(NuclearCraft.ITEM_GROUP));
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
    protected FireworkRocketEntity getEntity(World worldIn, ItemStack toBeFired, Entity playerIn, double x, double y, double z, boolean p_i231582_10_) {
        return new AtomicBombRocketEntity(worldIn, toBeFired, playerIn, x, y, z, p_i231582_10_);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(String.format("item.%s.rocket_launcher_atomic_bomb.tooltip", NuclearCraft.MODID)).mergeStyle(TextFormatting.GOLD));
    }
}
