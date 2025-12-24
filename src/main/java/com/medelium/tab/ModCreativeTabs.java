package com.medelium.tab;

import com.medelium.MedeliumMod;
import com.medelium.block.ModBlocks;
import com.medelium.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = 
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MedeliumMod.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MEDELIUM_TAB = 
        CREATIVE_MODE_TABS.register("medelium_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.medeliummod.medelium_tab"))
            .icon(() -> new ItemStack(ModItems.ROYAL_SWORD.get()))
            .displayItems((parameters, output) -> {
                // Add all items
                output.accept(ModItems.KNIGHT_SWORD.get());
                output.accept(ModItems.ROYAL_SWORD.get());
                output.accept(ModItems.SILVER_COIN.get());
                output.accept(ModItems.GOLD_COIN.get());
                output.accept(ModItems.ROYAL_SEAL.get());
                output.accept(ModItems.ANCIENT_SCROLL.get());
                output.accept(ModItems.MEDIEVAL_BREAD.get());
                output.accept(ModItems.MEAD.get());
                
                // Add all blocks
                output.accept(ModBlocks.CASTLE_STONE.get());
                output.accept(ModBlocks.CASTLE_STONE_BRICKS.get());
                output.accept(ModBlocks.MEDIEVAL_LANTERN.get());
                output.accept(ModBlocks.ROYAL_THRONE.get());
                output.accept(ModBlocks.MEDIEVAL_TABLE.get());
            }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
