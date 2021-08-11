//package com.song.nuclear_craft.jei;
//
//import com.song.nuclear_craft.NuclearCraft;
//import com.song.nuclear_craft.items.ItemList;
//import com.song.nuclear_craft.misc.ConfigCommon;
//import com.song.nuclear_craft.misc.Util;
//import mezz.jei.api.IModPlugin;
//import mezz.jei.api.JeiPlugin;
//import mezz.jei.api.registration.IGuiHandlerRegistration;
//import mezz.jei.api.registration.IRecipeCatalystRegistration;
//import mezz.jei.api.registration.IRecipeCategoryRegistration;
//import mezz.jei.api.registration.IRecipeRegistration;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.resources.ResourceLocation;
//
//@JeiPlugin
//public class JEIPlugin implements IModPlugin {
//    public JEIPlugin(){
//
//    }
//
//    @Override
//    public ResourceLocation getPluginUid() {
//        return new ResourceLocation(NuclearCraft.MODID, "jei_plugin");
//    }
//
//    @Override
//    public void registerCategories(IRecipeCategoryRegistration registration) {
//        registration.addRecipeCategories(new NCTradingCategory(registration.getJeiHelpers().getGuiHelper(), Items.EMERALD,
//                net.minecraft.Util.makeDescriptionId("title", Util.getResource("trading"))));
//
//    }
//
//    @Override
//    public void registerRecipes(IRecipeRegistration registration) {
//        registration.addRecipes(ConfigCommon.NCTradings, NCTradingCategory.UID);
//    }
//
//    @Override
//    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
//        registration.addRecipeCatalyst(new ItemStack(ItemList.STATUE_OF_LIBERTY.get()), NCTradingCategory.UID);
//        registration.addRecipeCatalyst(new ItemStack(ItemList.STATUE_OF_EXPLOSIVE.get()), NCTradingCategory.UID);
//        registration.addRecipeCatalyst(new ItemStack(ItemList.STATUE_OF_RIFLE_AMMO.get()), NCTradingCategory.UID);
//        registration.addRecipeCatalyst(new ItemStack(ItemList.STATUE_OF_ROCKET.get()), NCTradingCategory.UID);
//        registration.addRecipeCatalyst(new ItemStack(ItemList.STATUE_OF_SHOTGUN_AMMO.get()), NCTradingCategory.UID);
//    }
//
//    @Override
//    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
//
//    }
//}
