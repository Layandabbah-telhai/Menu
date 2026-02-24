package com.example.menu.models;

import java.util.List;

public class ModifierGroupWithOptions {

    private final ModifierGroup group;
    private final List<ModifierOption> options;

    public ModifierGroupWithOptions(ModifierGroup group, List<ModifierOption> options) {
        this.group = group;
        this.options = options;
    }

    public ModifierGroup getGroup() {
        return group;
    }

    public List<ModifierOption> getOptions() {
        return options;
    }
}