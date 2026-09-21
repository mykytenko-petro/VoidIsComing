![banner](./banner.png)
# Void Is Coming

A Fabric mod for Minecraft focused on RPG progression, skill-based weapon damage scaling, mana management, and magic wands. Built as part of the "VoidIsComing" modding framework.

## Features
- **Mod Entities**: New mobs and boss
![skilltree](./boss.jpg)
- **Skill Progression System**: Unlocks weapon damage bonuses for swords, bows, and wands.
![skilltree](./SkillTree.jpg)
- **Magic Wands**: Tiered magic wands (Wooden, Iron, Diamond, Netherite) shooting custom.
![wands](./wands.jpg)
- **Custom Player Attributes**: Dynamic scaling for health, armor, movement speed, and mana.
- **Consumables**: Custom health and mana potions.
- **In-Game Guide**: After creating the world you will get guidebook (`mod_book`) for progression tracking.

## Technical & Architecture Highlights

- **Dynamic Attribute Modifiers**: Swords and Bows scale attack damage via `PlayerStatApplier` using `EntityAttributeModifier`.
- **Custom Projectile Scaling**: Wand damage is calculated dynamically in `WandItem.java` based on tier values and unlocked skills.
- **Tick-Based Stat Sync**: Player stats and modifiers are re-evaluated using `ServerTickEvents`.
- **Cardinal Components API**: Player mana and skill data are managed via `ModComponents`.
- **Node Based SkillTree**: SkillTree with Noded Structure

## Requirements

- Minecraft `1.20.1`
- Fabric Loader `>=0.14.x`
- Java version 21+
- Fabric API
- Cardinal Components API

## Getting Started
### How to start playing?
- You will need Minecraft 1.20.1 with Fabric mod loader installed
- Also you will need Fabric API 
- Then you shall download our mod from: GitHub,ModRinth,PlanetMC
- And JUST DROP IT INSIDE .minecraft/mods
- After all you should run your minecraft and check it running properly

### Installation

Clone the repository:
```bash
git clone https://github.com/mykytenko-petro/VoidIsComing.git
```
```bash
cd VoidIsComing
```
```bash
gradlew Build
```
```bash
gradlew runClient
```
---

