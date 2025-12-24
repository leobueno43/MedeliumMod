package com.medelium;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = MedeliumMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue ENABLE_MEDIEVAL_STRUCTURES = BUILDER
            .comment("Enable medieval structures generation")
            .define("enableMedievalStructures", true);

    private static final ModConfigSpec.IntValue CASTLE_SPAWN_RATE = BUILDER
            .comment("Castle spawn rate (lower = more common)")
            .defineInRange("castleSpawnRate", 1000, 100, 10000);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean enableMedievalStructures;
    public static int castleSpawnRate;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        enableMedievalStructures = ENABLE_MEDIEVAL_STRUCTURES.get();
        castleSpawnRate = CASTLE_SPAWN_RATE.get();
    }
}
