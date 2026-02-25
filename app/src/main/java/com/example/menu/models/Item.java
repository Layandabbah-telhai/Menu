package com.example.menu.models;

public class Item {

    private long id;
    private long sectionId;
    private String name;
    private String description;
    private boolean isActive;

    public Item(long id, long sectionId, String name, String description, boolean isActive) {
        this.id = id;
        this.sectionId = sectionId;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public long getSectionId() {
        return sectionId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return name;
    }
}