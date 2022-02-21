package com.song.nuclear_craft.items.guns;

import com.song.nuclear_craft.NuclearCraft;
import com.song.nuclear_craft.entities.AbstractAmmoEntity;
import com.song.nuclear_craft.entities.AmmoEntities.IAmmoEntityFactory;
import com.song.nuclear_craft.events.ClientEventForgeSubscriber;
import com.song.nuclear_craft.items.AbstractAmmo;
import com.song.nuclear_craft.items.Ammo.AmmoPossibleCombination;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import com.song.nuclear_craft.items.ItemList;
import com.song.nuclear_craft.items.RocketLauncherWithAmmo;
import com.song.nuclear_craft.network.GunLoadingPacket;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import com.song.nuclear_craft.network.SoundPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public abstract class AbstractGunItem extends Item {
    protected int coolDown;
    protected Random random = new Random();
    public AbstractGunItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level worldIn, Player playerIn, @Nonnull InteractionHand handIn) {
        ItemStack heldItemStack = playerIn.getItemInHand(handIn);
        if (heldItemStack.getItem() instanceof AbstractGunItem){
            AbstractGunItem gunItem = (AbstractGunItem) heldItemStack.getItem();
            if((!worldIn.isClientSide)&&(hasAmmo(heldItemStack))){
                AmmoType ammoType = getAmmoType(heldItemStack);
                assert ammoType != null;
                AmmoSize ammoSize = compatibleSize();
                if(gunItem.getCoolDown()>0){
                    playerIn.getCooldowns().addCooldown(heldItemStack.getItem(), gunItem.getCoolDown());
                }
                AbstractAmmo ammoItem = gunItem.getAmmoItem(ammoType, ammoSize);
                ItemStack toBeFired = new ItemStack(ammoItem);
                Vec3 lookVec = playerIn.getLookAngle();

                for(int i = 0; i< getBirdShotCount(ammoType); i++){
                    AbstractAmmoEntity entity = getAmmoEntity(gunItem, playerIn, lookVec, toBeFired, worldIn, ammoItem, ammoType, ammoSize);
                    entity.shoot(lookVec.x, lookVec.y, lookVec.z, ammoItem.getBaseSpeed()*getSpeedModifier()*(1+0.01f*random.nextFloat()), getInaccuracy(worldIn, playerIn));
                    // one tick to avoid shooting yourself
                    entity.tick();
                    worldIn.addFreshEntity(entity);
                }

                BlockPos playerPos = playerIn.blockPosition();
                NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(
                        playerPos.getX(), playerPos.getY(), playerPos.getZ(), getGunSoundDist(), playerIn.level.dimension())),
                        new SoundPacket(playerPos, getShootActionString()));

                shrinkAmmoNBT(heldItemStack);
                return InteractionResultHolder.sidedSuccess(heldItemStack, worldIn.isClientSide());
            }
            else if(! worldIn.isClientSide){
                BlockPos pos = playerIn.blockPosition();
                NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), 4, playerIn.level.dimension())),
                        new SoundPacket(pos, "no_ammo"));
            }
        }
        return super.use(worldIn, playerIn, handIn);
    }

    protected AbstractAmmoEntity getAmmoEntity(AbstractGunItem gunItem, Player playerIn, Vec3 lookVec, ItemStack toBeFired, Level worldIn, AbstractAmmo ammoItem, AmmoType ammoType, AmmoSize ammoSize){
        // We add 0.1m to avoid player from shooting themselves when they are running in their shooting direction
        AbstractAmmoEntity entity = gunItem.getAmmoEntity(playerIn.getX()+lookVec.x*0.1, playerIn.getEyeY() - (double)0.15F+lookVec.y*0.1, playerIn.getZ()+lookVec.z*0.1, worldIn, toBeFired, playerIn, ammoType, ammoSize);
        entity.setSilent(true);
        // handle it myself
        entity.setNoGravity(true);
        entity.setGravity(ammoItem.getGravity());
        entity.setBaseDamage(getDamageModifier()*entity.getBaseDamage());
        return entity;
    }

    protected float getInaccuracy(Level world, Player playerEntity){
        return 0;
    }

    protected int getBirdShotCount(AmmoType ammoType){
        // Number of ammo entities per shoot, only used for shotguns! If you want it shoots faster, see "AbstractMachineGunItem"
        return 1;
    }

    protected double getGunSoundDist(){
        return 20;
    }

    public static boolean hasAmmo(ItemStack itemStack){
        CompoundTag nbt = itemStack.getTagElement("ammo");
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
        itemStack.removeTagKey("ammo");
    }

    public static int getAmmoCount(ItemStack itemStack){
        CompoundTag nbt = itemStack.getTagElement("ammo");
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
        CompoundTag nbt = itemStack.getOrCreateTagElement("ammo");
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
            CompoundTag nbt = itemStack.getOrCreateTagElement("ammo");
            nbt.putInt("count", nAmmoAfter);
        }
    }

    public static AmmoType getAmmoType(ItemStack itemStack){
        CompoundTag nbt = itemStack.getTagElement("ammo");
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

    public AbstractAmmoEntity getAmmoEntity(double x, double y, double z, Level world, ItemStack toBeFired, Player shooter, AmmoType ammoType, AmmoSize ammoSize) {
        return IAmmoEntityFactory.getAmmoEntityFactory(ammoType).create(x, y, z, world, toBeFired, shooter);
    }

    protected int getAmmoCountPerLoad(){
        // Maximum ammo count per load, used for short guns if you want to load one bullet at a time
        // -1 means as much as possible
        return -1;
    }


    public void addAmmo(ItemStack offhand, ItemStack mainHand, int slot, Player entity) {
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
                    entity.getCooldowns().addCooldown(mainHand.getItem(), getLoadTime());
                    if(!entity.level.isClientSide){
                        BlockPos pos = entity.blockPosition();
                        NuclearCraftPacketHandler.C4_SETTING_CHANNEL.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), 10, entity.level.dimension())),
                                new SoundPacket(pos, getReloadSound()));
                    }
                }
            }
        }
    }

    public ItemStack findAmmo(ItemStack offhand, ItemStack mainHand, int slot, Player entity){
        // Find ammo similar to arrow finding algorithm
        if(offhand.getItem() instanceof AbstractAmmo && ((AbstractAmmo) offhand.getItem()).getSize()==compatibleSize()){
            return offhand;
        }
        for(ItemStack stack: entity.getInventory().items){
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
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        // loading ammo
        // Client side
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        if(isSelected&&worldIn.isClientSide){
            if(ClientEventForgeSubscriber.gunReload.consumeClick() && (entityIn instanceof Player)){
                NuclearCraftPacketHandler.KEY_BIND.sendToServer(new GunLoadingPacket(itemSlot));
                }
            Minecraft.getInstance().gui.setOverlayMessage(new TranslatableComponent(String.format("item.%s.guns.ammo_left", NuclearCraft.MODID)).withStyle(ChatFormatting.GRAY).
                    append(new TextComponent(" "+getAmmoCount(stack)).withStyle(ChatFormatting.GOLD)), false);
        }

    }

    public static void tryLoadAmmo(ServerLevel world, Player entity, int itemSlot){
        ItemStack itemStackMain = entity.getMainHandItem();
        if ((itemStackMain.getItem() instanceof AbstractGunItem)&&!(entity.getCooldowns().isOnCooldown(itemStackMain.getItem()))){
            ItemStack itemStackOff = entity.getOffhandItem();
            ((AbstractGunItem) itemStackMain.getItem()).addAmmo(itemStackOff, itemStackMain, itemSlot, entity);
        }
        else if (itemStackMain.getItem() instanceof RocketLauncherWithAmmo){
            ItemStack itemStackOff = entity.getOffhandItem();
            ((RocketLauncherWithAmmo) itemStackMain.getItem()).addAmmo(itemStackOff, itemStackMain, itemSlot, entity);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if(hasAmmo(stack)){
            int n_ammo = getAmmoCount(stack);
            tooltip.add(new TranslatableComponent(String.format("item.%s.guns.ammo_left", NuclearCraft.MODID)).withStyle(ChatFormatting.GRAY).
                    append(new TextComponent(" "+n_ammo).withStyle(ChatFormatting.GOLD)));
            tooltip.add(new TranslatableComponent(String.format("item.%s.guns.ammo_type", NuclearCraft.MODID)).withStyle(ChatFormatting.GRAY).
                    append(new TextComponent(Objects.requireNonNull(getAmmoType(stack)).getDescription()).withStyle(ChatFormatting.GOLD)));
        }

        tooltip.add(new TranslatableComponent(String.format("item.%s.guns.compatible_ammo_size", NuclearCraft.MODID)).withStyle(ChatFormatting.GRAY)
                .append(new TextComponent(" "+compatibleSize())));
        tooltip.add(new TranslatableComponent(String.format("item.%s.guns.damage_modifier", NuclearCraft.MODID)).withStyle(ChatFormatting.GRAY)
                .append(new TextComponent(String.format(" %.0f", 100*getDamageModifier())+"%")));
        tooltip.add(new TranslatableComponent(String.format("item.%s.guns.speed_modifier", NuclearCraft.MODID)).withStyle(ChatFormatting.GRAY)
                .append(new TextComponent(String.format(" %.0f", 100*getSpeedModifier())+"%")));

        if(canUseScope()){
            // add scope tutorial
            tooltip.add(new TranslatableComponent(String.format("item.%s.guns.use_scope", NuclearCraft.MODID)).withStyle(ChatFormatting.GRAY).append(new TextComponent(" Z")));
        }
    }

    public boolean canUseScope(){
        return false;
    }
}
