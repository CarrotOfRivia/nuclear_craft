package com.song.nuclear_craft;

import com.song.nuclear_craft.blocks.BlockList;
import com.song.nuclear_craft.blocks.tileentity.TileEntityList;
import com.song.nuclear_craft.blocks.container.ContainerTypeList;
import com.song.nuclear_craft.entities.EntityList;
import com.song.nuclear_craft.items.ItemList;
import com.song.nuclear_craft.misc.Config;
import com.song.nuclear_craft.network.NuclearCraftPacketHandler;
import com.song.nuclear_craft.particles.NukeParticle;
import com.song.nuclear_craft.particles.ParticleList;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
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
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
import org.lwjgl.glfw.GLFW;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NuclearCraft.MODID)
public class NuclearCraft
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "nuclear_craft";
    public static final ItemGroup ITEM_GROUP = new ItemGroup(ItemGroup.GROUPS.length, "nuclear_craft") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemList.ATOMIC_BOMB_ROCKET);
        }
    };
    public static KeyBinding gunReload;
    public static KeyBinding zoom;

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
        NuclearCraftPacketHandler.register();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.CONFIG);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
        gunReload = new KeyBinding("key."+MODID+".load_ammo", GLFW.GLFW_KEY_R, "key."+MODID+".categories"); // keyBinding is a static variable
        zoom = new KeyBinding("key."+MODID+".zoom", GLFW.GLFW_KEY_Z, "key."+MODID+".categories");
        ClientRegistry.registerKeyBinding(gunReload);
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
            event.getRegistry().registerAll(ItemList.ATOMIC_BOMB_ROCKET, ItemList.ROCKET_LAUNCHER, ItemList.ROCKET_LAUNCHER_ATOMIC_BOMB,
                    ItemList.ROCKET_CONTROL_UNIT, ItemList.URANIUM_235, ItemList.URANIUM_238, ItemList.INCENDIARY_ROCKET,
                    ItemList.SMOKE_ROCKET, ItemList.ROCKET_LAUNCHER_INCENDIARY, ItemList.ROCKET_LAUNCHER_SMOKE, ItemList.HIGH_EXPLOSIVE_ROCKET,
                    ItemList.ROCKET_LAUNCHER_HIGH_EXPLOSIVE, ItemList.C4_ATOMIC_BOMB, ItemList.C4_HIGH_EXPLOSIVE, ItemList.C4_INCENDIARY,
                    ItemList.C4_SMOKE, ItemList.DESERT_EAGLE, ItemList.AMMO_9MM, ItemList.AMMO_TEST, ItemList.AMMO_9MM_ANTI_GRAVITY, ItemList.AMMO_9MM_INCENDIARY,
                    ItemList.AMMO_9MM_NUKE, ItemList.AMMO_9MM_SILVER, ItemList.AMMO_9MM_TUNGSTEN, ItemList.AMMO_9MM_EXPLOSIVE, ItemList.GLOCK, ItemList.USP, ItemList.AK47,
                    ItemList.AMMO_762_ANTI_GRAVITY, ItemList.AMMO_762_EXPLOSIVE, ItemList.AMMO_762_INCENDIARY, ItemList.AMMO_762_NORMAL, ItemList.AMMO_762_NUKE,
                    ItemList.AMMO_762_SILVER, ItemList.AMMO_762_TUNGSTEN);
        }

        @SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event){
            // register entities
            event.getRegistry().registerAll(EntityList.ATOMIC_BOMB_ENTITY, EntityList.BULLET_ENTITY);
        }

        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event){
            // Tile Entities
            event.getRegistry().registerAll(TileEntityList.C4_ATOMIC_BOMB_TE_TYPE, TileEntityList.C4_HIGH_EXPLOSIVE_TE_TYPE,
                    TileEntityList.C4_INCENDIARY_TE_TYPE, TileEntityList.C4_SMOKE_TE_TYPE);
        }

        @SubscribeEvent
        public static void onParticleRegistry(final RegistryEvent.Register<ParticleType<?>> event){
            // Register Particles
            event.getRegistry().registerAll(ParticleList.NUKE_PARTICLE_SMOKE, ParticleList.NUKE_PARTICLE_FIRE, ParticleList.BIG_SMOKE);
        }

        @SubscribeEvent
        public static void onParticleFactoryRegistry(final ParticleFactoryRegisterEvent event){
            Minecraft.getInstance().particles.registerFactory(ParticleList.NUKE_PARTICLE_SMOKE, NukeParticle.NukeParticleFactory::new);
            Minecraft.getInstance().particles.registerFactory(ParticleList.NUKE_PARTICLE_FIRE, NukeParticle.NukeParticleFactory::new);
            Minecraft.getInstance().particles.registerFactory(ParticleList.BIG_SMOKE, NukeParticle.BigSmokeFactory::new);
        }

        @SubscribeEvent
        public static void onContainerTypeRegistry(final RegistryEvent.Register<ContainerType<?>> event){
            event.getRegistry().registerAll(ContainerTypeList.C4_BOMB_CONTAINER_TYPE);
        }
    }
}
