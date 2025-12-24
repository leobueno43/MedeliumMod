# 📚 Guide Complet de Développement - Medelium Mod

Ce guide vous explique **TOUT** ce que vous pouvez ajouter à votre mod médiéval fantasy, étape par étape, même si vous débutez en programmation !

---

## 🎯 Table des Matières

1. [Objets (Items)](#1-objets-items)
2. [Blocs (Blocks)](#2-blocs-blocks)
3. [Armures (Armor)](#3-armures-armor)
4. [Outils (Tools)](#4-outils-tools)
5. [Nourriture et Potions](#5-nourriture-et-potions)
6. [Entités (Mobs)](#6-entités-mobs)
7. [Structures](#7-structures)
8. [Dimensions](#8-dimensions)
9. [Enchantements](#9-enchantements)
10. [Effets de Potion](#10-effets-de-potion)
11. [Recettes de Craft](#11-recettes-de-craft)
12. [Génération de Minerais](#12-génération-de-minerais)
13. [Interfaces Graphiques (GUI)](#13-interfaces-graphiques-gui)
14. [Sons Personnalisés](#14-sons-personnalisés)
15. [Textures et Modèles](#15-textures-et-modèles)
16. [Events et Mécaniques](#16-events-et-mécaniques)
17. [Commandes Personnalisées](#17-commandes-personnalisées)
18. [Systèmes de Compétences](#18-systèmes-de-compétences)

---

## 1. 📦 Objets (Items)

### 1.1 Créer un Objet Simple

**Où :** `src/main/java/com/medelium/item/ModItems.java`

```java
// Ajouter dans la classe ModItems
public static final DeferredItem<Item> MON_OBJET = ITEMS.register("mon_objet",
    () -> new Item(new Item.Properties()));
```

**Types d'objets simples à créer :**
- 💰 Monnaies (pièces, gemmes)
- 📜 Documents (parchemins, lettres, livres)
- 🔑 Clés et objets de quête
- 💎 Gemmes et matériaux rares
- 🎨 Objets décoratifs
- 🧪 Ingrédients d'alchimie

### 1.2 Créer un Objet avec Propriétés Spéciales

```java
// Objet qui brille dans l'inventaire
public static final DeferredItem<Item> GEMME_MAGIQUE = ITEMS.register("gemme_magique",
    () -> new Item(new Item.Properties()
        .stacksTo(16)  // Max 16 par stack au lieu de 64
        .rarity(Rarity.RARE)  // Couleur rare (cyan)
        .fireResistant()  // Ne brûle pas dans la lave
    ));
```

**Propriétés disponibles :**
- `.stacksTo(nombre)` - Combien peut-on empiler
- `.rarity(Rarity.X)` - Couleur du nom (COMMON, UNCOMMON, RARE, EPIC)
- `.fireResistant()` - Résistant au feu
- `.durability(nombre)` - Durabilité (pour outils)

### 1.3 Ajouter les Traductions

**Fichier :** `src/main/resources/assets/medeliummod/lang/fr_fr.json`

```json
{
  "item.medeliummod.mon_objet": "Mon Super Objet",
  "item.medeliummod.gemme_magique": "Gemme Magique"
}
```

### 1.4 Ajouter l'Objet au Creative Tab

**Fichier :** `src/main/java/com/medelium/tab/ModCreativeTabs.java`

```java
// Dans displayItems, ajouter :
output.accept(ModItems.MON_OBJET.get());
```

---

## 2. 🧱 Blocs (Blocks)

### 2.1 Bloc Simple

**Où :** `src/main/java/com/medelium/block/ModBlocks.java`

```java
public static final DeferredBlock<Block> MON_BLOC = registerBlock("mon_bloc",
    () -> new Block(BlockBehaviour.Properties.of()
        .strength(3.0f, 6.0f)  // dureté, résistance explosion
        .requiresCorrectToolForDrops()  // Nécessite bon outil
        .sound(SoundType.STONE)  // Son de pierre
    ));
```

**Types de sons (SoundType) :**
- `STONE` - Pierre
- `WOOD` - Bois
- `METAL` - Métal
- `GLASS` - Verre
- `SAND` - Sable
- `GRAVEL` - Gravier
- `GRASS` - Herbe

### 2.2 Bloc Lumineux

```java
public static final DeferredBlock<Block> TORCHE_MAGIQUE = registerBlock("torche_magique",
    () -> new Block(BlockBehaviour.Properties.of()
        .strength(0.5f)
        .lightLevel((state) -> 15)  // Lumière max (15)
        .sound(SoundType.WOOD)
    ));
```

**Niveaux de lumière :** 0 (sombre) à 15 (très lumineux comme une torche)

### 2.3 Bloc Transparent

```java
public static final DeferredBlock<Block> VITRAIL = registerBlock("vitrail",
    () -> new Block(BlockBehaviour.Properties.of()
        .strength(0.3f)
        .sound(SoundType.GLASS)
        .noOcclusion()  // Transparent
    ));
```

### 2.4 Bloc qui Ne Peut Pas Être Cassé en Survie

```java
public static final DeferredBlock<Block> PIERRE_INDESTRUCTIBLE = registerBlock("pierre_indestructible",
    () -> new Block(BlockBehaviour.Properties.of()
        .strength(-1.0f, 3600000.0f)  // Incassable
        .requiresCorrectToolForDrops()
    ));
```

### 2.5 Escaliers et Dalles

```java
// Escalier
public static final DeferredBlock<StairBlock> ESCALIER_CHATEAU = registerBlock("escalier_chateau",
    () -> new StairBlock(
        ModBlocks.CASTLE_STONE.get().defaultBlockState(),
        BlockBehaviour.Properties.of().strength(2.0f)
    ));

// Dalle
public static final DeferredBlock<SlabBlock> DALLE_CHATEAU = registerBlock("dalle_chateau",
    () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2.0f)));

// Mur
public static final DeferredBlock<WallBlock> MUR_CHATEAU = registerBlock("mur_chateau",
    () -> new WallBlock(BlockBehaviour.Properties.of().strength(2.0f)));
```

---

## 3. 🛡️ Armures (Armor)

### 3.1 Créer un Matériau d'Armure

**Créer un nouveau fichier :** `src/main/java/com/medelium/item/ModArmorMaterials.java`

```java
package com.medelium.item;

import com.medelium.MedeliumMod;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    
    // Armure de Chevalier
    public static final Holder<ArmorMaterial> KNIGHT = register("knight",
        Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
            map.put(ArmorItem.Type.BOOTS, 2);      // Protection bottes
            map.put(ArmorItem.Type.LEGGINGS, 5);   // Protection jambières
            map.put(ArmorItem.Type.CHESTPLATE, 6); // Protection plastron
            map.put(ArmorItem.Type.HELMET, 2);     // Protection casque
            map.put(ArmorItem.Type.BODY, 5);       // Protection corps
        }),
        15,  // Enchantabilité
        SoundEvents.ARMOR_EQUIP_IRON,  // Son équipement
        () -> Ingredient.of(ModItems.SILVER_COIN.get()),  // Matériau réparation
        List.of(new ArmorMaterial.Layer(
            ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, "knight")
        )),
        0.0F,  // Ténacité
        0.0F   // Résistance recul
    );

    private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> defense,
                                                   int enchantmentValue, Holder<SoundEvent> equipSound,
                                                   Supplier<Ingredient> repairIngredient,
                                                   List<ArmorMaterial.Layer> layers,
                                                   float toughness, float knockbackResistance) {
        EnumMap<ArmorItem.Type, Integer> enummap = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            enummap.put(type, defense.get(type));
        }
        
        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL,
            ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, name),
            new ArmorMaterial(enummap, enchantmentValue, equipSound, repairIngredient, layers,
                toughness, knockbackResistance));
    }
}
```

### 3.2 Créer les Pièces d'Armure

**Dans ModItems.java :**

```java
// Importer en haut du fichier
import net.minecraft.world.item.ArmorItem;

// Armure de Chevalier
public static final DeferredItem<ArmorItem> KNIGHT_HELMET = ITEMS.register("knight_helmet",
    () -> new ArmorItem(ModArmorMaterials.KNIGHT, ArmorItem.Type.HELMET,
        new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))));

public static final DeferredItem<ArmorItem> KNIGHT_CHESTPLATE = ITEMS.register("knight_chestplate",
    () -> new ArmorItem(ModArmorMaterials.KNIGHT, ArmorItem.Type.CHESTPLATE,
        new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))));

public static final DeferredItem<ArmorItem> KNIGHT_LEGGINGS = ITEMS.register("knight_leggings",
    () -> new ArmorItem(ModArmorMaterials.KNIGHT, ArmorItem.Type.LEGGINGS,
        new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))));

public static final DeferredItem<ArmorItem> KNIGHT_BOOTS = ITEMS.register("knight_boots",
    () -> new ArmorItem(ModArmorMaterials.KNIGHT, ArmorItem.Type.BOOTS,
        new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))));
```

**Ajouter au Creative Tab :**
```java
output.accept(ModItems.KNIGHT_HELMET.get());
output.accept(ModItems.KNIGHT_CHESTPLATE.get());
output.accept(ModItems.KNIGHT_LEGGINGS.get());
output.accept(ModItems.KNIGHT_BOOTS.get());
```

**Traductions :**
```json
{
  "item.medeliummod.knight_helmet": "Casque de Chevalier",
  "item.medeliummod.knight_chestplate": "Plastron de Chevalier",
  "item.medeliummod.knight_leggings": "Jambières de Chevalier",
  "item.medeliummod.knight_boots": "Bottes de Chevalier"
}
```

---

## 4. ⛏️ Outils (Tools)

### 4.1 Créer un Tier d'Outil

**Créer :** `src/main/java/com/medelium/item/ModToolTiers.java`

```java
package com.medelium.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    // Outils en Argent
    public static final Tier SILVER = new SimpleTier(
        BlockTags.INCORRECT_FOR_IRON_TOOL,  // Blocs minables
        500,  // Durabilité
        6.0F,  // Vitesse de minage
        2.0F,  // Dégâts d'attaque bonus
        14,  // Enchantabilité
        () -> Ingredient.of(ModItems.SILVER_COIN.get())  // Matériau réparation
    );
}
```

### 4.2 Créer les Outils

**Dans ModItems.java :**

```java
import net.minecraft.world.item.*;

// Pioche
public static final DeferredItem<PickaxeItem> SILVER_PICKAXE = ITEMS.register("silver_pickaxe",
    () -> new PickaxeItem(ModToolTiers.SILVER, new Item.Properties()
        .attributes(PickaxeItem.createAttributes(ModToolTiers.SILVER, 1.0F, -2.8F))));

// Hache
public static final DeferredItem<AxeItem> SILVER_AXE = ITEMS.register("silver_axe",
    () -> new AxeItem(ModToolTiers.SILVER, new Item.Properties()
        .attributes(AxeItem.createAttributes(ModToolTiers.SILVER, 6.0F, -3.1F))));

// Pelle
public static final DeferredItem<ShovelItem> SILVER_SHOVEL = ITEMS.register("silver_shovel",
    () -> new ShovelItem(ModToolTiers.SILVER, new Item.Properties()
        .attributes(ShovelItem.createAttributes(ModToolTiers.SILVER, 1.5F, -3.0F))));

// Houe
public static final DeferredItem<HoeItem> SILVER_HOE = ITEMS.register("silver_hoe",
    () -> new HoeItem(ModToolTiers.SILVER, new Item.Properties()
        .attributes(HoeItem.createAttributes(ModToolTiers.SILVER, -2.0F, -1.0F))));
```

---

## 5. 🍖 Nourriture et Potions

### 5.1 Nourriture Simple

**Dans ModFoods.java :**

```java
public static final FoodProperties FROMAGE = new FoodProperties.Builder()
    .nutrition(4)  // Points de nourriture (1 = demi-cuisse)
    .saturationModifier(0.3f)  // Saturation
    .build();

// Nourriture rapide à manger
public static final FoodProperties POMME_OR_MEDELIUM = new FoodProperties.Builder()
    .nutrition(4)
    .saturationModifier(1.2f)
    .fast()  // Mange rapidement
    .alwaysEdible()  // Mange même si pas faim
    .build();
```

**Dans ModItems.java :**
```java
public static final DeferredItem<Item> FROMAGE = ITEMS.register("fromage",
    () -> new Item(new Item.Properties().food(ModFoods.FROMAGE)));
```

### 5.2 Nourriture avec Effets

```java
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

public static final FoodProperties POTION_FORCE = new FoodProperties.Builder()
    .nutrition(2)
    .saturationModifier(0.1f)
    .effect(() -> new MobEffectInstance(
        MobEffects.DAMAGE_BOOST,  // Effet Force
        600,  // Durée en ticks (20 ticks = 1 seconde)
        1     // Niveau (0 = niveau 1, 1 = niveau 2, etc.)
    ), 1.0f)  // Probabilité (1.0 = 100%)
    .build();
```

**Effets disponibles (MobEffects) :**
- `MOVEMENT_SPEED` - Rapidité
- `MOVEMENT_SLOWDOWN` - Lenteur
- `DIG_SPEED` - Célérité
- `DIG_SLOWDOWN` - Fatigue
- `DAMAGE_BOOST` - Force
- `HEAL` - Soin instantané
- `HARM` - Dégâts instantanés
- `JUMP` - Sauter boost
- `CONFUSION` - Nausée
- `REGENERATION` - Régénération
- `DAMAGE_RESISTANCE` - Résistance
- `FIRE_RESISTANCE` - Résistance au feu
- `WATER_BREATHING` - Respiration aquatique
- `INVISIBILITY` - Invisibilité
- `BLINDNESS` - Cécité
- `NIGHT_VISION` - Vision nocturne
- `HUNGER` - Faim
- `WEAKNESS` - Faiblesse
- `POISON` - Poison
- `WITHER` - Wither
- `HEALTH_BOOST` - Boost santé
- `ABSORPTION` - Absorption
- `SATURATION` - Saturation
- `GLOWING` - Surbrillance
- `LEVITATION` - Lévitation
- `LUCK` - Chance
- `UNLUCK` - Malchance
- `SLOW_FALLING` - Chute lente

---

## 6. 👾 Entités (Mobs)

### 6.1 Créer une Entité Basique

**Créer :** `src/main/java/com/medelium/entity/ModEntities.java`

```java
package com.medelium.entity;

import com.medelium.MedeliumMod;
import com.medelium.entity.custom.GuardEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(Registries.ENTITY_TYPE, MedeliumMod.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<GuardEntity>> GUARD =
        ENTITY_TYPES.register("guard", () -> EntityType.Builder.of(
            GuardEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.8f)  // Taille (largeur, hauteur)
            .build("guard"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
```

**Dans MedeliumMod.java, ajouter :**
```java
ModEntities.register(modEventBus);
```

### 6.2 Créer la Classe de l'Entité

**Créer :** `src/main/java/com/medelium/entity/custom/GuardEntity.java`

```java
package com.medelium.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class GuardEntity extends PathfinderMob {
    
    public GuardEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        // Objectifs de l'entité
        this.goalSelector.addGoal(0, new FloatGoal(this));  // Nager
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));  // Paniquer si attaqué
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));  // Regarder joueur
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));  // Regarder autour
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));  // Se promener
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 20.0)  // Points de vie
            .add(Attributes.MOVEMENT_SPEED, 0.25)  // Vitesse
            .add(Attributes.FOLLOW_RANGE, 24.0);  // Distance détection
    }
}
```

### 6.3 Enregistrer les Attributs

**Créer :** `src/main/java/com/medelium/event/ModEventBusEvents.java`

```java
package com.medelium.event;

import com.medelium.MedeliumMod;
import com.medelium.entity.ModEntities;
import com.medelium.entity.custom.GuardEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = MedeliumMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GUARD.get(), GuardEntity.createAttributes().build());
    }
}
```

---

## 7. 🏰 Structures

### 7.1 Créer une Structure Simple avec Template Pools

**Créer :** `src/main/resources/data/medeliummod/worldgen/template_pool/castle/start.json`

```json
{
  "name": "medeliummod:castle/start",
  "fallback": "minecraft:empty",
  "elements": [
    {
      "weight": 1,
      "element": {
        "location": "medeliummod:castle/main_hall",
        "processors": "minecraft:empty",
        "projection": "rigid",
        "element_type": "minecraft:single_pool_element"
      }
    }
  ]
}
```

### 7.2 Créer la Configuration de Structure

**Créer :** `src/main/resources/data/medeliummod/worldgen/structure/medieval_castle.json`

```json
{
  "type": "minecraft:jigsaw",
  "start_pool": "medeliummod:castle/start",
  "size": 7,
  "max_distance_from_center": 80,
  "biomes": "#minecraft:is_overworld",
  "step": "surface_structures",
  "spawn_overrides": {},
  "terrain_adaptation": "beard_thin",
  "start_height": {
    "absolute": 0
  },
  "project_start_to_heightmap": "WORLD_SURFACE_WG",
  "use_expansion_hack": false
}
```

---

## 8. 🌌 Dimensions

### 8.1 Créer une Dimension Personnalisée

**Créer :** `src/main/resources/data/medeliummod/dimension/medieval_realm.json`

```json
{
  "type": "minecraft:overworld",
  "generator": {
    "type": "minecraft:noise",
    "biome_source": {
      "type": "minecraft:multi_noise",
      "preset": "minecraft:overworld"
    },
    "settings": "minecraft:overworld"
  }
}
```

### 8.2 Créer un Type de Dimension

**Créer :** `src/main/resources/data/medeliummod/dimension_type/medieval_realm.json`

```json
{
  "ultrawarm": false,
  "natural": true,
  "piglin_safe": false,
  "respawn_anchor_works": false,
  "bed_works": true,
  "has_raids": true,
  "has_skylight": true,
  "has_ceiling": false,
  "coordinate_scale": 1.0,
  "ambient_light": 0.0,
  "logical_height": 384,
  "effects": "minecraft:overworld",
  "infiniburn": "#minecraft:infiniburn_overworld",
  "min_y": -64,
  "height": 384,
  "monster_spawn_light_level": {
    "type": "minecraft:uniform",
    "value": {
      "min_inclusive": 0,
      "max_inclusive": 7
    }
  },
  "monster_spawn_block_light_limit": 0
}
```

---

## 9. ✨ Enchantements

### 9.1 Créer un Enchantement Personnalisé

**Créer :** `src/main/java/com/medelium/enchantment/ModEnchantments.java`

```java
package com.medelium.enchantment;

import com.medelium.MedeliumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
        DeferredRegister.create(Registries.ENCHANTMENT, MedeliumMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
```

---

## 10. 🧪 Effets de Potion

### 10.1 Créer un Effet Personnalisé

**Créer :** `src/main/java/com/medelium/effect/ModEffects.java`

```java
package com.medelium.effect;

import com.medelium.MedeliumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
        DeferredRegister.create(Registries.MOB_EFFECT, MedeliumMod.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> BLESSING = MOB_EFFECTS.register("blessing",
        () -> new BlessingEffect(MobEffectCategory.BENEFICIAL, 0xFFD700));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
```

**Créer :** `src/main/java/com/medelium/effect/BlessingEffect.java`

```java
package com.medelium.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BlessingEffect extends MobEffect {
    
    public BlessingEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        // Code qui s'exécute chaque tick
        if (!entity.level().isClientSide()) {
            // Exemple : soigner l'entité
            if (entity.getHealth() < entity.getMaxHealth()) {
                entity.heal(0.5F);
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        // Exécuter toutes les 20 ticks (1 seconde)
        return duration % 20 == 0;
    }
}
```

---

## 11. 📝 Recettes de Craft

### 11.1 Recette Shaped (Forme Précise)

**Créer :** `src/main/resources/data/medeliummod/recipe/knight_sword.json`

```json
{
  "type": "minecraft:crafting_shaped",
  "category": "equipment",
  "pattern": [
    " S ",
    " S ",
    " T "
  ],
  "key": {
    "S": {
      "item": "medeliummod:silver_coin"
    },
    "T": {
      "item": "minecraft:stick"
    }
  },
  "result": {
    "id": "medeliummod:knight_sword",
    "count": 1
  }
}
```

### 11.2 Recette Shapeless (Forme Libre)

**Créer :** `src/main/resources/data/medeliummod/recipe/gold_coin.json`

```json
{
  "type": "minecraft:crafting_shapeless",
  "category": "misc",
  "ingredients": [
    {
      "item": "medeliummod:silver_coin"
    },
    {
      "item": "medeliummod:silver_coin"
    },
    {
      "item": "medeliummod:silver_coin"
    },
    {
      "item": "medeliummod:silver_coin"
    }
  ],
  "result": {
    "id": "medeliummod:gold_coin",
    "count": 1
  }
}
```

### 11.3 Recette de Cuisson (Fourneau)

**Créer :** `src/main/resources/data/medeliummod/recipe/medieval_bread_smelting.json`

```json
{
  "type": "minecraft:smelting",
  "category": "food",
  "cookingtime": 200,
  "experience": 0.35,
  "ingredient": {
    "item": "minecraft:wheat"
  },
  "result": {
    "id": "medeliummod:medieval_bread"
  }
}
```

### 11.4 Recette Smithing (Table de Forgeron)

**Créer :** `src/main/resources/data/medeliummod/recipe/royal_sword_smithing.json`

```json
{
  "type": "minecraft:smithing_transform",
  "template": {
    "item": "medeliummod:royal_seal"
  },
  "base": {
    "item": "medeliummod:knight_sword"
  },
  "addition": {
    "item": "minecraft:diamond"
  },
  "result": {
    "id": "medeliummod:royal_sword"
  }
}
```

---

## 12. ⛏️ Génération de Minerais

### 12.1 Créer un Minerai

**Dans ModBlocks.java :**

```java
public static final DeferredBlock<Block> SILVER_ORE = registerBlock("silver_ore",
    () -> new Block(BlockBehaviour.Properties.of()
        .strength(3.0f)
        .requiresCorrectToolForDrops()
        .sound(SoundType.STONE)));

public static final DeferredBlock<Block> DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore",
    () -> new Block(BlockBehaviour.Properties.of()
        .strength(4.5f)
        .requiresCorrectToolForDrops()
        .sound(SoundType.DEEPSLATE)));
```

### 12.2 Configurer la Génération

**Créer :** `src/main/resources/data/medeliummod/worldgen/placed_feature/silver_ore_placed.json`

```json
{
  "feature": "medeliummod:silver_ore",
  "placement": [
    {
      "type": "minecraft:count",
      "count": 20
    },
    {
      "type": "minecraft:in_square"
    },
    {
      "type": "minecraft:height_range",
      "height": {
        "type": "minecraft:trapezoid",
        "max_inclusive": {
          "absolute": 64
        },
        "min_inclusive": {
          "absolute": -64
        }
      }
    },
    {
      "type": "minecraft:biome"
    }
  ]
}
```

**Créer :** `src/main/resources/data/medeliummod/worldgen/configured_feature/silver_ore.json`

```json
{
  "type": "minecraft:ore",
  "config": {
    "discard_chance_on_air_exposure": 0.0,
    "size": 9,
    "targets": [
      {
        "target": {
          "predicate_type": "minecraft:tag_match",
          "tag": "minecraft:stone_ore_replaceables"
        },
        "state": {
          "Name": "medeliummod:silver_ore"
        }
      },
      {
        "target": {
          "predicate_type": "minecraft:tag_match",
          "tag": "minecraft:deepslate_ore_replaceables"
        },
        "state": {
          "Name": "medeliummod:deepslate_silver_ore"
        }
      }
    ]
  }
}
```

---

## 13. 🖥️ Interfaces Graphiques (GUI)

### 13.1 Créer un Bloc avec Inventaire

**Créer :** `src/main/java/com/medelium/block/custom/AlchemyTableBlock.java`

```java
package com.medelium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class AlchemyTableBlock extends Block implements EntityBlock {
    
    public AlchemyTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            // Ouvrir l'interface ici
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        // Retourner votre BlockEntity
        return null;
    }
}
```

---

## 14. 🔊 Sons Personnalisés

### 14.1 Enregistrer des Sons

**Créer :** `src/main/java/com/medelium/sound/ModSounds.java`

```java
package com.medelium.sound;

import com.medelium.MedeliumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
        DeferredRegister.create(Registries.SOUND_EVENT, MedeliumMod.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> CASTLE_BELL = registerSound("castle_bell");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSound(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
```

### 14.2 Définir les Sons

**Créer :** `src/main/resources/assets/medeliummod/sounds.json`

```json
{
  "castle_bell": {
    "sounds": [
      {
        "name": "medeliummod:castle_bell",
        "stream": false
      }
    ],
    "subtitle": "sound.medeliummod.castle_bell"
  }
}
```

**Placer le fichier son :**
- Format : `.ogg`
- Emplacement : `src/main/resources/assets/medeliummod/sounds/castle_bell.ogg`

---

## 15. 🎨 Textures et Modèles

### 15.1 Structure des Fichiers

```
src/main/resources/assets/medeliummod/
├── textures/
│   ├── item/
│   │   ├── knight_sword.png
│   │   └── silver_coin.png
│   ├── block/
│   │   └── castle_stone.png
│   └── entity/
│       └── guard.png
├── models/
│   ├── item/
│   │   └── knight_sword.json
│   └── block/
│       └── castle_stone.json
└── blockstates/
    └── castle_stone.json
```

### 15.2 Modèle d'Objet Simple

**Créer :** `src/main/resources/assets/medeliummod/models/item/silver_coin.json`

```json
{
  "parent": "minecraft:item/generated",
  "textures": {
    "layer0": "medeliummod:item/silver_coin"
  }
}
```

### 15.3 Modèle d'Outil (Épée, Pioche, etc.)

**Créer :** `src/main/resources/assets/medeliummod/models/item/knight_sword.json`

```json
{
  "parent": "minecraft:item/handheld",
  "textures": {
    "layer0": "medeliummod:item/knight_sword"
  }
}
```

### 15.4 Modèle de Bloc Simple

**Créer :** `src/main/resources/assets/medeliummod/models/block/castle_stone.json`

```json
{
  "parent": "minecraft:block/cube_all",
  "textures": {
    "all": "medeliummod:block/castle_stone"
  }
}
```

**Créer :** `src/main/resources/assets/medeliummod/models/item/castle_stone.json`

```json
{
  "parent": "medeliummod:block/castle_stone"
}
```

### 15.5 Blockstate

**Créer :** `src/main/resources/assets/medeliummod/blockstates/castle_stone.json`

```json
{
  "variants": {
    "": {
      "model": "medeliummod:block/castle_stone"
    }
  }
}
```

### 15.6 Taille des Textures

- **Objets/Blocs :** 16x16 pixels (ou multiples : 32x32, 64x64, 128x128)
- **Format :** PNG avec transparence
- **Nom :** Tout en minuscules, underscores pour espaces

---

## 16. ⚡ Events et Mécaniques

### 16.1 Event de Joueur

**Créer :** `src/main/java/com/medelium/event/ModEvents.java`

```java
package com.medelium.event;

import com.medelium.MedeliumMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = MedeliumMod.MOD_ID)
public class ModEvents {
    
    // Quand un joueur se connecte
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        // Code ici
        MedeliumMod.LOGGER.info("Joueur {} a rejoint le serveur", event.getEntity().getName().getString());
    }
    
    // Quand une entité meurt
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof net.minecraft.world.entity.player.Player player) {
            // Le joueur est mort
        }
    }
}
```

### 16.2 Events Disponibles

**Events Joueur :**
- `PlayerEvent.PlayerLoggedInEvent` - Connexion
- `PlayerEvent.PlayerLoggedOutEvent` - Déconnexion
- `PlayerEvent.PlayerRespawnEvent` - Respawn
- `PlayerInteractEvent.RightClickBlock` - Clic droit sur bloc
- `PlayerInteractEvent.RightClickItem` - Clic droit avec objet
- `AttackEntityEvent` - Attaque entité

**Events Entité :**
- `LivingDeathEvent` - Mort
- `LivingHurtEvent` - Dégâts
- `LivingFallEvent` - Chute
- `LivingEntityUseItemEvent` - Utilisation objet

**Events Bloc :**
- `BlockEvent.BreakEvent` - Bloc cassé
- `BlockEvent.PlaceEvent` - Bloc placé

---

## 17. 💻 Commandes Personnalisées

### 17.1 Créer une Commande

**Créer :** `src/main/java/com/medelium/command/ModCommands.java`

```java
package com.medelium.command;

import com.medelium.MedeliumMod;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = MedeliumMod.MOD_ID)
public class ModCommands {
    
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        
        // Commande simple : /medelium
        dispatcher.register(Commands.literal("medelium")
            .executes(ModCommands::executeMedelium));
        
        // Commande avec argument : /heal <joueur>
        dispatcher.register(Commands.literal("heal")
            .requires(source -> source.hasPermission(2))  // Niveau OP 2
            .then(Commands.argument("player", net.minecraft.commands.arguments.EntityArgument.player())
                .executes(ModCommands::executeHeal)));
    }
    
    private static int executeMedelium(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(
            () -> Component.literal("§6Bienvenue dans Medelium Mod!"),
            false
        );
        return 1;
    }
    
    private static int executeHeal(CommandContext<CommandSourceStack> context) {
        try {
            var player = net.minecraft.commands.arguments.EntityArgument.getPlayer(context, "player");
            player.setHealth(player.getMaxHealth());
            context.getSource().sendSuccess(
                () -> Component.literal("§a" + player.getName().getString() + " a été soigné!"),
                true
            );
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
```

---

## 18. 🎓 Systèmes de Compétences

### 18.1 Stocker des Données Joueur (NBT)

**Créer :** `src/main/java/com/medelium/capability/PlayerManaProvider.java`

```java
package com.medelium.capability;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class PlayerManaProvider implements INBTSerializable<CompoundTag> {
    private int mana = 0;
    private final int maxMana = 100;

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = Math.min(mana, maxMana);
    }

    public void addMana(int amount) {
        this.mana = Math.min(mana + amount, maxMana);
    }

    public void removeMana(int amount) {
        this.mana = Math.max(mana - amount, 0);
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("mana", mana);
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.mana = nbt.getInt("mana");
    }
}
```

---

## 📋 Checklist Complète de Développement

### Pour chaque Objet :
- [ ] Enregistrer dans `ModItems.java`
- [ ] Ajouter au Creative Tab
- [ ] Créer la traduction (fr_fr.json, en_us.json)
- [ ] Créer le modèle JSON (`models/item/`)
- [ ] Créer la texture PNG (`textures/item/`)
- [ ] (Optionnel) Créer une recette de craft

### Pour chaque Bloc :
- [ ] Enregistrer dans `ModBlocks.java`
- [ ] Ajouter au Creative Tab
- [ ] Créer la traduction
- [ ] Créer le modèle de bloc (`models/block/`)
- [ ] Créer le modèle d'item (`models/item/`)
- [ ] Créer le blockstate (`blockstates/`)
- [ ] Créer la texture (`textures/block/`)
- [ ] Définir le loot table (drop)

### Pour chaque Armure :
- [ ] Créer le matériau dans `ModArmorMaterials.java`
- [ ] Enregistrer les 4 pièces dans `ModItems.java`
- [ ] Ajouter au Creative Tab
- [ ] Créer les 4 traductions
- [ ] Créer les 4 modèles JSON
- [ ] Créer les 4 textures
- [ ] Créer la texture de l'armure portée (2 fichiers dans `textures/models/armor/`)

### Pour chaque Outil :
- [ ] Créer le tier dans `ModToolTiers.java`
- [ ] Enregistrer dans `ModItems.java`
- [ ] Ajouter au Creative Tab
- [ ] Créer la traduction
- [ ] Créer le modèle (parent: `handheld`)
- [ ] Créer la texture

### Pour chaque Entité :
- [ ] Enregistrer dans `ModEntities.java`
- [ ] Créer la classe de l'entité
- [ ] Enregistrer les attributs dans `ModEventBusEvents`
- [ ] Créer le renderer
- [ ] Créer le modèle 3D
- [ ] Créer la texture
- [ ] Ajouter la traduction
- [ ] (Optionnel) Créer l'œuf de spawn

---

## 🔧 Compilation et Test

### Commandes Gradle :

```bash
# Nettoyer le build
.\gradlew clean

# Compiler le mod
.\gradlew build

# Lancer le client de test
.\gradlew runClient

# Lancer le serveur de test
.\gradlew runServer

# Générer les données (recipes, loot tables, etc.)
.\gradlew runData

# Actualiser les dépendances
.\gradlew --refresh-dependencies
```

### Trouver le Fichier JAR :
Après `.\gradlew build`, le fichier se trouve dans :
`build/libs/medeliummod-1.0.0.jar`

---

## 🎯 Idées de Contenu Médiéval Fantasy

### Objets :
- Monnaies (cuivre, argent, or, platine)
- Documents RP (contrats, lettres scellées, cartes au trésor)
- Bijoux (couronnes, bagues, amulettes)
- Instruments (luth, flûte, tambour)
- Drapeaux et bannières
- Sceaux de différentes maisons nobles

### Blocs :
- Différents types de pierres de château
- Mobilier médiéval (trônes, tables, chaises, lits)
- Décorations (tapisseries, chandeliers, torches murales)
- Fortifications (créneaux, meurtrières)
- Éléments de donjon (cellules, chaînes)
- Autels et statues

### Armes et Armures :
- Épées (courte, longue, bâtarde, grande)
- Lances et hallebardes
- Arcs et arbalètes
- Boucliers
- Armures complètes (garde, chevalier, roi, mage)
- Casques variés

### Entités :
- Gardes et soldats
- Marchands ambulants
- Nobles et rois
- Paysans
- Bandits
- Créatures fantasy (dragons, golems)
- Chevaux avec armure

### Structures :
- Châteaux
- Villages médiévaux
- Donjons
- Tours de garde
- Églises et cathédrales
- Camps de bandits
- Ruines anciennes

### Mécaniques RP :
- Système de monnaie
- Système de réputation avec factions
- Quêtes et missions
- Métiers (forgeron, alchimiste, archer)
- Système de noblesse/titres
- Mariages et alliances

---

## 🐛 Résolution de Problèmes Courants

### Le jeu crash au démarrage :
1. Vérifier que Java 21 est installé
2. Vérifier `neoforge.mods.toml` (syntaxe correcte)
3. Regarder les logs dans `logs/latest.log`

### Les textures sont violettes/noires :
1. Vérifier le chemin dans le JSON
2. Vérifier que le PNG existe
3. Vérifier l'orthographe (sensible à la casse)

### L'objet n'apparaît pas en jeu :
1. Vérifier l'enregistrement dans `ModItems`
2. Vérifier l'ajout au Creative Tab
3. Relancer le jeu avec `.\gradlew runClient`

### Les recettes ne fonctionnent pas :
1. Vérifier la syntaxe JSON (virgules, guillemets)
2. Vérifier que les IDs d'items sont corrects
3. Générer les données avec `.\gradlew runData`

---

## 📚 Ressources Utiles

### Documentation :
- NeoForge Docs: https://docs.neoforged.net/
- Minecraft Wiki: https://minecraft.wiki/

### Outils :
- **Blockbench** - Créer des modèles 3D
- **GIMP/Photoshop** - Créer des textures
- **NBTExplorer** - Éditer les données de sauvegarde
- **MCreator** - Générateur de code (pour apprendre)

### Générateurs en Ligne :
- Crafting Recipe Generator
- Loot Table Generator
- Custom Enchantment Generator

---

## ✅ Vous êtes Prêt !

Vous savez maintenant **TOUT** ce qu'il est possible de créer dans votre mod ! 

**Conseils finaux :**
1. Commencez petit (quelques objets simples)
2. Testez après chaque ajout
3. Sauvegardez régulièrement votre code
4. N'hésitez pas à copier-coller les exemples
5. Les erreurs sont normales, lisez les logs !

**Bon développement ! ⚔️🏰✨**
