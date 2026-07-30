package com.voidiscoming.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerSkillComponent implements SkillComponent, AutoSyncedComponent {
    private final PlayerEntity player;
    private final List<String> unlockedSkills = new ArrayList<>();
    
    // Хотбар на 4 слота
    private final String[] equippedSkills = new String[4];

    public PlayerSkillComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public List<String> getUnlockedSkills() { 
        return this.unlockedSkills; 
    }

    @Override
    public void unlockSkill(String skillId) {
        if (skillId != null && !hasSkill(skillId)) {
            this.unlockedSkills.add(skillId);
            ModComponents.SKILLS.sync(this.player);
        }
    }

    @Override
    public boolean hasSkill(String skillId) {
        return skillId != null && this.unlockedSkills.contains(skillId);
    }

    // --- Логика Хотбара ---
    @Override
    public String[] getEquippedSkills() {
        return this.equippedSkills;
    }

    @Override
    public void equipSkill(int slot, String skillId) {
        if (slot >= 0 && slot < equippedSkills.length && hasSkill(skillId)) {
            // Если скилл уже экипирован в другом слоте — убираем его оттуда
            for (int i = 0; i < equippedSkills.length; i++) {
                if (skillId.equals(equippedSkills[i])) {
                    equippedSkills[i] = null;
                }
            }
            equippedSkills[slot] = skillId;
            ModComponents.SKILLS.sync(this.player);
        }
    }

    @Override
    public void unequipSkill(int slot) {
        if (slot >= 0 && slot < equippedSkills.length) {
            equippedSkills[slot] = null;
            ModComponents.SKILLS.sync(this.player);
        }
    }

    // --- Запись хотбара в NBT ---
    @Override
    public void writeToNbt(NbtCompound tag) {
        // Изученные скиллы
        NbtList skillList = new NbtList();
        for (String skillId : unlockedSkills) {
            if (skillId != null) skillList.add(NbtString.of(skillId));
        }
        tag.put("UnlockedSkills", skillList);

        // Хотбар скиллов
        NbtList hotbarList = new NbtList();
        for (String skillId : equippedSkills) {
            hotbarList.add(NbtString.of(skillId != null ? skillId : ""));
        }
        tag.put("SkillHotbar", hotbarList);
    }

    // --- Чтение хотбара из NBT ---
    @Override
    public void readFromNbt(NbtCompound tag) {
        this.unlockedSkills.clear();
        Arrays.fill(this.equippedSkills, null);

        if (tag.contains("UnlockedSkills", NbtElement.LIST_TYPE)) {
            NbtList skillList = tag.getList("UnlockedSkills", NbtElement.STRING_TYPE);
            for (int i = 0; i < skillList.size(); i++) {
                String id = skillList.getString(i);
                if (!id.isEmpty()) this.unlockedSkills.add(id);
            }
        }

        if (tag.contains("SkillHotbar", NbtElement.LIST_TYPE)) {
            NbtList hotbarList = tag.getList("SkillHotbar", NbtElement.STRING_TYPE);
            for (int i = 0; i < Math.min(hotbarList.size(), equippedSkills.length); i++) {
                String id = hotbarList.getString(i);
                this.equippedSkills[i] = id.isEmpty() ? null : id;
            }
        }
    }
}