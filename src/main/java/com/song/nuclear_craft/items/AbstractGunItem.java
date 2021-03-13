package com.song.nuclear_craft.items;

import com.song.nuclear_craft.entities.AmmoEntities.IAmmoEntityFactory;
import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.items.Ammo.AmmoPossibleCombination;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import com.song.nuclear_craft.events.ClientEventForgeSubscriber;
import com.song.nuclear_craft.network.GunLoadingPacket;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import com.song.nuclear_craft.network.SoundPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public abstract class AbstractGunItem extends Item {
    protected int coolDown;
    public AbstractGunItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack heldItemStack = playerIn.getHeldItem(handIn);
        if (heldItemStack.getItem() instanceof AbstractGunItem){
            AbstractGunItem gunItem = (AbstractGunItem) heldItemStack.getItem();
            if((!worldIn.isRemote)&&(hasAmmo(heldItemStack))){
                AmmoType ammoType = getAmmoType(heldItemStack);
                assert ammoType != null;
                AmmoSize ammoSize = compatibleSize();
                if(gunItem.getCoolDown()>0){
                    playerIn.getCooldownTracker().setCooldown(heldItemStack.getItem(), gunItem.getCoolDown());
                }
                AbstractAmmo ammoItem = gunItem.getAmmoItem(ammoType, ammoSize);
                ItemStack toBeFired = new ItemStack(ammoItem);
                Vector3d lookVec = playerIn.getLookVec();

                for(int i = 0; i< getBirdShotCount(ammoType); i++){
                    AbstractAmmoEntity entity = getAmmoEntity(gunItem, playerIn, lookVec, toBeFired, worldIn, ammoItem, ammoType, ammoSize);
                    entity.shoot(lookVec.x, lookVec.y, lookVec.z, ammoItem.getBaseSpeed()*getSpeedModifier(), getInaccuracy(worldIn, playerIn));
                    worldIn.addEntity(entity);
                }

                BlockPos pos = playerIn.getPosition();
                NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), getGunSoundDist(), playerIn.world.getDimensionKey())),
                        new SoundPacket(pos, getShootActionString()));

                shrinkAmmoNBT(heldItemStack);
                return ActionResult.func_233538_a_(heldItemStack, worldIn.isRemote());
            }
            else if(! worldIn.isRemote){
                BlockPos pos = playerIn.getPosition();
                NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), 4, playerIn.world.getDimensionKey())),
                        new SoundPacket(pos, "no_ammo"));
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private AbstractAmmoEntity getAmmoEntity(AbstractGunItem gunItem, PlayerEntity playerIn, Vector3d lookVec, ItemStack toBeFired, World worldIn, AbstractAmmo ammoItem, AmmoType ammoType, AmmoSize ammoSize){
        // We add 0.1m to avoid player from shooting themselves when they are running in their shooting direction
        AbstractAmmoEntity entity = gunItem.getAmmoEntity(playerIn.getPosX()+lookVec.x*0.1, playerIn.getPosYEye() - (double)0.15F+lookVec.y*0.1, playerIn.getPosZ()+lookVec.z*0.1, worldIn, toBeFired, playerIn, ammoType, ammoSize);
        entity.setSilent(true);
        // handle it myself
        entity.setNoGravity(true);
        entity.setGravity(ammoItem.getGravity());
        entity.setBaseDamage(getDamageModifier()*entity.getBaseDamage());
        return entity;
    }

    protected float getInaccuracy(World world, PlayerEntity playerEntity){
        return 0;
    }

    protected int getBirdShotCount(AmmoType ammoType){
        // Number of ammo entities per shoot, used for short guns
        return 1;
    }

    protected double getGunSoundDist(){
        return 20;
    }

    public static boolean hasAmmo(ItemStack itemStack){
        CompoundNBT nbt = itemStack.getChildTag("ammo");
        if (nbt == null){
            return false;
        }
        else {
            int nAmmo = nbt.getInt("count");
            if (nAmmo > 0){
                return true;
            }
            else {
                clearAmmoNBT(itemStack);
                return false;
            }
        }
    }

    public static void clearAmmoNBT(ItemStack itemStack){
        itemStack.removeChildTag("ammo");
    }

    public static int getAmmoCount(ItemStack itemStack){
        CompoundNBT nbt = itemStack.getChildTag("ammo");
        if (nbt == null){
            return 0;
        }else {
            int nAmmo = nbt.getInt("count");
            if (nAmmo > 0){
                return nAmmo;
            }
            else {
                clearAmmoNBT(itemStack);
                return 0;
            }
        }
    }

    public static void addAmmoNBT(ItemStack itemStack, int n, String type){
        CompoundNBT nbt = itemStack.getOrCreateChildTag("ammo");
        nbt.putInt("count", nbt.getInt("count")+n);
        nbt.putString("type", type);
    }

    public static void shrinkAmmoNBT(ItemStack itemStack){
        int nAmmo = getAmmoCount(itemStack);
        int nAmmoAfter = nAmmo - 1;
        if (nAmmoAfter <= 0){
            clearAmmoNBT(itemStack);
        }
        else {
            CompoundNBT nbt = itemStack.getOrCreateChildTag("ammo");
            nbt.putInt("count", nAmmoAfter);
        }
    }

    public static AmmoType getAmmoType(ItemStack itemStack){
        CompoundNBT nbt = itemStack.getChildTag("ammo");
        if (nbt == null){
            return null;
        }else{
            return AmmoType.valueOf(nbt.getString("type"));
        }
    }

    public static String getAmmoShowType(ItemStack itemStack){
        AmmoType type = getAmmoType(itemStack);
        return type == null ? "none" : type.getDescription();
    }

    public AbstractAmmo getAmmoItem(AmmoType ammoType, AmmoSize ammoSize) {
        // Get item to be rendered when shoot
        if(AmmoPossibleCombination.SHOTGUN_AMMO.getAmmoTypes().contains(ammoType)){
            return AmmoType.getBirdShotFromType(ammoType);
        }
        return ItemList.AMMO_REGISTRIES_TYPE.get(ammoSize).get(ammoType).get();

    }


    public abstract int getCoolDown();

    public AbstractAmmoEntity getAmmoEntity(double x, double y, double z, World world, ItemStack toBeFired, PlayerEntity shooter, AmmoType ammoType, AmmoSize ammoSize) {
        return IAmmoEntityFactory.getAmmoEntityFactory(ammoType).create(x, y, z, world, toBeFired, shooter);
    }

    protected int getAmmoCountPerLoad(){
        // Maximum ammo count per load, used for short guns if you want to load one bullet at a time
        // -1 means as much as possible
        return -1;
    }


    public void addAmmo(ItemStack offhand, ItemStack mainHand, int slot, PlayerEntity entity) {
        ItemStack ammo = findAmmo(offhand, mainHand, slot, entity);
        if(ammo != null){
            AbstractAmmo ammoItem = (AbstractAmmo) ammo.getItem();
            if ((hasAmmo(mainHand) && (ammoItem.getType())==(getAmmoType(mainHand)))||(!hasAmmo(mainHand))){
                int n_load = 0;
                if(getAmmoCountPerLoad() > 0){
                    n_load = Math.min(getAmmoCountPerLoad(), this.maxAmmo() - getAmmoCount(mainHand));
                }
                else{
                    n_load = Math.min(ammo.getCount(), this.maxAmmo() - getAmmoCount(mainHand));
                }
                if (n_load > 0){
                    ammo.shrink(n_load);
                    addAmmoNBT(mainHand, n_load, ammoItem.getType().name());
                    entity.getCooldownTracker().setCooldown(mainHand.getItem(), getLoadTime());
                    if(!entity.world.isRemote){
                        BlockPos pos = entity.getPosition();
                        NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), 10, entity.world.getDimensionKey())),
                                new SoundPacket(pos, getReloadSound()));
                    }
                }
            }
        }
    }

    public ItemStack findAmmo(ItemStack offhand, ItemStack mainHand, int slot, PlayerEntity entity){
        // Find ammo similar to arrow finding algorithm
        if(offhand.getItem() instanceof AbstractAmmo && ((AbstractAmmo) offhand.getItem()).getSize()==compatibleSize()){
            return offhand;
        }
        for(ItemStack stack: entity.inventory.mainInventory){
            if(stack.getItem() instanceof AbstractAmmo && ((AbstractAmmo)stack.getItem()).getSize()==compatibleSize()){
                return stack;
            }
        }
        return null;
    }

    public abstract String getShootActionString();

    public abstract int maxAmmo();

    public abstract int getLoadTime();

    @Nonnull
    public abstract AmmoSize compatibleSize();

    public abstract float getSpeedModifier();

    public abstract double getDamageModifier();

    public abstract String getReloadSound();

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        // loading ammo
        // Client side
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if(isSelected&&worldIn.isRemote){
            if(ClientEventForgeSubscriber.gunReload.isPressed() && (entityIn instanceof PlayerEntity)){
                NuclearCraftPacketHandler.KEY_BIND.sendToServer(new GunLoadingPacket(itemSlot));
                }
            Minecraft.getInstance().ingameGUI.setOverlayMessage(new TranslationTextComponent(String.format("item.%s.guns.ammo_left", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY).
                    append(new StringTextComponent(" "+getAmmoCount(stack)).mergeStyle(TextFormatting.GOLD)), false);
        }

    }

    public static void tryLoadAmmo(ServerWorld world, PlayerEntity entity, int itemSlot){
        ItemStack itemStackMain = entity.getHeldItemMainhand();
        if ((itemStackMain.getItem() instanceof AbstractGunItem)&&!(entity.getCooldownTracker().hasCooldown(itemStackMain.getItem()))){
            ItemStack itemStackOff = entity.getHeldItemOffhand();
            ((AbstractGunItem) itemStackMain.getItem()).addAmmo(itemStackOff, itemStackMain, itemSlot, entity);
        }
        else if (itemStackMain.getItem() instanceof RocketLauncherWithAmmo){
            ItemStack itemStackOff = entity.getHeldItemOffhand();
            ((RocketLauncherWithAmmo) itemStackMain.getItem()).addAmmo(itemStackOff, itemStackMain, itemSlot, entity);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(hasAmmo(stack)){
            int n_ammo = getAmmoCount(stack);
            tooltip.add(new TranslationTextComponent(String.format("item.%s.guns.ammo_left", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY).
                    append(new StringTextComponent(" "+n_ammo).mergeStyle(TextFormatting.GOLD)));
            tooltip.add(new TranslationTextComponent(String.format("item.%s.guns.ammo_type", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY).
                    append(new StringTextComponent(Objects.requireNonNull(getAmmoType(stack)).getDescription()).mergeStyle(TextFormatting.GOLD)));
        }

        tooltip.add(new TranslationTextComponent(String.format("item.%s.guns.compatible_ammo_size", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY)
                .append(new StringTextComponent(" "+compatibleSize())));
        tooltip.add(new TranslationTextComponent(String.format("item.%s.guns.damage_modifier", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY)
                .append(new StringTextComponent(String.format(" %.0f", 100*getDamageModifier())+"%")));
        tooltip.add(new TranslationTextComponent(String.format("item.%s.guns.speed_modifier", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY)
                .append(new StringTextComponent(String.format(" %.0f", 100*getSpeedModifier())+"%")));

        if(canUseScope()){
            // add scope tutorial
            tooltip.add(new TranslationTextComponent(String.format("item.%s.guns.use_scope", NuclearCraft.MODID)).mergeStyle(TextFormatting.GRAY).append(new StringTextComponent(" Z")));
        }
    }

    public boolean canUseScope(){
        return false;
    }
}
