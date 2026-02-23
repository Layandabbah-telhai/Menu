package com.example.menu.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SeedData {

    public static void seedIfEmpty(MenuDbHelper helper) {

        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT COUNT(*) FROM " + DbSchema.Menus.TABLE, null);
        try {
            if (c.moveToFirst() && c.getInt(0) > 0) {
                return; // data already exists
            }
        } finally {
            c.close();
        }

        db.beginTransaction();

        try {

            // -------- MENU --------
            ContentValues menuValues = new ContentValues();
            menuValues.put(DbSchema.Menus.COL_NAME, "Dinner Menu");
            long menuId = db.insert(DbSchema.Menus.TABLE, null, menuValues);

            // -------- SECTIONS --------
            long startersId = insertSection(db, menuId, "Starters", 1);
            long mainsId = insertSection(db, menuId, "Main Courses", 2);
            long dessertsId = insertSection(db, menuId, "Desserts", 3);

            // -------- ITEMS --------
            long saladId = insertItem(db, startersId, "Greek Salad");
            long pizzaId = insertItem(db, mainsId, "Margherita Pizza");
            long cakeId = insertItem(db, dessertsId, "Chocolate Cake");

            // -------- VARIANTS --------
            insertVariant(db, saladId, "Regular", 38);
            insertVariant(db, pizzaId, "Small", 45);
            insertVariant(db, pizzaId, "Large", 60);
            insertVariant(db, cakeId, "Slice", 28);

            // -------- MODIFIER GROUP --------
            long toppingsGroupId = insertModifierGroup(db, "Pizza Toppings", 0, 3);

            insertModifierOption(db, toppingsGroupId, "Olives", 5);
            insertModifierOption(db, toppingsGroupId, "Mushrooms", 6);
            insertModifierOption(db, toppingsGroupId, "Extra Cheese", 8);

            linkItemToModifierGroup(db, pizzaId, toppingsGroupId);

            // -------- ALLERGEN --------
            long glutenId = insertAllergen(db, "Gluten");

            linkItemToAllergen(db, pizzaId, glutenId);

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }
    }

    private static long insertSection(SQLiteDatabase db, long menuId, String name, int order) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Sections.COL_MENU_ID, menuId);
        values.put(DbSchema.Sections.COL_NAME, name);
        values.put(DbSchema.Sections.COL_SORT_ORDER, order);
        return db.insert(DbSchema.Sections.TABLE, null, values);
    }

    private static long insertItem(SQLiteDatabase db, long sectionId, String name) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Items.COL_SECTION_ID, sectionId);
        values.put(DbSchema.Items.COL_NAME, name);
        values.put(DbSchema.Items.COL_DESCRIPTION, "");
        values.put(DbSchema.Items.COL_PRICE, 0); // price stored in variants
        return db.insert(DbSchema.Items.TABLE, null, values);
    }

    private static void insertVariant(SQLiteDatabase db, long itemId, String label, double price) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Variants.COL_ITEM_ID, itemId);
        values.put(DbSchema.Variants.COL_LABEL, label);
        values.put(DbSchema.Variants.COL_PRICE, price);
        db.insert(DbSchema.Variants.TABLE, null, values);
    }

    private static long insertModifierGroup(SQLiteDatabase db, String name, int min, int max) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.ModifierGroups.COL_NAME, name);
        values.put(DbSchema.ModifierGroups.COL_MIN_SELECT, min);
        values.put(DbSchema.ModifierGroups.COL_MAX_SELECT, max);
        return db.insert(DbSchema.ModifierGroups.TABLE, null, values);
    }

    private static long insertModifierOption(SQLiteDatabase db, long groupId, String name, double priceDelta) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.ModifierOptions.COL_GROUP_ID, groupId);
        values.put(DbSchema.ModifierOptions.COL_NAME, name);
        values.put(DbSchema.ModifierOptions.COL_PRICE_DELTA, priceDelta);
        return db.insert(DbSchema.ModifierOptions.TABLE, null, values);
    }

    private static void linkItemToModifierGroup(SQLiteDatabase db, long itemId, long groupId) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.ItemModifierGroups.COL_ITEM_ID, itemId);
        values.put(DbSchema.ItemModifierGroups.COL_GROUP_ID, groupId);
        db.insert(DbSchema.ItemModifierGroups.TABLE, null, values);
    }

    private static long insertAllergen(SQLiteDatabase db, String name) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Allergens.COL_NAME, name);
        return db.insert(DbSchema.Allergens.TABLE, null, values);
    }

    private static void linkItemToAllergen(SQLiteDatabase db, long itemId, long allergenId) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.ItemAllergens.COL_ITEM_ID, itemId);
        values.put(DbSchema.ItemAllergens.COL_ALLERGEN_ID, allergenId);
        db.insert(DbSchema.ItemAllergens.TABLE, null, values);
    }
}