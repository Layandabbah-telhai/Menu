package com.example.menu.models;

public class Allergen {

    private long id;
    private String name;

    public Allergen(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}