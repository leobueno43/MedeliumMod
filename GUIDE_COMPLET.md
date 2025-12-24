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

### 1.5 📝 Descriptions Personnalisées (Tooltips)

#### 1.5.1 Tooltip Simple sur Objet

**Créer :** `src/main/java/com/medelium/item/custom/CustomTooltipItem.java`

```java
package com.medelium.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class CustomTooltipItem extends Item {
    
    public CustomTooltipItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        // Ajouter des lignes de description
        tooltipComponents.add(Component.literal("§7Description normale en gris"));
        tooltipComponents.add(Component.literal("§6Texte en or doré"));
        tooltipComponents.add(Component.literal("§cTexte en rouge"));
        tooltipComponents.add(Component.literal("§b§oTexte cyan italique"));
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("§8Lore: §fObjet légendaire"));
        
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
```

**Utiliser dans ModItems.java :**
```java
public static final DeferredItem<Item> EPEE_LEGENDAIRE = ITEMS.register("epee_legendaire",
    () -> new CustomTooltipItem(new Item.Properties().rarity(Rarity.EPIC)));
```

#### 1.5.2 Codes Couleur Disponibles

```
§0 - Noir
§1 - Bleu foncé
§2 - Vert foncé
§3 - Cyan foncé
§4 - Rouge foncé
§5 - Violet
§6 - Or
§7 - Gris
§8 - Gris foncé
§9 - Bleu
§a - Vert
§b - Cyan
§c - Rouge
§d - Rose
§e - Jaune
§f - Blanc

§l - Gras
§o - Italique
§n - Souligné
§m - Barré
§k - Aléatoire (animé)
§r - Reset (retour normal)
```

#### 1.5.3 Tooltip avec Informations Dynamiques

```java
@Override
public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
    // Récupérer des données NBT
    if (stack.hasTag()) {
        int level = stack.getTag().getInt("power_level");
        tooltipComponents.add(Component.literal("§6Niveau de Puissance: §f" + level));
    }
    
    // Afficher seulement si SHIFT est pressé
    if (Screen.hasShiftDown()) {
        tooltipComponents.add(Component.literal("§7Informations détaillées:"));
        tooltipComponents.add(Component.literal("§8- Propriété 1"));
        tooltipComponents.add(Component.literal("§8- Propriété 2"));
    } else {
        tooltipComponents.add(Component.literal("§7[SHIFT pour plus d'infos]"));
    }
    
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
}
```

**Importer Screen :**
```java
import net.minecraft.client.gui.screens.Screen;
```

#### 1.5.4 Tooltip sur Bloc

**Créer :** `src/main/java/com/medelium/block/custom/CustomTooltipBlock.java`

```java
package com.medelium.block.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class CustomTooltipBlock extends Block {
    
    public CustomTooltipBlock(Properties properties) {
        super(properties);
    }
}

// Créer aussi le BlockItem personnalisé
package com.medelium.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class CustomTooltipBlockItem extends BlockItem {
    
    public CustomTooltipBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("§7Bloc spécial médiéval"));
        tooltipComponents.add(Component.literal("§6Résistance: §fÉlevée"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
```

**Dans ModBlocks.java :**
```java
private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
    ModItems.ITEMS.register(name, () -> new CustomTooltipBlockItem(block.get(), new Item.Properties()));
}
```

#### 1.5.5 Tooltip Multilingue

Au lieu de texte codé en dur, utiliser des traductions :

```java
@Override
public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
    tooltipComponents.add(Component.translatable("tooltip.medeliummod.royal_sword.line1"));
    tooltipComponents.add(Component.translatable("tooltip.medeliummod.royal_sword.line2"));
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
}
```

**Dans fr_fr.json :**
```json
{
  "tooltip.medeliummod.royal_sword.line1": "§6Épée légendaire des rois",
  "tooltip.medeliummod.royal_sword.line2": "§7Tranche tout ce qui se dresse sur son chemin"
}
```

### 1.6 🎮 Système de Progression avec Tooltips Dynamiques

> **Exemple complet :** Pioche qui level up en cassant des blocs, avec barre de progression, changement de texture, et déblocage d'avantages

#### 1.6.1 Créer l'Objet avec Progression

**Créer :** `src/main/java/com/medelium/item/custom/LevelablePickaxeItem.java`

