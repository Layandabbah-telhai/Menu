package com.example.menu.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.menu.data.DbSchema;
import com.example.menu.data.MenuDbHelper;
import com.example.menu.models.Allergen;
import com.example.menu.models.Item;
import com.example.menu.models.ItemVariant;
import com.example.menu.models.Menu;
import com.example.menu.models.ModifierGroup;
import com.example.menu.models.ModifierOption;
import com.example.menu.models.Section;
import com.example.menu.models.ModifierGroupWithOptions;

import java.util.ArrayList;
import java.util.List;
import com.example.menu.models.ItemImage;
public class MenuRepository {

    private final MenuDbHelper dbHelper;

    public MenuRepository(Context context) {
        dbHelper = new MenuDbHelper(context);
    }

    // ---------------- GET MENUS ----------------
    public List<Menu> getMenus() {
        List<Menu> menus = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DbSchema.Menus.TABLE,
                null, null, null, null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(DbSchema.Menus.COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DbSchema.Menus.COL_NAME));
                menus.add(new Menu(id, name));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return menus;
    }

    // ---------------- GET SECTIONS BY MENU ----------------
    public List<Section> getSections(long menuId) {
        List<Section> sections = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DbSchema.Sections.TABLE,
                null,
                DbSchema.Sections.COL_MENU_ID + "=?",
                new String[]{String.valueOf(menuId)},
                null, null,
                DbSchema.Sections.COL_SORT_ORDER + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(DbSchema.Sections.COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DbSchema.Sections.COL_NAME));
                sections.add(new Section(id, menuId, name));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return sections;
    }

    // ---------------- GET ITEMS BY SECTION ----------------
    public List<Item> getItems(long sectionId) {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DbSchema.Items.TABLE,
                null,
                DbSchema.Items.COL_SECTION_ID + "=?",
                new String[]{String.valueOf(sectionId)},
                null, null,
                DbSchema.Items.COL_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                items.add(readItemFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return items;
    }

    // ---------------- GET ITEM BY ID ----------------
    public Item getItemById(long itemId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DbSchema.Items.TABLE,
                null,
                DbSchema.Items.COL_ID + "=?",
                new String[]{String.valueOf(itemId)},
                null, null,
                null
        );

        Item item = null;
        if (cursor.moveToFirst()) {
            item = readItemFromCursor(cursor);
        }

        cursor.close();
        db.close();
        return item;
    }

    // ---------------- INSERT ITEM ----------------
    public long insertItem(long sectionId, String name, String description, boolean isActive) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbSchema.Items.COL_SECTION_ID, sectionId);
        values.put(DbSchema.Items.COL_NAME, name);
        values.put(DbSchema.Items.COL_DESCRIPTION, description);
        values.put(DbSchema.Items.COL_PRICE, 0); // not used (price is on variants)
        values.put(DbSchema.Items.COL_IS_ACTIVE, isActive ? 1 : 0);

        long newId = db.insert(DbSchema.Items.TABLE, null, values);

        db.close();
        return newId;
    }

    // ---------------- UPDATE ITEM ----------------
    public int updateItem(long itemId, String newName, String newDescription, boolean isActive) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbSchema.Items.COL_NAME, newName);
        values.put(DbSchema.Items.COL_DESCRIPTION, newDescription);
        values.put(DbSchema.Items.COL_IS_ACTIVE, isActive ? 1 : 0);

        int rows = db.update(
                DbSchema.Items.TABLE,
                values,
                DbSchema.Items.COL_ID + "=?",
                new String[]{String.valueOf(itemId)}
        );

        db.close();
        return rows;
    }

    // ---------------- DELETE ITEM ----------------
    public int deleteItem(long itemId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rows = db.delete(
                DbSchema.Items.TABLE,
                DbSchema.Items.COL_ID + "=?",
                new String[]{String.valueOf(itemId)}
        );

        db.close();
        return rows;
    }

    // ---------------- SEARCH ITEMS BY NAME ----------------
    public List<Item> searchItemsByName(String queryText) {
        List<Item> items = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = DbSchema.Items.COL_NAME + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + queryText + "%"};

        Cursor cursor = db.query(
                DbSchema.Items.TABLE,
                null,
                selection,
                selectionArgs,
                null, null,
                DbSchema.Items.COL_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                items.add(readItemFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return items;
    }

    // ---------------- GET VARIANTS BY ITEM ----------------
    public List<ItemVariant> getVariants(long itemId) {
        List<ItemVariant> variants = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DbSchema.Variants.TABLE,
                null,
                DbSchema.Variants.COL_ITEM_ID + "=?",
                new String[]{String.valueOf(itemId)},
                null, null,
                DbSchema.Variants.COL_ID + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(DbSchema.Variants.COL_ID));
                String label = cursor.getString(cursor.getColumnIndexOrThrow(DbSchema.Variants.COL_LABEL));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DbSchema.Variants.COL_PRICE));
                variants.add(new ItemVariant(id, itemId, label, price));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return variants;
    }

    // ---------------- GET MODIFIER GROUPS FOR ITEM ----------------
    public List<ModifierGroup> getModifierGroups(long itemId) {
        List<ModifierGroup> groups = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql =
                "SELECT g." + DbSchema.ModifierGroups.COL_ID + ", " +
                        "g." + DbSchema.ModifierGroups.COL_NAME + ", " +
                        "g." + DbSchema.ModifierGroups.COL_MIN_SELECT + ", " +
                        "g." + DbSchema.ModifierGroups.COL_MAX_SELECT +
                        " FROM " + DbSchema.ModifierGroups.TABLE + " g " +
                        " INNER JOIN " + DbSchema.ItemModifierGroups.TABLE + " img " +
                        " ON g." + DbSchema.ModifierGroups.COL_ID + " = img." + DbSchema.ItemModifierGroups.COL_GROUP_ID +
                        " WHERE img." + DbSchema.ItemModifierGroups.COL_ITEM_ID + " = ?";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(itemId)});

        if (cursor.moveToFirst()) {
            do {
                long groupId = cursor.getLong(0);
                String name = cursor.getString(1);
                int min = cursor.getInt(2);
                int max = cursor.getInt(3);
                groups.add(new ModifierGroup(groupId, name, min, max));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return groups;
    }

    // ---------------- GET OPTIONS FOR GROUP ----------------
    public List<ModifierOption> getModifierOptions(long groupId) {
        List<ModifierOption> options = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DbSchema.ModifierOptions.TABLE,
                null,
                DbSchema.ModifierOptions.COL_GROUP_ID + "=?",
                new String[]{String.valueOf(groupId)},
                null, null,
                DbSchema.ModifierOptions.COL_ID + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                long optionId = cursor.getLong(cursor.getColumnIndexOrThrow(DbSchema.ModifierOptions.COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DbSchema.ModifierOptions.COL_NAME));
                double delta = cursor.getDouble(cursor.getColumnIndexOrThrow(DbSchema.ModifierOptions.COL_PRICE_DELTA));
                options.add(new ModifierOption(optionId, groupId, name, delta));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return options;
    }

    // ---------------- GET ALLERGENS FOR ITEM ----------------
    public List<Allergen> getAllergens(long itemId) {
        List<Allergen> allergens = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql =
                "SELECT a." + DbSchema.Allergens.COL_ID + ", a." + DbSchema.Allergens.COL_NAME +
                        " FROM " + DbSchema.Allergens.TABLE + " a " +
                        " INNER JOIN " + DbSchema.ItemAllergens.TABLE + " ia " +
                        " ON a." + DbSchema.Allergens.COL_ID + " = ia." + DbSchema.ItemAllergens.COL_ALLERGEN_ID +
                        " WHERE ia." + DbSchema.ItemAllergens.COL_ITEM_ID + " = ?";

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(itemId)});

        if (cursor.moveToFirst()) {
            do {
                long allergenId = cursor.getLong(0);
                String name = cursor.getString(1);
                allergens.add(new Allergen(allergenId, name));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return allergens;
    }



    // ---------------- HELPER: READ ITEM FROM CURSOR ----------------
    private Item readItemFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DbSchema.Items.COL_ID));
        long sectionId = cursor.getLong(cursor.getColumnIndexOrThrow(DbSchema.Items.COL_SECTION_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(DbSchema.Items.COL_NAME));
        String desc = cursor.getString(cursor.getColumnIndexOrThrow(DbSchema.Items.COL_DESCRIPTION));
        int activeInt = cursor.getInt(cursor.getColumnIndexOrThrow(DbSchema.Items.COL_IS_ACTIVE));
        boolean isActive = activeInt == 1;

        return new Item(id, sectionId, name, desc, isActive);
    }

    // ---------------- GET MODIFIERS FOR ITEM (groups + options) ----------------
    public List<ModifierGroupWithOptions> getModifiersForItem(long itemId) {
        List<ModifierGroupWithOptions> result = new ArrayList<>();

        List<ModifierGroup> groups = getModifierGroups(itemId); // already exists
        if (groups == null) return result;

        for (ModifierGroup g : groups) {
            List<ModifierOption> options = getModifierOptions(g.getId()); // already exists
            result.add(new ModifierGroupWithOptions(g, options));
        }

        return result;
    }
    public List<ItemImage> getItemImages(long itemId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ItemImage> result = new ArrayList<>();

        Cursor cursor = db.query(
                DbSchema.ItemImages.TABLE,
                null,
                DbSchema.ItemImages.COL_ITEM_ID + "=?",
                new String[]{String.valueOf(itemId)},
                null, null,
                DbSchema.ItemImages.COL_SORT_ORDER + " ASC"
        );

        try {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(DbSchema.ItemImages.COL_ID));
                String res = cursor.getString(cursor.getColumnIndexOrThrow(DbSchema.ItemImages.COL_IMAGE_RES));
                int order = cursor.getInt(cursor.getColumnIndexOrThrow(DbSchema.ItemImages.COL_SORT_ORDER));
                result.add(new ItemImage(id, itemId, res, order));
            }
        } finally {
            cursor.close();
            db.close();
        }

        return result;
    }
}