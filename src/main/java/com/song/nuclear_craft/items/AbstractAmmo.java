package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoPossibleCombination;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import com.song.nuclear_craft.misc.ConfigCommon;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import net.minecraft.world.item.Item.Properties;

public class AbstractAmmo extends Item {
    private final AmmoSize size;
    private final AmmoType type;
    private final int extraTooltipLine;

    public AbstractAmmo(Properties properties, AmmoSize size, AmmoType type){
        this(properties, size, type, 0);
    }

    public AbstractAmmo(Properties properties, AmmoSize size, AmmoType type, int extraTooltipLine){
        super(properties);
        this.size = size;
        this.type = type;
        this.extraTooltipLine=extraTooltipLine;
    }

    @Nonnull
    public AmmoSize getSize(){
        return size;
    }

    @Nonnull
    public AmmoType getType(){
        return type;
    }

    public double getBaseDamage(){
        return ConfigCommon.DAMAGE_MAP.get(size).get(type).get();
    }

    public float getBaseSpeed() {
        // speed will have a 1% fluctuation
        return ConfigCommon.SPEED_MAP.get(size).get(type).get().floatValue();
    }

    public double getGravity() {
        return ConfigCommon.GRAVITY_MAP.get(size).get(type).get();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        for(int l=0; l<this.extraTooltipLine; l++){
            tooltip.add(new TranslatableComponent(String.format("tooltip.%s.%s.line%d", NuclearCraft.MODID,
                    Objects.requireNonNull(this.getRegistryName()).getPath(), l)).withStyle(ChatFormatting.GRAY));
        }
        tooltip.add(new TranslatableComponent(String.format("tooltip.%s.ammo.desc1", NuclearCraft.MODID)).withStyle(ChatFormatting.GRAY));
        tooltip.add(new TranslatableComponent(String.format("tooltip.%s.ammo.desc2", NuclearCraft.MODID)).withStyle(ChatFormatting.GRAY));
        if(AmmoPossibleCombination.SHOTGUN_AMMO.getAmmoTypes().contains(getType())){
            // ShortGun ammo damage: dmg*count
            tooltip.add(new TranslatableComponent(String.format("tooltip.%s.ammo.base_damage", NuclearCraft.MODID))
                    .append(new TextComponent(String.format(" %.2f x %d", getBaseDamage(), ConfigCommon.BIRD_SHOT_COUNT_MAP.get(getType()).get()))).withStyle(ChatFormatting.GRAY));
        }
        else {
            tooltip.add(new TranslatableComponent(String.format("tooltip.%s.ammo.base_damage", NuclearCraft.MODID)).append(new TextComponent(String.format(" %.2f", getBaseDamage())).withStyle(ChatFormatting.GRAY)));
        }
        tooltip.add(new TranslatableComponent(String.format("tooltip.%s.ammo.base_speed", NuclearCraft.MODID)).append(new TextComponent(String.format(" %.2f m/s", 20*getBaseSpeed())).withStyle(ChatFormatting.GRAY)));
        tooltip.add(new TranslatableComponent(String.format("tooltip.%s.ammo.gravity", NuclearCraft.MODID)).append(new TextComponent(String.format(" %.2f m/s^2", 400*getGravity()))).withStyle(ChatFormatting.GRAY));
    }
}
