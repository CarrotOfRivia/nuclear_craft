package com.song.nuclear_craft;

import com.song.nuclear_craft.blocks.BlockList;
import com.song.nuclear_craft.blocks.container.ContainerTypeList;
import com.song.nuclear_craft.blocks.tileentity.TileEntityRegister;
import com.song.nuclear_craft.client.ClientSetup;
import com.song.nuclear_craft.entities.EntityRegister;
import com.song.nuclear_craft.items.Ammo.AmmoSize;
import com.song.nuclear_craft.items.Ammo.AmmoType;
import com.song.nuclear_craft.items.ItemList;
import com.song.nuclear_craft.misc.ConfigCommon;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import com.song.nuclear_craft.particles.*;
import com.song.nuclear_craft.villagers.PointOfInterestTypes;
import com.song.nuclear_craft.villagers.ProfessionTypes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
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
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
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
    public static final ItemGroup ITEM_GROUP = new ItemGroup("weapons") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemList.ATOMIC_BOMB_ROCKET.get());
        }
    };
    public static final ItemGroup AMMO_ITEM_GROUP = new ItemGroup("bullets") {
        @Override
        public ItemStack createIcon() {
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
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigCommon.CONFIG);

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
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            // register blocks
            LOGGER.info("HELLO from Register Block");
            event.getRegistry().registerAll(BlockList.C4_ATOMIC_BOMB, BlockList.C4_HIGH_EXPLOSIVE, BlockList.C4_INCENDIARY, BlockList.C4_SMOKE);
        }

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            // register items
            LOGGER.info("HELLO from Register Item");
            event.getRegistry().registerAll(ItemList.C4_ATOMIC_BOMB, ItemList.C4_HIGH_EXPLOSIVE, ItemList.C4_INCENDIARY,
                    ItemList.C4_SMOKE);
        }

        @SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event){
            // register entities
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event){
            // Tile Entities
            event.getRegistry().registerAll(TileEntityRegister.C4_ATOMIC_BOMB_TE_TYPE, TileEntityRegister.C4_HIGH_EXPLOSIVE_TE_TYPE,
                    TileEntityRegister.C4_INCENDIARY_TE_TYPE, TileEntityRegister.C4_SMOKE_TE_TYPE);
        }

        @SubscribeEvent
        public static void onParticleRegistry(final RegistryEvent.Register<ParticleType<?>> event){
            // Register Particles
//            event.getRegistry().registerAll(ParticleList.NUKE_PARTICLE_SMOKE, ParticleList.NUKE_PARTICLE_FIRE, ParticleList.BIG_SMOKE);
        }

        @SubscribeEvent
        public static void onParticleFactoryRegistry(final ParticleFactoryRegisterEvent event){
            Minecraft.getInstance().particles.registerFactory(ParticleRegister.NUKE_PARTICLE_SMOKE.get(), BigSmokeParticle.NukeParticleFactory::new);
            Minecraft.getInstance().particles.registerFactory(ParticleRegister.NUKE_PARTICLE_FIRE.get(), BigSmokeParticle.NukeParticleFactory::new);
            Minecraft.getInstance().particles.registerFactory(ParticleRegister.BIG_SMOKE.get(), BigSmokeParticle.BigSmokeFactory::new);
            Minecraft.getInstance().particles.registerFactory(ParticleRegister.RESTRICTED_HEIGHT_SMOKE_PARTICLE.get(), RestrictedSmokeParticle.Factory::new);
            Minecraft.getInstance().particles.registerFactory(ParticleRegister.DOWN_SMOKE.get(), DownSmoke.Factory::new);

            Minecraft.getInstance().particles.registerFactory(ParticleRegister.EXPLODE_CORE.get(), ExplodeCoreParticle.Factory::new);
        }

        @SubscribeEvent
        public static void onContainerTypeRegistry(final RegistryEvent.Register<ContainerType<?>> event){
            event.getRegistry().registerAll(ContainerTypeList.C4_BOMB_CONTAINER_TYPE);
        }

        @SubscribeEvent
        public static void doBothStuff(final FMLCommonSetupEvent event){
            NuclearCraftPacketHandler.register();
        }
    }
}