```java
package com.medelium.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class LevelablePickaxeItem extends PickaxeItem {
    
    public LevelablePickaxeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        // Récupérer les données NBT
        int blocksBreaking = getBlocksBroken(stack);
        int level = getLevel(stack);
        int blocksNeeded = getBlocksNeededForNextLevel(level);
        int currentProgress = blocksBreaking % blocksNeeded;
        
        // Afficher le niveau avec couleur dynamique
        String levelColor = getLevelColor(level);
        tooltipComponents.add(Component.literal(levelColor + "⚡ Niveau: " + level));
        
        // Barre de progression visuelle
        String progressBar = createProgressBar(currentProgress, blocksNeeded);
        tooltipComponents.add(Component.literal("§7" + progressBar));
        tooltipComponents.add(Component.literal("§7Blocs cassés: §f" + currentProgress + "§7/§f" + blocksNeeded));
        
        // Ligne vide
        tooltipComponents.add(Component.literal(""));
        
        // Avantages actuels
        tooltipComponents.add(Component.literal("§6Avantages actifs:"));
        if (level >= 5) {
            tooltipComponents.add(Component.literal("§a✓ Rapidité I"));
        }
        if (level >= 10) {
            tooltipComponents.add(Component.literal("§a✓ Efficacité II"));
        }
        if (level >= 15) {
            tooltipComponents.add(Component.literal("§a✓ Fortune I"));
        }
        if (level >= 20) {
            tooltipComponents.add(Component.literal("§a✓ Rapidité III"));
            tooltipComponents.add(Component.literal("§d✓ Mode Dieu"));
        }
        
        // Informations détaillées avec SHIFT
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.literal("§8Prochain niveau:"));
            
            if (level < 5) {
                tooltipComponents.add(Component.literal("§7Niveau 5: §aRapidité I"));
            } else if (level < 10) {
                tooltipComponents.add(Component.literal("§7Niveau 10: §aEfficacité II"));
            } else if (level < 15) {
                tooltipComponents.add(Component.literal("§7Niveau 15: §aFortune I"));
            } else if (level < 20) {
                tooltipComponents.add(Component.literal("§7Niveau 20: §d§lMode Dieu"));
            } else {
                tooltipComponents.add(Component.literal("§6§l★ NIVEAU MAX ★"));
            }
        } else {
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.literal("§8[SHIFT pour voir les prochains déblocages]"));
        }
        
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
    
    // Créer une barre de progression visuelle
    private String createProgressBar(int current, int max) {
        int barLength = 20;  // Longueur de la barre
        int filled = (int) ((double) current / max * barLength);
        
        StringBuilder bar = new StringBuilder("§a[");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) {
                bar.append("§a■");  // Partie remplie
            } else {
                bar.append("§7■");  // Partie vide
            }
        }
        bar.append("§a]");
        
        return bar.toString();
    }
    
    // Couleur selon le niveau
    private String getLevelColor(int level) {
        if (level >= 20) return "§d§l";  // Rose gras (légendaire)
        if (level >= 15) return "§6§l";  // Or gras (épique)
        if (level >= 10) return "§5";    // Violet (rare)
        if (level >= 5) return "§b";     // Cyan (peu commun)
        return "§f";                      // Blanc (commun)
    }
    
    // Blocs nécessaires pour level up (augmente avec le niveau)
    private int getBlocksNeededForNextLevel(int level) {
        return 100 + (level * 50);  // 100 au début, +50 par niveau
    }
    
    // Récupérer le nombre de blocs cassés
    public static int getBlocksBroken(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getInt("blocks_broken");
        }
        return 0;
    }
    
    // Récupérer le niveau
    public static int getLevel(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getInt("level");
        }
        return 1;  // Niveau de départ
    }
    
    // Définir les blocs cassés
    public static void setBlocksBroken(ItemStack stack, int amount) {
        stack.getOrCreateTag().putInt("blocks_broken", amount);
    }
    
    // Définir le niveau
    public static void setLevel(ItemStack stack, int level) {
        stack.getOrCreateTag().putInt("level", level);
    }
}
```

#### 1.6.2 Détecter les Blocs Cassés et Augmenter la Progression

**Créer :** `src/main/java/com/medelium/event/LevelingEvents.java`

```java
package com.medelium.event;

import com.medelium.MedeliumMod;
import com.medelium.item.custom.LevelablePickaxeItem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = MedeliumMod.MOD_ID)
public class LevelingEvents {
    
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getMainHandItem();
        
        // Vérifier si c'est notre pioche qui level
        if (heldItem.getItem() instanceof LevelablePickaxeItem) {
            // Augmenter le compteur de blocs cassés
            int blocksBroken = LevelablePickaxeItem.getBlocksBroken(heldItem);
            int currentLevel = LevelablePickaxeItem.getLevel(heldItem);
            
            blocksBroken++;
            LevelablePickaxeItem.setBlocksBroken(heldItem, blocksBroken);
            
            // Calculer les blocs nécessaires pour level up
            int blocksNeeded = 100 + (currentLevel * 50);
            int progress = blocksBroken % blocksNeeded;
            
            // Vérifier si on doit level up
            if (progress == 0 && blocksBroken > 0) {
                levelUp(player, heldItem, currentLevel);
            }
        }
    }
    
    private static void levelUp(Player player, ItemStack item, int oldLevel) {
        int newLevel = oldLevel + 1;
        LevelablePickaxeItem.setLevel(item, newLevel);
        
        // Message de level up
        player.sendSystemMessage(Component.literal("§6§l⬆ NIVEAU AUGMENTÉ !"));
        player.sendSystemMessage(Component.literal("§7Pioche niveau §f" + oldLevel + " §7→ §a" + newLevel));
        
        // Son de level up
        player.level().playSound(null, player.blockPosition(), 
            SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.0F);
        
        // Particules
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.level().sendParticles(
                net.minecraft.core.particles.ParticleTypes.TOTEM_OF_UNDYING,
                player.getX(), player.getY() + 1, player.getZ(),
                50, 0.5, 0.5, 0.5, 0.1
            );
        }
        
        // Déblocages spéciaux
        if (newLevel == 5) {
            player.sendSystemMessage(Component.literal("§a✓ Débloqué: Rapidité I"));
        } else if (newLevel == 10) {
            player.sendSystemMessage(Component.literal("§a✓ Débloqué: Efficacité II"));
        } else if (newLevel == 15) {
            player.sendSystemMessage(Component.literal("§a✓ Débloqué: Fortune I"));
        } else if (newLevel == 20) {
            player.sendSystemMessage(Component.literal("§d§l✓ DÉBLOQUÉ: MODE DIEU !"));
            player.level().playSound(null, player.blockPosition(), 
                SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
}
```

