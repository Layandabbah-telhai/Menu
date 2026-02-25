package com.example.menu.models;

public class ModifierGroup {

    private long id;
    private String name;
    private int minSelect;
    private int maxSelect;

    public ModifierGroup(long id, String name, int minSelect, int maxSelect) {
        this.id = id;
        this.name = name;
        this.minSelect = minSelect;
        this.maxSelect = maxSelect;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMinSelect() {
        return minSelect;
    }

    public int getMaxSelect() {
        return maxSelect;
    }

    @Override
    public String toString() {
        return name + " (min=" + minSelect + ", max=" + maxSelect + ")";
    }
}