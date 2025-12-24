package com.medelium.block;

import com.medelium.MedeliumMod;
import com.medelium.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = 
        DeferredRegister.createBlocks(MedeliumMod.MOD_ID);

    // Medieval Stone Blocks
    public static final DeferredBlock<Block> CASTLE_STONE = registerBlock("castle_stone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2.0f, 6.0f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> CASTLE_STONE_BRICKS = registerBlock("castle_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2.0f, 6.0f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MEDIEVAL_LANTERN = registerBlock("medieval_lantern",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(0.3f)
                    .lightLevel((state) -> 15)
                    .sound(SoundType.LANTERN)));

    public static final DeferredBlock<Block> ROYAL_THRONE = registerBlock("royal_throne",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2.0f)
                    .sound(SoundType.WOOD)));

    public static final DeferredBlock<Block> MEDIEVAL_TABLE = registerBlock("medieval_table",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2.0f)
                    .sound(SoundType.WOOD)));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