#### 1.6.3 Appliquer les Avantages (Effets Actifs)

**Ajouter dans LevelablePickaxeItem.java :**

```java
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

// Méthode appelée quand l'objet est dans la main
@Override
public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slotId, boolean isSelected) {
    if (isSelected && entity instanceof LivingEntity living) {
        int itemLevel = getLevel(stack);
        
        // Niveau 5+ : Rapidité I
        if (itemLevel >= 5) {
            living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.DIG_SPEED, 20, 0, false, false));
        }
        
        // Niveau 10+ : Efficacité II
        if (itemLevel >= 10) {
            living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.DIG_SPEED, 20, 1, false, false));
        }
        
        // Niveau 15+ : Chance (simule Fortune)
        if (itemLevel >= 15) {
            living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.LUCK, 20, 0, false, false));
        }
        
        // Niveau 20+ : Mode Dieu
        if (itemLevel >= 20) {
            living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.DIG_SPEED, 20, 2, false, false));
            living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 20, 1, false, false));
            living.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                net.minecraft.world.effect.MobEffects.REGENERATION, 20, 0, false, false));
        }
    }
    
    super.inventoryTick(stack, level, entity, slotId, isSelected);
}
```

#### 1.6.4 Changement de Texture Selon le Niveau

**Option 1 : Plusieurs items différents**

Créer plusieurs pioches : `levelable_pickaxe_1.png`, `levelable_pickaxe_2.png`, etc.

**Option 2 : Item dynamique avec NBT (avancé)**

**Créer un override dans le modèle JSON :**

`models/item/levelable_pickaxe.json`:
```json
{
  "parent": "minecraft:item/handheld",
  "textures": {
    "layer0": "medeliummod:item/levelable_pickaxe_1"
  },
  "overrides": [
    {
      "predicate": {
        "medeliummod:level": 5
      },
      "model": "medeliummod:item/levelable_pickaxe_5"
    },
    {
      "predicate": {
        "medeliummod:level": 10
      },
      "model": "medeliummod:item/levelable_pickaxe_10"
    },
    {
      "predicate": {
        "medeliummod:level": 15
      },
      "model": "medeliummod:item/levelable_pickaxe_15"
    },
    {
      "predicate": {
        "medeliummod:level": 20
      },
      "model": "medeliummod:item/levelable_pickaxe_20"
    }
  ]
}
```

**Enregistrer le predicate personnalisé :**

```java
// Dans un event client
@SubscribeEvent
public static void registerItemProperties(RegisterEvent event) {
    if (event.getRegistryKey().equals(Registries.ITEM)) {
        ItemProperties.register(ModItems.LEVELABLE_PICKAXE.get(),
            ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, "level"),
            (stack, level, entity, seed) -> {
                int itemLevel = LevelablePickaxeItem.getLevel(stack);
                if (itemLevel >= 20) return 20;
                if (itemLevel >= 15) return 15;
                if (itemLevel >= 10) return 10;
                if (itemLevel >= 5) return 5;
                return 1;
            });
    }
}
```

**Créer les différents modèles :**
- `levelable_pickaxe_1.json` → texture normale
- `levelable_pickaxe_5.json` → texture cyan (niveau 5)
- `levelable_pickaxe_10.json` → texture violette (niveau 10)
- `levelable_pickaxe_15.json` → texture dorée (niveau 15)
- `levelable_pickaxe_20.json` → texture rose/légendaire (niveau 20)

#### 1.6.5 Système Complet avec Récompenses Variées

**Exemple : Différents types de progression**

```java
// Dans le tooltip
tooltipComponents.add(Component.literal("§6Récompenses débloquées:"));

// Récompenses de vitesse
if (level >= 3) {
    tooltipComponents.add(Component.literal("§a✓ Vitesse de minage +10%"));
}
if (level >= 7) {
    tooltipComponents.add(Component.literal("§a✓ Vitesse de minage +25%"));
}

// Récompenses de butin
if (level >= 5) {
    tooltipComponents.add(Component.literal("§a✓ Chance de double drop: 10%"));
}
if (level >= 12) {
    tooltipComponents.add(Component.literal("§a✓ Chance de double drop: 25%"));
}

// Récompenses spéciales
if (level >= 10) {
    tooltipComponents.add(Component.literal("§b✓ Auto-réparation"));
}
if (level >= 15) {
    tooltipComponents.add(Component.literal("§d✓ Veine miner (mine 3x3)"));
}
if (level >= 20) {
    tooltipComponents.add(Component.literal("§6§l✓ PIOCHE DIVINE"));
}

// Statistiques totales
tooltipComponents.add(Component.literal(""));
tooltipComponents.add(Component.literal("§8Total blocs cassés: §7" + getBlocksBroken(stack)));
```

