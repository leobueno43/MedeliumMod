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

> **📖 C'est quoi un Item ?**
> Un item (objet) est tout ce qui peut être tenu dans l'inventaire : pièces de monnaie, nourriture, outils, armes, etc.
> Dans Minecraft, TOUT ce que vous pouvez ramasser est un Item.
>
> **🎯 Pourquoi créer des items personnalisés ?**
> - Ajouter de la monnaie pour votre système économique RP
> - Créer des objets de quête uniques
> - Fabriquer des matériaux spéciaux pour le craft
> - Développer votre univers médiéval avec des objets thématiques
>
> **🔧 Comment ça marche ?**
> 1. Vous **enregistrez** l'item dans le code (ModItems.java)
> 2. Vous **traduisez** son nom (fr_fr.json)
> 3. Vous créez sa **texture** (image PNG 16x16)
> 4. Vous créez son **modèle** (fichier JSON qui lie la texture)
> 5. Vous l'ajoutez à un **onglet créatif** pour le trouver en jeu

### 1.1 Créer un Objet Simple

**📝 Ce que vous allez apprendre :**
- Enregistrer un item basique dans le code
- Comprendre les propriétés de base
- Le rendre disponible en jeu

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

> **📖 Pourquoi les traductions sont OBLIGATOIRES ?**
> Sans fichier de traduction, votre item s'affiche comme : `item.medeliummod.mon_objet`
> Avec traduction, il s'affiche : `Mon Super Objet` ✨
>
> **🎯 Comment ça marche :**
> - Minecraft lit le fichier `fr_fr.json` (français) ou `en_us.json` (anglais)
> - La clé `item.medeliummod.mon_objet` correspond à votre item
> - La valeur est le nom affiché en jeu
>
> **💡 Astuce :** Créez les deux fichiers (fr_fr.json ET en_us.json) pour supporter multi-langues !

**Fichier :** `src/main/resources/assets/medeliummod/lang/fr_fr.json`

```json
{
  "item.medeliummod.mon_objet": "Mon Super Objet",
  "item.medeliummod.gemme_magique": "Gemme Magique"
}
```

### 1.4 Ajouter l'Objet au Creative Tab

> **📖 C'est quoi un Creative Tab ?**
> C'est un onglet dans le menu créatif (mode créatif) qui regroupe vos items.
> Comme "Blocs de construction", "Redstone", etc.
>
> **🎯 Pourquoi créer votre propre onglet ?**
> - Tous vos items du mod au même endroit
> - Plus facile pour les joueurs de trouver vos objets
> - Design professionnel
> - Icône personnalisée pour l'onglet
>
> **💡 Structure :**
> 1. Vous créez l'onglet dans ModCreativeTabs.java
> 2. Vous définissez son icône (un de vos items)
> 3. Vous ajoutez tous vos items dedans
> 4. Il apparaît automatiquement en jeu !

**Fichier :** `src/main/java/com/medelium/tab/ModCreativeTabs.java`

```java
// Dans displayItems, ajouter :
output.accept(ModItems.MON_OBJET.get());
```

### 1.5 📝 Descriptions Personnalisées (Tooltips)

> **📖 C'est quoi un Tooltip ?**
> C'est le petit texte qui s'affiche quand vous survolez un objet avec votre souris dans l'inventaire.
> Par défaut, Minecraft affiche juste le nom de l'objet et ses enchantements.
>
> **🎯 Pourquoi personnaliser les tooltips ?**
> - Ajouter du **lore** (histoire) à vos objets légendaires
> - Afficher des **statistiques** (dégâts, bonus, etc.)
> - Montrer les **conditions** d'utilisation (niveau requis, métier)
> - Donner des **indices** pour les quêtes
> - Rendre votre mod plus **immersif** et professionnel
>
> **🔧 Comment ça marche techniquement ?**
> 1. Vous créez une classe d'item personnalisée
> 2. Vous override (remplacez) la méthode `appendHoverText`
> 3. Dans cette méthode, vous ajoutez vos lignes de texte
> 4. Vous utilisez des codes couleur pour styliser
>
> **🎨 Codes couleur disponibles :**
> `§0-§9, §a-§f` pour les couleurs + `§l` (gras), `§o` (italique), `§n` (souligné)
>
> **💡 Astuce Pro :** Utilisez `Screen.hasShiftDown()` pour afficher plus d'infos quand le joueur appuie sur SHIFT !

#### 1.5.1 Tooltip Simple sur Objet

**📝 Ce code ajoute des lignes de texte coloré sous le nom de l'objet :**

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

> **📖 C'est quoi un système de progression ?**
> C'est rendre vos objets "vivants" : ils gagnent de l'expérience, montent de niveau, débloquent des capacités.
> Comme dans les RPG où votre personnage devient plus fort en jouant !
>
> **🎯 Exemple concret :**
> Vous avez une pioche. Au début, elle est normale (niveau 1).
> Chaque fois que vous cassez un bloc, elle gagne de l'XP.
> À 100 blocs cassés → niveau 2 → elle mine plus vite
> À 500 blocs cassés → niveau 5 → effet Fortune activé
> À 1000 blocs cassés → niveau 10 → texture change en or, super pouvoirs !
>
> **🔧 Comment ça fonctionne techniquement ?**
>
> **1. Stockage des données (NBT)**
> Les objets peuvent stocker des données cachées (comme un mini-fichier de sauvegarde)
> On y stocke : niveau actuel, blocs cassés, etc.
>
> **2. Détection des événements**
> On "écoute" quand le joueur casse un bloc avec cet objet
> → On incrémente le compteur
>
> **3. Calcul et Level Up**
> On vérifie si assez de blocs cassés
> → Si oui : niveau + 1, réinitialiser le compteur, effets spéciaux
>
> **4. Affichage dynamique**
> Le tooltip lit les données NBT et affiche la progression en temps réel
> Barre visuelle : `[■■■■§7■■■]` = 50% de progression
>
> **5. Application des bonus**
> Selon le niveau, l'objet donne des effets (rapidité, fortune, etc.)
>
> **💡 Ce système est RÉUTILISABLE pour :**
> - Épées qui deviennent plus fortes en tuant des mobs
> - Armures qui se renforcent avec les dégâts subis
> - Outils qui débloquent des capacités spéciales
> - Items de quête qui évoluent avec le joueur

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

