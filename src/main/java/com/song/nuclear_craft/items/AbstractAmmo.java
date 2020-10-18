package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import com.song.nuclear_craft.misc.Config;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class AbstractAmmo extends Item {
    private final AmmoSize size;
    private final AmmoType type;

    public AbstractAmmo(Properties properties, AmmoSize size, AmmoType type){
        super(properties);
        this.size = size;
        this.type = type;
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
        return Config.DAMAGE_MAP.get(size).get(type).get();
    }

    public float getBaseSpeed() {
        return Config.SPEED_MAP.get(size).get(type).get().floatValue();
    }

    public double getGravity() {
        return Config.GRAVITY_MAP.get(size).get(type).get();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(String.format("tooltip.%s.ammo.desc1", NuclearCraft.MODID)));
        tooltip.add(new TranslationTextComponent(String.format("tooltip.%s.ammo.desc2", NuclearCraft.MODID)));
        tooltip.add(new TranslationTextComponent(String.format("tooltip.%s.ammo.base_damage", NuclearCraft.MODID)).append(new StringTextComponent(String.format(" %.2f", getBaseDamage()))));
        tooltip.add(new TranslationTextComponent(String.format("tooltip.%s.ammo.base_speed", NuclearCraft.MODID)).append(new StringTextComponent(String.format(" %.2f m/s", 20*getBaseSpeed()))));
        tooltip.add(new TranslationTextComponent(String.format("tooltip.%s.ammo.gravity", NuclearCraft.MODID)).append(new StringTextComponent(String.format(" %.2f m/s^2", 400*getGravity()))));
    }
}
