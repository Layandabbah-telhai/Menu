package com.example.menu.logic;

import com.example.menu.models.ModifierOption;
import java.util.List;

public final class PriceCalculator {

    private PriceCalculator() {}

    // basePrice = מחיר ה-Variant שנבחר
    public static double calculateTotalPrice(double basePrice, List<ModifierOption> selectedOptions) {
        double total = basePrice;

        if (selectedOptions != null) {
            for (ModifierOption opt : selectedOptions) {
                if (opt != null) {
                    total += opt.getPriceDelta();
                }
            }
        }
        return total;
    }
};