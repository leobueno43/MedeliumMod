package com.medelium.item;

import com.medelium.MedeliumMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = 
        DeferredRegister.createItems(MedeliumMod.MOD_ID);

    // Medieval Weapons
    public static final DeferredItem<SwordItem> KNIGHT_SWORD = ITEMS.register("knight_sword",
            () -> new SwordItem(Tiers.IRON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.IRON, 5, -2.4F))));

    public static final DeferredItem<SwordItem> ROYAL_SWORD = ITEMS.register("royal_sword",
            () -> new SwordItem(Tiers.DIAMOND, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.DIAMOND, 6, -2.4F))));

    // Medieval Items
    public static final DeferredItem<Item> SILVER_COIN = ITEMS.register("silver_coin",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GOLD_COIN = ITEMS.register("gold_coin",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ROYAL_SEAL = ITEMS.register("royal_seal",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> ANCIENT_SCROLL = ITEMS.register("ancient_scroll",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> MEDIEVAL_BREAD = ITEMS.register("medieval_bread",
            () -> new Item(new Item.Properties().food(ModFoods.MEDIEVAL_BREAD)));

    public static final DeferredItem<Item> MEAD = ITEMS.register("mead",
            () -> new Item(new Item.Properties().food(ModFoods.MEAD)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