> **📖 C'est quoi un Bloc ?**
> Un bloc est un élément placeable dans le monde : pierre, bois, votre château personnalisé, etc.
> Contrairement aux items, les blocs ont une présence physique dans le monde 3D.
>
> **🎯 Différence Item vs Bloc :**
> - **Item** = dans l'inventaire, dans la main
> - **Bloc** = placé dans le monde
> - Un bloc a TOUJOURS un item associé pour pouvoir le placer/récupérer
>
> **🔧 Comment ça marche ?**
> 1. Vous créez le **Bloc** (ce qui existe dans le monde)
> 2. Vous créez automatiquement le **BlockItem** (version inventaire du bloc)
> 3. Le système les lie ensemble automatiquement
>
> **💡 Ce que vous pouvez faire :**
> - Blocs décoratifs (murs de château, trônes)
> - Blocs fonctionnels (four custom, table d'alchimie)
> - Blocs lumineux (lanternes, cristaux magiques)
> - Blocs transparents (vitraux)
> - Blocs interactifs (coffres, portes secrètes)

### 2.1 Bloc Simple

**📝 Étapes pour créer un bloc :**
1. Enregistrer le bloc dans ModBlocks.java
2. Le BlockItem est créé automatiquement
3. Créer les textures et modèles
4. Ajouter les traductions

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

> **📖 Comment fonctionnent les armures dans Minecraft ?**
> Une armure complète = 4 pièces (casque, plastron, jambières, bottes)
> Chaque pièce a ses propres valeurs de protection et durabilité.
>
> **🎯 Le système de matériaux :**
> Au lieu de créer 4 items séparés avec des valeurs en dur, Minecraft utilise un système de **Matériau**.
> Le matériau définit TOUTES les propriétés une seule fois, puis vous créez les 4 pièces qui l'utilisent.
>
> **🔧 Processus en 2 étapes :**
>
> **ÉTAPE 1 : Créer le Matériau** (ex: "KNIGHT")
> - Protection de chaque pièce (casque=2, plastron=6, etc.)
> - Durabilité totale
> - Enchantabilité (facilité à enchanter)
> - Son d'équipement
> - Matériau de réparation (quoi utiliser sur l'enclume)
> - Texture de l'armure portée
>
> **ÉTAPE 2 : Créer les 4 Pièces**
> - Chaque pièce utilise le matériau créé à l'étape 1
> - Elles héritent automatiquement de toutes ses propriétés
>
> **💡 Avantage :** Si vous voulez changer la protection, vous modifiez UNE ligne dans le matériau,
> et les 4 pièces sont mises à jour automatiquement !

### 3.1 Créer un Matériau d'Armure

**📝 Ce fichier définit les caractéristiques générales de votre armure :**

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

> **📖 Comment fonctionnent les outils dans Minecraft ?**
> Les outils = pioche, hache, pelle, houe, épée
> Chaque outil a un **Tier** (niveau de matériau) qui définit ses caractéristiques.
>
> **🎯 Le système de Tiers (Niveaux de Matériaux) :**
>
> Dans Minecraft Vanilla :
> - **Bois** (Tier 0) : Mine pierre, durabilité 59
> - **Pierre** (Tier 1) : Mine fer, durabilité 131
> - **Fer** (Tier 2) : Mine diamant, durabilité 250
> - **Diamant** (Tier 3) : Mine obsidienne, durabilité 1561
> - **Netherite** (Tier 4) : Mine tout, durabilité 2031
>
> **🔧 Propriétés d'un Tier custom :**
>
> **1. Mining Level** (niveau d'extraction)
> - Définit QUELS blocs l'outil peut miner
> - Tag de bloc : "needs_iron_tool", "needs_diamond_tool", etc.
> - Si votre pioche a un mining level trop bas, le bloc ne drop rien
>
> **2. Durability** (durabilité)
> - Combien d'utilisations avant de casser
> - 1 utilisation = casser 1 bloc ou frapper 1 fois
>
> **3. Speed** (vitesse de minage)
> - Multiplicateur de vitesse
> - 4.0 = bois, 6.0 = pierre, 8.0 = fer, 10.0 = netherite
> - Plus c'est haut, plus c'est rapide
>
> **4. Attack Damage Bonus**
> - Dégâts supplémentaires pour les armes
> - Pour pioche/hache utilisée comme arme
>
> **5. Enchantment Value** (enchantabilité)
> - Facilité à obtenir de bons enchantements
> - Or = 22 (très enchantable mais fragile)
> - Diamant = 10
> - Plus c'est haut, meilleurs sont les enchantements possibles
>
> **6. Repair Ingredient** (matériau de réparation)
> - Quel item utiliser dans l'enclume pour réparer
> - Ex: lingot de fer pour outils en fer
>
> **💡 Créer un set d'outils custom :**
> 1. Créer le TIER avec toutes les propriétés
> 2. Créer les 5 outils qui utilisent ce tier
> 3. Ils héritent automatiquement des caractéristiques !

### 4.1 Créer un Tier d'Outil Custom

**📝 Ce fichier définit les propriétés de votre matériau d'outil :**

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

## 5. 💬 Affichage de Messages et Notifications

> **📖 Pourquoi communiquer avec le joueur ?**
> Dans un mod RP, l'immersion est primordiale. Le joueur doit être informé de ce qui se passe :
> - Quand il level up
> - Quand une quête est terminée
> - Quand il manque une condition
> - Pour afficher sa progression
>
> **🎯 Les 4 méthodes de notification dans Minecraft :**
>
> **1. TITRE (Centre écran)** 📺
> - Grand texte au milieu de l'écran
> - Impossible à rater
> - Usage : Événements MAJEURS (victoire boss, level up important)
> - Durée : 3-5 secondes
>
> **2. ACTION BAR (Au-dessus hotbar)** 📊
> - Petite ligne de texte juste au-dessus de la barre d'objets
> - Reste visible jusqu'à ce qu'on le change
> - Usage : Informations CONTINUES (progression, jauge)
> - Parfait pour : Barres de progression en temps réel
>
> **3. TOAST/ACHIEVEMENT (Popup haut-droite)** 🏆
> - Petite fenêtre qui slide depuis le coin
> - Style "succès débloqué" de Minecraft
> - Usage : Notifications, déblocages, récompenses
> - Durée : 5 secondes, puis disparaît
>
> **4. POPUP PERSONNALISÉE (Image custom)** 🎨
> - Fenêtre complètement personnalisée avec votre propre design
> - Peut être placée N'IMPORTE OÙ sur l'écran
> - Usage : Menus spéciaux, annonces importantes
> - Total contrôle sur l'apparence
>
> **🔧 Côté Client vs Serveur :**
> **IMPORTANT :** Ces affichages se font côté CLIENT (l'écran du joueur)
> Si vous êtes sur un serveur, il faut envoyer depuis le serveur vers le client.
>
> **💡 Quand utiliser quoi ?**
> - Level up important → **Titre** + **Toast**
> - Progression continue → **Action Bar**
> - Quête terminée → **Toast** ou **Popup**
> - Avertissement urgent → **Titre**
> - Statistiques temps réel → **Action Bar**

### 5.1 📺 Titre au Centre de l'Écran

**📝 Envoie un grand message au centre qui s'affiche puis disparaît en fondu :**

**Afficher un titre + sous-titre au milieu de l'écran (comme "Bienvenue")**

```java
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

// Dans votre code (événement, commande, etc.)
public static void showTitle(ServerPlayer player, String title, String subtitle) {
    // Texte du titre
    player.connection.send(new ClientboundSetTitleTextPacket(
        Component.literal("§6§l" + title)
    ));
    
    // Texte du sous-titre
    player.connection.send(new ClientboundSetSubtitleTextPacket(
        Component.literal("§7" + subtitle)
    ));
    
    // Durée d'affichage (fadeIn, stay, fadeOut en ticks)
    player.connection.send(new ClientboundSetTitlesAnimationPacket(
        10,   // Fade in (0.5 sec)
        70,   // Temps d'affichage (3.5 sec)
        20    // Fade out (1 sec)
    ));
}

// Exemple d'utilisation
showTitle(player, "NIVEAU AUGMENTÉ !", "Vous êtes maintenant niveau 5");
```

**Exemples de titres stylisés :**

```java
// Titre de réussite
showTitle(player, "✓ SUCCÈS !", "Quête terminée");

// Titre d'avertissement
player.connection.send(new ClientboundSetTitleTextPacket(
    Component.literal("§c§l⚠ ATTENTION !")));
player.connection.send(new ClientboundSetSubtitleTextPacket(
    Component.literal("§7Un boss approche...")));

// Titre animé avec couleurs
player.connection.send(new ClientboundSetTitleTextPacket(
    Component.literal("§d§k||§r §5§lROYAUTÉ §d§k||")));
```

### 5.2 📊 Action Bar (Au-dessus de la Hotbar)

**Afficher un message au-dessus de la barre d'objets (persiste et se met à jour)**

```java
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;

// Message simple
public static void showActionBar(ServerPlayer player, String message) {
    player.connection.send(new ClientboundSetActionBarTextPacket(
        Component.literal(message)
    ));
}

// Exemple d'utilisation
showActionBar(player, "§aBlocs cassés: §f50§7/§f100");
```

**Utilisation courante - Barre de progression en temps réel :**

```java
// Dans votre event de casse de bloc
@SubscribeEvent
public static void onBlockBreak(BlockEvent.BreakEvent event) {
    if (event.getPlayer() instanceof ServerPlayer player) {
        ItemStack item = player.getMainHandItem();
        
        int progress = getBlocksBroken(item);
        int needed = getBlocksNeeded(item);
        
        // Créer une barre visuelle
        String bar = createProgressBar(progress, needed);
        
        // Afficher dans l'action bar
        showActionBar(player, "§6Progression: " + bar + " §f" + progress + "§7/§f" + needed);
    }
}

private static String createProgressBar(int current, int max) {
    int percentage = (int)((double)current / max * 100);
    int bars = percentage / 5;  // 20 barres max
    
    StringBuilder result = new StringBuilder("§a[");
    for (int i = 0; i < 20; i++) {
        if (i < bars) {
            result.append("§a|");
        } else {
            result.append("§7|");
        }
    }
    result.append("§a]");
    return result.toString();
}
```

**Action bar avec mise à jour continue :**

```java
// Dans inventoryTick de votre item
@Override
public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
    if (!level.isClientSide() && isSelected && entity instanceof ServerPlayer player) {
        // Mettre à jour l'action bar chaque seconde
        if (level.getGameTime() % 20 == 0) {
            int blocksLeft = getBlocksNeeded(stack) - (getBlocksBroken(stack) % getBlocksNeeded(stack));
            showActionBar(player, "§7Il reste §e" + blocksLeft + " blocs §7avant le niveau suivant");
        }
    }
}
```

### 5.3 🏆 Toast/Advancement (Popup en Haut à Droite)

**Afficher une notification style achievement qui slide depuis le côté**

#### 5.3.1 Toast Simple (Sans Achievement)

```java
import net.minecraft.advancements.AdvancementType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

// Créer un toast personnalisé
public static void showToast(ServerPlayer player, String title, String description, ItemStack icon) {
    // Utiliser le système de chat pour simuler (méthode simple)
    player.sendSystemMessage(Component.literal("§6[Notification] §f" + title));
    player.sendSystemMessage(Component.literal("§7" + description));
}
```

#### 5.3.2 Advancement Personnalisé (Vrai Toast)

**Créer :** `src/main/resources/data/medeliummod/advancements/notifications/level_up.json`

```json
{
  "display": {
    "icon": {
      "id": "minecraft:diamond_pickaxe"
    },
    "title": {
      "text": "Niveau Augmenté !",
      "color": "gold",
      "bold": true
    },
    "description": {
      "text": "Vous avez atteint le niveau 5",
      "color": "gray"
    },
    "frame": "goal",
    "show_toast": true,
    "announce_to_chat": false,
    "hidden": false,
    "background": "minecraft:textures/gui/advancements/backgrounds/stone.png"
  },
  "criteria": {
    "requirement": {
      "trigger": "minecraft:impossible"
    }
  }
}
```

**Déclencher l'advancement via code :**

```java
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;

public static void grantAdvancement(ServerPlayer player, String advancementId) {
    ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, advancementId);
    AdvancementHolder advancement = player.server.getAdvancements().get(id);
    
    if (advancement != null) {
        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
        if (!progress.isDone()) {
            for (String criterion : progress.getRemainingCriteria()) {
                player.getAdvancements().award(advancement, criterion);
            }
        }
    }
}

// Utilisation
grantAdvancement(player, "notifications/level_up");
```

#### 5.3.3 Toast Complètement Personnalisé (avec Image)

**Créer une classe de Toast personnalisée :**

```java
package com.medelium.client.toast;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CustomToast implements Toast {
    private static final ResourceLocation BACKGROUND_IMAGE = 
        ResourceLocation.fromNamespaceAndPath("medeliummod", "textures/gui/toast.png");
    
    private final Component title;
    private final Component description;
    private final ItemStack icon;
    private long firstDisplay;
    private boolean newDisplay;

    public CustomToast(Component title, Component description, ItemStack icon) {
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    @Override
    public Visibility render(GuiGraphics guiGraphics, ToastComponent toastComponent, long timeSinceLastVisible) {
        if (this.newDisplay) {
            this.firstDisplay = timeSinceLastVisible;
            this.newDisplay = false;
        }

        // Dessiner le fond (160x32 pixels)
        guiGraphics.blit(BACKGROUND_IMAGE, 0, 0, 0, 0, 160, 32, 160, 32);

        // Dessiner l'icône
        guiGraphics.renderFakeItem(this.icon, 8, 8);

        // Dessiner le titre
        guiGraphics.drawString(toastComponent.getMinecraft().font, 
            this.title, 30, 7, 0xFFFF00, false);

        // Dessiner la description
        guiGraphics.drawString(toastComponent.getMinecraft().font, 
            this.description, 30, 18, 0xFFFFFF, false);

        // Durée d'affichage : 5 secondes
        return timeSinceLastVisible - this.firstDisplay >= 5000L ? Visibility.HIDE : Visibility.SHOW;
    }

    // Afficher le toast
    public static void show(Component title, Component description, ItemStack icon) {
        net.minecraft.client.Minecraft.getInstance().getToasts().addToast(
            new CustomToast(title, description, icon)
        );
    }
}
```

**Créer l'image du toast :** `textures/gui/toast.png` (160x32 pixels)

**Utiliser le toast :**

```java
// Côté client uniquement !
if (level.isClientSide()) {
    CustomToast.show(
        Component.literal("§6§lNiveau 5 Atteint !"),
        Component.literal("§7Rapidité débloquée"),
        new ItemStack(ModItems.ROYAL_SWORD.get())
    );
}
```

**Envoyer du serveur au client :**

```java
// Créer un packet réseau (système avancé)
// Ou utiliser l'advancement system ci-dessus (plus simple)
```

### 5.4 🎨 Popup avec Image Personnalisée

**Créer un écran overlay qui s'affiche temporairement**

#### 5.4.1 Popup avec Position Personnalisable

```java
package com.medelium.client.screen;

import com.medelium.MedeliumMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class PopupScreen extends Screen {
    private static final ResourceLocation POPUP_TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, "textures/gui/popup.png");
    
    private final int displayTime;
    private int ticksDisplayed = 0;
    private final PopupPosition position;
    private final int popupWidth = 200;
    private final int popupHeight = 100;

    // Enum pour les positions
    public enum PopupPosition {
        CENTER,           // Centre de l'écran
        TOP_LEFT,         // Coin haut gauche
        TOP_RIGHT,        // Coin haut droit
        BOTTOM_LEFT,      // Coin bas gauche
        BOTTOM_RIGHT,     // Coin bas droit
        TOP_CENTER,       // Centre en haut
        BOTTOM_CENTER,    // Centre en bas
        LEFT_CENTER,      // Centre à gauche
        RIGHT_CENTER,     // Centre à droite
        CUSTOM            // Position personnalisée
    }
    
    private int customX = 0;
    private int customY = 0;

    public PopupScreen(int displayTimeInTicks, PopupPosition position) {
        super(Component.empty());
        this.displayTime = displayTimeInTicks;
        this.position = position;
    }
    
    public PopupScreen(int displayTimeInTicks, int customX, int customY) {
        super(Component.empty());
        this.displayTime = displayTimeInTicks;
        this.position = PopupPosition.CUSTOM;
        this.customX = customX;
        this.customY = customY;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Calculer la position selon le choix
        int x = calculateX();
        int y = calculateY();
        
        // Fond semi-transparent (optionnel)
        // guiGraphics.fill(0, 0, this.width, this.height, 0x80000000);
        
        // Image de popup
        guiGraphics.blit(POPUP_TEXTURE, x, y, 0, 0, popupWidth, popupHeight, 256, 256);
        
        // Texte personnalisé (centré dans la popup)
        guiGraphics.drawCenteredString(this.font, 
            "§6§lNIVEAU AUGMENTÉ !", 
            x + popupWidth / 2, y + 30, 0xFFFFFF);
        
        guiGraphics.drawCenteredString(this.font, 
            "§7Vous êtes maintenant niveau 5", 
            x + popupWidth / 2, y + 50, 0xAAAAAA);
    }
    
    private int calculateX() {
        return switch (position) {
            case CENTER -> (this.width - popupWidth) / 2;
            case TOP_LEFT, BOTTOM_LEFT, LEFT_CENTER -> 10;  // Marge de 10px
            case TOP_RIGHT, BOTTOM_RIGHT, RIGHT_CENTER -> this.width - popupWidth - 10;
            case TOP_CENTER, BOTTOM_CENTER -> (this.width - popupWidth) / 2;
            case CUSTOM -> customX;
        };
    }
    
    private int calculateY() {
        return switch (position) {
            case CENTER -> (this.height - popupHeight) / 2;
            case TOP_LEFT, TOP_RIGHT, TOP_CENTER -> 10;  // Marge de 10px
            case BOTTOM_LEFT, BOTTOM_RIGHT, BOTTOM_CENTER -> this.height - popupHeight - 10;
            case LEFT_CENTER, RIGHT_CENTER -> (this.height - popupHeight) / 2;
            case CUSTOM -> customY;
        };
    }

    @Override
    public void tick() {
        super.tick();
        ticksDisplayed++;
        
        if (ticksDisplayed >= displayTime) {
            this.onClose();
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
    
    // Méthodes statiques pour afficher facilement
    public static void show(int durationTicks, PopupPosition position) {
        Minecraft.getInstance().setScreen(new PopupScreen(durationTicks, position));
    }
    
    public static void showCenter(int durationTicks) {
        show(durationTicks, PopupPosition.CENTER);
    }
    
    public static void showTopRight(int durationTicks) {
        show(durationTicks, PopupPosition.TOP_RIGHT);
    }
    
    public static void showBottomLeft(int durationTicks) {
        show(durationTicks, PopupPosition.BOTTOM_LEFT);
    }
    
    // Position personnalisée (coordonnées exactes)
    public static void showCustom(int durationTicks, int x, int y) {
        Minecraft.getInstance().setScreen(new PopupScreen(durationTicks, x, y));
    }
}
```

#### 5.4.2 Exemples d'Utilisation

```java
// Centre de l'écran
PopupScreen.showCenter(60);

// Coin haut droit
PopupScreen.showTopRight(60);

// Coin bas gauche
PopupScreen.showBottomLeft(60);

// Position spécifique
PopupScreen.show(60, PopupScreen.PopupPosition.BOTTOM_CENTER);

// Coordonnées exactes (100 pixels de gauche, 50 de haut)
PopupScreen.showCustom(60, 100, 50);
```

#### 5.4.3 Popup avec Animation de Slide

**Popup qui slide depuis un côté :**

```java
public class AnimatedPopupScreen extends Screen {
    private static final ResourceLocation POPUP_TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, "textures/gui/popup.png");
    
    private final int displayTime;
    private int ticksDisplayed = 0;
    private final SlideDirection direction;
    private final int popupWidth = 200;
    private final int popupHeight = 100;
    
    public enum SlideDirection {
        FROM_LEFT, FROM_RIGHT, FROM_TOP, FROM_BOTTOM
    }
    
    public AnimatedPopupScreen(int displayTimeInTicks, SlideDirection direction) {
        super(Component.empty());
        this.displayTime = displayTimeInTicks;
        this.direction = direction;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Position finale (centre droit par exemple)
        int targetX = this.width - popupWidth - 10;
        int targetY = (this.height - popupHeight) / 2;
        
        // Calculer l'animation (0.0 à 1.0)
        float animationProgress = Math.min((float)ticksDisplayed / 20f, 1.0f);  // 1 seconde pour entrer
        
        // Position actuelle avec animation
        int x = getAnimatedX(targetX, animationProgress);
        int y = getAnimatedY(targetY, animationProgress);
        
        // Dessiner la popup
        guiGraphics.blit(POPUP_TEXTURE, x, y, 0, 0, popupWidth, popupHeight, 256, 256);
        
        // Texte seulement si l'animation est presque finie
        if (animationProgress > 0.5f) {
            guiGraphics.drawCenteredString(this.font, 
                "§6§lNOTIFICATION", 
                x + popupWidth / 2, y + 30, 0xFFFFFF);
        }
    }
    
    private int getAnimatedX(int targetX, float progress) {
        return switch (direction) {
            case FROM_LEFT -> (int)(targetX * progress - popupWidth * (1 - progress));
            case FROM_RIGHT -> (int)(this.width - (this.width - targetX) * progress);
            case FROM_TOP, FROM_BOTTOM -> targetX;
        };
    }
    
    private int getAnimatedY(int targetY, float progress) {
        return switch (direction) {
            case FROM_TOP -> (int)(targetY * progress - popupHeight * (1 - progress));
            case FROM_BOTTOM -> (int)(this.height - (this.height - targetY) * progress);
            case FROM_LEFT, FROM_RIGHT -> targetY;
        };
    }

    @Override
    public void tick() {
        super.tick();
        ticksDisplayed++;
        if (ticksDisplayed >= displayTime) {
            this.onClose();
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
    
    public static void slideFromRight(int durationTicks) {
        Minecraft.getInstance().setScreen(
            new AnimatedPopupScreen(durationTicks, SlideDirection.FROM_RIGHT));
    }
}
```

#### 5.4.4 Popup Sans Bloquer le Jeu (Overlay)

**Popup qui s'affiche PAR DESSUS le jeu sans ouvrir de menu :**

```java
package com.medelium.client.overlay;

import com.medelium.MedeliumMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;

@EventBusSubscriber(modid = MedeliumMod.MOD_ID, bus = EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class PopupOverlay {
    private static final ResourceLocation POPUP_TEXTURE = 
        ResourceLocation.fromNamespaceAndPath(MedeliumMod.MOD_ID, "textures/gui/popup.png");
    
    private static boolean isVisible = false;
    private static int ticksRemaining = 0;
    private static int popupX = 0;
    private static int popupY = 0;
    private static String title = "";
    private static String subtitle = "";
    
    @SubscribeEvent
    public static void onRenderGui(RenderGuiOverlayEvent.Post event) {
        if (isVisible && ticksRemaining > 0) {
            GuiGraphics guiGraphics = event.getGuiGraphics();
            Minecraft mc = Minecraft.getInstance();
            
            // Dessiner la popup
            guiGraphics.blit(POPUP_TEXTURE, popupX, popupY, 0, 0, 200, 100, 256, 256);
            
            // Texte
            guiGraphics.drawCenteredString(mc.font, title, 
                popupX + 100, popupY + 30, 0xFFFFFF);
            guiGraphics.drawCenteredString(mc.font, subtitle, 
                popupX + 100, popupY + 50, 0xAAAAAA);
            
            ticksRemaining--;
            
            if (ticksRemaining <= 0) {
                isVisible = false;
            }
        }
    }
    
    // Afficher la popup à une position
    public static void show(String title, String subtitle, int x, int y, int durationTicks) {
        PopupOverlay.title = title;
        PopupOverlay.subtitle = subtitle;
        PopupOverlay.popupX = x;
        PopupOverlay.popupY = y;
        PopupOverlay.ticksRemaining = durationTicks;
        PopupOverlay.isVisible = true;
    }
    
    // Méthodes pratiques
    public static void showTopRight(String title, String subtitle, int durationTicks) {
        Minecraft mc = Minecraft.getInstance();
        show(title, subtitle, mc.getWindow().getGuiScaledWidth() - 210, 10, durationTicks);
    }
    
    public static void showBottomCenter(String title, String subtitle, int durationTicks) {
        Minecraft mc = Minecraft.getInstance();
        int x = (mc.getWindow().getGuiScaledWidth() - 200) / 2;
        int y = mc.getWindow().getGuiScaledHeight() - 110;
        show(title, subtitle, x, y, durationTicks);
    }
    
    public static void showCenter(String title, String subtitle, int durationTicks) {
        Minecraft mc = Minecraft.getInstance();
        int x = (mc.getWindow().getGuiScaledWidth() - 200) / 2;
        int y = (mc.getWindow().getGuiScaledHeight() - 100) / 2;
        show(title, subtitle, x, y, durationTicks);
    }
}
```

**Utiliser l'overlay :**

```java
// Depuis n'importe où (client-side)
PopupOverlay.showTopRight("§6Level Up!", "§7Niveau 5 atteint", 100);
PopupOverlay.showBottomCenter("§aQuête terminée", "§7+100 pièces", 80);
PopupOverlay.showCenter("§c§lATTENTION", "§7Boss proche", 120);

// Position personnalisée
PopupOverlay.show("§bInfo", "§7Message", 50, 50, 60);
```

**Avantage de l'overlay :** Le jeu continue normalement, pas de pause ni de changement d'écran !

#### 5.4.5 Positions Prédéfinies - Référence Visuelle

```
Écran de jeu (exemple 1920x1080 scaled)
┌────────────────────────────────────────┐
│ TOP_LEFT    TOP_CENTER      TOP_RIGHT  │
│    📦           📦              📦      │
│                                        │
│                                        │
│ LEFT_CENTER    CENTER    RIGHT_CENTER │
│    📦           📦              📦      │
│                                        │
│                                        │
│ BOTTOM_LEFT  BOTTOM_CENTER BOTTOM_RIGHT│
│    📦           📦              📦      │
└────────────────────────────────────────┘

Coordonnées utiles :
- Marge standard : 10px
- Popup standard : 200x100px
- Centre X : (screenWidth - 200) / 2
- Centre Y : (screenHeight - 100) / 2
```

### 5.5 📋 Récapitulatif des Méthodes

| Méthode | Position | Durée | Complexité | Usage Recommandé |
|---------|----------|-------|------------|------------------|
| **Title** | Centre écran | 3-5 sec | ⭐ Facile | Events majeurs (boss battu, level up) |
| **Action Bar** | Au-dessus hotbar | Jusqu'à update | ⭐ Facile | Informations continues (progression) |
| **Toast** | Haut droite | 5 sec | ⭐⭐ Moyen | Notifications (réussite, déblocage) |
| **Popup** | Centre écran | Variable | ⭐⭐⭐ Avancé | Events spéciaux avec image |

### 5.6 💡 Exemples d'Utilisation Pratiques

**Lors d'un level up :**

```java
private static void levelUp(Player player, ItemStack item, int newLevel) {
    if (player instanceof ServerPlayer serverPlayer) {
        // 1. Action bar pour confirmation rapide
        showActionBar(serverPlayer, "§a✓ Niveau " + newLevel + " atteint !");
        
        // 2. Titre dramatique
        showTitle(serverPlayer, "NIVEAU " + newLevel, "Continuez ainsi !");
        
        // 3. Toast avec l'item
        grantAdvancement(serverPlayer, "notifications/level_up");
        
        // 4. Son + Particules
        serverPlayer.level().playSound(null, serverPlayer.blockPosition(), 
            SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
```

**Progression en temps réel :**

```java
// Mise à jour continue dans action bar
@Override
public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
    if (!level.isClientSide() && isSelected && entity instanceof ServerPlayer player) {
        if (level.getGameTime() % 10 == 0) {  // Update toutes les 0.5 sec
            int progress = getBlocksBroken(stack) % getBlocksNeeded(stack);
            int needed = getBlocksNeeded(stack);
            String bar = createProgressBar(progress, needed);
            
            showActionBar(player, "§6⛏ " + bar + " §f" + progress + "§7/§f" + needed);
        }
    }
}
```

**Système de quêtes :**

```java
public static void completeQuest(ServerPlayer player, String questName) {
    // Titre
    showTitle(player, "✓ QUÊTE TERMINÉE", questName);
    
    // Toast
    CustomToast.show(
        Component.literal("§aQuête terminée !"),
        Component.literal("§7" + questName),
        new ItemStack(Items.EMERALD)
    );
    
    // Récompense dans chat
    player.sendSystemMessage(Component.literal("§6+100 pièces d'or"));
}
```

---

## 6. 🍖 Nourriture et Potions

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

> **📖 Différence entre Mob normal et Boss :**
>
> **Mob Normal :**
> - 20-40 HP
> - Comportement simple
> - Pas de barre de vie visible
> - Drops basiques
>
> **BOSS :**
> - 200-500+ HP
> - Barre de vie en haut de l'écran (comme l'Ender Dragon)
> - Phases de combat (change de comportement selon sa vie)
> - Immunités spéciales
> - Attaques puissantes et variées
> - Drops légendaires
> - Particules et effets visuels
>
> **🎯 Éléments qui font un bon boss :**
>
> **1. Barre de Vie (Boss Bar)**
> - Affichée en haut pour tous les joueurs proches
> - Change de couleur selon la phase
> - Nom personnalisé stylisé
>
> **2. Système de Phases**
> - 100% HP = Phase 1 (lent, attaques basiques)
> - 50% HP = Phase 2 (plus rapide, attaques spéciales)
> - 25% HP = Phase 3 (mode berserk, très dangereux)
>
> **3. Résistances**
> - Immunisé au recul (knockback resistance = 1.0)
> - Immunisé à certains effets (poison, wither)
> - Armure naturelle élevée
>
> **4. Mécaniques Spéciales**
> - Tir de projectiles
> - Invocation de mobs
> - Zones de dégâts au sol
> - Téléportation
>
> **5. Récompenses Épiques**
> - Items légendaires uniques
> - XP massive (500+)
> - Achievements
>
> **🔧 Comment créer un boss (étapes) :**
> 1. Créer la classe qui hérite de Monster (pas PathfinderMob)
> 2. Créer le ServerBossEvent (barre de vie)
> 3. Définir les attributs (HP, dégâts, armure)
> 4. Programmer les phases de combat
> 5. Ajouter les attaques spéciales
> 6. Configurer les immunités
> 7. Définir les loots
>
> **💡 Astuce :** Commencez simple, puis ajoutez les mécaniques une par une en testant !

#### 6.3.1 Classe de Boss avec Barre de Vie

**📝 Cette classe crée un boss complet avec barre de vie visible :**

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

> **📖 C'est quoi un Mini-Boss ?**
> Entre un mob normal et un boss complet :
> - Plus de vie qu'un mob normal (100-200 HP)
> - Pas de barre visible en haut (mais peut avoir un healthbar custom)
> - Phases de combat simplifiées (2-3 phases max)
> - Drops meilleurs que les mobs normaux
> - Peut spawner dans le monde naturellement (rareté)
>
> **🎯 Différences Mini-Boss vs Boss complet :**
>
> | Caractéristique | Mini-Boss | Boss Complet |
> |-----------------|-----------|-------------|
> | HP | 100-200 | 300-1000+ |
> | Barre visible | ❌ Non | ✅ Oui (en haut) |
> | Phases | 2-3 | 3-5+ |
> | Spawn naturel | ✅ Oui (rare) | ❌ Non (invoqué) |
> | Difficulté | Moyen | Très dur |
> | Temps de combat | 30-60s | 3-10min |
>
> **🔧 Système de Phases Simplifié :**
>
> **Phase 1 (100%-50% HP) :**
> - Attaques de mêlée normales
> - Vitesse normale
> - Pas d'effets spéciaux
>
> **Phase 2 (50%-0% HP) :**
> - Speed boost (+30%)
> - Commence à lancer des projectiles
> - Effets de particules
> - Son spécial
>
> **💡 Exemples d'utilisation :**
> - **Chevalier Noir** : Spawn dans les donjons, 150 HP, 2 phases
> - **Loup-Garou** : Spawn la nuit en forêt, 120 HP, devient enragé à 50%
> - **Bandit Chef** : Spawn dans les camps, 100 HP, appelle des renforts à 30%
>
> **🎮 Le code ci-dessous montre :**
> - Comment détecter le changement de phase
> - Comment modifier le comportement selon la phase
> - Comment ajouter des effets visuels et sonores
> - Comment donner de meilleurs loots

**📝 Classe de Mini-Boss avec phases de combat :**

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

> **📖 C'est quoi une Structure ?**
> Une structure = construction qui spawn naturellement dans le monde
> Exemples Vanilla : villages, temples, donjons, forteresses
>
> **🎯 Structures pour votre mod médiéval :**
> - 🏰 Châteaux abandonnés
> - ⛪ Chapelles
> - 🏪 Tavernes
> - 🏗️ Ruines anciennes
> - ⛺ Camps de bandits
> - 🔮 Tours de mage
>
> **🔧 Comment créer une structure (étapes) :**
>
> **1. Construire en jeu**
> - Construisez votre structure en mode créatif
> - Utilisez le bloc "Structure Block" de Minecraft
> - Sauvegardez la structure (.nbt)
>
> **2. Exporter le fichier**
> - Le fichier .nbt va dans `resources/data/medeliummod/structures/`
>
> **3. Créer le système de spawn**
> - Code Java qui dit OÙ et QUAND la structure spawn
> - Définir : biomes, rareté, distance entre structures
>
> **4. Enregistrer**
> - Enregistrer le type de structure dans le mod
>
> **💡 IMPORTANT :**
> Les structures sont COMPLEXES pour les débutants.
> Recommandation : Commencez par des structures simples (petite ruine)
> Puis augmentez la complexité (grand château avec plusieurs salles)

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

> **📖 C'est quoi une Dimension ?**
> Une dimension = monde parallèle complètement séparé
> Exemples Vanilla : Nether, End, Overworld
>
> **🎯 Dimensions pour mod médiéval fantasy :**
> - 🌌 **Royaume des Fées** (forêt enchantée, ciel rose)
> - 🔥 **Enfers** (lave, démons, feu partout)
> - ❄️ **Terres Gelées** (glace, neige infinie)
> - 🌃 **Plan Astéral** (flottant dans l'espace, cristaux)
> - 🌲 **Forêt Ancienne** (arbres géants, ruines elfiques)
>
> **🔧 Ce qu'implique créer une dimension :**
>
> **1. Générateur de terrain**
> - Définir comment le monde se génère
> - Blocs par défaut (pierre, terre, etc.)
> - Hauteur du monde
>
> **2. Système de Biomes**
> - Quels biomes existent dans cette dimension
> - Leur distribution
>
> **3. Portail d'accès**
> - Comment le joueur y entre (portail custom)
> - Où il spawn en arrivant
>
> **4. Propriétés spéciales**
> - Couleur du ciel
> - Effet de brouillard
> - Mobs qui spawnent naturellement
> - Jour/nuit ou toujours nuit
> - Lit fonctionne ou explose
>
> **⚠️ ATTENTION : C'EST TRÈS AVANCÉ !**
> Créer une dimension complète demande :
> - Fichiers JSON complexes
> - Connaissance approfondie de la génération de monde
> - Beaucoup de tests
>
> **💡 Conseil pour débutants :**
> 1. Maîtrisez d'abord les items/blocs/entités
> 2. Puis tentez une dimension simple (juste un monde plat custom)
> 3. Ensuite compliquez progressivement

### 8.1 Créer une Dimension Personnalisée

**📝 Ce fichier JSON définit votre dimension et comment le terrain se génère :**

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

> **📖 Le fichier dimension_type définit les "règles physiques" de votre dimension :**
>
> **ultrawarm** = Est-ce que l'eau s'évapore (comme le Nether) ?
> **natural** = Est-ce un monde "naturel" (ciel, nuages, jour/nuit) ?
> **bed_works** = Est-ce que les lits fonctionnent ou explosent ?
> **has_skylight** = Y a-t-il de la lumière du ciel ?
> **coordinate_scale** = Ratio de distance (8.0 = comme le Nether)
> **ambient_light** = Luminosité ambiante (0.0 = noir, 1.0 = lumineux)
>
> **💡 Exemple :** Royaume Féerique = natural:true, ambient_light:0.8 (toujours lumineux)

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

> **📖 Comment fonctionnent les enchantements ?**
> Un enchantement = bonus magique sur un item (comme Tranchant, Fortune, etc.)
> S'obtient via table d'enchantement, enclume, ou livres enchantés
>
> **🎯 Enchantements pour mod médiéval :**
> - **Feu Sacré** : Épée qui inflige des dégâts supplémentaires aux morts-vivants
> - **Bénédiction** : Armure qui régénère la vie lentement
> - **Fortune du Marchand** : Outil qui double les drops de certains blocs
> - **Légèreté** : Bottes qui permettent de courir plus vite
> - **Âme de Dragon** : Arme qui stocke l'XP des kills
>
> **🔧 Propriétés d'un enchantement :**
>
> **1. Niveau Maximum**
> - Combien de niveaux l'enchantement a (I, II, III, etc.)
> - Ex: Fortune va jusqu'à III
>
> **2. Rareté**
> - COMMON : Facile à obtenir
> - UNCOMMON : Moyen
> - RARE : Difficile
> - VERY_RARE : Très rare
>
> **3. Compatibilité**
> - Sur quoi peut-il être appliqué (arme, armure, outil)
> - Quels enchantements sont incompatibles entre eux
>
> **4. Trésor**
> - Si true : Ne s'obtient QUE dans les coffres/loots
> - Si false : Table d'enchantement possible
>
> **💡 Application de l'effet :**
> L'enchantement ne fait rien tout seul !
> Il faut écouter des événements (attaque, dégâts reçus, etc.)
> Et checker si l'item a votre enchantement
> Puis appliquer l'effet custom

### 9.1 Créer un Enchantement Personnalisé

**📝 Ce fichier enregistre votre enchantement dans le jeu :**

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

> **📖 C'est quoi un Effet de Potion ?**
> Un effet = status temporaire sur le joueur (comme Régénération, Poison, Force)
> Peut être donné par : potions, nourriture, enchantements, zones, mobs
>
> **🎯 Effets pour mod médiéval :**
> - 🛡️ **Égide du Chevalier** : +20% de résistance aux dégâts
> - 🎯 **Précision de l'Archer** : Projectiles plus rapides et précis
> - ⚔️ **Rage du Barbare** : +50% dégâts mais -20% défense
> - 🧝 **Vision Magique** : Voir les coffres à travers les murs
> - 💉 **Peste Noire** : Perte de vie progressive + contagieux
> - 🌲 **Bénédiction de la Forêt** : Régénération en forêt uniquement
>
> **🔧 Types d'effets :**
>
> **BENEFIQUE (bleu) :**
> - Bon pour le joueur
> - Icône bleue
> - Ex: Force, Speed, Régénération
>
> **NUISIBLE (rouge) :**
> - Mauvais pour le joueur
> - Icône rouge
> - Ex: Poison, Faiblesse, Lenteur
>
> **NEUTRE (gris) :**
> - Ni bon ni mauvais
> - Ex: Vision nocturne, Invisibilité
>
> **💡 Comment programmer l'effet :**
> 1. Créer la classe de l'effet (ce qui se passe chaque tick)
> 2. L'enregistrer dans le jeu
> 3. Créer une icône 18x18 pixels
> 4. Appliquer l'effet via code : `player.addEffect(new MobEffectInstance(...))`
>
> **🎮 Application automatique :**
> - Manger une nourriture → Donne l'effet
> - Entrer dans une zone → Effet activé
> - Être touché par un mob → Effet appliqué
> - Porter une armure → Effet permanent

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

> **📖 C'est quoi un GUI (Graphical User Interface) ?**
> C'est un menu/fenêtre qui s'affiche à l'écran : inventaire, four, table de craft, etc.
> Dans votre mod RP, vous pouvez créer des interfaces pour TOUT :
> - Menu de quêtes
> - Shop de marchand
> - Système de métiers
> - Table d'alchimie
> - Livre de sorts
>
> **🎯 Deux approches pour créer un GUI :**
>
> **APPROCHE 1 : Composants Minecraft** (Complexe)
> - Utiliser les boutons, slots, et widgets de Minecraft
> - Beaucoup de code Java
> - Difficile à positionner exactement
> - Mais : Intégration native
>
> **APPROCHE 2 : Image Pure + Zones Cliquables** (Simple - ce qu'on va faire !)
> - Vous dessinez TOUTE l'interface dans un logiciel d'image (Paint, GIMP, etc.)
> - Vous définissez des zones rectangulaires cliquables dans le code
> - Total contrôle du design
> - Plus facile pour les débutants
>
> **🔧 Comment fonctionne l'approche "Image Pure" :**
>
> **ÉTAPE 1 : Dessiner l'image** (logiciel d'image)
> - Créer une image 176x166 pixels
> - Dessiner le fond, les boutons, le texte, tout !
> - Sauvegarder en PNG
>
> **ÉTAPE 2 : Créer le Menu** (Java)
> - Fichier qui dit "ce GUI existe"
> - Très simple si pas de slots d'inventaire
>
> **ÉTAPE 3 : Créer l'Écran** (Java)
> - Code qui affiche votre image
> - Définit où sont les zones cliquables
> - Exemple : "Si clic entre X=20-80 et Y=40-60 → action"
>
> **ÉTAPE 4 : Enregistrer** (Java)
> - Dire à Minecraft que ce GUI existe
> - Lier le menu et l'écran ensemble
>
> **💡 Avantage ÉNORME de cette méthode :**
> Vous modifiez juste l'image PNG pour changer le design !
> Pas besoin de retoucher le code si vous voulez changer les couleurs ou ajouter des décos.

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

## 18. 🔒 Systèmes de Conditions et Dépendances

> **📖 C'est quoi un système de conditions ?**
> C'est empêcher l'utilisation de quelque chose tant que le joueur n'a pas rempli certains critères.
> Comme un niveau minimum dans les MMO, ou une quête préalable dans les RPG.
>
> **🎯 Exemples concrets dans un mod RP médiéval :**
>
> **Four de Forgeron :**
> - ❌ Condition : Être forgeron niveau 10
> - ✅ Si oui → Peut l'utiliser
> - ❌ Si non → Message "Seuls les forgerons peuvent utiliser ça !"
>
> **Épée Légendaire :**
> - ❌ Condition 1 : Être niveau 50
> - ❌ Condition 2 : Avoir tué le dragon
> - ❌ Condition 3 : Être de classe Guerrier
> - ✅ Toutes remplies → L'épée fonctionne
> - ❌ Sinon → L'épée ne fait aucun dégât
>
> **Portail Dimensionnel :**
> - ❌ Condition : 10 diamants dans l'inventaire
> - ✅ Si oui → Portail s'active, diamants consommés
> - ❌ Si non → Rien ne se passe
>
> **🔧 Comment implémenter techniquement :**
>
> **1. Stocker les données du joueur**
> Utiliser le système d'Attachments de NeoForge
> → Sauvegarder : métier, niveau, XP, statistiques
> → Ces données survivent à la mort et déconnexion
>
> **2. Créer des vérifications**
> Avant d'exécuter une action (utiliser bloc, item, GUI), on check :
> ```java
> if (!joueur.aLeMetier("forgeron")) {
>     return ERREUR;
> }
> if (joueur.niveau < 10) {
>     return ERREUR;
> }
> // OK, action autorisée
> ```
>
> **3. Donner du feedback**
> Messages clairs : "❌ Vous devez être forgeron niveau 10"
> Sons d'erreur, effets visuels
>
> **4. Afficher les requis**
> Dans les tooltips : montrer ce qui est nécessaire
> Dans les GUIs : afficher les conditions
>
> **💡 Système de Métiers = La base :**
> On va d'abord créer un système de métiers (forgeron, alchimiste, etc.)
> Puis on l'utilisera pour conditionner l'accès aux blocs, items, et fonctionnalités.
>
> **🎮 Résultat en jeu :**
> - Les joueurs choisissent un métier avec `/profession set forgeron`
> - Ils gagnent de l'XP en faisant des actions liées
> - Montent de niveau pour débloquer du contenu
> - Créé de la progression et de la spécialisation (RP !)

### 18.1 💼 Système de Métiers/Professions

**📝 On va créer un système complet de métiers avec niveau et XP :**

**Créer un système où les joueurs choisissent un métier qui débloque certaines fonctionnalités**

#### 18.1.1 Créer la Classe de Données de Joueur

**Créer :** `src/main/java/com/medelium/capability/PlayerProfession.java`

```java
package com.medelium.capability;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class PlayerProfession implements INBTSerializable<CompoundTag> {
    private String profession = "none";  // forgeron, alchimiste, mineur, etc.
    private int level = 1;
    private int experience = 0;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
        this.level = 1;
        this.experience = 0;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void addExperience(int amount) {
        this.experience += amount;
        checkLevelUp();
    }

    private void checkLevelUp() {
        int xpNeeded = getXPNeededForLevel(level);
        if (experience >= xpNeeded) {
            experience -= xpNeeded;
            level++;
        }
    }

    private int getXPNeededForLevel(int currentLevel) {
        return 100 * currentLevel;  // 100, 200, 300, etc.
    }

    public boolean hasProfession(String profession) {
        return this.profession.equals(profession);
    }

    public boolean hasLevel(int minimumLevel) {
        return this.level >= minimumLevel;
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("profession", profession);
        nbt.putInt("level", level);
        nbt.putInt("experience", experience);
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.profession = nbt.getString("profession");
        this.level = nbt.getInt("level");
        this.experience = nbt.getInt("experience");
    }
}
```

#### 18.1.2 Attacher les Données au Joueur

**Créer :** `src/main/java/com/medelium/capability/ModCapabilities.java`

```java
package com.medelium.capability;

import com.medelium.MedeliumMod;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

@EventBusSubscriber(modid = MedeliumMod.MOD_ID)
public class ModCapabilities {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = 
        DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MedeliumMod.MOD_ID);

    public static final Supplier<AttachmentType<PlayerProfession>> PLAYER_PROFESSION = 
        ATTACHMENT_TYPES.register("player_profession", 
            () -> AttachmentType.serializable(PlayerProfession::new).build());

    // Récupérer les données d'un joueur
    public static PlayerProfession getProfession(Player player) {
        return player.getData(PLAYER_PROFESSION);
    }

    // Copier les données à la mort
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            PlayerProfession oldData = event.getOriginal().getData(PLAYER_PROFESSION);
            PlayerProfession newData = event.getEntity().getData(PLAYER_PROFESSION);
            
            newData.setProfession(oldData.getProfession());
            newData.setLevel(oldData.getLevel());
        }
    }
}
```

**Enregistrer dans MedeliumMod.java :**
```java
ModCapabilities.ATTACHMENT_TYPES.register(modEventBus);
```

#### 18.1.3 Commandes pour Gérer les Métiers

**Créer :** `src/main/java/com/medelium/command/ProfessionCommands.java`

```java
package com.medelium.command;

import com.medelium.MedeliumMod;
import com.medelium.capability.ModCapabilities;
import com.medelium.capability.PlayerProfession;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = MedeliumMod.MOD_ID)
public class ProfessionCommands {
    
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        
        // /profession set <métier>
        dispatcher.register(Commands.literal("profession")
            .then(Commands.literal("set")
                .then(Commands.argument("profession", StringArgumentType.word())
                    .suggests((context, builder) -> {
                        builder.suggest("forgeron");
                        builder.suggest("alchimiste");
                        builder.suggest("mineur");
                        builder.suggest("fermier");
                        builder.suggest("bucheron");
                        return builder.buildFuture();
                    })
                    .executes(ProfessionCommands::setProfession)))
            
            // /profession info
            .then(Commands.literal("info")
                .executes(ProfessionCommands::showInfo))
            
            // /profession addxp <montant>
            .then(Commands.literal("addxp")
                .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                    .executes(ProfessionCommands::addXP))));
    }
    
    private static int setProfession(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getEntity() instanceof ServerPlayer player) {
            String profession = StringArgumentType.getString(context, "profession");
            PlayerProfession data = ModCapabilities.getProfession(player);
            
            data.setProfession(profession);
            
            player.sendSystemMessage(Component.literal("§6Métier choisi: §f" + profession));
            player.sendSystemMessage(Component.literal("§7Vous commencez au niveau 1"));
        }
        return 1;
    }
    
    private static int showInfo(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getEntity() instanceof ServerPlayer player) {
            PlayerProfession data = ModCapabilities.getProfession(player);
            
            player.sendSystemMessage(Component.literal("§6=== Informations Métier ==="));
            player.sendSystemMessage(Component.literal("§7Métier: §f" + data.getProfession()));
            player.sendSystemMessage(Component.literal("§7Niveau: §a" + data.getLevel()));
            player.sendSystemMessage(Component.literal("§7Expérience: §e" + data.getExperience()));
        }
        return 1;
    }
    
    private static int addXP(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getEntity() instanceof ServerPlayer player) {
            int amount = IntegerArgumentType.getInteger(context, "amount");
            PlayerProfession data = ModCapabilities.getProfession(player);
            
            int oldLevel = data.getLevel();
            data.addExperience(amount);
            int newLevel = data.getLevel();
            
            player.sendSystemMessage(Component.literal("§a+§e" + amount + " XP"));
            
            if (newLevel > oldLevel) {
                player.sendSystemMessage(Component.literal("§6§l⬆ NIVEAU AUGMENTÉ !"));
                player.sendSystemMessage(Component.literal("§7Niveau §f" + oldLevel + " §7→ §a" + newLevel));
            }
        }
        return 1;
    }
}
```

### 18.2 🔒 Bloc avec Conditions d'Utilisation

**Créer un bloc (four, table de craft) qui nécessite un métier et un niveau spécifiques**

#### 18.2.1 Four Personnalisé avec Conditions

**Créer :** `src/main/java/com/medelium/block/custom/BlacksmithForgeBlock.java`

```java
package com.medelium.block.custom;

import com.medelium.capability.ModCapabilities;
import com.medelium.capability.PlayerProfession;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BlacksmithForgeBlock extends Block {
    
    private static final String REQUIRED_PROFESSION = "forgeron";
    private static final int REQUIRED_LEVEL = 5;
    
    public BlacksmithForgeBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            // Vérifier les conditions
            if (!checkConditions(serverPlayer)) {
                return InteractionResult.FAIL;
            }
            
            // Conditions remplies, ouvrir le GUI ou faire l'action
            serverPlayer.sendSystemMessage(Component.literal("§aForge utilisée avec succès !"));
            level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
            
            // Ici, ouvrir votre GUI de forge
            // serverPlayer.openMenu(...);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
    
    private boolean checkConditions(ServerPlayer player) {
        PlayerProfession data = ModCapabilities.getProfession(player);
        
        // Vérifier le métier
        if (!data.hasProfession(REQUIRED_PROFESSION)) {
            player.sendSystemMessage(Component.literal("§c✗ Métier requis: §f" + REQUIRED_PROFESSION));
            player.playNotifySound(SoundEvents.VILLAGER_NO, SoundSource.PLAYERS, 1.0F, 1.0F);
            return false;
        }
        
        // Vérifier le niveau
        if (!data.hasLevel(REQUIRED_LEVEL)) {
            player.sendSystemMessage(Component.literal("§c✗ Niveau requis: §f" + REQUIRED_LEVEL + 
                " §7(Vous êtes niveau " + data.getLevel() + ")"));
            player.playNotifySound(SoundEvents.VILLAGER_NO, SoundSource.PLAYERS, 1.0F, 1.0F);
            return false;
        }
        
        return true;
    }
}
```

#### 18.2.2 Afficher les Conditions dans le Tooltip du Bloc

**Créer un BlockItem personnalisé :**

```java
package com.medelium.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ConditionalBlockItem extends BlockItem {
    private final String requiredProfession;
    private final int requiredLevel;
    
    public ConditionalBlockItem(Block block, Properties properties, String profession, int level) {
        super(block, properties);
        this.requiredProfession = profession;
        this.requiredLevel = level;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("§cConditions requises:"));
        tooltipComponents.add(Component.literal("§7• Métier: §f" + requiredProfession));
        tooltipComponents.add(Component.literal("§7• Niveau: §f" + requiredLevel));
        
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
```

**Enregistrer le bloc avec le bon BlockItem :**

```java
// Dans ModBlocks.java
public static final DeferredBlock<Block> BLACKSMITH_FORGE = 
    BLOCKS.register("blacksmith_forge", () -> new BlacksmithForgeBlock(
        BlockBehaviour.Properties.of().strength(3.0f).requiresCorrectToolForDrops()));

// Dans ModItems.java - enregistrer le BlockItem spécial
public static final DeferredItem<Item> BLACKSMITH_FORGE_ITEM = 
    ITEMS.register("blacksmith_forge", () -> new ConditionalBlockItem(
        ModBlocks.BLACKSMITH_FORGE.get(), 
        new Item.Properties(),
        "forgeron",
        5
    ));
```

### 18.3 🛠️ Objet avec Conditions d'Utilisation

**Outil qui nécessite un métier spécifique**

```java
package com.medelium.item.custom;

import com.medelium.capability.ModCapabilities;
import com.medelium.capability.PlayerProfession;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ProfessionalPickaxeItem extends PickaxeItem {
    private static final String REQUIRED_PROFESSION = "mineur";
    private static final int REQUIRED_LEVEL = 10;
    
    public ProfessionalPickaxeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            PlayerProfession data = ModCapabilities.getProfession(player);
            
            if (!data.hasProfession(REQUIRED_PROFESSION) || !data.hasLevel(REQUIRED_LEVEL)) {
                player.sendSystemMessage(Component.literal("§c✗ Vous ne pouvez pas utiliser cet outil !"));
                player.sendSystemMessage(Component.literal("§7Requis: §f" + REQUIRED_PROFESSION + " niveau " + REQUIRED_LEVEL));
                level.playSound(null, player.blockPosition(), 
                    SoundEvents.VILLAGER_NO, SoundSource.PLAYERS, 1.0F, 1.0F);
                return InteractionResultHolder.fail(player.getItemInHand(hand));
            }
        }
        return super.use(level, player, hand);
    }
    
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.literal("§6Pioche Professionnelle"));
        tooltipComponents.add(Component.literal("§cRequis: §f" + REQUIRED_PROFESSION + " Niv." + REQUIRED_LEVEL));
        
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
```

### 18.4 🎯 GUI avec Vérification de Conditions

**Menu qui vérifie les conditions avant de s'ouvrir**

```java
package com.medelium.block.custom;

import com.medelium.capability.ModCapabilities;
import com.medelium.capability.PlayerProfession;
import com.medelium.screen.AlchemyTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class AlchemyTableBlock extends Block {
    
    public AlchemyTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            PlayerProfession data = ModCapabilities.getProfession(serverPlayer);
            
            // Vérifier métier
            if (!data.hasProfession("alchimiste")) {
                serverPlayer.sendSystemMessage(Component.literal("§c✗ Seuls les alchimistes peuvent utiliser cette table !"));
                return InteractionResult.FAIL;
            }
            
            // Vérifier niveau
            if (!data.hasLevel(3)) {
                serverPlayer.sendSystemMessage(Component.literal("§c✗ Niveau d'alchimie insuffisant !"));
                serverPlayer.sendSystemMessage(Component.literal("§7Niveau requis: §f3 §7(Vous: §f" + data.getLevel() + "§7)"));
                return InteractionResult.FAIL;
            }
            
            // Ouvrir le GUI
            serverPlayer.openMenu(new SimpleMenuProvider(
                (id, playerInv, p) -> new AlchemyTableMenu(id, playerInv),
                Component.literal("§5Table d'Alchimie")
            ));
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
```

### 18.5 ⚡ Système Multi-Conditions

**Vérifier plusieurs conditions en même temps**

```java
package com.medelium.util;

import com.medelium.capability.ModCapabilities;
import com.medelium.capability.PlayerProfession;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ConditionChecker {
    
    public static class Condition {
        private final String description;
        private final boolean met;
        
        public Condition(String description, boolean met) {
            this.description = description;
            this.met = met;
        }
        
        public boolean isMet() { return met; }
        public String getDescription() { return description; }
    }
    
    // Vérifier toutes les conditions et afficher le résultat
    public static boolean checkConditions(ServerPlayer player, List<Condition> conditions) {
        boolean allMet = true;
        
        for (Condition condition : conditions) {
            if (!condition.isMet()) {
                player.sendSystemMessage(Component.literal("§c✗ " + condition.getDescription()));
                allMet = false;
            }
        }
        
        return allMet;
    }
    
    // Builder pour créer des conditions facilement
    public static class Builder {
        private final ServerPlayer player;
        private final List<Condition> conditions = new ArrayList<>();
        
        public Builder(ServerPlayer player) {
            this.player = player;
        }
        
        public Builder profession(String profession) {
            PlayerProfession data = ModCapabilities.getProfession(player);
            conditions.add(new Condition(
                "Métier: " + profession,
                data.hasProfession(profession)
            ));
            return this;
        }
        
        public Builder level(int minLevel) {
            PlayerProfession data = ModCapabilities.getProfession(player);
            conditions.add(new Condition(
                "Niveau minimum: " + minLevel,
                data.hasLevel(minLevel)
            ));
            return this;
        }
        
        public Builder hasItem(ItemStack item, int amount) {
            boolean hasEnough = player.getInventory().countItem(item.getItem()) >= amount;
            conditions.add(new Condition(
                "Objet: " + item.getHoverName().getString() + " x" + amount,
                hasEnough
            ));
            return this;
        }
        
        public Builder customCondition(String description, boolean met) {
            conditions.add(new Condition(description, met));
            return this;
        }
        
        public boolean check() {
            return checkConditions(player, conditions);
        }
        
        public List<Condition> getConditions() {
            return conditions;
        }
    }
}
```

**Utiliser le builder :**

```java
// Dans votre bloc ou item
PlayerProfession data = ModCapabilities.getProfession(serverPlayer);

boolean canUse = new ConditionChecker.Builder(serverPlayer)
    .profession("forgeron")
    .level(10)
    .hasItem(new ItemStack(Items.DIAMOND), 3)
    .customCondition("Avoir tué le dragon", hasKilledDragon(serverPlayer))
    .check();

if (!canUse) {
    serverPlayer.sendSystemMessage(Component.literal("§cConditions non remplies !"));
    return InteractionResult.FAIL;
}
```

### 18.6 📊 Afficher les Conditions au Survol (Advanced)

**Tooltip dynamique selon si le joueur peut utiliser ou non**

```java
@Override
public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
    tooltipComponents.add(Component.literal(""));
    tooltipComponents.add(Component.literal("§6Conditions:"));
    
    // Note: Côté client, on ne peut pas accéder aux données serveur facilement
    // Donc soit on affiche juste les requis, soit on utilise un système de sync
    
    tooltipComponents.add(Component.literal("§7• Forgeron niveau 10"));
    tooltipComponents.add(Component.literal("§7• 3 diamants dans l'inventaire"));
    
    // Si on veut afficher si c'est rempli ou non, il faut synchroniser les données
    // avec un packet réseau (système avancé)
    
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
}
```

### 18.7 💡 Exemples d'Utilisation Pratiques

**Four de forgeron avancé :**
- Métier: Forgeron niveau 15
- Item requis: Charbon de bois x5
- Déblocage: Avoir crafté 100 outils

**Table d'enchantement améliorée :**
- Métier: Enchanteur niveau 20
- Item requis: Lapis x10, Livre enchanté x1
- Condition: Être dans un biome montagne

**Portail dimensionnel :**
- Multi-métiers: Mage niveau 30 OU Alchimiste niveau 40
- Item: Œil d'ender x12
- Condition: Phase de lune = pleine lune

---

## 19. 🎓 Systèmes de Compétences

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

---

## 19. 📜 Système de Quêtes Complet

> **📖 Pourquoi un système de quêtes ?**
> Les quêtes sont le CŒUR d'un mod RP. Elles donnent du sens aux actions, racontent une histoire, et motivent les joueurs.
>
> **🎯 Types de quêtes possibles :**
> - 📦 **Collecte** : Ramener X items
> - ⚔️ **Combat** : Tuer X mobs/boss
> - 🗣️ **Dialogue** : Parler à des PNJ
> - 🏃 **Exploration** : Découvrir des lieux
> - 🔨 **Craft** : Fabriquer des objets
> - 🎭 **Multi-étapes** : Combinaison de tout ça
>
> **🔧 Architecture d'un système de quêtes :**
>
> **1. La Quête** (Quest)
> - ID unique
> - Titre et description
> - Objectifs à accomplir
> - Récompenses
> - Conditions de démarrage
>
> **2. Les Objectifs** (Objectives)
> - Type d'objectif
> - Progression actuelle / max
> - Validation
>
> **3. Le Gestionnaire** (QuestManager)
> - Stocke les quêtes actives par joueur
> - Vérifie la progression
> - Distribue les récompenses
>
> **4. Le Journal** (Quest Log UI)
> - Affiche les quêtes en cours
> - Montre la progression
> - Permet d'abandonner
>
> **💡 Persistance :**
> Toutes les données de quête doivent être sauvegardées avec le joueur (NBT/Capability)
> pour survivre aux déconnexions et redémarrages serveur.

### 19.1 Structure de Base d'une Quête

**📝 Classe Quest - Représente une quête complète :**

```java
package com.medelium.quest;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class Quest {
    private final String id;
    private final Component title;
    private final Component description;
    private final List<QuestObjective> objectives;
    private final List<QuestReward> rewards;
    private final int minLevel;
    
    public Quest(String id, Component title, Component description, int minLevel) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.objectives = new ArrayList<>();
        this.rewards = new ArrayList<>();
        this.minLevel = minLevel;
    }
    
    public String getId() { return id; }
    public Component getTitle() { return title; }
    public Component getDescription() { return description; }
    public int getMinLevel() { return minLevel; }
    
    public Quest addObjective(QuestObjective objective) {
        objectives.add(objective);
        return this;
    }
    
    public Quest addReward(QuestReward reward) {
        rewards.add(reward);
        return this;
    }
    
    public List<QuestObjective> getObjectives() { return objectives; }
    public List<QuestReward> getRewards() { return rewards; }
    
    // Vérifier si toutes les objectifs sont complétés
    public boolean isCompleted(ServerPlayer player) {
        PlayerQuestData data = player.getData(ModAttachments.QUEST_DATA);
        QuestProgress progress = data.getProgress(this.id);
        if (progress == null) return false;
        
        for (int i = 0; i < objectives.size(); i++) {
            if (progress.getObjectiveProgress(i) < objectives.get(i).getRequiredAmount()) {
                return false;
            }
        }
        return true;
    }
    
    // Donner les récompenses
    public void giveRewards(ServerPlayer player) {
        for (QuestReward reward : rewards) {
            reward.grant(player);
        }
    }
}
```

### 19.2 Objectifs de Quête

**📝 Interface QuestObjective - Base pour tous les types d'objectifs :**

```java
package com.medelium.quest;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public interface QuestObjective {
    Component getDescription();
    int getRequiredAmount();
    
    // Vérifier si cet événement fait progresser l'objectif
    boolean checkProgress(ServerPlayer player, Object eventData);
}
```

**📝 Objectif : Collecter des Items :**

```java
package com.medelium.quest.objectives;

import com.medelium.quest.QuestObjective;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CollectItemObjective implements QuestObjective {
    private final Item item;
    private final int amount;
    
    public CollectItemObjective(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }
    
    @Override
    public Component getDescription() {
        return Component.literal("Collecter " + amount + "x " + item.getDescription().getString());
    }
    
    @Override
    public int getRequiredAmount() {
        return amount;
    }
    
    @Override
    public boolean checkProgress(ServerPlayer player, Object eventData) {
        if (eventData instanceof ItemStack stack) {
            return stack.is(item);
        }
        return false;
    }
    
    // Compter combien le joueur en a dans son inventaire
    public int countInInventory(ServerPlayer player) {
        int count = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(item)) {
                count += stack.getCount();
            }
        }
        return count;
    }
}
```

**📝 Objectif : Tuer des Mobs :**

```java
package com.medelium.quest.objectives;

import com.medelium.quest.QuestObjective;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class KillMobObjective implements QuestObjective {
    private final EntityType<?> entityType;
    private final int amount;
    
    public KillMobObjective(EntityType<?> entityType, int amount) {
        this.entityType = entityType;
        this.amount = amount;
    }
    
    @Override
    public Component getDescription() {
        return Component.literal("Tuer " + amount + "x " + entityType.getDescription().getString());
    }
    
    @Override
    public int getRequiredAmount() {
        return amount;
    }
    
    @Override
    public boolean checkProgress(ServerPlayer player, Object eventData) {
        if (eventData instanceof LivingEntity entity) {
            return entity.getType() == entityType;
        }
        return false;
    }
}
```

### 19.3 Gestionnaire de Quêtes

**📝 PlayerQuestData - Stocke les quêtes du joueur :**

```java
package com.medelium.quest;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.UnknownNullability;

import java.util.HashMap;
import java.util.Map;

public class PlayerQuestData {
    private final Map<String, QuestProgress> activeQuests = new HashMap<>();
    private final Map<String, Long> completedQuests = new HashMap<>();
    
    public void startQuest(String questId) {
        if (!activeQuests.containsKey(questId)) {
            activeQuests.put(questId, new QuestProgress(questId));
        }
    }
    
    public void completeQuest(String questId) {
        activeQuests.remove(questId);
        completedQuests.put(questId, System.currentTimeMillis());
    }
    
    public boolean isQuestActive(String questId) {
        return activeQuests.containsKey(questId);
    }
    
    public boolean isQuestCompleted(String questId) {
        return completedQuests.containsKey(questId);
    }
    
    public QuestProgress getProgress(String questId) {
        return activeQuests.get(questId);
    }
    
    public void incrementObjective(String questId, int objectiveIndex, int amount) {
        QuestProgress progress = activeQuests.get(questId);
        if (progress != null) {
            progress.incrementObjective(objectiveIndex, amount);
        }
    }
    
    // Sauvegarde NBT
    public CompoundTag save(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        
        // Quêtes actives
        ListTag activeList = new ListTag();
        for (QuestProgress progress : activeQuests.values()) {
            activeList.add(progress.save());
        }
        tag.put("ActiveQuests", activeList);
        
        // Quêtes complétées
        CompoundTag completedTag = new CompoundTag();
        for (Map.Entry<String, Long> entry : completedQuests.entrySet()) {
            completedTag.putLong(entry.getKey(), entry.getValue());
        }
        tag.put("CompletedQuests", completedTag);
        
        return tag;
    }
    
    // Chargement NBT
    public void load(CompoundTag tag, HolderLookup.Provider provider) {
        activeQuests.clear();
        completedQuests.clear();
        
        // Quêtes actives
        ListTag activeList = tag.getList("ActiveQuests", Tag.TAG_COMPOUND);
        for (Tag t : activeList) {
            QuestProgress progress = QuestProgress.load((CompoundTag) t);
            activeQuests.put(progress.getQuestId(), progress);
        }
        
        // Quêtes complétées
        CompoundTag completedTag = tag.getCompound("CompletedQuests");
        for (String key : completedTag.getAllKeys()) {
            completedQuests.put(key, completedTag.getLong(key));
        }
    }
}
```

**📝 QuestProgress - Progression d'une quête :**

```java
package com.medelium.quest;

import net.minecraft.nbt.CompoundTag;

public class QuestProgress {
    private final String questId;
    private final int[] objectiveProgress;
    
    public QuestProgress(String questId) {
        this.questId = questId;
        // Récupérer la quête pour savoir combien d'objectifs
        Quest quest = QuestRegistry.getQuest(questId);
        this.objectiveProgress = new int[quest.getObjectives().size()];
    }
    
    public QuestProgress(String questId, int objectiveCount) {
        this.questId = questId;
        this.objectiveProgress = new int[objectiveCount];
    }
    
    public String getQuestId() { return questId; }
    
    public int getObjectiveProgress(int index) {
        if (index < 0 || index >= objectiveProgress.length) return 0;
        return objectiveProgress[index];
    }
    
    public void incrementObjective(int index, int amount) {
        if (index >= 0 && index < objectiveProgress.length) {
            objectiveProgress[index] += amount;
        }
    }
    
    public void setObjectiveProgress(int index, int value) {
        if (index >= 0 && index < objectiveProgress.length) {
            objectiveProgress[index] = value;
        }
    }
    
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("QuestId", questId);
        tag.putIntArray("Progress", objectiveProgress);
        return tag;
    }
    
    public static QuestProgress load(CompoundTag tag) {
        String questId = tag.getString("QuestId");
        int[] progress = tag.getIntArray("Progress");
        QuestProgress questProgress = new QuestProgress(questId, progress.length);
        for (int i = 0; i < progress.length; i++) {
            questProgress.setObjectiveProgress(i, progress[i]);
        }
        return questProgress;
    }
}
```

### 19.4 Registre de Quêtes

**📝 QuestRegistry - Enregistre toutes les quêtes du mod :**

```java
package com.medelium.quest;

import com.medelium.item.ModItems;
import com.medelium.quest.objectives.CollectItemObjective;
import com.medelium.quest.objectives.KillMobObjective;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public class QuestRegistry {
    private static final Map<String, Quest> QUESTS = new HashMap<>();
    
    public static void register() {
        // Quête 1 : Première collecte
        Quest firstQuest = new Quest(
            "first_collection",
            Component.literal("§6Première Collecte"),
            Component.literal("§7Le forgeron a besoin de fer pour forger."),
            1
        );
        firstQuest.addObjective(new CollectItemObjective(Items.IRON_INGOT, 10));
        firstQuest.addReward(new ItemReward(ModItems.GOLD_COIN.get(), 50));
        firstQuest.addReward(new XPReward(100));
        QUESTS.put(firstQuest.getId(), firstQuest);
        
        // Quête 2 : Chasse aux zombies
        Quest zombieHunt = new Quest(
            "zombie_hunt",
            Component.literal("§6Menace Zombie"),
            Component.literal("§7Des zombies menacent le village !"),
            3
        );
        zombieHunt.addObjective(new KillMobObjective(EntityType.ZOMBIE, 20));
        zombieHunt.addReward(new ItemReward(ModItems.SILVER_SWORD.get(), 1));
        zombieHunt.addReward(new XPReward(250));
        QUESTS.put(zombieHunt.getId(), zombieHunt);
        
        // Quête 3 : Multi-objectifs
        Quest epicQuest = new Quest(
            "epic_journey",
            Component.literal("§6§lVoyage Épique"),
            Component.literal("§7Une quête légendaire vous attend..."),
            10
        );
        epicQuest.addObjective(new CollectItemObjective(Items.DIAMOND, 5));
        epicQuest.addObjective(new KillMobObjective(EntityType.ENDER_DRAGON, 1));
        epicQuest.addObjective(new CollectItemObjective(Items.NETHER_STAR, 1));
        epicQuest.addReward(new ItemReward(ModItems.LEGENDARY_SWORD.get(), 1));
        epicQuest.addReward(new XPReward(5000));
        QUESTS.put(epicQuest.getId(), epicQuest);
    }
    
    public static Quest getQuest(String id) {
        return QUESTS.get(id);
    }
    
    public static Map<String, Quest> getAllQuests() {
        return QUESTS;
    }
}
```

### 19.5 Récompenses de Quêtes

**📝 Interface QuestReward :**

```java
package com.medelium.quest;

import net.minecraft.server.level.ServerPlayer;

public interface QuestReward {
    void grant(ServerPlayer player);
}
```

**📝 ItemReward - Donner un item :**

```java
package com.medelium.quest;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemReward implements QuestReward {
    private final Item item;
    private final int amount;
    
    public ItemReward(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }
    
    @Override
    public void grant(ServerPlayer player) {
        ItemStack stack = new ItemStack(item, amount);
        player.addItem(stack);
    }
}
```

**📝 XPReward - Donner de l'XP :**

```java
package com.medelium.quest;

import net.minecraft.server.level.ServerPlayer;

public class XPReward implements QuestReward {
    private final int amount;
    
    public XPReward(int amount) {
        this.amount = amount;
    }
    
    @Override
    public void grant(ServerPlayer player) {
        player.giveExperiencePoints(amount);
    }
}
```

### 19.6 Événements de Progression

**📝 Écouter les événements pour progresser les quêtes :**

```java
package com.medelium.event;

import com.medelium.MedeliumMod;
import com.medelium.quest.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = MedeliumMod.MOD_ID)
public class QuestEvents {
    
    // Quand un joueur tue une entité
    @SubscribeEvent
    public static void onEntityKilled(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            LivingEntity killed = event.getEntity();
            
            PlayerQuestData data = player.getData(ModAttachments.QUEST_DATA);
            
            // Vérifier toutes les quêtes actives
            for (String questId : data.getActiveQuests().keySet()) {
                Quest quest = QuestRegistry.getQuest(questId);
                if (quest == null) continue;
                
                // Vérifier chaque objectif
                for (int i = 0; i < quest.getObjectives().size(); i++) {
                    QuestObjective objective = quest.getObjectives().get(i);
                    
                    if (objective.checkProgress(player, killed)) {
                        data.incrementObjective(questId, i, 1);
                        
                        // Vérifier si la quête est complétée
                        if (quest.isCompleted(player)) {
                            completeQuest(player, quest);
                        } else {
                            // Message de progression
                            QuestProgress progress = data.getProgress(questId);
                            int current = progress.getObjectiveProgress(i);
                            int required = objective.getRequiredAmount();
                            player.displayClientMessage(
                                Component.literal("§aQuête: " + current + "/" + required + " " + objective.getDescription().getString()),
                                true
                            );
                        }
                    }
                }
            }
        }
    }
    
    // Quand un joueur ramasse un item
    @SubscribeEvent
    public static void onItemPickup(PlayerEvent.ItemPickupEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ItemStack pickedUp = event.getStack();
            
            PlayerQuestData data = player.getData(ModAttachments.QUEST_DATA);
            
            for (String questId : data.getActiveQuests().keySet()) {
                Quest quest = QuestRegistry.getQuest(questId);
                if (quest == null) continue;
                
                for (int i = 0; i < quest.getObjectives().size(); i++) {
                    QuestObjective objective = quest.getObjectives().get(i);
                    
                    if (objective.checkProgress(player, pickedUp)) {
                        // Pour les items, on compte combien il en a dans l'inventaire
                        if (objective instanceof CollectItemObjective collectObj) {
                            int currentCount = collectObj.countInInventory(player);
                            QuestProgress progress = data.getProgress(questId);
                            progress.setObjectiveProgress(i, Math.min(currentCount, objective.getRequiredAmount()));
                            
                            if (quest.isCompleted(player)) {
                                completeQuest(player, quest);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static void completeQuest(ServerPlayer player, Quest quest) {
        PlayerQuestData data = player.getData(ModAttachments.QUEST_DATA);
        
        // Donner les récompenses
        quest.giveRewards(player);
        
        // Marquer comme complétée
        data.completeQuest(quest.getId());
        
        // Message de réussite
        player.sendSystemMessage(Component.literal("§6§l✔ QUÊTE TERMINÉE !"));
        player.sendSystemMessage(Component.literal("§e" + quest.getTitle().getString()));
        
        // Son de succès
        player.playSound(SoundEvents.PLAYER_LEVELUP, 1.0F, 1.0F);
    }
}
```

### 19.7 Commandes de Quête

**📝 Commandes pour gérer les quêtes :**

```java
package com.medelium.command;

import com.medelium.quest.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class QuestCommands {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("quest")
            .then(Commands.literal("start")
                .then(Commands.argument("questId", StringArgumentType.string())
                    .executes(QuestCommands::startQuest)))
            .then(Commands.literal("list")
                .executes(QuestCommands::listQuests))
            .then(Commands.literal("progress")
                .executes(QuestCommands::showProgress))
            .then(Commands.literal("complete")
                .then(Commands.argument("questId", StringArgumentType.string())
                    .requires(source -> source.hasPermission(2))
                    .executes(QuestCommands::forceComplete)))
        );
    }
    
    private static int startQuest(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        String questId = StringArgumentType.getString(context, "questId");
        
        Quest quest = QuestRegistry.getQuest(questId);
        if (quest == null) {
            player.sendSystemMessage(Component.literal("§cQuête introuvable !"));
            return 0;
        }
        
        PlayerQuestData data = player.getData(ModAttachments.QUEST_DATA);
        
        if (data.isQuestCompleted(questId)) {
            player.sendSystemMessage(Component.literal("§cVous avez déjà complété cette quête !"));
            return 0;
        }
        
        if (data.isQuestActive(questId)) {
            player.sendSystemMessage(Component.literal("§cCette quête est déjà en cours !"));
            return 0;
        }
        
        data.startQuest(questId);
        player.sendSystemMessage(Component.literal("§a§lNOUVELLE QUÊTE !"));
        player.sendSystemMessage(quest.getTitle());
        player.sendSystemMessage(quest.getDescription());
        
        return 1;
    }
    
    private static int listQuests(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerQuestData data = player.getData(ModAttachments.QUEST_DATA);
        
        player.sendSystemMessage(Component.literal("§6§l=== QUÊTES ACTIVES ==="));
        
        if (data.getActiveQuests().isEmpty()) {
            player.sendSystemMessage(Component.literal("§7Aucune quête en cours"));
        } else {
            for (String questId : data.getActiveQuests().keySet()) {
                Quest quest = QuestRegistry.getQuest(questId);
                if (quest != null) {
                    player.sendSystemMessage(Component.literal("§e- " + quest.getTitle().getString()));
                }
            }
        }
        
        return 1;
    }
    
    private static int showProgress(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerQuestData data = player.getData(ModAttachments.QUEST_DATA);
        
        for (String questId : data.getActiveQuests().keySet()) {
            Quest quest = QuestRegistry.getQuest(questId);
            if (quest == null) continue;
            
            QuestProgress progress = data.getProgress(questId);
            
            player.sendSystemMessage(Component.literal("§6" + quest.getTitle().getString()));
            for (int i = 0; i < quest.getObjectives().size(); i++) {
                QuestObjective obj = quest.getObjectives().get(i);
                int current = progress.getObjectiveProgress(i);
                int required = obj.getRequiredAmount();
                
                String status = current >= required ? "§a✔" : "§7○";
                player.sendSystemMessage(Component.literal(
                    status + " " + obj.getDescription().getString() + " (" + current + "/" + required + ")"
                ));
            }
        }
        
        return 1;
    }
    
    private static int forceComplete(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        String questId = StringArgumentType.getString(context, "questId");
        
        Quest quest = QuestRegistry.getQuest(questId);
        if (quest == null) {
            player.sendSystemMessage(Component.literal("§cQuête introuvable !"));
            return 0;
        }
        
        PlayerQuestData data = player.getData(ModAttachments.QUEST_DATA);
        quest.giveRewards(player);
        data.completeQuest(questId);
        
        player.sendSystemMessage(Component.literal("§aQuête complétée de force !"));
        return 1;
    }
}
```

---

## 20. 🏛️ Système de Factions & Réputation

> **📖 Pourquoi des factions ?**
> Les factions ajoutent de la profondeur au RP : alliances, ennemis, accès conditionnels.
> Le joueur doit faire des choix qui impactent son gameplay.
>
> **🎯 Exemples de factions médiévales :**
> - ⚔️ **Garde Royale** : Protection du royaume
> - 🗡️ **Guilde des Mercenaires** : Contrats et combats
> - 📚 **Ordre des Mages** : Magie et connaissance
> - 🌙 **Culte des Ombres** : Faction secrète maléfique
> - 🏪 **Ligue des Marchands** : Commerce et richesse
>
> **🔧 Système de réputation :**
>
> **Niveaux de réputation :**
> - **Haï** (-1000 à -500) : Attaqué à vue, aucun accès
> - **Hostile** (-500 à -100) : Pas de commerce, quêtes refusées
> - **Neutre** (-100 à 100) : Accès basique
> - **Amical** (100 à 500) : Réductions, quêtes spéciales
> - **Allié** (500 à 1000) : Accès VIP, équipement faction
> - **Exalté** (1000+) : Récompenses légendaires
>
> **💡 Actions qui modifient la réputation :**
> - Compléter des quêtes : +50 à +200
> - Tuer des ennemis de la faction : +10 à +50
> - Tuer des membres de la faction : -200 à -500
> - Commerce : +1 à +5 par transaction
> - Donner des items : Variable selon valeur

### 20.1 Structure de Faction

**📝 Classe Faction :**

```java
package com.medelium.faction;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class Faction {
    private final String id;
    private final Component name;
    private final Component description;
    private final Map<Item, Integer> favoriteItems; // Items qui donnent réputation
    private final Map<EntityType<?>, Integer> enemyMobs; // Mobs à tuer pour réputation
    
    public Faction(String id, Component name, Component description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.favoriteItems = new HashMap<>();
        this.enemyMobs = new HashMap<>();
    }
    
    public String getId() { return id; }
    public Component getName() { return name; }
    public Component getDescription() { return description; }
    
    public Faction addFavoriteItem(Item item, int reputationGain) {
        favoriteItems.put(item, reputationGain);
        return this;
    }
    
    public Faction addEnemyMob(EntityType<?> type, int reputationGain) {
        enemyMobs.put(type, reputationGain);
        return this;
    }
    
    public int getReputationForItem(Item item) {
        return favoriteItems.getOrDefault(item, 0);
    }
    
    public int getReputationForKill(EntityType<?> type) {
        return enemyMobs.getOrDefault(type, 0);
    }
    
    // Déterminer le niveau de réputation
    public static ReputationLevel getReputationLevel(int reputation) {
        if (reputation >= 1000) return ReputationLevel.EXALTED;
        if (reputation >= 500) return ReputationLevel.ALLIED;
        if (reputation >= 100) return ReputationLevel.FRIENDLY;
        if (reputation >= -100) return ReputationLevel.NEUTRAL;
        if (reputation >= -500) return ReputationLevel.HOSTILE;
        return ReputationLevel.HATED;
    }
    
    public enum ReputationLevel {
        HATED("§4Haï", -1000, -500),
        HOSTILE("§c Hostile", -500, -100),
        NEUTRAL("§7Neutre", -100, 100),
        FRIENDLY("§aAmical", 100, 500),
        ALLIED("§2Allié", 500, 1000),
        EXALTED("§6§lExalté", 1000, Integer.MAX_VALUE);
        
        private final String displayName;
        private final int min;
        private final int max;
        
        ReputationLevel(String displayName, int min, int max) {
            this.displayName = displayName;
            this.min = min;
            this.max = max;
        }
        
        public String getDisplayName() { return displayName; }
        public int getMin() { return min; }
        public int getMax() { return max; }
    }
}
```

### 20.2 Données de Réputation du Joueur

**📝 PlayerFactionData - Stocke les réputations :**

```java
package com.medelium.faction;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;

public class PlayerFactionData {
    private final Map<String, Integer> reputations = new HashMap<>();
    
    public int getReputation(String factionId) {
        return reputations.getOrDefault(factionId, 0);
    }
    
    public void setReputation(String factionId, int value) {
        reputations.put(factionId, Math.max(-1000, Math.min(1000, value)));
    }
    
    public void addReputation(String factionId, int amount) {
        int current = getReputation(factionId);
        setReputation(factionId, current + amount);
    }
    
    public Faction.ReputationLevel getLevel(String factionId) {
        return Faction.getReputationLevel(getReputation(factionId));
    }
    
    public boolean hasMinimumReputation(String factionId, int minimum) {
        return getReputation(factionId) >= minimum;
    }
    
    // Sauvegarde NBT
    public CompoundTag save(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : reputations.entrySet()) {
            tag.putInt(entry.getKey(), entry.getValue());
        }
        return tag;
    }
    
    public void load(CompoundTag tag, HolderLookup.Provider provider) {
        reputations.clear();
        for (String key : tag.getAllKeys()) {
            reputations.put(key, tag.getInt(key));
        }
    }
}
```

### 20.3 Registre de Factions

**📝 FactionRegistry :**

```java
package com.medelium.faction;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public class FactionRegistry {
    private static final Map<String, Faction> FACTIONS = new HashMap<>();
    
    public static void register() {
        // Garde Royale
        Faction royalGuard = new Faction(
            "royal_guard",
            Component.literal("§6Garde Royale"),
            Component.literal("§7Protecteurs du royaume et de la couronne")
        );
        royalGuard.addEnemyMob(EntityType.ZOMBIE, 5);
        royalGuard.addEnemyMob(EntityType.SKELETON, 5);
        royalGuard.addEnemyMob(EntityType.PILLAGER, 15);
        royalGuard.addFavoriteItem(Items.IRON_INGOT, 2);
        royalGuard.addFavoriteItem(Items.DIAMOND, 10);
        FACTIONS.put(royalGuard.getId(), royalGuard);
        
        // Ordre des Mages
        Faction mageOrder = new Faction(
            "mage_order",
            Component.literal("§5Ordre des Mages"),
            Component.literal("§7Gardiens de la connaissance arcanique")
        );
        mageOrder.addEnemyMob(EntityType.WITCH, 10);
        mageOrder.addEnemyMob(EntityType.EVOKER, 20);
        mageOrder.addFavoriteItem(Items.LAPIS_LAZULI, 3);
        mageOrder.addFavoriteItem(Items.ENCHANTED_BOOK, 15);
        FACTIONS.put(mageOrder.getId(), mageOrder);
        
        // Guilde des Mercenaires
        Faction mercenaries = new Faction(
            "mercenaries",
            Component.literal("§cGuilde des Mercenaires"),
            Component.literal("§7Combattants à louer pour le plus offrant")
        );
        mercenaries.addEnemyMob(EntityType.CREEPER, 8);
        mercenaries.addEnemyMob(EntityType.ENDERMAN, 12);
        mercenaries.addFavoriteItem(Items.GOLD_INGOT, 5);
        mercenaries.addFavoriteItem(Items.EMERALD, 8);
        FACTIONS.put(mercenaries.getId(), mercenaries);
    }
    
    public static Faction getFaction(String id) {
        return FACTIONS.get(id);
    }
    
    public static Map<String, Faction> getAllFactions() {
        return FACTIONS;
    }
}
```

### 20.4 Événements de Réputation

**📝 Gagner de la réputation :**

```java
package com.medelium.event;

import com.medelium.MedeliumMod;
import com.medelium.faction.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = MedeliumMod.MOD_ID)
public class FactionEvents {
    
    @SubscribeEvent
    public static void onMobKilled(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            LivingEntity killed = event.getEntity();
            PlayerFactionData data = player.getData(ModAttachments.FACTION_DATA);
            
            // Vérifier toutes les factions
            for (Faction faction : FactionRegistry.getAllFactions().values()) {
                int reputationGain = faction.getReputationForKill(killed.getType());
                
                if (reputationGain > 0) {
                    int oldRep = data.getReputation(faction.getId());
                    Faction.ReputationLevel oldLevel = Faction.getReputationLevel(oldRep);
                    
                    data.addReputation(faction.getId(), reputationGain);
                    
                    int newRep = data.getReputation(faction.getId());
                    Faction.ReputationLevel newLevel = Faction.getReputationLevel(newRep);
                    
                    // Message de gain
                    player.displayClientMessage(
                        Component.literal("§a+" + reputationGain + " §7réputation avec §f" + faction.getName().getString()),
                        true
                    );
                    
                    // Si changement de niveau
                    if (oldLevel != newLevel) {
                        player.sendSystemMessage(Component.literal("§6§l✦ NOUVEAU RANG !"));
                        player.sendSystemMessage(Component.literal(
                            "§e" + faction.getName().getString() + " : " + newLevel.getDisplayName()
                        ));
                        player.playSound(SoundEvents.PLAYER_LEVELUP, 1.0F, 1.5F);
                    }
                }
            }
        }
    }
}
```

### 20.5 Accès Conditionnels basés sur Réputation

**📝 Bloc nécessitant une réputation :**

```java
package com.medelium.block.custom;

import com.medelium.faction.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class FactionDoorBlock extends Block {
    private final String requiredFaction;
    private final int minimumReputation;
    
    public FactionDoorBlock(Properties properties, String factionId, int minRep) {
        super(properties);
        this.requiredFaction = factionId;
        this.minimumReputation = minRep;
    }
    
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, 
                                               Player player, BlockHitResult hitResult) {
        if (player instanceof ServerPlayer serverPlayer) {
            PlayerFactionData data = serverPlayer.getData(ModAttachments.FACTION_DATA);
            int reputation = data.getReputation(requiredFaction);
            
            if (reputation < minimumReputation) {
                Faction faction = FactionRegistry.getFaction(requiredFaction);
                player.displayClientMessage(Component.literal(
                    "§cAccès refusé ! Réputation " + faction.getName().getString() + 
                    " requise : " + minimumReputation + " (vous: " + reputation + ")"
                ), true);
                return InteractionResult.FAIL;
            }
            
            // Ouvrir la porte ou accès autorisé
            player.displayClientMessage(Component.literal("§aAccès autorisé"), true);
            return InteractionResult.SUCCESS;
        }
        
        return InteractionResult.PASS;
    }
}
```

### 20.6 Commandes de Faction

**📝 Commandes pour gérer les factions :**

```java
package com.medelium.command;

import com.medelium.faction.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class FactionCommands {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("faction")
            .then(Commands.literal("list")
                .executes(FactionCommands::listFactions))
            .then(Commands.literal("reputation")
                .executes(FactionCommands::showReputations))
            .then(Commands.literal("set")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("factionId", StringArgumentType.string())
                    .then(Commands.argument("amount", IntegerArgumentType.integer(-1000, 1000))
                        .executes(FactionCommands::setReputation))))
        );
    }
    
    private static int listFactions(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        
        player.sendSystemMessage(Component.literal("§6§l=== FACTIONS ==="));
        for (Faction faction : FactionRegistry.getAllFactions().values()) {
            player.sendSystemMessage(Component.literal("§e- " + faction.getName().getString()));
            player.sendSystemMessage(Component.literal("  §7" + faction.getDescription().getString()));
        }
        
        return 1;
    }
    
    private static int showReputations(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerFactionData data = player.getData(ModAttachments.FACTION_DATA);
        
        player.sendSystemMessage(Component.literal("§6§l=== VOS RÉPUTATIONS ==="));
        
        for (Faction faction : FactionRegistry.getAllFactions().values()) {
            int rep = data.getReputation(faction.getId());
            Faction.ReputationLevel level = Faction.getReputationLevel(rep);
            
            player.sendSystemMessage(Component.literal(
                level.getDisplayName() + " §f" + faction.getName().getString() + " §7(" + rep + ")"
            ));
        }
        
        return 1;
    }
    
    private static int setReputation(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        String factionId = StringArgumentType.getString(context, "factionId");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        
        Faction faction = FactionRegistry.getFaction(factionId);
        if (faction == null) {
            player.sendSystemMessage(Component.literal("§cFaction introuvable !"));
            return 0;
        }
        
        PlayerFactionData data = player.getData(ModAttachments.FACTION_DATA);
        data.setReputation(factionId, amount);
        
        player.sendSystemMessage(Component.literal(
            "§aRéputation " + faction.getName().getString() + " définie à " + amount
        ));
        
        return 1;
    }
}
```

---

---

## 21. ⚡ Événements Dynamiques du Monde

> **📖 C'est quoi un événement dynamique ?**
> Un événement = quelque chose qui se passe automatiquement dans le monde, indépendamment du joueur.
> Comme les invasions de pillagers Vanilla, mais en BEAUCOUP plus customisé !
>
> **🎯 Exemples pour mod médiéval :**
> - 🌙 **Nuit des Morts-Vivants** : Zombies x3, chaque nuit de pleine lune
> - 🐉 **Arrivée du Dragon** : Boss spawn aléatoire dans le monde
> - ⚔️ **Guerre des Factions** : PNJ se battent entre eux
> - 🌊 **Invasion des Mers** : Monstres marins attaquent les côtes
> - 🎭 **Festival du Village** : PNJ festifs, bonus XP pendant 3 jours
> - ☄️ **Pluie de Météorites** : Blocs magiques tombent du ciel
>
> **🔧 Types d'événements :**
>
> **1. TEMPOREL** (basé sur le temps)
> - Chaque X jours
> - À une heure précise
> - Phases de lune
>
> **2. SPATIAL** (basé sur la position)
> - Dans certains biomes
> - Autour de structures
> - Zones définies
>
> **3. CONDITIONNEL** (basé sur des conditions)
> - Quand un joueur atteint un niveau
> - Quand un item est crafté
> - Quand une quête est terminée
>
> **💡 Architecture d'un événement :**
> 1. **Déclencheur** : Quand l'événement démarre
> 2. **Durée** : Combien de temps ça dure
> 3. **Effets** : Ce qui se passe
> 4. **Fin** : Ce qui se passe à la fin

### 21.1 Structure d'Événement

**📝 Classe WorldEvent - Base pour tous les événements :**

```java
package com.medelium.worldevent;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

public abstract class WorldEvent {
    private final String id;
    private final Component name;
    private final int durationTicks;
    private int ticksActive = 0;
    private boolean active = false;
    
    public WorldEvent(String id, Component name, int durationTicks) {
        this.id = id;
        this.name = name;
        this.durationTicks = durationTicks;
    }
    
    public String getId() { return id; }
    public Component getName() { return name; }
    public boolean isActive() { return active; }
    
    // Démarrer l'événement
    public void start(ServerLevel level) {
        if (!active) {
            active = true;
            ticksActive = 0;
            onStart(level);
        }
    }
    
    // Tick de l'événement (appelé chaque tick tant qu'actif)
    public void tick(ServerLevel level) {
        if (!active) return;
        
        ticksActive++;
        onTick(level);
        
        // Vérifier si l'événement doit se terminer
        if (ticksActive >= durationTicks) {
            end(level);
        }
    }
    
    // Terminer l'événement
    public void end(ServerLevel level) {
        if (active) {
            active = false;
            onEnd(level);
        }
    }
    
    // Méthodes à override dans les sous-classes
    protected abstract void onStart(ServerLevel level);
    protected abstract void onTick(ServerLevel level);
    protected abstract void onEnd(ServerLevel level);
    
    // Vérifier si l'événement peut démarrer
    public abstract boolean canStart(ServerLevel level);
}
```

### 21.2 Événement : Invasion de Zombies

**📝 ZombieInvasionEvent - Invasion massive de zombies :**

```java
package com.medelium.worldevent.events;

import com.medelium.worldevent.WorldEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;

public class ZombieInvasionEvent extends WorldEvent {
    private int zombiesSpawned = 0;
    private final int maxZombies = 50;
    
    public ZombieInvasionEvent() {
        super(
            "zombie_invasion",
            Component.literal("§4§lINVASION ZOMBIE !"),
            20 * 60 * 5 // 5 minutes
        );
    }
    
    @Override
    public boolean canStart(ServerLevel level) {
        // Peut démarrer seulement la nuit
        long time = level.getDayTime() % 24000;
        return time >= 13000 && time <= 23000;
    }
    
    @Override
    protected void onStart(ServerLevel level) {
        // Annoncer à tous les joueurs
        for (ServerPlayer player : level.players()) {
            player.sendSystemMessage(Component.literal("§4§l━━━━━━━━━━━━━━━━━━━━"));
            player.sendSystemMessage(Component.literal("§c§l   INVASION ZOMBIE !"));
            player.sendSystemMessage(Component.literal("§7Une horde massive approche..."));
            player.sendSystemMessage(Component.literal("§4§l━━━━━━━━━━━━━━━━━━━━"));
            
            player.playNotifySound(SoundEvents.WITHER_SPAWN, SoundSource.MASTER, 1.0F, 0.8F);
        }
        
        zombiesSpawned = 0;
    }
    
    @Override
    protected void onTick(ServerLevel level) {
        // Spawn des zombies toutes les 2 secondes
        if (ticksActive % 40 == 0 && zombiesSpawned < maxZombies) {
            for (ServerPlayer player : level.players()) {
                // Spawn 2-5 zombies autour de chaque joueur
                int amount = 2 + level.random.nextInt(4);
                
                for (int i = 0; i < amount && zombiesSpawned < maxZombies; i++) {
                    // Position aléatoire autour du joueur (rayon 15-30 blocs)
                    double angle = level.random.nextDouble() * Math.PI * 2;
                    double distance = 15 + level.random.nextDouble() * 15;
                    
                    double x = player.getX() + Math.cos(angle) * distance;
                    double z = player.getZ() + Math.sin(angle) * distance;
                    double y = level.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE, (int)x, (int)z);
                    
                    BlockPos spawnPos = new BlockPos((int)x, (int)y, (int)z);
                    
                    // Créer le zombie
                    Zombie zombie = EntityType.ZOMBIE.create(level);
                    if (zombie != null) {
                        zombie.setPos(x, y, z);
                        
                        // Zombie plus fort pendant l'invasion
                        zombie.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).setBaseValue(30.0);
                        zombie.setHealth(30.0F);
                        zombie.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE).setBaseValue(5.0);
                        
                        level.addFreshEntity(zombie);
                        zombiesSpawned++;
                    }
                }
            }
        }
        
        // Message de progression toutes les minutes
        if (ticksActive % (20 * 60) == 0) {
            int minutesLeft = (durationTicks - ticksActive) / (20 * 60);
            for (ServerPlayer player : level.players()) {
                player.displayClientMessage(
                    Component.literal("§cInvasion: " + minutesLeft + " minute(s) restante(s)"),
                    true
                );
            }
        }
    }
    
    @Override
    protected void onEnd(ServerLevel level) {
        for (ServerPlayer player : level.players()) {
            player.sendSystemMessage(Component.literal("§a§l✔ L'invasion est terminée !"));
            player.sendSystemMessage(Component.literal("§7Les zombies se retirent..."));
            
            player.playNotifySound(SoundEvents.PLAYER_LEVELUP, SoundSource.MASTER, 1.0F, 1.0F);
            
            // Récompense de survie
            player.giveExperiencePoints(500);
        }
    }
}
```

### 21.3 Événement : Boss Apparition

**📝 DragonEventSpawn - Dragon spawn aléatoire :**

```java
package com.medelium.worldevent.events;

import com.medelium.entity.ModEntities;
import com.medelium.worldevent.WorldEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

public class DragonSpawnEvent extends WorldEvent {
    private Entity spawnedDragon = null;
    
    public DragonSpawnEvent() {
        super(
            "dragon_spawn",
            Component.literal("§4§l⚠ APPARITION DU DRAGON"),
            20 * 60 * 30 // 30 minutes
        );
    }
    
    @Override
    public boolean canStart(ServerLevel level) {
        // 5% de chance chaque jour
        return level.random.nextInt(100) < 5;
    }
    
    @Override
    protected void onStart(ServerLevel level) {
        // Trouver un joueur aléatoire pour spawn le dragon près de lui
        if (!level.players().isEmpty()) {
            ServerPlayer randomPlayer = level.players().get(level.random.nextInt(level.players().size()));
            
            // Position dans le ciel
            double x = randomPlayer.getX() + (level.random.nextDouble() - 0.5) * 100;
            double y = 150;
            double z = randomPlayer.getZ() + (level.random.nextDouble() - 0.5) * 100;
            
            BlockPos spawnPos = new BlockPos((int)x, (int)y, (int)z);
            
            // Spawn du dragon
            Entity dragon = ModEntities.DRAGON_BOSS.get().create(level);
            if (dragon != null) {
                dragon.setPos(x, y, z);
                level.addFreshEntity(dragon);
                spawnedDragon = dragon;
                
                // Annonce mondiale
                for (ServerPlayer player : level.players()) {
                    player.sendSystemMessage(Component.literal("§4§l━━━━━━━━━━━━━━━━━━━━"));
                    player.sendSystemMessage(Component.literal("§c§l  ⚠ DRAGON DÉTECTÉ !"));
                    player.sendSystemMessage(Component.literal("§7Un dragon ancien s'est réveillé !"));
                    player.sendSystemMessage(Component.literal("§ePosition: " + (int)x + ", " + (int)y + ", " + (int)z));
                    player.sendSystemMessage(Component.literal("§4§l━━━━━━━━━━━━━━━━━━━━"));
                    
                    player.playNotifySound(SoundEvents.ENDER_DRAGON_GROWL, SoundSource.MASTER, 2.0F, 0.8F);
                }
            }
        }
    }
    
    @Override
    protected void onTick(ServerLevel level) {
        // Vérifier si le dragon est mort
        if (spawnedDragon != null && !spawnedDragon.isAlive()) {
            // Dragon tué !
            for (ServerPlayer player : level.players()) {
                player.sendSystemMessage(Component.literal("§a§l✔ LE DRAGON A ÉTÉ VAINCU !"));
                player.sendSystemMessage(Component.literal("§6Récompense: 1000 XP"));
                player.giveExperiencePoints(1000);
            }
            end(level);
        }
    }
    
    @Override
    protected void onEnd(ServerLevel level) {
        if (spawnedDragon != null && spawnedDragon.isAlive()) {
            // Le dragon s'enfuit
            spawnedDragon.discard();
            
            for (ServerPlayer player : level.players()) {
                player.sendSystemMessage(Component.literal("§7Le dragon s'est enfui..."));
            }
        }
    }
}
```

### 21.4 Gestionnaire d'Événements

**📝 WorldEventManager - Gère tous les événements :**

```java
package com.medelium.worldevent;

import com.medelium.worldevent.events.*;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;

public class WorldEventManager {
    private final List<WorldEvent> registeredEvents = new ArrayList<>();
    private final List<WorldEvent> activeEvents = new ArrayList<>();
    private final ServerLevel level;
    
    public WorldEventManager(ServerLevel level) {
        this.level = level;
        registerEvents();
    }
    
    private void registerEvents() {
        registeredEvents.add(new ZombieInvasionEvent());
        registeredEvents.add(new DragonSpawnEvent());
        // Ajouter d'autres événements ici
    }
    
    public void tick() {
        // Tick des événements actifs
        for (WorldEvent event : new ArrayList<>(activeEvents)) {
            event.tick(level);
            
            if (!event.isActive()) {
                activeEvents.remove(event);
            }
        }
        
        // Vérifier si de nouveaux événements peuvent démarrer
        // (seulement toutes les 10 secondes pour optimiser)
        if (level.getGameTime() % 200 == 0) {
            for (WorldEvent event : registeredEvents) {
                if (!event.isActive() && event.canStart(level)) {
                    // Chance aléatoire de démarrer (10%)
                    if (level.random.nextInt(100) < 10) {
                        startEvent(event);
                    }
                }
            }
        }
    }
    
    public void startEvent(WorldEvent event) {
        if (!activeEvents.contains(event)) {
            event.start(level);
            activeEvents.add(event);
        }
    }
    
    public void stopAllEvents() {
        for (WorldEvent event : new ArrayList<>(activeEvents)) {
            event.end(level);
        }
        activeEvents.clear();
    }
    
    public List<WorldEvent> getActiveEvents() {
        return new ArrayList<>(activeEvents);
    }
}
```

### 21.5 Intégration au Serveur

**📝 Ticker pour les événements mondiaux :**

```java
package com.medelium.event;

import com.medelium.MedeliumMod;
import com.medelium.worldevent.WorldEventManager;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = MedeliumMod.MOD_ID)
public class WorldEventTicker {
    private static final Map<ServerLevel, WorldEventManager> MANAGERS = new HashMap<>();
    
    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            // Créer le manager si n'existe pas
            WorldEventManager manager = MANAGERS.computeIfAbsent(
                serverLevel, 
                WorldEventManager::new
            );
            
            // Tick du manager
            manager.tick();
        }
    }
}
```

### 21.6 Commandes pour Événements

**📝 Commandes admin pour déclencher événements :**

```java
package com.medelium.command;

import com.medelium.worldevent.*;
import com.medelium.worldevent.events.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

public class EventCommands {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("worldevent")
            .requires(source -> source.hasPermission(2))
            .then(Commands.literal("start")
                .then(Commands.literal("zombie_invasion")
                    .executes(ctx -> startZombieInvasion(ctx)))
                .then(Commands.literal("dragon_spawn")
                    .executes(ctx -> startDragonSpawn(ctx))))
            .then(Commands.literal("stop")
                .executes(EventCommands::stopAllEvents))
            .then(Commands.literal("list")
                .executes(EventCommands::listActiveEvents))
        );
    }
    
    private static int startZombieInvasion(CommandContext<CommandSourceStack> ctx) {
        ServerLevel level = ctx.getSource().getLevel();
        WorldEventManager manager = getManager(level);
        
        WorldEvent event = new ZombieInvasionEvent();
        manager.startEvent(event);
        
        ctx.getSource().sendSuccess(() -> 
            Component.literal("§aInvasion zombie démarrée !"), true);
        
        return 1;
    }
    
    private static int startDragonSpawn(CommandContext<CommandSourceStack> ctx) {
        ServerLevel level = ctx.getSource().getLevel();
        WorldEventManager manager = getManager(level);
        
        WorldEvent event = new DragonSpawnEvent();
        manager.startEvent(event);
        
        ctx.getSource().sendSuccess(() -> 
            Component.literal("§aDragon invoqué !"), true);
        
        return 1;
    }
    
    private static int stopAllEvents(CommandContext<CommandSourceStack> ctx) {
        ServerLevel level = ctx.getSource().getLevel();
        WorldEventManager manager = getManager(level);
        
        manager.stopAllEvents();
        
        ctx.getSource().sendSuccess(() -> 
            Component.literal("§aTous les événements arrêtés"), true);
        
        return 1;
    }
    
    private static int listActiveEvents(CommandContext<CommandSourceStack> ctx) {
        ServerLevel level = ctx.getSource().getLevel();
        WorldEventManager manager = getManager(level);
        
        List<WorldEvent> active = manager.getActiveEvents();
        
        if (active.isEmpty()) {
            ctx.getSource().sendSuccess(() -> 
                Component.literal("§7Aucun événement actif"), false);
        } else {
            ctx.getSource().sendSuccess(() -> 
                Component.literal("§6§lÉvénements actifs:"), false);
            
            for (WorldEvent event : active) {
                ctx.getSource().sendSuccess(() -> 
                    Component.literal("§e- " + event.getName().getString()), false);
            }
        }
        
        return 1;
    }
    
    private static WorldEventManager getManager(ServerLevel level) {
        // Récupérer depuis WorldEventTicker.MANAGERS
        return WorldEventTicker.MANAGERS.computeIfAbsent(level, WorldEventManager::new);
    }
}
```

---

## 22. ⚙️ Performance & Optimisation

> **📖 Pourquoi optimiser ?**
> Un mod mal optimisé = serveur qui lag, joueurs qui quittent, expérience ruinée.
> Même avec du bon code, des mauvaises pratiques peuvent DÉTRUIRE les performances.
>
> **🎯 Les 3 tueurs de performance :**
>
> **1. TICKS EXCESSIFS** 🔄
> - Faire des calculs CHAQUE tick (20x/seconde)
> - Vérifier des choses inutilement
> - Boucles sur TOUS les joueurs/entités
>
> **2. NETWORK SPAM** 📡
> - Envoyer trop de packets
> - Synchro trop fréquente
> - Données trop grosses
>
> **3. MEMORY LEAKS** 💾
> - Garder des références à des objets supprimés
> - Collections qui grandissent infiniment
> - Pas de nettoyage
>
> **🔧 Règles d'or :**
>
> ✅ **Faire :**
> - Cacher les calculs coûteux
> - Utiliser des timers (toutes les X secondes au lieu de chaque tick)
> - Limiter la portée des vérifications
> - Nettoyer les données inutiles
>
> ❌ **Ne JAMAIS faire :**
> - Boucler sur TOUTES les entités du monde
> - Faire des calculs complexes chaque tick
> - Créer des objets inutilement dans une boucle
> - Ignorer les null checks

### 22.1 Optimisation des Ticks

**❌ MAUVAIS - Vérifie TOUTES les entités chaque tick :**

```java
// NE JAMAIS FAIRE ÇA !
@SubscribeEvent
public static void onServerTick(TickEvent.ServerTickEvent event) {
    for (ServerLevel level : server.getAllLevels()) {
        for (Entity entity : level.getAllEntities()) {
            // Calculs coûteux sur chaque entité...
            // 1000 entités × 20 ticks = 20,000 calculs par seconde !
        }
    }
}
```

**✅ BON - Limite avec un timer :**

```java
private static int tickCounter = 0;

@SubscribeEvent
public static void onServerTick(TickEvent.ServerTickEvent event) {
    tickCounter++;
    
    // Seulement toutes les 5 secondes (100 ticks)
    if (tickCounter >= 100) {
        tickCounter = 0;
        
        // Maintenant les calculs se font 20x moins souvent
        performExpensiveCalculation();
    }
}
```

### 22.2 Cache des Calculs Coûteux

**📝 Exemple : Distance entre joueurs (calcul coûteux) :**

```java
package com.medelium.util;

import net.minecraft.server.level.ServerPlayer;
import java.util.HashMap;
import java.util.Map;

public class PlayerDistanceCache {
    private static final Map<String, Double> distanceCache = new HashMap<>();
    private static long lastUpdateTime = 0;
    private static final long UPDATE_INTERVAL = 100; // Update toutes les 5 secondes
    
    public static double getDistance(ServerPlayer player1, ServerPlayer player2) {
        long currentTime = player1.level().getGameTime();
        
        // Clé unique pour cette paire de joueurs
        String key = getCacheKey(player1, player2);
        
        // Utiliser le cache si récent
        if (currentTime - lastUpdateTime < UPDATE_INTERVAL && distanceCache.containsKey(key)) {
            return distanceCache.get(key);
        }
        
        // Calculer et mettre en cache
        double distance = player1.distanceTo(player2);
        distanceCache.put(key, distance);
        lastUpdateTime = currentTime;
        
        return distance;
    }
    
    private static String getCacheKey(ServerPlayer p1, ServerPlayer p2) {
        // Ordre cohérent pour la clé
        if (p1.getId() < p2.getId()) {
            return p1.getUUID() + "_" + p2.getUUID();
        } else {
            return p2.getUUID() + "_" + p1.getUUID();
        }
    }
    
    public static void clearCache() {
        distanceCache.clear();
    }
}
```

### 22.3 Limiter les Packets Network

**❌ MAUVAIS - Envoie un packet CHAQUE tick :**

```java
// NE PAS FAIRE - 20 packets par seconde !
public void tick() {
    syncToClient(); // Appelé chaque tick
}
```

**✅ BON - Envoie seulement quand nécessaire :**

```java
private int lastSyncedValue = 0;
private int ticksSinceLastSync = 0;

public void tick() {
    ticksSinceLastSync++;
    
    // Sync seulement si la valeur a changé ET au moins 1 seconde s'est écoulée
    if (currentValue != lastSyncedValue && ticksSinceLastSync >= 20) {
        syncToClient();
        lastSyncedValue = currentValue;
        ticksSinceLastSync = 0;
    }
}
```

### 22.4 Nettoyage de Mémoire

**📝 Nettoyer les données des joueurs déconnectés :**

```java
package com.medelium.event;

import com.medelium.MedeliumMod;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = MedeliumMod.MOD_ID)
public class CleanupEvents {
    
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            // Nettoyer TOUTES les références au joueur
            PlayerDistanceCache.clearForPlayer(player);
            QuestTracker.removePlayer(player.getUUID());
            ActiveEventManager.removePlayer(player.getUUID());
            
            // Etc. pour tous vos systèmes
        }
    }
}
```

### 22.5 Profiling - Trouver les Problèmes

**📝 Simple profiler pour mesurer le temps :**

```java
package com.medelium.util;

import com.medelium.MedeliumMod;

public class PerformanceProfiler {
    private static long startTime = 0;
    
    public static void start(String label) {
        startTime = System.nanoTime();
    }
    
    public static void end(String label) {
        long elapsed = System.nanoTime() - startTime;
        double milliseconds = elapsed / 1_000_000.0;
        
        // Log si ça prend plus de 1ms (signe de problème)
        if (milliseconds > 1.0) {
            MedeliumMod.LOGGER.warn("[PERF] {} took {}ms", label, String.format("%.2f", milliseconds));
        }
    }
}

// Utilisation:
PerformanceProfiler.start("Quest Check");
// ... code à mesurer ...
PerformanceProfiler.end("Quest Check");
```

### 22.6 Optimisations Spécifiques

**📝 Limiter les spawns d'entités :**

```java
private static final int MAX_CUSTOM_MOBS_PER_CHUNK = 5;

public static boolean canSpawnMob(ServerLevel level, BlockPos pos) {
    // Compter les mobs custom dans le chunk
    ChunkPos chunkPos = new ChunkPos(pos);
    int count = 0;
    
    for (Entity entity : level.getChunk(chunkPos.x, chunkPos.z).getEntities()) {
        if (entity instanceof CustomMobEntity) {
            count++;
        }
    }
    
    return count < MAX_CUSTOM_MOBS_PER_CHUNK;
}
```

**📝 Limiter les particules :**

```java
private int particleSpawnCooldown = 0;

public void spawnParticles() {
    particleSpawnCooldown--;
    
    if (particleSpawnCooldown <= 0) {
        // Spawn les particules
        level.addParticle(...);
        
        // Cooldown de 10 ticks (0.5 secondes)
        particleSpawnCooldown = 10;
    }
}
```

---

## 23. 🔒 Sécurité & Anti-Exploit

> **📖 Pourquoi la sécurité est CRITIQUE ?**
> Les joueurs vont TOUJOURS essayer de tricher. Toujours.
> Si vous ne validez pas côté serveur, votre mod sera exploité en 5 minutes.
>
> **🎯 Les exploits les plus communs :**
>
> **1. PACKET INJECTION** 📡
> - Joueur envoie des packets modifiés
> - Exemple : "J'ai tué le boss" sans l'avoir tué
>
> **2. DUPLICATION D'ITEMS** 📦
> - Profiter de la synchro client/serveur
> - Cloner des items précieux
>
> **3. BYPASS DE CONDITIONS** 🚪
> - Ignorer les checks de niveau/profession
> - Accéder à du contenu verrouillé
>
> **4. XP/MONEY FARMING** 💰
> - Répéter une action infiniment
> - Exploiter les récompenses
>
> **🔧 Règle ABSOLUE :**
> **TOUT doit être validé côté SERVEUR, JAMAIS faire confiance au client !**

### 23.1 Validation Serveur Stricte

**❌ DANGEREUX - Fait confiance au client :**

```java
// NE JAMAIS FAIRE ÇA !
public void handleQuestComplete(Player player, String questId) {
    // Pas de vérification = le joueur peut prétendre avoir fini n'importe quelle quête
    Quest quest = QuestRegistry.getQuest(questId);
    quest.giveRewards(player);
}
```

**✅ SÉCURISÉ - Valide tout :**

```java
public void handleQuestComplete(ServerPlayer player, String questId) {
    Quest quest = QuestRegistry.getQuest(questId);
    
    // VÉRIFICATION 1 : La quête existe ?
    if (quest == null) {
        MedeliumMod.LOGGER.warn("Player {} tried to complete invalid quest {}", 
            player.getName().getString(), questId);
        return;
    }
    
    PlayerQuestData data = player.getData(ModAttachments.QUEST_DATA);
    
    // VÉRIFICATION 2 : Le joueur a cette quête active ?
    if (!data.isQuestActive(questId)) {
        MedeliumMod.LOGGER.warn("Player {} tried to complete inactive quest {}", 
            player.getName().getString(), questId);
        return;
    }
    
    // VÉRIFICATION 3 : TOUS les objectifs sont vraiment complétés ?
    if (!quest.isCompleted(player)) {
        MedeliumMod.LOGGER.warn("Player {} tried to complete unfinished quest {}", 
            player.getName().getString(), questId);
        return;
    }
    
    // OK, tout est validé
    quest.giveRewards(player);
    data.completeQuest(questId);
}
```

### 23.2 Anti-Duplication

**📝 Vérifier l'inventaire avant donner des items :**

```java
public static boolean giveItemSafely(ServerPlayer player, ItemStack stack) {
    // Compter combien le joueur a AVANT
    int countBefore = countItem(player, stack.getItem());
    
    // Donner l'item
    boolean success = player.addItem(stack);
    
    // Compter combien le joueur a APRÈS
    int countAfter = countItem(player, stack.getItem());
    
    // Vérifier que la différence est cohérente
    int expectedDifference = stack.getCount();
    int actualDifference = countAfter - countBefore;
    
    if (actualDifference != expectedDifference) {
        MedeliumMod.LOGGER.error("Item duplication detected for player {}!", 
            player.getName().getString());
        
        // Rollback : retirer les items en trop
        removeItem(player, stack.getItem(), actualDifference);
        return false;
    }
    
    return success;
}

private static int countItem(ServerPlayer player, Item item) {
    int count = 0;
    for (ItemStack stack : player.getInventory().items) {
        if (stack.is(item)) {
            count += stack.getCount();
        }
    }
    return count;
}
```

### 23.3 Cooldowns Anti-Spam

**📝 Empêcher le spam d'actions :**

```java
package com.medelium.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private static final Map<String, Long> cooldowns = new HashMap<>();
    
    public static boolean isOnCooldown(UUID playerId, String action) {
        String key = playerId + ":" + action;
        Long lastUse = cooldowns.get(key);
        
        if (lastUse == null) {
            return false;
        }
        
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastUse) < getCooldownDuration(action);
    }
    
    public static void setCooldown(UUID playerId, String action) {
        String key = playerId + ":" + action;
        cooldowns.put(key, System.currentTimeMillis());
    }
    
    public static long getRemainingCooldown(UUID playerId, String action) {
        String key = playerId + ":" + action;
        Long lastUse = cooldowns.get(key);
        
        if (lastUse == null) {
            return 0;
        }
        
        long elapsed = System.currentTimeMillis() - lastUse;
        long duration = getCooldownDuration(action);
        
        return Math.max(0, duration - elapsed);
    }
    
    private static long getCooldownDuration(String action) {
        return switch (action) {
            case "quest_start" -> 5000; // 5 secondes
            case "faction_donate" -> 10000; // 10 secondes
            case "boss_summon" -> 300000; // 5 minutes
            default -> 1000; // 1 seconde par défaut
        };
    }
}

// Utilisation:
if (CooldownManager.isOnCooldown(player.getUUID(), "quest_start")) {
    long remaining = CooldownManager.getRemainingCooldown(player.getUUID(), "quest_start");
    player.sendSystemMessage(Component.literal(
        "§cCooldown: " + (remaining / 1000) + " secondes restantes"
    ));
    return;
}

CooldownManager.setCooldown(player.getUUID(), "quest_start");
// ... exécuter l'action ...
```

### 23.4 Validation des Permissions

**📝 System de permissions par niveau :**

```java
package com.medelium.util;

import net.minecraft.server.level.ServerPlayer;

public class PermissionChecker {
    
    public enum Permission {
        USE_ADMIN_COMMANDS(2),
        BYPASS_COOLDOWNS(2),
        MODIFY_QUESTS(3),
        GIVE_ITEMS(2),
        TELEPORT_PLAYERS(2),
        MANAGE_EVENTS(3);
        
        private final int requiredLevel;
        
        Permission(int level) {
            this.requiredLevel = level;
        }
        
        public int getRequiredLevel() {
            return requiredLevel;
        }
    }
    
    public static boolean hasPermission(ServerPlayer player, Permission permission) {
        // Vérifier le niveau d'opérateur
        int playerLevel = player.getServer().getProfilePermissions(player.getGameProfile());
        return playerLevel >= permission.getRequiredLevel();
    }
    
    public static boolean checkAndWarn(ServerPlayer player, Permission permission) {
        if (!hasPermission(player, permission)) {
            player.sendSystemMessage(Component.literal(
                "§cVous n'avez pas la permission pour cette action !"
            ));
            
            MedeliumMod.LOGGER.warn("Player {} tried to use {} without permission", 
                player.getName().getString(), permission.name());
            
            return false;
        }
        return true;
    }
}

// Utilisation dans une commande:
if (!PermissionChecker.checkAndWarn(player, Permission.MODIFY_QUESTS)) {
    return 0;
}
```

### 23.5 Logs de Sécurité

**📝 Logger les actions suspectes :**

```java
package com.medelium.security;

import com.medelium.MedeliumMod;
import net.minecraft.server.level.ServerPlayer;

public class SecurityLogger {
    
    public static void logSuspiciousActivity(ServerPlayer player, String activity, String details) {
        MedeliumMod.LOGGER.warn("[SECURITY] Player: {} | Activity: {} | Details: {}", 
            player.getName().getString(),
            activity,
            details
        );
        
        // Optionnel : Notifier les admins en ligne
        for (ServerPlayer admin : player.getServer().getPlayerList().getPlayers()) {
            if (admin.hasPermissions(3)) {
                admin.sendSystemMessage(Component.literal(
                    "§c[SECURITY] " + player.getName().getString() + " : " + activity
                ));
            }
        }
    }
    
    public static void logItemGiven(ServerPlayer player, ItemStack stack) {
        MedeliumMod.LOGGER.info("[ITEM] Given to {}: {}x {}", 
            player.getName().getString(),
            stack.getCount(),
            stack.getDisplayName().getString()
        );
    }
    
    public static void logQuestComplete(ServerPlayer player, String questId) {
        MedeliumMod.LOGGER.info("[QUEST] Player {} completed quest {}", 
            player.getName().getString(),
            questId
        );
    }
}
```

---

**Bon développement ! ⚔️🏰✨**