#### 1.6.6 Sauvegarder la Progression (Persistance)

Le système NBT sauvegarde automatiquement, mais vous pouvez aussi :

**Sauvegarder dans les données du joueur :**

```java
// Quand l'objet est craftée/obtenu
@Override
public void onCraftedBy(ItemStack stack, Level level, Player player) {
    // Initialiser avec niveau 1
    setLevel(stack, 1);
    setBlocksBroken(stack, 0);
    
    // Ajouter un UUID unique
    stack.getOrCreateTag().putString("owner", player.getStringUUID());
    stack.getOrCreateTag().putLong("created_time", level.getGameTime());
    
    super.onCraftedBy(stack, level, player);
}
```

**Afficher l'historique dans le tooltip :**

```java
if (Screen.hasShiftDown()) {
    if (stack.hasTag() && stack.getTag().contains("owner")) {
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("§8Propriétaire: §7" + getPlayerName(stack)));
        
        long createdTime = stack.getTag().getLong("created_time");
        long currentTime = minecraft.level.getGameTime();
        long ticksExisted = currentTime - createdTime;
        long secondsExisted = ticksExisted / 20;
        
        tooltipComponents.add(Component.literal("§8Âge: §7" + formatTime(secondsExisted)));
    }
}
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

### 6.3 🐉 Créer un BOSS

#### 6.3.1 Classe de Boss avec Barre de Vie

**Créer :** `src/main/java/com/medelium/entity/custom/DragonBossEntity.java`

```java
package com.medelium.entity.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DragonBossEntity extends Monster {
    private final ServerBossEvent bossEvent;

    public DragonBossEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        // Créer la barre de boss
        this.bossEvent = new ServerBossEvent(
            Component.literal("§4§l⚔ Dragon des Ténèbres ⚔"),
            BossEvent.BossBarColor.RED,
            BossEvent.BossBarOverlay.PROGRESS
        );
        this.xpReward = 500;  // XP donné à la mort
    }

    @Override
    protected void registerGoals() {
        // Objectifs agressifs
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 24.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        
        // Cibler les joueurs
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 500.0)  // 500 HP (250 coeurs)
            .add(Attributes.MOVEMENT_SPEED, 0.35)
            .add(Attributes.ATTACK_DAMAGE, 15.0)  // 15 dégâts (7.5 coeurs)
            .add(Attributes.ARMOR, 10.0)  // Armure
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)  // Pas de recul
            .add(Attributes.FOLLOW_RANGE, 64.0);  // Détection longue distance
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);  // Afficher la barre de boss
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);  // Cacher la barre
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        // Mettre à jour la barre de vie
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public boolean canBeAffected(net.minecraft.world.effect.MobEffectInstance effect) {
        // Boss immunisé à certains effets
        if (effect.getEffect() == net.minecraft.world.effect.MobEffects.WITHER ||
            effect.getEffect() == net.minecraft.world.effect.MobEffects.POISON) {
            return false;
        }
        return super.canBeAffected(effect);
    }
}
```

#### 6.3.2 Boss avec Phases de Combat

```java
public class DragonBossEntity extends Monster {
    private int phase = 1;  // Phase de combat actuelle
    
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        
        float healthPercentage = this.getHealth() / this.getMaxHealth();
        
        // Phase 2 : 50% de vie
        if (healthPercentage <= 0.5f && phase == 1) {
            phase = 2;
            this.bossEvent.setName(Component.literal("§4§l⚔ Dragon Enragé ⚔"));
            this.bossEvent.setColor(BossEvent.BossBarColor.PURPLE);
            
            // Augmenter les stats
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.45);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(25.0);
            
            // Effet visuel
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 
                0.0F, Level.ExplosionInteraction.NONE);
        }
        
        // Phase 3 : 25% de vie
        if (healthPercentage <= 0.25f && phase == 2) {
            phase = 3;
            this.bossEvent.setName(Component.literal("§c§l§k⚔§r §4Dragon Furieux §c§l§k⚔"));
            this.bossEvent.setColor(BossEvent.BossBarColor.RED);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(35.0);
        }
        
        this.bossEvent.setProgress(healthPercentage);
    }
}
```

#### 6.3.3 Boss avec Attaques Spéciales

```java
private int attackCooldown = 0;

@Override
public void aiStep() {
    super.aiStep();
    
    if (!this.level().isClientSide && this.getTarget() != null) {
        attackCooldown--;
        
        if (attackCooldown <= 0) {
            // Attaque spéciale toutes les 5 secondes (100 ticks)
            specialAttack();
            attackCooldown = 100;
        }
    }
}

