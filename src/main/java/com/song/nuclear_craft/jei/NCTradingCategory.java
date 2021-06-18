package com.song.nuclear_craft.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.song.nuclear_craft.misc.Util;
import com.song.nuclear_craft.villagers.NCTradingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ForgeI18n;

public class NCTradingCategory implements IRecipeCategory<NCTradingRecipe> {
    public static final ResourceLocation UID = Util.getResource("trading_category");
    protected static final ResourceLocation BACKGROUND_LOC = Util.getResource("textures/gui/jei/casting.png");
    public static final ResourceLocation RECIPE_GUI_VANILLA = new ResourceLocation("jei", "textures/gui/gui_vanilla.png");
    private final IDrawableStatic background;
    private final IDrawable icon;
    private final String title;

    protected NCTradingCategory(IGuiHelper guiHelper, Item icon, String translationKey) {
        this.background = guiHelper.drawableBuilder(RECIPE_GUI_VANILLA, 0, 168, 125, 18).addPadding(0, 20, 0, 0).build();
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(icon));
        this.title = ForgeI18n.getPattern(translationKey);
    }


    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends NCTradingRecipe> getRecipeClass() {
        return NCTradingRecipe.class;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(NCTradingRecipe NCTradingRecipe, IIngredients iIngredients) {
        iIngredients.setInputs(VanillaTypes.ITEM, NCTradingRecipe.getInputStacks());
        iIngredients.setOutput(VanillaTypes.ITEM, NCTradingRecipe.getOutputStack());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, NCTradingRecipe NCTradingRecipe, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStackGroup = iRecipeLayout.getItemStacks();
        guiItemStackGroup.init(0, true, 0, 0);
        guiItemStackGroup.init(1, true, 49, 0);
        guiItemStackGroup.init(2, false, 107, 0);
        guiItemStackGroup.set(iIngredients);
    }

    @Override
    public void draw(NCTradingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.fontRenderer.drawString(matrixStack, recipe.price1Min.get()+"-"+recipe.price1Max.get(), 0, 22, 0x5161f);
        if(recipe.getItem2() != null){
            minecraft.fontRenderer.drawString(matrixStack, recipe.price2Min.get()+"-"+recipe.price2Max.get(), 49, 22, 0x5161f);
        }
        minecraft.fontRenderer.drawString(matrixStack, recipe.outputMin.get()+"-"+recipe.outputMax.get(), 107, 22, 0x5161f);
    }
}
