package com.example.menu.logic;

import com.example.menu.models.ModifierGroup;

public final class ModifierSelectionValidator {

    private ModifierSelectionValidator() {}

    public static boolean isValid(ModifierGroup group, int selectedCount) {
        if (group == null) return true;

        int min = group.getMinSelect();
        int max = group.getMaxSelect();

        return selectedCount >= min && selectedCount <= max;
    }

    public static String getErrorMessage(ModifierGroup group, int selectedCount) {
        if (group == null) return null;

        int min = group.getMinSelect();
        int max = group.getMaxSelect();

        if (selectedCount < min) {
            return "Must select at least " + min + " options in: " + group.getName();
        }

        if (selectedCount > max) {
            return "Must select at most " + max + " options in: " + group.getName();
        }

        return null;
    }
}