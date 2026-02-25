package com.example.menu.models;

public class ModifierOption {

    private long id;
    private long groupId;
    private String name;
    private double priceDelta;

    public ModifierOption(long id, long groupId, String name, double priceDelta) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.priceDelta = priceDelta;
    }

    public long getId() {
        return id;
    }

    public long getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public double getPriceDelta() {
        return priceDelta;
    }

    @Override
    public String toString() {
        return name + " (+" + priceDelta + ")";
    }
}