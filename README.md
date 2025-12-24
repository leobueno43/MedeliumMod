# Medelium Mod - Medieval Fantasy RP

Un mod Minecraft NeoForge 1.21.1 pour le roleplay médiéval fantasy.

## Contenu du Mod

### Objets
- **Épée de Chevalier** - Épée en fer pour les chevaliers
- **Épée Royale** - Épée en diamant pour la royauté
- **Pièce d'Argent** - Monnaie médiévale
- **Pièce d'Or** - Monnaie royale
- **Sceau Royal** - Objet de prestige
- **Parchemin Ancien** - Document médiéval
- **Pain Médiéval** - Nourriture médiévale
- **Hydromel** - Boisson avec effet de régénération

### Blocs
- **Pierre de Château** - Bloc de construction médiéval
- **Briques de Pierre de Château** - Variante décorative
- **Lanterne Médiévale** - Source de lumière
- **Trône Royal** - Décoration royale
- **Table Médiévale** - Mobilier médiéval

## Installation

1. Assurez-vous d'avoir Java 21 installé
2. Placez le fichier .jar compilé dans le dossier `mods` de votre instance Minecraft
3. Lancez Minecraft avec NeoForge 1.21.1

## Développement

### Prérequis
- Java JDK 21
- Gradle (inclus via wrapper)

### Compiler le mod
```bash
./gradlew build
```

Le fichier .jar sera généré dans `build/libs/`

### Lancer le client de test
```bash
./gradlew runClient
```

### Lancer le serveur de test
```bash
./gradlew runServer
```

## Configuration

Le fichier de configuration se trouve dans `config/medeliummod-common.toml` après le premier lancement.

Options disponibles:
- `enableMedievalStructures` - Active/désactive la génération de structures médiévales
- `castleSpawnRate` - Taux d'apparition des châteaux

## Licence

MIT License
