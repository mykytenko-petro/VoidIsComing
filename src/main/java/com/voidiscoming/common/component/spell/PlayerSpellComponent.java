package com.voidiscoming.common.component.spell;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.spell.ModSpells;

public class PlayerSpellComponent implements SpellComponent, AutoSyncedComponent {
    private final PlayerEntity player;
    
    // Спеллбар на 4 слоти (зберігає ID екіпірованих скіллів)
    private final Identifier[] equippedSpells = new Identifier[4];

    // Кулдауни
    private final Map<Identifier, Long> cooldownEnds = new HashMap<>();
    private final Map<Identifier, Integer> cooldownDurations = new HashMap<>();

    public PlayerSpellComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public Identifier[] getEquippedSpells() {
        return this.equippedSpells;
    }

    @Override
    public void equipSpell(int slot, Identifier spellId) {
        // Перевіряємо валідність слота та чи існує взагалі такий скілл у реєстрі мода
        if (slot >= 0 && slot < equippedSpells.length && spellId != null && ModSpells.get(spellId) != null) {
            // Якщо цей скілл вже стоїть в іншому слоті — спочатку зануляємо його там
            for (int i = 0; i < equippedSpells.length; i++) {
                if (spellId.equals(equippedSpells[i])) {
                    equippedSpells[i] = null;
                }
            }
            equippedSpells[slot] = spellId;
            sync();
        }
    }

    @Override
    public void unequipSpell(int slot) {
        if (slot >= 0 && slot < equippedSpells.length) {
            equippedSpells[slot] = null;
            sync();
        }
    }

    @Override
    public boolean isOnCooldown(Identifier spellId) {
        Long endTime = cooldownEnds.get(spellId);
        if (endTime == null) return false;
        return player.getWorld().getTime() < endTime;
    }

    @Override
    public long getCooldownEnd(Identifier spellId) {
        return cooldownEnds.getOrDefault(spellId, 0L);
    }

    @Override
    public int getTotalCooldownTicks(Identifier spellId) {
        return cooldownDurations.getOrDefault(spellId, 0);
    }

    @Override
    public void setCooldown(Identifier spellId, int ticks) {
        long endTime = player.getWorld().getTime() + ticks;
        cooldownEnds.put(spellId, endTime);
        cooldownDurations.put(spellId, ticks);
        sync();
    }

    private void sync() {
        if (this.player != null && this.player.getWorld() != null && !this.player.getWorld().isClient()) {
            try {
                ModComponents.SPELLS.sync(this.player);
            } catch (Exception e) {
                // Ignore sync exceptions if not ready
            }
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        // Зберігаємо лише спеллбар
        NbtList hotbarList = new NbtList();
        for (Identifier spellId : equippedSpells) {
            hotbarList.add(NbtString.of(spellId != null ? spellId.toString() : ""));
        }
        tag.put("SpellHotbar", hotbarList);

        // Зберігаємо кулдауни
        NbtCompound cooldownEndTag = new NbtCompound();
        for (Map.Entry<Identifier, Long> entry : cooldownEnds.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                cooldownEndTag.putLong(entry.getKey().toString(), entry.getValue());
            }
        }
        tag.put("SpellCooldownEnds", cooldownEndTag);

        NbtCompound cooldownDurationTag = new NbtCompound();
        for (Map.Entry<Identifier, Integer> entry : cooldownDurations.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                cooldownDurationTag.putInt(entry.getKey().toString(), entry.getValue());
            }
        }
        tag.put("SpellCooldownDurations", cooldownDurationTag);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        Arrays.fill(this.equippedSpells, null);
        this.cooldownEnds.clear();
        this.cooldownDurations.clear();

        // Читаємо спеллбар
        if (tag.contains("SpellHotbar", NbtElement.LIST_TYPE)) {
            NbtList hotbarList = tag.getList("SpellHotbar", NbtElement.STRING_TYPE);
            for (int i = 0; i < Math.min(hotbarList.size(), equippedSpells.length); i++) {
                String str = hotbarList.getString(i);
                this.equippedSpells[i] = (str == null || str.isEmpty()) ? null : Identifier.tryParse(str);
            }
        }

        if (tag.contains("SpellCooldownEnds", NbtElement.COMPOUND_TYPE)) {
            NbtCompound cooldownEndTag = tag.getCompound("SpellCooldownEnds");
            for (String key : cooldownEndTag.getKeys()) {
                Identifier id = Identifier.tryParse(key);
                if (id != null) {
                    this.cooldownEnds.put(id, cooldownEndTag.getLong(key));
                }
            }
        }

        if (tag.contains("SpellCooldownDurations", NbtElement.COMPOUND_TYPE)) {
            NbtCompound cooldownDurationTag = tag.getCompound("SpellCooldownDurations");
            for (String key : cooldownDurationTag.getKeys()) {
                Identifier id = Identifier.tryParse(key);
                if (id != null) {
                    this.cooldownDurations.put(id, cooldownDurationTag.getInt(key));
                }
            }
        }
    }
}