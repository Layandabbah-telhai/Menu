package com.example.menu.logic;

import com.example.menu.models.ModifierGroup;
import com.example.menu.models.ModifierGroupWithOptions;
import com.example.menu.models.ModifierOption;

import java.util.Collections;
import java.util.List;

public final class ItemDetailsLogic {

    private ItemDetailsLogic() {}

    // בדיקה לכל הקבוצות: מחזיר null אם הכל תקין, אחרת הודעת שגיאה ראשונה
    public static String validateAllGroups(List<ModifierGroupWithOptions> modifiers,
                                           SelectedModifiersManager manager) {

        if (modifiers == null || modifiers.isEmpty()) return null;
        if (manager == null) return "Internal error: selections manager is null";

        for (ModifierGroupWithOptions gwo : modifiers) {
            if (gwo == null) continue;

            ModifierGroup g = gwo.getGroup();
            if (g == null) continue;

            int count = manager.getSelectedCount(g.getId());
            String err = ModifierSelectionValidator.getErrorMessage(g, count);

            if (err != null) return err;
        }
        return null;
    }

    // נוח: לבדיקה מהירה האם הכל תקין
    public static boolean isSelectionValid(List<ModifierGroupWithOptions> modifiers,
                                           SelectedModifiersManager manager) {
        return validateAllGroups(modifiers, manager) == null;
    }

    // חישוב מחיר כולל: variantPrice + כל ה-priceDelta של האופציות שנבחרו
    public static double computeTotalPrice(double variantPrice,
                                           SelectedModifiersManager manager) {

        if (manager == null) return variantPrice;

        List<ModifierOption> allSelected = manager.getAllSelectedOptions();
        if (allSelected == null) allSelected = Collections.emptyList();

        return PriceCalculator.calculateTotalPrice(variantPrice, allSelected);
    }

}