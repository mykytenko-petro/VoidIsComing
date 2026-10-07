[Українська версія](#void-is-comming-українська)
![banner](./banner.png)
# How to Download
- **First, click Edit in the right corner, then click Mods.** ![](./download.gif)
- **Then click Download Mods, select CurseForge, and search for Void Is Coming. At the bottom, click Select mod for download, then Review and confirm. Click Launch.**  ![](./downloadMod.gif)
# Void Is Coming

A Fabric mod for Minecraft focused on RPG progression, skill-based weapon damage scaling, mana management, and magic wands. Built as part of the "VoidIsComing" modding framework.

## Features
![](./game.gif)
- **Mod Entities**: New mobs and boss
![modentities](./boss.jpg)
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

### Conclusion
In this project, we learned how to work with:
* The Java programming language and OOP principles
* 2D and 3D design
* Teamwork using Git and GitHub

**Our biggest challenges were:**
* Creating Skills and Spells
* Developing animations
* World generation (biomes)

Our mod can serve as a useful example for developing modifications for Minecraft (Fabric).

### Team
- [Petro Mykytenko](https://github.com/mykytenko-petro) Team lead, dev, 2D artist
- [Vadim Kuchmii](https://github.com/vxdlmchk-code) member, dev, 2D artist
- [Andrew Skulskyi](https://github.com/andrewskulskuia) member, dev, 2D artist, 3D artist,
- [Mykola Boyarkin](https://github.com/KolyaBojarkin) member, dev, 2D artist 

# Void Is Comming (Українська)

# Як завантажити

- **Спочатку натисніть Edit у правому куті, а потім натисніть Mods.** ![](./download.gif)

- **Потім натисніть Download Mods, виберіть CurseForge та введіть Void Is Coming. Внизу натисніть Select mod for download, потім Review and confirm. Натисніть Launch.**  ![](./downloadMod.gif)

# Void Is Coming

Мод для Minecraft на Fabric, зосереджений на RPG-прогресії, системі навичок, масштабуванні шкоди зброї залежно від навичок, управлінні маною та магічних посохах. Мод створений у межах моддингового проєкту "VoidIsComing".

## Можливості

![](./game.gif)

- **Сутності моду**: Нові моби та боси

![modentities](./boss.jpg)

- **Система розвитку навичок**: Відкриває бонуси до шкоди для мечів, луків та посохів.

![skilltree](./SkillTree.jpg)

- **Магічні посохи**: Магічні посохи різних рівнів (дерев'яний, залізний, діамантовий, незеритовий), які стріляють власними снарядами.

![wands](./wands.jpg)

- **Власні характеристики гравця**: Динамічне масштабування здоров'я, броні, швидкості пересування та мани.

- **Витратні предмети**: Власні зілля здоров'я та мани.

- **Ігровий посібник**: Після створення світу ви отримаєте книгу-посібник (`mod_book`) для відстеження прогресу.

## Технічні особливості та архітектура

- **Динамічні модифікатори характеристик**: Мечі та луки масштабують шкоду від атаки через `PlayerStatApplier`, використовуючи `EntityAttributeModifier`.

- **Динамічне масштабування снарядів**: Шкода від посохів динамічно розраховується у `WandItem.java` залежно від рівня посоха та відкритих навичок.

- **Синхронізація характеристик на основі тиків****: Характеристики гравця та модифікатори повторно перевіряються за допомогою `ServerTickEvents`.

- **Cardinal Components API**: Мана та дані про навички гравця керуються через `ModComponents`.

- **Деревоподібна система навичок**: SkillTree з вузловою структурою.

## Вимоги

- Minecraft `1.20.1`

- Fabric Loader `>=0.14.x`

- Java версії 21+

- Fabric API

- Cardinal Components API

## Початок роботи

### Як почати грати?

- Вам знадобиться Minecraft 1.20.1 із встановленим Fabric mod loader.

- Також вам знадобиться Fabric API.

- Після цього потрібно завантажити наш мод з: GitHub,ModRinth,PlanetMC

- І ПРОСТО ПЕРЕМІСТІТЬ ЙОГО У .minecraft/mods

- Після цього запустіть Minecraft і перевірте, чи мод працює належним чином.

### Встановлення

Клонуйте репозиторій:

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

### Висновок

У цьому проєкті ми навчилися працювати з:

* Мовою програмування Java та принципами ООП

* 2D- та 3D-дизайном

* Командною роботою з використанням Git та GitHub

Нашими найбільшими труднощами були:

* Створення навичок та заклинань

* Розробка анімацій

* Генерація світу (біомів)

Наш мод може бути корисним прикладом для розробки модифікацій для Minecraft (Fabric).

### Команда

- [Петро Микитенко](https://github.com/mykytenko-petro) Team lead, дев-розробник, 2Д артист

- [Вадим Кучній](https://github.com/vxdlmchk-code) учасник, дев-розробник, 2Д артист

- [Андрій Скульский](https://github.com/andrewskulskuia) учасник, дев-розробник, 2Д артист, 3Д артист,

- [Микола Бояркий](https://github.com/KolyaBojarkin) учасник, дев-розробник, 2Д артист
