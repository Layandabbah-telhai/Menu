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
            // =========================
            // ===== MENUS + SECTIONS ===
            // =========================
            long breakfastId = insertMenu(db, "Breakfast");
            long lunchId = insertMenu(db, "Lunch");
            long dinnerId = insertMenu(db, "Dinner");

            // Breakfast Sections
            long bfApp = insertSection(db, breakfastId, "Appetizers", 1);
            long bfMain = insertSection(db, breakfastId, "Main Dishes", 2);
            long bfHot = insertSection(db, breakfastId, "Drinks - Hot", 3);
            long bfCold = insertSection(db, breakfastId, "Drinks - Cold", 4);
            long bfDess = insertSection(db, breakfastId, "Desserts", 5);

            // Lunch Sections
            long lnApp = insertSection(db, lunchId, "Appetizers", 1);
            long lnMain = insertSection(db, lunchId, "Main Dishes", 2);
            long lnHot = insertSection(db, lunchId, "Drinks - Hot", 3);
            long lnCold = insertSection(db, lunchId, "Drinks - Cold", 4);
            long lnDess = insertSection(db, lunchId, "Desserts", 5);

            // Dinner Sections
            long dnApp = insertSection(db, dinnerId, "Appetizers", 1);
            long dnMain = insertSection(db, dinnerId, "Main Dishes", 2);
            long dnHot = insertSection(db, dinnerId, "Drinks - Hot", 3);
            long dnCold = insertSection(db, dinnerId, "Drinks - Cold", 4);
            long dnDess = insertSection(db, dinnerId, "Desserts", 5);

            // =========================
            // ===== SHARED ALLERGENS ===
            // =========================
            long allergenGluten = insertAllergen(db, "Gluten");
            long allergenDairy = insertAllergen(db, "Dairy");
            long allergenEggs = insertAllergen(db, "Eggs");
            long allergenNuts = insertAllergen(db, "Nuts");

            // =========================
            // ===== MODIFIER GROUPS ====
            // =========================
            long coffeeMilkGroup = insertModifierGroup(db, "Milk Choice", 0, 1);
            long milkRegular = insertModifierOption(db, coffeeMilkGroup, "Regular Milk", 0);
            long milkOat = insertModifierOption(db, coffeeMilkGroup, "Oat Milk", 3);
            long milkAlmond = insertModifierOption(db, coffeeMilkGroup, "Almond Milk", 3);

            long coffeeExtrasGroup = insertModifierGroup(db, "Coffee Extras", 0, 3);
            long extraShot = insertModifierOption(db, coffeeExtrasGroup, "Extra Espresso Shot", 4);
            long vanillaSyrup = insertModifierOption(db, coffeeExtrasGroup, "Vanilla Syrup", 3);
            long caramelSyrup = insertModifierOption(db, coffeeExtrasGroup, "Caramel Syrup", 3);

            long burgerAddonsGroup = insertModifierGroup(db, "Burger Add-ons", 0, 3);
            long addCheese = insertModifierOption(db, burgerAddonsGroup, "Add Cheese", 5);
            long addBacon = insertModifierOption(db, burgerAddonsGroup, "Add Bacon", 7);
            long addPatty = insertModifierOption(db, burgerAddonsGroup, "Extra Patty", 15);

            // =========================
            // ===== BREAKFAST ITEMS ====
            // =========================
            // Appetizers
            long yogurtId = insertItem(db, bfApp, "Greek Yogurt Bowl",
                    "Creamy Greek yogurt with honey, granola, and seasonal fruit.", true);
            insertVariant(db, yogurtId, "Regular", 22);
            insertVariant(db, yogurtId, "Large", 28);
            insertItemImage(db, yogurtId, "greek_yogurt_bowl", 1);
            insertItemImage(db, yogurtId, "greek_yogurt_bowl_2", 2);
            linkItemToAllergen(db, yogurtId, allergenDairy);

            long fruitPlateId = insertItem(db, bfApp, "Seasonal Fruits Plate",
                    "A fresh selection of seasonal fruits.", true);
            insertVariant(db, fruitPlateId, "Regular", 20);
            insertItemImage(db, fruitPlateId, "seasonal_fruits_plate_1", 1);
            insertItemImage(db, fruitPlateId, "seasonal_fruits_plate_2", 2);
            insertItemImage(db, fruitPlateId, "seasonal_fruits_plate_3", 3);

            long croissantId = insertItem(db, bfApp, "Butter Croissant",
                    "Flaky, buttery croissant baked daily.", true);
            insertVariant(db, croissantId, "Single", 14);
            insertVariant(db, croissantId, "With Jam", 16);
            insertItemImage(db, croissantId, "butter_croissant", 1);
            linkItemToAllergen(db, croissantId, allergenGluten);
            linkItemToAllergen(db, croissantId, allergenDairy);

            // Main Dishes
            long pancakeId = insertItem(db, bfMain, "Pancake Stack",
                    "Fluffy pancakes served with maple syrup.", true);
            insertVariant(db, pancakeId, "Classic", 34);
            insertVariant(db, pancakeId, "With Berries", 40);
            insertItemImage(db, pancakeId, "pancake_stack", 1);
            linkItemToAllergen(db, pancakeId, allergenGluten);
            linkItemToAllergen(db, pancakeId, allergenDairy);
            linkItemToAllergen(db, pancakeId, allergenEggs);

            long omeletteId = insertItem(db, bfMain, "Cheese Omelette",
                    "Three-egg omelette with melted cheese.", true);
            insertVariant(db, omeletteId, "Regular", 32);
            insertItemImage(db, omeletteId, "cheese_omelette", 1);
            linkItemToAllergen(db, omeletteId, allergenEggs);
            linkItemToAllergen(db, omeletteId, allergenDairy);

            long frenchToastId = insertItem(db, bfMain, "French Toast",
                    "Brioche toast with cinnamon and powdered sugar.", true);
            insertVariant(db, frenchToastId, "Regular", 36);
            insertItemImage(db, frenchToastId, "french_toast", 1);
            linkItemToAllergen(db, frenchToastId, allergenGluten);
            linkItemToAllergen(db, frenchToastId, allergenDairy);
            linkItemToAllergen(db, frenchToastId, allergenEggs);

            long shakshukaId = insertItem(db, bfMain, "Shakshuka",
                    "Eggs poached in a rich tomato and pepper sauce.", true);
            insertVariant(db, shakshukaId, "Regular", 39);
            insertItemImage(db, shakshukaId, "shakshuka", 1);
            linkItemToAllergen(db, shakshukaId, allergenEggs);

            // Hot Drinks
            long espressoId = insertItem(db, bfHot, "Espresso",
                    "Strong and rich espresso shot.", true);
            insertVariant(db, espressoId, "Single", 10);
            insertVariant(db, espressoId, "Double", 13);
            insertItemImage(db, espressoId, "espresso", 1);
            linkItemToModifierGroup(db, espressoId, coffeeExtrasGroup);

            long cappuccinoId = insertItem(db, bfHot, "Cappuccino",
                    "Espresso with steamed milk foam.", true);
            insertVariant(db, cappuccinoId, "Small", 14);
            insertVariant(db, cappuccinoId, "Large", 18);
            insertItemImage(db, cappuccinoId, "cappuccino", 1);
            linkItemToModifierGroup(db, cappuccinoId, coffeeMilkGroup);
            linkItemToModifierGroup(db, cappuccinoId, coffeeExtrasGroup);
            linkItemToAllergen(db, cappuccinoId, allergenDairy);

            long herbalTeaId = insertItem(db, bfHot, "Herbal Tea",
                    "A calming herbal tea selection.", true);
            insertVariant(db, herbalTeaId, "Cup", 12);
            insertVariant(db, herbalTeaId, "Pot", 18);
            insertItemImage(db, herbalTeaId, "herbal_tea", 1);

            // Cold Drinks
            long icedCoffeeId = insertItem(db, bfCold, "Iced Coffee",
                    "Cold brew style coffee served over ice.", true);
            insertVariant(db, icedCoffeeId, "Small", 16);
            insertVariant(db, icedCoffeeId, "Large", 20);
            insertItemImage(db, icedCoffeeId, "iced_coffee", 1);
            linkItemToModifierGroup(db, icedCoffeeId, coffeeMilkGroup);
            linkItemToModifierGroup(db, icedCoffeeId, coffeeExtrasGroup);

            long ojId = insertItem(db, bfCold, "Fresh Orange Juice",
                    "100% freshly squeezed oranges.", true);
            insertVariant(db, ojId, "Glass", 16);
            insertVariant(db, ojId, "Large", 22);
            insertItemImage(db, ojId, "fresh_orange_juice", 1);

            // Desserts
            long blueberryMuffinId = insertItem(db, bfDess, "Blueberry Muffin",
                    "Soft muffin packed with blueberries.", true);
            insertVariant(db, blueberryMuffinId, "Single", 15);
            insertItemImage(db, blueberryMuffinId, "blueberry_muffin", 1);
            linkItemToAllergen(db, blueberryMuffinId, allergenGluten);
            linkItemToAllergen(db, blueberryMuffinId, allergenDairy);
            linkItemToAllergen(db, blueberryMuffinId, allergenEggs);

            long miniCheesecakeId = insertItem(db, bfDess, "Mini Cheesecake",
                    "Creamy cheesecake bite with a crunchy base.", true);
            insertVariant(db, miniCheesecakeId, "Single", 22);
            insertItemImage(db, miniCheesecakeId, "mini_cheesecake", 1);
            linkItemToAllergen(db, miniCheesecakeId, allergenDairy);
            linkItemToAllergen(db, miniCheesecakeId, allergenEggs);

            // =========================
            // ===== LUNCH ITEMS ========
            // =========================
            // Appetizers
            long tomatoSoupId = insertItem(db, lnApp, "Tomato Soup",
                    "Creamy tomato soup served warm.", true);
            insertVariant(db, tomatoSoupId, "Bowl", 22);
            insertItemImage(db, tomatoSoupId, "tomato_soup", 1);
            linkItemToAllergen(db, tomatoSoupId, allergenDairy);

            long caesarId = insertItem(db, lnApp, "Caesar Salad",
                    "Romaine lettuce, parmesan, and house caesar dressing.", true);
            insertVariant(db, caesarId, "Regular", 29);
            insertItemImage(db, caesarId, "caesar_salad", 1);
            linkItemToAllergen(db, caesarId, allergenDairy);

            long friesId = insertItem(db, lnApp, "Crispy Fries",
                    "Golden fries served with house sauce.", true);
            insertVariant(db, friesId, "Regular", 19);
            insertVariant(db, friesId, "Large", 25);
            insertItemImage(db, friesId, "crispy_fries", 1);

            // Main Dishes
            long burgerId = insertItem(db, lnMain, "Beef Burger",
                    "Juicy beef burger with lettuce, tomato, and sauce.", true);
            insertVariant(db, burgerId, "Regular", 55);
            insertVariant(db, burgerId, "With Cheese", 60);
            insertItemImage(db, burgerId, "beef_burger", 1);
            linkItemToModifierGroup(db, burgerId, burgerAddonsGroup);
            linkItemToAllergen(db, burgerId, allergenGluten);

            long wrapId = insertItem(db, lnMain, "Chicken Wrap",
                    "Grilled chicken, fresh veggies, and house dressing.", true);
            insertVariant(db, wrapId, "Regular", 44);
            insertItemImage(db, wrapId, "chicken_wrap", 1);
            linkItemToAllergen(db, wrapId, allergenGluten);

            long alfredoId = insertItem(db, lnMain, "Chicken Alfredo",
                    "Creamy pasta with grilled chicken.", true);
            insertVariant(db, alfredoId, "Regular", 48);
            insertItemImage(db, alfredoId, "chicken_alfredo", 1);
            linkItemToAllergen(db, alfredoId, allergenGluten);
            linkItemToAllergen(db, alfredoId, allergenDairy);

            long veggieBowlId = insertItem(db, lnMain, "Veggie Bowl",
                    "Quinoa, roasted vegetables, and tahini dressing.", true);
            insertVariant(db, veggieBowlId, "Regular", 42);
            insertItemImage(db, veggieBowlId, "veggie_bowl", 1);

            // Hot Drinks
            long americanoId = insertItem(db, lnHot, "Americano",
                    "Espresso diluted with hot water.", true);
            insertVariant(db, americanoId, "Small", 13);
            insertVariant(db, americanoId, "Large", 16);
            insertItemImage(db, americanoId, "americano", 1);
            linkItemToModifierGroup(db, americanoId, coffeeExtrasGroup);

            long mintTeaId = insertItem(db, lnHot, "Mint Tea",
                    "Fresh mint tea infusion.", true);
            insertVariant(db, mintTeaId, "Cup", 12);
            insertItemImage(db, mintTeaId, "mint_tea", 1);

            // Cold Drinks
            long colaId = insertItem(db, lnCold, "Cola",
                    "Chilled can of cola.", true);
            insertVariant(db, colaId, "Can", 10);
            insertItemImage(db, colaId, "cola", 1);

            long sparklingWaterId = insertItem(db, lnCold, "Sparkling Water",
                    "Cold sparkling water bottle.", true);
            insertVariant(db, sparklingWaterId, "Bottle", 12);
            insertItemImage(db, sparklingWaterId, "sparkling_water", 1);

            long lemonadeId = insertItem(db, lnCold, "Fresh Lemonade",
                    "Fresh lemon and mint lemonade.", true);
            insertVariant(db, lemonadeId, "Glass", 14);
            insertVariant(db, lemonadeId, "Large", 20);
            insertItemImage(db, lemonadeId, "fresh_lemonade", 1);

            // Desserts
            long vanillaIceCreamId = insertItem(db, lnDess, "Vanilla Ice Cream",
                    "Classic vanilla ice cream.", true);
            insertVariant(db, vanillaIceCreamId, "Single Scoop", 15);
            insertVariant(db, vanillaIceCreamId, "Double Scoop", 22);
            insertItemImage(db, vanillaIceCreamId, "vanilla_ice_cream", 1);
            linkItemToAllergen(db, vanillaIceCreamId, allergenDairy);

            long brownieId = insertItem(db, lnDess, "Chocolate Brownie",
                    "Rich chocolate brownie with fudgy center.", true);
            insertVariant(db, brownieId, "Single", 18);
            insertItemImage(db, brownieId, "chocolate_brownie", 1);
            linkItemToAllergen(db, brownieId, allergenGluten);
            linkItemToAllergen(db, brownieId, allergenEggs);
            linkItemToAllergen(db, brownieId, allergenDairy);

            // =========================
            // ===== DINNER ITEMS =======
            // =========================
            // Appetizers
            long greekSaladId = insertItem(db, dnApp, "Greek Salad",
                    "Tomatoes, cucumbers, feta, olives, and oregano.", true);
            insertVariant(db, greekSaladId, "Regular", 38);
            insertItemImage(db, greekSaladId, "greek_salad", 1);
            linkItemToAllergen(db, greekSaladId, allergenDairy);

            long bruschettaId = insertItem(db, dnApp, "Bruschetta",
                    "Toasted bread topped with tomato and basil.", true);
            insertVariant(db, bruschettaId, "4 Pieces", 30);
            insertItemImage(db, bruschettaId, "bruschetta", 1);
            linkItemToAllergen(db, bruschettaId, allergenGluten);

            long wingsId = insertItem(db, dnApp, "Spicy Wings",
                    "Crispy chicken wings tossed in spicy sauce.", true);
            insertVariant(db, wingsId, "6 Pieces", 42);
            insertVariant(db, wingsId, "12 Pieces", 74);
            insertItemImage(db, wingsId, "spicy_wings", 1);

            // Main Dishes
            long steakId = insertItem(db, dnMain, "Grilled Steak",
                    "Grilled steak served with potatoes and salad.", true);
            insertVariant(db, steakId, "250g", 95);
            insertVariant(db, steakId, "350g", 125);
            insertItemImage(db, steakId, "grilled_steak", 1);

            long salmonId = insertItem(db, dnMain, "Baked Salmon",
                    "Oven baked salmon served with seasonal vegetables.", true);
            insertVariant(db, salmonId, "Regular", 82);
            insertItemImage(db, salmonId, "baked_salmon", 1);

            long pizzaId = insertItem(db, dnMain, "Margherita Pizza",
                    "Tomato sauce, mozzarella, basil.", true);
            insertVariant(db, pizzaId, "Small", 45);
            insertVariant(db, pizzaId, "Large", 60);
            insertItemImage(db, pizzaId, "margherita_pizza", 1);
            linkItemToAllergen(db, pizzaId, allergenGluten);
            linkItemToAllergen(db, pizzaId, allergenDairy);

            long risottoId = insertItem(db, dnMain, "Mushroom Risotto",
                    "Creamy risotto with mushrooms and parmesan.", true);
            insertVariant(db, risottoId, "Regular", 68);
            insertItemImage(db, risottoId, "mushroom_risotto", 1);
            linkItemToAllergen(db, risottoId, allergenDairy);

            // Hot Drinks
            long latteId = insertItem(db, dnHot, "Latte",
                    "Espresso with steamed milk.", true);
            insertVariant(db, latteId, "Small", 16);
            insertVariant(db, latteId, "Large", 20);
            insertItemImage(db, latteId, "latte", 1);
            linkItemToModifierGroup(db, latteId, coffeeMilkGroup);
            linkItemToModifierGroup(db, latteId, coffeeExtrasGroup);
            linkItemToAllergen(db, latteId, allergenDairy);

            // Cold Drinks
            long icedTeaId = insertItem(db, dnCold, "Iced Tea",
                    "Refreshing iced tea (peach/lemon).", true);
            insertVariant(db, icedTeaId, "Glass", 14);
            insertVariant(db, icedTeaId, "Large", 20);
            insertItemImage(db, icedTeaId, "iced_tea", 1);

            long sodaId = insertItem(db, dnCold, "Soda",
                    "Sprite / Fanta / Cola.", true);
            insertVariant(db, sodaId, "Can", 10);
            insertItemImage(db, sodaId, "soda", 1);

            // Desserts
            long lavaCakeId = insertItem(db, dnDess, "Chocolate Lava Cake",
                    "Warm cake with molten chocolate center.", true);
            insertVariant(db, lavaCakeId, "Single", 32);
            insertItemImage(db, lavaCakeId, "chocolate_lava_cake", 1);
            linkItemToAllergen(db, lavaCakeId, allergenGluten);
            linkItemToAllergen(db, lavaCakeId, allergenEggs);
            linkItemToAllergen(db, lavaCakeId, allergenDairy);

            long tiramisuId = insertItem(db, dnDess, "Tiramisu",
                    "Classic tiramisu with coffee and mascarpone.", true);
            insertVariant(db, tiramisuId, "Single", 34);
            insertItemImage(db, tiramisuId, "tiramisu", 1);
            linkItemToAllergen(db, tiramisuId, allergenGluten);
            linkItemToAllergen(db, tiramisuId, allergenEggs);
            linkItemToAllergen(db, tiramisuId, allergenDairy);

            long cheesecakeId = insertItem(db, dnDess, "Classic Cheesecake",
                    "Creamy cheesecake slice with biscuit base.", true);
            insertVariant(db, cheesecakeId, "Slice", 30);
            insertItemImage(db, cheesecakeId, "classic_cheesecake", 1);
            linkItemToAllergen(db, cheesecakeId, allergenDairy);
            linkItemToAllergen(db, cheesecakeId, allergenEggs);

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }
    }

    // =========================
    // ===== Insert Helpers =====
    // =========================

    private static long insertMenu(SQLiteDatabase db, String name) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Menus.COL_NAME, name);
        values.put(DbSchema.Menus.COL_IS_ACTIVE, 1);
        return db.insert(DbSchema.Menus.TABLE, null, values);
    }

    private static long insertSection(SQLiteDatabase db, long menuId, String name, int order) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Sections.COL_MENU_ID, menuId);
        values.put(DbSchema.Sections.COL_NAME, name);
        values.put(DbSchema.Sections.COL_SORT_ORDER, order);
        return db.insert(DbSchema.Sections.TABLE, null, values);
    }

    private static long insertItem(SQLiteDatabase db, long sectionId, String name, String description, boolean isActive) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Items.COL_SECTION_ID, sectionId);
        values.put(DbSchema.Items.COL_NAME, name);
        values.put(DbSchema.Items.COL_DESCRIPTION, description);
        values.put(DbSchema.Items.COL_PRICE, 0);
        values.put(DbSchema.Items.COL_IS_ACTIVE, isActive ? 1 : 0);
        values.putNull(DbSchema.Items.COL_IMAGE_RES); // gallery is in item_images
        return db.insert(DbSchema.Items.TABLE, null, values);
    }

    private static void insertVariant(SQLiteDatabase db, long itemId, String label, double price) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Variants.COL_ITEM_ID, itemId);
        values.put(DbSchema.Variants.COL_LABEL, label);
        values.put(DbSchema.Variants.COL_PRICE, price);
        db.insert(DbSchema.Variants.TABLE, null, values);
    }

    private static void insertItemImage(SQLiteDatabase db, long itemId, String imageResName, int sortOrder) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.ItemImages.COL_ITEM_ID, itemId);
        values.put(DbSchema.ItemImages.COL_IMAGE_RES, imageResName);
        values.put(DbSchema.ItemImages.COL_SORT_ORDER, sortOrder);
        db.insert(DbSchema.ItemImages.TABLE, null, values);
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