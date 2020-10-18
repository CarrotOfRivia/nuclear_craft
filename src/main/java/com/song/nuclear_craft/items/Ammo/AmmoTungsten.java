//package com.song.nuclear_craft.items.Ammo;
//
//import com.song.nuclear_craft.NuclearCraft;
//import com.song.nuclear_craft.items.AbstractAmmo;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.util.text.TranslationTextComponent;
//import net.minecraft.world.World;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.util.List;
//
//public class AmmoTungsten extends AbstractAmmo {
//
//    public AmmoTungsten() {
//        super(new Properties().group(NuclearCraft.ITEM_GROUP));
//    }
//
//    public AmmoTungsten(String size) {
//        super(new Properties().group(NuclearCraft.ITEM_GROUP), size);
//    }
//
//    @Nonnull
//    @Override
//    public AmmoType getType() {
//        return AmmoType.TUNGSTEN;
//    }
//
//    @Override
//    public double getBaseDamage() {
//        return 50;
//    }
//
//    @Override
//    public float getBaseSpeed() {
//        return 10;
//    }
//
//    @Override
//    public double getGravity() {
//        return 0.03;
//    }
//
//    @Override
//    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
//        super.addInformation(stack, worldIn, tooltip, flagIn);
//        tooltip.add(new TranslationTextComponent("item."+NuclearCraft.MODID+".ammo.penetrate_stone").mergeStyle(TextFormatting.RED));
//    }
//}
