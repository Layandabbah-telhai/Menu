package com.example.menu.models;

public class Item {

    private long id;
    private long sectionId;
    private String name;
    private String description;
    private boolean isActive;

    // NEW: optional single fallback image (gallery is in item_images)
    private String imageResName;

    public Item(long id, long sectionId, String name, String description, boolean isActive) {
        this(id, sectionId, name, description, isActive, null);
    }

    public Item(long id, long sectionId, String name, String description, boolean isActive, String imageResName) {
        this.id = id;
        this.sectionId = sectionId;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.imageResName = imageResName;
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

    public String getImageResName() {
        return imageResName;
    }

    @Override
    public String toString() {
        return name;
    }
}