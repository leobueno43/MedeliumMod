package com.medelium;

import com.medelium.item.ModItems;
import com.medelium.block.ModBlocks;
import com.medelium.tab.ModCreativeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(MedeliumMod.MOD_ID)
public class MedeliumMod {
    public static final String MOD_ID = "medeliummod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MedeliumMod.class);

    public MedeliumMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        // Register items, blocks, and creative tabs
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::clientSetup);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Medelium Mod - Medieval Fantasy RP is loading...");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Medelium Mod - Client setup complete");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // Items will be automatically added via creative tabs
    }

    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Medelium Mod - Server is starting");
    }
}