private void specialAttack() {
    Player target = (Player) this.getTarget();
    if (target != null) {
        // Projectile de feu
        net.minecraft.world.entity.projectile.SmallFireball fireball = 
            new net.minecraft.world.entity.projectile.SmallFireball(
                this.level(), this, 
                target.getX() - this.getX(),
                target.getY() - this.getY(),
                target.getZ() - this.getZ()
            );
        fireball.setPos(this.getX(), this.getY() + 1, this.getZ());
        this.level().addFreshEntity(fireball);
        
        // Son et effet
        this.playSound(net.minecraft.sounds.SoundEvents.GHAST_SHOOT, 1.0F, 1.0F);
    }
}
```

### 6.4 🔱 Créer un MINI-BOSS

```java
package com.medelium.entity.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class MiniBossEntity extends Monster {
    private final ServerBossEvent bossEvent;

    public MiniBossEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        // Barre de boss plus petite
        this.bossEvent = new ServerBossEvent(
            Component.literal("§6Capitaine des Gardes"),
            BossEvent.BossBarColor.YELLOW,
            BossEvent.BossBarOverlay.NOTCHED_10  // 10 encoches
        );
        this.xpReward = 100;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 100.0)  // 50 coeurs
            .add(Attributes.MOVEMENT_SPEED, 0.3)
            .add(Attributes.ATTACK_DAMAGE, 8.0)
            .add(Attributes.ARMOR, 5.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }
}
```

**Styles de barres de boss disponibles :**
- `BossBarColor`: WHITE, PINK, BLUE, RED, GREEN, YELLOW, PURPLE
- `BossBarOverlay`: PROGRESS (pleine), NOTCHED_6, NOTCHED_10, NOTCHED_12, NOTCHED_20

### 6.5 💎 Système de Loot Aléatoire

#### 6.5.1 Loot Table pour Boss

**Créer :** `src/main/resources/data/medeliummod/loot_table/entities/dragon_boss.json`

```json
{
  "type": "minecraft:entity",
  "pools": [
    {
      "rolls": 1,
      "entries": [
        {
          "type": "minecraft:item",
          "name": "minecraft:diamond",
          "functions": [
            {
              "function": "minecraft:set_count",
              "count": {
                "min": 10,
                "max": 20
              }
            }
          ],
          "weight": 1
        }
      ]
    },
    {
      "rolls": {
        "min": 3,
        "max": 5
      },
      "entries": [
        {
          "type": "minecraft:item",
          "name": "medeliummod:royal_sword",
          "weight": 5,
          "functions": [
            {
              "function": "minecraft:enchant_randomly",
              "options": [
                "minecraft:sharpness",
                "minecraft:fire_aspect"
              ]
            }
          ]
        },
        {
          "type": "minecraft:item",
          "name": "medeliummod:gold_coin",
          "weight": 20,
          "functions": [
            {
              "function": "minecraft:set_count",
              "count": {
                "min": 5,
                "max": 15
              }
            }
          ]
        },
        {
          "type": "minecraft:item",
          "name": "minecraft:emerald",
          "weight": 15,
          "functions": [
            {
              "function": "minecraft:set_count",
              "count": {
                "min": 1,
                "max": 10
              }
            }
          ]
        }
      ]
    },
    {
      "rolls": 1,
      "entries": [
        {
          "type": "minecraft:item",
          "name": "medeliummod:dragon_scale",
          "weight": 100,
          "conditions": [
            {
              "condition": "minecraft:killed_by_player"
            }
          ]
        }
      ]
    }
  ]
}
```

#### 6.5.2 Loot Aléatoire dans le Code

**Ajouter dans l'entité :**

```java
@Override
public void die(net.minecraft.world.damagesource.DamageSource cause) {
    super.die(cause);
    
    if (!this.level().isClientSide && cause.getEntity() instanceof Player player) {
        // Drop garanti
        this.spawnAtLocation(ModItems.ROYAL_SEAL.get(), 1);
        
        // Drop avec probabilité
        if (this.random.nextFloat() < 0.5f) {  // 50% de chance
            this.spawnAtLocation(ModItems.ROYAL_SWORD.get(), 1);
        }
        
        // Drop rare
        if (this.random.nextFloat() < 0.05f) {  // 5% de chance
            ItemStack rareItem = new ItemStack(ModItems.ANCIENT_SCROLL.get());
            // Ajouter enchantement
            rareItem.enchant(net.minecraft.world.item.enchantment.Enchantments.SHARPNESS, 5);
            this.spawnAtLocation(rareItem);
        }
        
        // Drop aléatoire entre 5 et 15 pièces d'or
        int coinAmount = 5 + this.random.nextInt(11);
        this.spawnAtLocation(ModItems.GOLD_COIN.get(), coinAmount);
        
        // Effet à la mort
        this.level().explode(this, this.getX(), this.getY(), this.getZ(), 
            3.0F, Level.ExplosionInteraction.NONE);
    }
}
```

#### 6.5.3 Loot Table Avancé avec Conditions

```json
{
  "type": "minecraft:entity",
  "pools": [
    {
      "rolls": 1,
      "entries": [
        {
          "type": "minecraft:item",
          "name": "medeliummod:legendary_helmet",
          "weight": 1,
          "conditions": [
            {
              "condition": "minecraft:random_chance",
              "chance": 0.01
            },
            {
              "condition": "minecraft:killed_by_player"
            }
          ],
          "functions": [
            {
              "function": "minecraft:set_name",
              "name": {
                "text": "Casque du Dragon Ancien",
                "color": "gold",
                "bold": true
              }
            },
            {
              "function": "minecraft:set_lore",
              "lore": [
                {
                  "text": "Forgé dans les flammes",
                  "color": "gray",
                  "italic": true
                },
                {
                  "text": "d'un dragon millénaire",
                  "color": "gray",
                  "italic": true
                }
              ]
            },
            {
              "function": "minecraft:enchant_with_levels",
              "levels": {
                "min": 20,
                "max": 30
              },
              "treasure": true
            }
          ]
        }
      ]
    }
  ]
}
```

### 6.6 Enregistrer les Attributs

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

### 13.1 🎨 GUI Complètement Personnalisé avec Image

> **Important :** Cette section montre comment créer un GUI entièrement basé sur UNE IMAGE que vous dessinez vous-même, avec des zones cliquables. Pas besoin de composants classiques !

#### 13.1.1 Créer Votre Image de GUI

**Logiciel recommandé :** Paint.NET, GIMP, Photoshop, ou même Paint

**Emplacement :** `src/main/resources/assets/medeliummod/textures/gui/custom_menu.png`

**Dimensions :** 256x256 pixels (taille de l'image complète)

**Zone visible :** Les premiers 176x166 pixels (en haut à gauche) seront affichés

**Exemple de design à créer :**
```
┌─────────────────────────────────────────┐ 176px
│  🏰 Medelium - Menu Médiéval           │
│                                         │
│   ┌─────────┐  ┌─────────┐             │
│   │ Quêtes  │  │  Shop   │             │  166px
│   └─────────┘  └─────────┘             │
│                                         │
│   ┌─────────┐  ┌─────────┐             │
│   │ Métiers │  │ Statut  │             │
│   └─────────┘  └─────────┘             │
└─────────────────────────────────────────┘
```

**Conseils pour votre image :**
1. Dessinez tout : fond, boutons, texte, icônes
2. Utilisez des couleurs médiévales (marron, or, pierre)
3. Créez des "boutons" visuels (rectangles avec bordures)
4. Ajoutez des décorations (épées, couronnes, parchemin)
5. Marquez mentalement où vous voulez les zones cliquables

#### 13.1.2 Créer le Menu (Sans Inventaire)

**Créer :** `src/main/java/com/medelium/screen/AlchemyTableScreen.java`

```java
package com.medelium.screen;

