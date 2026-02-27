package com.example.menu.models;

public class ItemImage {
    private final long id;
    private final long itemId;
    private final String imageResName;
    private final int sortOrder;

    public ItemImage(long id, long itemId, String imageResName, int sortOrder) {
        this.id = id;
        this.itemId = itemId;
        this.imageResName = imageResName;
        this.sortOrder = sortOrder;
    }

    public long getId() { return id; }
    public long getItemId() { return itemId; }
    public String getImageResName() { return imageResName; }
    public int getSortOrder() { return sortOrder; }
}