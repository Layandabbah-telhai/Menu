package com.example.menu.logic;

import com.example.menu.models.Allergen;
import com.example.menu.models.Item;
import com.example.menu.models.ItemVariant;
import com.example.menu.models.ModifierGroup;
import com.example.menu.models.ModifierGroupWithOptions;
import com.example.menu.models.ModifierOption;
import com.example.menu.repositories.MenuRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public final class MenuJsonBuilder {

    private MenuJsonBuilder() {}

    // JSON ל-ItemDetails: item + variants + modifierGroups(options) + allergens
    public static JSONObject buildItemDetailsJson(MenuRepository repo, long itemId) throws Exception {

        Item item = repo.getItemById(itemId);
        List<ItemVariant> variants = repo.getVariants(itemId);
        List<ModifierGroupWithOptions> modifiers = repo.getModifiersForItem(itemId);
        List<Allergen> allergens = repo.getAllergens(itemId);

        JSONObject root = new JSONObject();

        // ---------- item ----------
        JSONObject itemObj = new JSONObject();
        if (item != null) {
            itemObj.put("id", item.getId());
            itemObj.put("sectionId", item.getSectionId());
            itemObj.put("name", item.getName());
            itemObj.put("description", item.getDescription());
            itemObj.put("active", item.isActive());
        }
        root.put("item", itemObj);

        // ---------- variants ----------
        JSONArray variantsArr = new JSONArray();
        if (variants != null) {
            for (ItemVariant v : variants) {
                JSONObject vObj = new JSONObject();
                vObj.put("id", v.getId());
                vObj.put("label", v.getLabel());
                vObj.put("price", v.getPrice());
                variantsArr.put(vObj);
            }
        }
        root.put("variants", variantsArr);

        // ---------- modifierGroups + options ----------
        JSONArray groupsArr = new JSONArray();
        if (modifiers != null) {
            for (ModifierGroupWithOptions gwo : modifiers) {
                ModifierGroup g = gwo.getGroup();
                List<ModifierOption> opts = gwo.getOptions();

                JSONObject gObj = new JSONObject();
                gObj.put("id", g.getId());
                gObj.put("name", g.getName());
                gObj.put("minSelect", g.getMinSelect());
                gObj.put("maxSelect", g.getMaxSelect());

                JSONArray optsArr = new JSONArray();
                if (opts != null) {
                    for (ModifierOption o : opts) {
                        JSONObject oObj = new JSONObject();
                        oObj.put("id", o.getId());
                        oObj.put("name", o.getName());
                        oObj.put("priceDelta", o.getPriceDelta());
                        optsArr.put(oObj);
                    }
                }

                gObj.put("options", optsArr);
                groupsArr.put(gObj);
            }
        }
        root.put("modifierGroups", groupsArr);

        // ---------- allergens ----------
        JSONArray allergArr = new JSONArray();
        if (allergens != null) {
            for (Allergen a : allergens) {
                JSONObject aObj = new JSONObject();
                aObj.put("id", a.getId());
                aObj.put("name", a.getName());
                allergArr.put(aObj);
            }
        }
        root.put("allergens", allergArr);

        return root;
    }
}