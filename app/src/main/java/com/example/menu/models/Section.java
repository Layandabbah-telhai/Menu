package com.example.menu.models;

public class Section {

    private long id;
    private long menuId;
    private String name;

    public Section(long id, long menuId, String name) {
        this.id = id;
        this.menuId = menuId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public long getMenuId() {
        return menuId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}