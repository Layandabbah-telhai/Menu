package com.example.menu.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MenuDbHelper extends SQLiteOpenHelper {

    public MenuDbHelper(Context context) {
        super(context, DbSchema.DB_NAME, null, DbSchema.DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE " + DbSchema.Menus.TABLE + " (" +
                        DbSchema.Menus.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DbSchema.Menus.COL_NAME + " TEXT NOT NULL, " +
                        DbSchema.Menus.COL_IS_ACTIVE + " INTEGER NOT NULL DEFAULT 1" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE " + DbSchema.Sections.TABLE + " (" +
                        DbSchema.Sections.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DbSchema.Sections.COL_MENU_ID + " INTEGER NOT NULL, " +
                        DbSchema.Sections.COL_NAME + " TEXT NOT NULL, " +
                        DbSchema.Sections.COL_SORT_ORDER + " INTEGER DEFAULT 0, " +
                        "FOREIGN KEY(" + DbSchema.Sections.COL_MENU_ID + ") REFERENCES " +
                        DbSchema.Menus.TABLE + "(" + DbSchema.Menus.COL_ID + ") ON DELETE CASCADE" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE " + DbSchema.Items.TABLE + " (" +
                        DbSchema.Items.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DbSchema.Items.COL_SECTION_ID + " INTEGER NOT NULL, " +
                        DbSchema.Items.COL_NAME + " TEXT NOT NULL, " +
                        DbSchema.Items.COL_DESCRIPTION + " TEXT, " +
                        DbSchema.Items.COL_PRICE + " REAL NOT NULL, " +
                        DbSchema.Items.COL_IS_ACTIVE + " INTEGER DEFAULT 1, " +
                        DbSchema.Items.COL_IMAGE_RES + " TEXT, " +
                        "FOREIGN KEY(" + DbSchema.Items.COL_SECTION_ID + ") REFERENCES " +
                        DbSchema.Sections.TABLE + "(" + DbSchema.Sections.COL_ID + ") ON DELETE CASCADE" +
                        ");"
        );

        // NEW: ITEM IMAGES table
        db.execSQL(
                "CREATE TABLE " + DbSchema.ItemImages.TABLE + " (" +
                        DbSchema.ItemImages.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DbSchema.ItemImages.COL_ITEM_ID + " INTEGER NOT NULL, " +
                        DbSchema.ItemImages.COL_IMAGE_RES + " TEXT NOT NULL, " +
                        DbSchema.ItemImages.COL_SORT_ORDER + " INTEGER NOT NULL DEFAULT 0, " +
                        "FOREIGN KEY(" + DbSchema.ItemImages.COL_ITEM_ID + ") REFERENCES " +
                        DbSchema.Items.TABLE + "(" + DbSchema.Items.COL_ID + ") ON DELETE CASCADE" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE " + DbSchema.Variants.TABLE + " (" +
                        DbSchema.Variants.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DbSchema.Variants.COL_ITEM_ID + " INTEGER NOT NULL, " +
                        DbSchema.Variants.COL_LABEL + " TEXT NOT NULL, " +
                        DbSchema.Variants.COL_PRICE + " REAL NOT NULL, " +
                        "FOREIGN KEY(" + DbSchema.Variants.COL_ITEM_ID + ") REFERENCES " +
                        DbSchema.Items.TABLE + "(" + DbSchema.Items.COL_ID + ") ON DELETE CASCADE" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE " + DbSchema.ModifierGroups.TABLE + " (" +
                        DbSchema.ModifierGroups.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DbSchema.ModifierGroups.COL_NAME + " TEXT NOT NULL, " +
                        DbSchema.ModifierGroups.COL_MIN_SELECT + " INTEGER DEFAULT 0, " +
                        DbSchema.ModifierGroups.COL_MAX_SELECT + " INTEGER DEFAULT 0" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE " + DbSchema.ModifierOptions.TABLE + " (" +
                        DbSchema.ModifierOptions.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DbSchema.ModifierOptions.COL_GROUP_ID + " INTEGER NOT NULL, " +
                        DbSchema.ModifierOptions.COL_NAME + " TEXT NOT NULL, " +
                        DbSchema.ModifierOptions.COL_PRICE_DELTA + " REAL NOT NULL, " +
                        "FOREIGN KEY(" + DbSchema.ModifierOptions.COL_GROUP_ID + ") REFERENCES " +
                        DbSchema.ModifierGroups.TABLE + "(" + DbSchema.ModifierGroups.COL_ID + ") ON DELETE CASCADE" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE " + DbSchema.ItemModifierGroups.TABLE + " (" +
                        DbSchema.ItemModifierGroups.COL_ITEM_ID + " INTEGER NOT NULL, " +
                        DbSchema.ItemModifierGroups.COL_GROUP_ID + " INTEGER NOT NULL, " +
                        "PRIMARY KEY(" + DbSchema.ItemModifierGroups.COL_ITEM_ID + ", " + DbSchema.ItemModifierGroups.COL_GROUP_ID + "), " +
                        "FOREIGN KEY(" + DbSchema.ItemModifierGroups.COL_ITEM_ID + ") REFERENCES " +
                        DbSchema.Items.TABLE + "(" + DbSchema.Items.COL_ID + ") ON DELETE CASCADE, " +
                        "FOREIGN KEY(" + DbSchema.ItemModifierGroups.COL_GROUP_ID + ") REFERENCES " +
                        DbSchema.ModifierGroups.TABLE + "(" + DbSchema.ModifierGroups.COL_ID + ") ON DELETE CASCADE" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE " + DbSchema.Allergens.TABLE + " (" +
                        DbSchema.Allergens.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DbSchema.Allergens.COL_NAME + " TEXT NOT NULL" +
                        ");"
        );

        db.execSQL(
                "CREATE TABLE " + DbSchema.ItemAllergens.TABLE + " (" +
                        DbSchema.ItemAllergens.COL_ITEM_ID + " INTEGER NOT NULL, " +
                        DbSchema.ItemAllergens.COL_ALLERGEN_ID + " INTEGER NOT NULL, " +
                        "PRIMARY KEY(" + DbSchema.ItemAllergens.COL_ITEM_ID + ", " + DbSchema.ItemAllergens.COL_ALLERGEN_ID + "), " +
                        "FOREIGN KEY(" + DbSchema.ItemAllergens.COL_ITEM_ID + ") REFERENCES " +
                        DbSchema.Items.TABLE + "(" + DbSchema.Items.COL_ID + ") ON DELETE CASCADE, " +
                        "FOREIGN KEY(" + DbSchema.ItemAllergens.COL_ALLERGEN_ID + ") REFERENCES " +
                        DbSchema.Allergens.TABLE + "(" + DbSchema.Allergens.COL_ID + ") ON DELETE CASCADE" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.ItemAllergens.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.Allergens.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.ItemModifierGroups.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.ModifierOptions.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.ModifierGroups.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.Variants.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.ItemImages.TABLE); // NEW
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.Items.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.Sections.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbSchema.Menus.TABLE);

        onCreate(db);
    }
}