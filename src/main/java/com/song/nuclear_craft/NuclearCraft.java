package com.song.nuclear_craft;

import com.song.nuclear_craft.blocks.BlockList;
import com.song.nuclear_craft.blocks.container.ContainerTypeList;
import com.song.nuclear_craft.blocks.tileentity.TileEntityRegister;
import com.song.nuclear_craft.client.ClientSetup;
import com.song.nuclear_craft.effects.EffectRegister;
import com.song.nuclear_craft.entities.EntityRegister;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import com.song.nuclear_craft.items.ItemList;
import com.song.nuclear_craft.misc.ConfigClient;
import com.song.nuclear_craft.misc.ConfigCommon;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import com.song.nuclear_craft.particles.*;
import com.song.nuclear_craft.villagers.PointOfInterestTypes;
import com.song.nuclear_craft.villagers.ProfessionTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

import static com.song.nuclear_craft.villagers.VillagerUtil.fixPOITypeBlockStates;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NuclearCraft.MODID)
public class NuclearCraft
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "nuclear_craft";
    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab("weapons") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemList.ATOMIC_BOMB_ROCKET.get());
        }
    };
    public static final CreativeModeTab AMMO_ITEM_GROUP = new CreativeModeTab("bullets") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemList.AMMO_REGISTRIES_TYPE.get(AmmoSize.SIZE_127).get(AmmoType.NORMAL).get());
        }
    };

    public NuclearCraft() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        EntityRegister.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ParticleRegister.PARTICLES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ItemList.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockList.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ProfessionTypes.VILLAGER_PROFESSION.register(FMLJavaModLoadingContext.get().getModEventBus());
        PointOfInterestTypes.POINT_OF_INTEREST_TYPE.register(FMLJavaModLoadingContext.get().getModEventBus());
        EffectRegister.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntityRegister.TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigClient.CLIENT);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigCommon.COMMON);
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

        fixPOITypeBlockStates(PointOfInterestTypes.RIFLE_AMMO_SELLER.get());
        fixPOITypeBlockStates(PointOfInterestTypes.SHOTGUN_AMMO_SELLER.get());
        fixPOITypeBlockStates(PointOfInterestTypes.GUN_SELLER.get());
        fixPOITypeBlockStates(PointOfInterestTypes.ROCKET_MASTER.get());
        fixPOITypeBlockStates(PointOfInterestTypes.EXPLOSIVE_MASTER.get());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ClientSetup.clientSetup(event);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            // register blocks
            LOGGER.info("HELLO from Register Block");
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            // register items
            LOGGER.info("HELLO from Register Item");
        }

        @SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event){
            // register entities
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<BlockEntityType<?>> event){
            // Tile Entities
        }

        @SubscribeEvent
        public static void onParticleRegistry(final RegistryEvent.Register<ParticleType<?>> event){
            // Register Particles
//            event.getRegistry().registerAll(ParticleList.NUKE_PARTICLE_SMOKE, ParticleList.NUKE_PARTICLE_FIRE, ParticleList.BIG_SMOKE);
        }

        @SubscribeEvent
        public static void onParticleFactoryRegistry(final ParticleFactoryRegisterEvent event){
            Minecraft.getInstance().particleEngine.register(ParticleRegister.NUKE_PARTICLE_SMOKE.get(), BigSmokeParticle.NukeParticleFactory::new);
            Minecraft.getInstance().particleEngine.register(ParticleRegister.NUKE_PARTICLE_FIRE.get(), BigSmokeParticle.NukeParticleFactory::new);
            Minecraft.getInstance().particleEngine.register(ParticleRegister.BIG_SMOKE.get(), BigSmokeParticle.BigSmokeFactory::new);
            Minecraft.getInstance().particleEngine.register(ParticleRegister.RESTRICTED_HEIGHT_SMOKE_PARTICLE.get(), RestrictedSmokeParticle.RestrictedHeightFactory::new);
            Minecraft.getInstance().particleEngine.register(ParticleRegister.MUSHROOM_SMOKE_PARTICLE.get(), RestrictedSmokeParticle.MushroomFactory::new);
            Minecraft.getInstance().particleEngine.register(ParticleRegister.SHOCK_WAVE.get(), ShockWaveParticle.Factory::new);

            Minecraft.getInstance().particleEngine.register(ParticleRegister.EXPLODE_CORE.get(), ExplodeCoreParticle.Factory::new);
        }

        @SubscribeEvent
        public static void onContainerTypeRegistry(final RegistryEvent.Register<MenuType<?>> event){
            event.getRegistry().registerAll(ContainerTypeList.C4_BOMB_CONTAINER_TYPE);
        }

        @SubscribeEvent
        public static void doBothStuff(final FMLCommonSetupEvent event){
            NuclearCraftPacketHandler.register();
        }
    }
}
