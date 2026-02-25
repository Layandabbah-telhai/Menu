package com.example.menu.data;

public final class DbSchema {

    private DbSchema() {}

    public static final String DB_NAME = "menu.db";
    public static final int DB_VERSION = 1;

    // ---------------- MENUS ----------------
    public static final class Menus {
        public static final String TABLE = "menus";
        public static final String COL_ID = "menu_id";
        public static final String COL_NAME = "name";
        public static final String COL_IS_ACTIVE = "is_active";
    }

    // ---------------- SECTIONS ----------------
    public static final class Sections {
        public static final String TABLE = "sections";
        public static final String COL_ID = "section_id";
        public static final String COL_MENU_ID = "menu_id";
        public static final String COL_NAME = "name";
        public static final String COL_SORT_ORDER = "sort_order";
    }

    // ---------------- ITEMS ----------------
    public static final class Items {
        public static final String TABLE = "items";
        public static final String COL_ID = "item_id";
        public static final String COL_SECTION_ID = "section_id";
        public static final String COL_NAME = "name";
        public static final String COL_DESCRIPTION = "description";
        public static final String COL_IS_ACTIVE = "is_active";
        public static final String COL_PRICE = "price";
    }

    // ---------------- VARIANTS ----------------
    public static final class Variants {
        public static final String TABLE = "item_variants";
        public static final String COL_ID = "variant_id";
        public static final String COL_ITEM_ID = "item_id";
        public static final String COL_LABEL = "label";
        public static final String COL_PRICE = "price";
    }

    // ---------------- MODIFIER GROUPS ----------------
    public static final class ModifierGroups {
        public static final String TABLE = "modifier_groups";
        public static final String COL_ID = "group_id";
        public static final String COL_NAME = "name";
        public static final String COL_MIN_SELECT = "min_select";
        public static final String COL_MAX_SELECT = "max_select";
    }

    // ---------------- MODIFIER OPTIONS ----------------
    public static final class ModifierOptions {
        public static final String TABLE = "modifier_options";
        public static final String COL_ID = "option_id";
        public static final String COL_GROUP_ID = "group_id";
        public static final String COL_NAME = "name";
        public static final String COL_PRICE_DELTA = "price_delta";
    }

    // ---------------- ITEM MODIFIER GROUPS ----------------
    public static final class ItemModifierGroups {
        public static final String TABLE = "item_modifier_groups";
        public static final String COL_ITEM_ID = "item_id";
        public static final String COL_GROUP_ID = "group_id";
    }

    // ---------------- ALLERGENS ----------------
    public static final class Allergens {
        public static final String TABLE = "allergens";
        public static final String COL_ID = "allergen_id";
        public static final String COL_NAME = "name";
    }

    // ---------------- ITEM ALLERGENS ----------------
    public static final class ItemAllergens {
        public static final String TABLE = "item_allergens";
        public static final String COL_ITEM_ID = "item_id";
        public static final String COL_ALLERGEN_ID = "allergen_id";
    }
}