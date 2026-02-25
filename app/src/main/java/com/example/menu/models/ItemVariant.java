package com.example.menu.models;

public class ItemVariant {

    private long id;
    private long itemId;
    private String label;
    private double price;

    public ItemVariant(long id, long itemId, String label, double price) {
        this.id = id;
        this.itemId = itemId;
        this.label = label;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public long getItemId() {
        return itemId;
    }

    public String getLabel() {
        return label;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return label + " (" + price + ")";
    }
}