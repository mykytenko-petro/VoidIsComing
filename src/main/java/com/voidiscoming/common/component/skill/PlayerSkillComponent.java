package com.voidiscoming.common.component.skill;

import com.voidiscoming.common.mechanic.skill.ModSkills;
import com.voidiscoming.common.component.ModComponents;
import com.voidiscoming.common.mechanic.skill.SkillNode;
import com.voidiscoming.common.mechanic.skill.SkillType;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;

public class PlayerSkillComponent implements SkillComponent {
    private final PlayerEntity player;
    private int skillPoints = 0;
    private final Set<Identifier> unlockedSkills = new HashSet<>();

    public PlayerSkillComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override public int getSkillPoints() { return skillPoints; }
    
    @Override 
    public void setSkillPoints(int points) { 
        this.skillPoints = points; 
        ModComponents.SKILLS.sync(player); 
    }

    @Override 
    public void addSkillPoints(int points) { 
        this.skillPoints += points; 
        ModComponents.SKILLS.sync(player); 
    }

    @Override public Set<Identifier> getUnlockedSkills() { return unlockedSkills; }
    @Override public boolean hasUnlocked(Identifier skillId) { return unlockedSkills.contains(skillId); }

    @Override
    public boolean hasUnlockedSpell(Identifier spellId) {
        if (spellId == null) return false;
        
        for (Identifier skillId : unlockedSkills) {
            SkillNode node = ModSkills.get(skillId);
            if (node != null && node.type() == SkillType.SPELL) {
                if (node.spellId().isPresent() && node.spellId().get().equals(spellId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canUnlock(Identifier skillId) {
        if (hasUnlocked(skillId)) return false;

        SkillNode node = ModSkills.get(skillId);
        if (node == null) return false;

        if (skillPoints < node.cost()) return false;
        if (node.isRoot()) return true;

        return hasUnlocked(node.parentId().get());
    }

    @Override
    public boolean unlockSkill(Identifier skillId) {
        if (!canUnlock(skillId)) return false;

        SkillNode node = ModSkills.get(skillId);
        this.skillPoints -= node.cost();
        this.unlockedSkills.add(skillId);

        ModComponents.SKILLS.sync(player);
        return true;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.skillPoints = tag.getInt("SkillPoints");
        this.unlockedSkills.clear();

        NbtList list = tag.getList("UnlockedSkills", NbtElement.STRING_TYPE);
        for (int i = 0; i < list.size(); i++) {
            this.unlockedSkills.add(new Identifier(list.getString(i)));
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("SkillPoints", skillPoints);
        
        NbtList list = new NbtList();
        for (Identifier id : unlockedSkills) {
            list.add(NbtString.of(id.toString()));
        }
        tag.put("UnlockedSkills", list);
    }
}