package com.voidiscoming.common.skill;

import net.minecraft.util.Identifier;

public class Skill {
    private final String id;
    private final String name;
    private final Identifier icon;

    public Skill(String id, String name, Identifier icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Identifier getIcon() { return icon; }
}