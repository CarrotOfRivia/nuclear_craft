package com.song.nuclear_craft.items;

import com.song.nuclear_craft.NuclearCraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public abstract class RocketLauncherWithAmmo extends Item {
    protected int MAX_AMMO = 10;
    public RocketLauncherWithAmmo(Properties properties) {
        super(properties);
    }

    protected int getAmmoCount(ItemStack itemStack){
        CompoundNBT compoundnbt = itemStack.getOrCreateTag();
        if(! compoundnbt.contains("ammo")){
            compoundnbt.putInt("ammo", this.MAX_AMMO);
        }
        return compoundnbt.getInt("ammo");
    }

    protected ActionResult<ItemStack>  afterFire(World worldIn, ItemStack thisItemStack){
        CompoundNBT compoundnbt = thisItemStack.getOrCreateTag();
        if(! compoundnbt.contains("ammo")){
            compoundnbt.putInt("ammo", this.MAX_AMMO);
        }
        if(!worldIn.isRemote){
            int n_ammo = compoundnbt.getInt("ammo");
            n_ammo --;
            compoundnbt.putInt("ammo", n_ammo);
        }
        if (compoundnbt.getInt("ammo") <= 0){
            return ActionResult.func_233538_a_(new ItemStack(ItemList.ROCKET_LAUNCHER), worldIn.isRemote());
        }
        else {
            return ActionResult.func_233538_a_(thisItemStack, worldIn.isRemote());
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        int n_ammo = getAmmoCount(stack);
        tooltip.add(new TranslationTextComponent(String.format("item.%s.rocket_launcher.ammo_left", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY));
        tooltip.add(new StringTextComponent(""+n_ammo).mergeStyle(TextFormatting.GOLD));
    }
}
