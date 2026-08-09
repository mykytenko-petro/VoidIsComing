package com.voidiscoming.common.component.spell;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.voidiscoming.common.component.ModComponents;

public class PlayerSpellComponent implements SpellComponent, AutoSyncedComponent {
    private final PlayerEntity player;
    private final List<String> unlockedSpells = new ArrayList<>();
    
    private final String[] equippedSpells = new String[4];

    private final Map<String, Long> cooldownEnds = new HashMap<>();
    private final Map<String, Integer> cooldownDurations = new HashMap<>();

    public PlayerSpellComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public List<String> getUnlockedSpells() { 
        return this.unlockedSpells; 
    }

    @Override
    public void unlockSpell(String spellId) {
        if (spellId != null && !hasSpell(spellId)) {
            this.unlockedSpells.add(spellId);
            if (!this.player.getWorld().isClient()) {
                ModComponents.SPELLS.sync(this.player);
            }
        }
    }

    @Override
    public boolean hasSpell(String spellId) {
        return spellId != null && this.unlockedSpells.contains(spellId);
    }

    @Override
    public String[] getEquippedSpells() {
        return this.equippedSpells;
    }

    @Override
    public void equipSpell(int slot, String spellId) {
        if (slot >= 0 && slot < equippedSpells.length && hasSpell(spellId)) {
            for (int i = 0; i < equippedSpells.length; i++) {
                if (spellId.equals(equippedSpells[i])) {
                    equippedSpells[i] = null;
                }
            }
            equippedSpells[slot] = spellId;
            if (!this.player.getWorld().isClient()) {
                ModComponents.SPELLS.sync(this.player);
            }
        }
    }

    @Override
    public void unequipSpell(int slot) {
        if (slot >= 0 && slot < equippedSpells.length) {
            equippedSpells[slot] = null;
            if (!this.player.getWorld().isClient()) {
                ModComponents.SPELLS.sync(this.player);
            }
        }
    }

    @Override
    public boolean isOnCooldown(String spellId) {
        Long endTime = cooldownEnds.get(spellId);
        if (endTime == null) return false;
        return player.getWorld().getTime() < endTime;
    }

    @Override
    public long getCooldownEnd(String spellId) {
        return cooldownEnds.getOrDefault(spellId, 0L);
    }

    @Override
    public int getTotalCooldownTicks(String spellId) {
        return cooldownDurations.getOrDefault(spellId, 0);
    }

    @Override
    public void setCooldown(String spellId, int ticks) {
        long endTime = player.getWorld().getTime() + ticks;
        cooldownEnds.put(spellId, endTime);
        cooldownDurations.put(spellId, ticks);
        if (!this.player.getWorld().isClient()) {
            ModComponents.SPELLS.sync(this.player);
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        NbtList spellList = new NbtList();
        for (String spellId : unlockedSpells) {
            if (spellId != null) spellList.add(NbtString.of(spellId));
        }
        tag.put("UnlockedSpells", spellList);

        NbtList hotbarList = new NbtList();
        for (String spellId : equippedSpells) {
            hotbarList.add(NbtString.of(spellId != null ? spellId : ""));
        }
        tag.put("SpellHotbar", hotbarList);

        NbtCompound cooldownEndTag = new NbtCompound();
        for (Map.Entry<String, Long> entry : cooldownEnds.entrySet()) {
            cooldownEndTag.putLong(entry.getKey(), entry.getValue());
        }
        tag.put("SpellCooldownEnds", cooldownEndTag);

        NbtCompound cooldownDurationTag = new NbtCompound();
        for (Map.Entry<String, Integer> entry : cooldownDurations.entrySet()) {
            cooldownDurationTag.putInt(entry.getKey(), entry.getValue());
        }
        tag.put("SpellCooldownDurations", cooldownDurationTag);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.unlockedSpells.clear();
        Arrays.fill(this.equippedSpells, null);
        this.cooldownEnds.clear();
        this.cooldownDurations.clear();

        if (tag.contains("UnlockedSpells", NbtElement.LIST_TYPE)) {
            NbtList spellList = tag.getList("UnlockedSpells", NbtElement.STRING_TYPE);
            for (int i = 0; i < spellList.size(); i++) {
                String id = spellList.getString(i);
                if (!id.isEmpty() && !this.unlockedSpells.contains(id)) {
                    this.unlockedSpells.add(id);
                }
            }
        }

        if (tag.contains("SpellHotbar", NbtElement.LIST_TYPE)) {
            NbtList hotbarList = tag.getList("SpellHotbar", NbtElement.STRING_TYPE);
            for (int i = 0; i < Math.min(hotbarList.size(), equippedSpells.length); i++) {
                String id = hotbarList.getString(i);
                this.equippedSpells[i] = id.isEmpty() ? null : id;
            }
        }

        if (tag.contains("SpellCooldownEnds", NbtElement.COMPOUND_TYPE)) {
            NbtCompound cooldownEndTag = tag.getCompound("SpellCooldownEnds");
            for (String key : cooldownEndTag.getKeys()) {
                this.cooldownEnds.put(key, cooldownEndTag.getLong(key));
            }
        }

        if (tag.contains("SpellCooldownDurations", NbtElement.COMPOUND_TYPE)) {
            NbtCompound cooldownDurationTag = tag.getCompound("SpellCooldownDurations");
            for (String key : cooldownDurationTag.getKeys()) {
                this.cooldownDurations.put(key, cooldownDurationTag.getInt(key));
            }
        }

        if (this.unlockedSpells.isEmpty()) {
            this.unlockedSpells.add("vampirism");
            this.unlockedSpells.add("heal");
            this.unlockedSpells.add("invisibility"); 
            
            this.equippedSpells[0] = "vampirism";
            this.equippedSpells[1] = "heal";
            this.equippedSpells[2] = "invisibility";
        }
    }
}