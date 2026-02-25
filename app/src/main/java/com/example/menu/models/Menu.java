package com.example.menu.models;

public class Menu {

    private long id;
    private String name;

    public Menu(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Useful for ListView/ArrayAdapter (it will display the menu name)
    @Override
    public String toString() {
        return name;
    }
}