import com.medelium.MedeliumMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AlchemyTableScreen extends AbstractContainerScreen<AlchemyTableMenu> {
    // Chemin vers votre texture de GUI
    private static final ResourceLocation TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, "textures/gui/alchemy_table.png");

    public AlchemyTableScreen(AlchemyTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 166;  // Hauteur de votre GUI
        this.imageWidth = 176;   // Largeur de votre GUI
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        
        // Dessiner l'image de fond
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
```

#### 13.1.3 Ajouter des Zones Cliquables sur Votre Image

**Voici où vous créez l'interactivité !**

```java
public class CustomMenuScreen extends AbstractContainerScreen<CustomMenu> {
    private static final ResourceLocation TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, "textures/gui/custom_menu.png");

    public CustomMenuScreen(CustomMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 166;
        this.imageWidth = 176;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        
        // Dessiner votre image
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
        
        // Surligner Plusieurs Images Superposées

**Exemple : Image de fond + overlay qui change**

```java
private static final ResourceLocation BG_TEXTURE = 
    ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, "textures/gui/menu_background.png");
private static final ResourceLocation OVERLAY_HOVER = 
    ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, "textures/gui/button_hover.png");

@Override
protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;
    
    // 1. Dessiner le fond principal
    guiGraphics.blit(BG_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    
    // 2. Dessiner une image de survol sur un bouton spécifique
    if (isMouseOverButton1(mouseX, mouseY, x, y)) {
        // Dessiner une petite image de surbrillance (60x20 pixels depuis votre image)
        guiGraphics.blit(OVERLAY_HOVER, 
            x + 20, y + 40,  // Position où dessiner
            0, 0,            // Position dans l'image source (u, v)
            60, 20);         // Taille à dessiner
    }
}
```

#### 13.1.5 GUI Animé avec Images

**Créer une animation simple (bouton qui pulse) :**

```java
private int animationTick = 0;

@Override
protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;
    
    // Image de fond
    guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    
    // Animation : changer l'image toutes les 10 ticks
    animationTick++;
    int frame = (animationTick / 10) % 4;  // 4 frames d'animation
    
    // Dessiner la frame actuelle (depuis différentes positions dans votre image)
    guiGraphics.blit(TEXTURE, 
        x + 100, y + 50,      // Où dessiner
        0, 166 + (frame * 20), // Position dans l'image (u, v)
        20, 20);              // Taille
}
```

**Organisation de votre image pour animation :**
```
┌────────────────────┐
│  GUI principal     │ 0-165 pixels de haut
│  176x166          │
├────────────────────┤
│ Frame 1 │ 166-185 │
├────────────────────┤
│ Frame 2 │ 186-205 │
├────────────────────┤
│ Frame 3 │ 206-225 │
├────────────────────┤
│ Frame 4 │ 226-245 │
└───────────Ouvrir le GUI avec une Commande ou Objet

**Option 1 : Commande pour ouvrir le GUI**

```java
// Dans ModCommands.java
dispatcher.register(Commands.literal("openmenu")
    .executes(context -> {
        if (context.getSource().getEntity() instanceof ServerPlayer player) {
            player.openMenu(new SimpleMenuProvider(
                (id, playerInv, p) -> new CustomMenu(id, playerInv),
                Component.literal("Menu Médiéval")
            ));
        }
        return 1;
    }));
```

**Option 2 : Objet qui ouvre le GUI**

```java
package com.medelium.item.custom;

import com.medelium.screen.CustomMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MenuOpenerItem extends Item {
    
    public MenuOpenerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider(
                (id, playerInv, p) -> new CustomMenu(id, playerInv),
                Component.literal("§6Menu Médiéval")
            ));
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
```

**Enregistrer l'objet :**
```java
// Dans ModItems.java
public static final DeferredItem<Item> MENU_OPENER = ITEMS.register("menu_opener",
    () -> new MenuOpenerItem(new Item.Properties()));
```

**Option 3 : Bloc qui ouvre le GUI**

```java
// Dans le bloc
**Code complet :**

```java
package com.medelium.screen;

import com.medelium.MedeliumMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class QuestMenuScreen extends AbstractContainerScreen<CustomMenu> {
    private static final ResourceLocation TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, "textures/gui/quest_menu.png");

    public QuestMenuScreen(CustomMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 166;
        this.imageWidth = 176;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        
        // Dessiner l'image complète
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
        
        // Surbrillance au survol
        if (isOverQuest1(mouseX, mouseY, x, y)) {
            guiGraphics.fill(x + 10, y + 30, x + 166, y + 55, 0x40FFFFFF);
        }
        if (isOverQuest2(mouseX, mouseY, x, y)) {
            guiGraphics.fill(x + 10, y + 60, x + 166, y + 85, 0x40FFFFFF);
        }
        if (isOverQuest3(mouseX, mouseY, x, y)) {
            guiGraphics.fill(x + 10, y + 90, x + 166, y + 115, 0x40FFFFFF);
        }
        if (isOverCloseButton(mouseX, mouseY, x, y)) {
            guiGraphics.fill(x + 60, y + 140, x + 116, y + 156, 0x40FF0000);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        
        // Quête 1 cliquée
        if (isOverQuest1(mouseX, mouseY, x, y)) {
            minecraft.player.sendSystemMessage(Component.literal("§aQuête 1 : Tuer le Dragon"));
            playDownSound(minecraft.getSoundManager());
            return true;
        }
        
        // Quête 2 cliquée
        if (isOverQuest2(mouseX, mouseY, x, y)) {
            minecraft.player.sendSystemMessage(Component.literal("§aQuête 2 : Récupérer l'épée"));
            playDownSound(minecraft.getSoundManager());
            return true;
        }
        
        // Quête 3 cliquée
        if (isOverQuest3(mouseX, mouseY, x, y)) {
            minecraft.player.sendSystemMessage(Component.literal("§aQuête 3 : Sauver le village"));
            playDownSound(minecraft.getSoundManager());
            return true;
        }
        
        // Bouton fermer
        if (isOverCloseButton(mouseX, mouseY, x, y)) {
            this.onClose();
            return true;
        }
        
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    // Zones de détection
    private boolean isOverQuest1(double mX, double mY, int gX, int gY) {
        return mX >= gX + 10 && mX <= gX + 166 && mY >= gY + 30 && mY <= gY + 55;
    }
    
    private boolean isOverQuest2(double mX, double mY, int gX, int gY) {
        return mX >= gX + 10 && mX <= gX + 166 && mY >= gY + 60 && mY <= gY + 85;
    }
    
    private boolean isOverQuest3(double mX, double mY, int gX, int gY) {
        return mX >= gX + 10 && mX <= gX + 166 && mY >= gY + 90 && mY <= gY + 115;
    }
    
    private boolean isOverCloseButton(double mX, double mY, int gX, int gY) {
        return mX >= gX + 60 && mX <= gX + 116 && mY >= gY + 140 && mY <= gY + 156;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        
        // Tooltips
        if (isOverQuest1(mouseX, mouseY, x, y)) {
            guiGraphics.renderTooltip(this.font, 
                Component.literal("§6Récompense: 100 pièces d'or"), mouseX, mouseY);
        }
    }
    
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // Vide = votre image contient déjà tout
    }
}
```

**Résumé : GUI = Image Pure**
1. ✅ Vous créez UNE image avec tout dessiné dessus
2. ✅ Vous définissez les zones cliquables dans le code
3. ✅ Pas besoin de composants Minecraft classiques
4. ✅ Total contrôle visuel sur votre interface !
1. **Ouvrez votre image** dans un éditeur
2. **Notez les coordonnées** de chaque bouton que vous avez dessiné :
   - X de départ (depuis la gauche)
   - Y de départ (depuis le haut)
   - Largeur du bouton
   - Hauteur du bouton

3. **Exemple** : Si vous avez dessiné un bouton "Quêtes" à :
   - Position : 20 pixels de gauche, 40 pixels du haut
   - Taille : 60 pixels large, 20 pixels haut
   - Code : `mouseX >= x + 20 && mouseX <= x + 80 && mouseY >= y + 40 && mouseY <= y + 60`

#### 13.1.4 GUI avec Éléments Personnalisés

```java
@Override
protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;
    
    // Image de fond principale
    guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    
    // Ajouter une barre de progression (si en train de crafter)
    if (menu.isCrafting()) {
        int progress = menu.getScaledProgress();
        // Dessiner barre (x, y, u, v, width, height)
        guiGraphics.blit(TEXTURE, x + 79, y + 35, 176, 0, progress, 16);
    }
    
    // Ajouter une icône de feu (si actif)
    if (menu.isLit()) {
        guiGraphics.blit(TEXTURE, x + 56, y + 53, 176, 16, 14, 14);
    }
}

@Override
public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
    super.render(guiGraphics, mouseX, mouseY, partialTick);
    this.renderTooltip(guiGraphics, mouseX, mouseY);
    
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;
    
    // Ajouter du texte personnalisé
    guiGraphics.drawString(this.font, "§6Alchimie Médiévale", x + 8, y + 6, 0xFFFFFF, false);
    
    // Tooltip personnalisé au survol
    if (mouseX >= x + 79 && mouseX <= x + 103 && mouseY >= y + 35 && mouseY <= y + 51) {
        guiGraphics.renderTooltip(this.font, Component.literal("Progression: " + menu.getProgress() + "%"), mouseX, mouseY);
    }
}
```

#### 13.1.5 GUI avec Plusieurs Images

```java
private static final ResourceLocation BG_TEXTURE = 
    ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, "textures/gui/alchemy_bg.png");
private static final ResourceLocation OVERLAY_TEXTURE = 
    ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, "textures/gui/alchemy_overlay.png");
private static final ResourceLocation ICONS_TEXTURE = 
    ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, "textures/gui/icons.png");

@Override
protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;
    
    // Fond
    guiGraphics.blit(BG_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    
    // Overlay décoratif
    guiGraphics.blit(OVERLAY_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    
    // Icônes spéciales
    guiGraphics.blit(ICONS_TEXTURE, x + 10, y + 10, 0, 0, 16, 16, 256, 256);
}
```

#### 13.1.6 Enregistrer le Menu Type

**Créer :** `src/main/java/com/medelium/screen/ModMenuTypes.java`

```java
package com.medelium.screen;

import com.medelium.MedeliumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
        DeferredRegister.create(Registries.MENU, MedeliumMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<AlchemyTableMenu>> ALCHEMY_TABLE_MENU =
        MENUS.register("alchemy_table_menu", () ->
            IMenuTypeExtension.create((windowId, inv, data) -> {
                // Logique de création du menu
                return new AlchemyTableMenu(windowId, inv, new ItemStackHandler(3), 
                    ContainerLevelAccess.NULL);
            }));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
```

**Dans MedeliumMod.java :**
```java
ModMenuTypes.register(modEventBus);
```

#### 13.1.7 Enregistrer l'Écran (Client)

**Créer :** `src/main/java/com/medelium/event/ModClientEvents.java`

```java
package com.medelium.event;

import com.medelium.MedeliumMod;
import com.medelium.screen.AlchemyTableScreen;
import com.medelium.screen.ModMenuTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = MedeliumMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {
    
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.ALCHEMY_TABLE_MENU.get(), AlchemyTableScreen::new);
    }
}
```

#### 13.1.8 GUI avec Boutons Cliquables

```java
public class AlchemyTableScreen extends AbstractContainerScreen<AlchemyTableMenu> {
    
    @Override
    protected void init() {
        super.init();
        
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        
        // Ajouter un bouton
        this.addRenderableWidget(Button.builder(
            Component.literal("Transmuter"),
            button -> onTransmuteClicked()
        ).bounds(x + 70, y + 60, 60, 20).build());
    }
    
    private void onTransmuteClicked() {
        // Envoyer packet au serveur
        MedeliumMod.LOGGER.info("Bouton Transmuter cliqué!");
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        
        // Zone cliquable personnalisée
        if (mouseX >= x + 100 && mouseX <= x + 116 && 
            mouseY >= y + 20 && mouseY <= y + 36) {
            // Action personnalisée
            return true;
        }
        
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
```

### 13.2 Créer un Bloc avec Inventaire

**Créer :** `src/main/java/com/medelium/block/custom/AlchemyTableBlock.java`

```java
package com.medelium.block.custom;

import com.medelium.block.entity.AlchemyTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
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
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof AlchemyTableBlockEntity alchemyTable) {
                serverPlayer.openMenu(alchemyTable, pos);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AlchemyTableBlockEntity(pos, state);
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
