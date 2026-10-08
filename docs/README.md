**English** | [Українська](README.uk.md)

![Void Is Coming banner](./banner.png)

# Void Is Coming

**A hardcore RPG overhaul for Minecraft: choose a class, grow through a skill tree, cast spells with mana, and face the creatures of the Void Plains.**

![Minecraft 1.20.1](https://img.shields.io/badge/Minecraft-1.20.1-62b47a) ![Fabric](https://img.shields.io/badge/Loader-Fabric-dbd0b4) ![Java 17+](https://img.shields.io/badge/Java-17%2B-orange) ![License: MIT](https://img.shields.io/badge/License-MIT-blue)

![Gameplay preview](./game.gif)

## The Idea

Vanilla Minecraft progression is mostly about better materials. **Void Is Coming** adds a second layer: your character grows too. Your combat, movement and magic are shaped by the class you choose and the skills you unlock, so two players with the same gear can play very differently.

- **Progression you choose.** Pick a path through the skill tree: Warrior, Archer or Mage.
- **Levels give you skill points.** Every level-up rewards points you spend on new skills.
- **Spells with a cost.** Equip spells and manage your mana during fights.
- **One system for everything.** Swords, bows, wands and spells all scale from the same skill and stat system, which keeps the game consistent and easy to extend.
- **Hardcore survival.** There is no passive regeneration, and the Void creatures are much harder than vanilla mobs, so every fight and every potion matters.

## Features

### Classes and skill tree

![Skill tree](./SkillTree.jpg)

A node-based skill tree with three branches. Skills unlock passive bonuses, grant access to spells and gate further progression.

- **Warrior**: armor and melee-focused buffs.
- **Archer**: extra health, speed, bow bonuses and stealth / detection skills.
- **Mage**: mana scaling, wand power, utility spells, mobility and sustain.

### Spells and mana

Equip spells and cast them with mana. Some spells are active, others work passively or trigger on kills.

### The Void Plains and its creatures

![Mod entities](./boss.jpg)

A new Void Plains biome with its own trees and grass, populated by Void Pigs, Void Cows and Void Sheep, and guarded by the Stone Golem, a strong boss-like mob. These mobs are far tougher than their vanilla counterparts and give your skills something to be tested against.

### Magic wands

![Wands](./wands.jpg)

Tiered wands (Wooden, Iron, Diamond, Netherite) that shoot custom projectiles. Their damage depends on the wand tier and on the skills you have unlocked.

### And more

- **Custom player attributes**: dynamic scaling for health, armor, movement speed, mana and damage.
- **Consumables**: health and mana potions.
- **Custom interface**: a skill tree screen plus HUD overlays for mana, stats and your equipped spells.
- **In-game guide**: a guidebook for tracking your progression.

## Conclusion

In this project, we learned how to work with:

- The Java programming language and OOP principles
- 2D and 3D design
- Teamwork using Git and GitHub

Our biggest challenges were:

- **Skills and spells**: designing skills that actually change how weapons and wands behave, and keeping all of that data in sync with the player.
- **Animations**: making new mobs and the boss feel alive.
- **World generation (biomes)**: building new terrain that fits the mod's atmosphere.

Our mod can also serve as a useful example for developing Minecraft (Fabric) modifications.

## Team

- [Petro Mykytenko](https://github.com/mykytenko-petro): team lead, developer, 2D artist
- [Vadim Kuchnii](https://github.com/vxdlmchk-code): developer, 2D artist
- [Andrew Skulskyi](https://github.com/andrewskulskuia): developer, 2D artist, 3D artist
- [Mykola Boyarkin](https://github.com/KolyaBojarkin): developer, 2D artist

## Installation

### Requirements

- Minecraft `1.20.1`
- [Fabric Loader](https://fabricmc.net/use/) `>=0.14.0`
- [Fabric API](https://modrinth.com/mod/fabric-api)
- [Cardinal Components API](https://modrinth.com/mod/cardinal-components-api)

### Install with Pinecone MC Launcher

1. Click **Edit** in the right corner, then click **Mods**.

   ![Opening the Mods menu](./download.gif)

2. Click **Download Mods**, select **CurseForge**, and search for **Void Is Coming**.
3. At the bottom, click **Select mod for download**, then **Review and confirm**.
4. Click **Launch**.

   ![Downloading the mod from CurseForge](./downloadMod.gif)

<details>
<summary>Manual installation</summary>

1. Install Minecraft 1.20.1 with the Fabric loader.
2. Download Fabric API and Cardinal Components API.
3. Download the latest Void Is Coming `.jar` from [GitHub Releases](https://github.com/mykytenko-petro/VoidIsComing/releases) (also available on CurseForge, Modrinth and PlanetMC).
4. Put all `.jar` files into `.minecraft/mods`.
5. Launch Minecraft with the Fabric profile and check that the mod loads.

</details>

<details>
<summary>Build from source</summary>

Requires Java 17+ (check your Gradle configuration if the build asks for a newer version).

```bash
git clone https://github.com/mykytenko-petro/VoidIsComing.git
cd VoidIsComing
./gradlew build
./gradlew runClient
```

On Windows, use `gradlew.bat` instead of `./gradlew`.

</details>

## Bug Reports & Contributing

Found a bug or have an idea? Open an [issue](https://github.com/mykytenko-petro/VoidIsComing/issues). Pull requests are welcome.

## License

This project is licensed under the [MIT License](LICENSE).