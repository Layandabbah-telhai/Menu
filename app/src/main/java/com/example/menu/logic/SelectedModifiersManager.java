package com.example.menu.logic;

import com.example.menu.models.ModifierOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectedModifiersManager {

    // groupId -> selected options
    private final Map<Long, List<ModifierOption>> selected = new HashMap<>();

    public void clear() {
        selected.clear();
    }

    // ✅ זה מה שהיה חסר לך (selectOption)
    public void selectOption(long groupId, ModifierOption option) {
        if (option == null) return;

        List<ModifierOption> list = selected.get(groupId);
        if (list == null) {
            list = new ArrayList<>();
            selected.put(groupId, list);
        }

        // prevent duplicates
        for (ModifierOption o : list) {
            if (o.getId() == option.getId()) return;
        }
        list.add(option);
    }

    public void unselectOption(long groupId, long optionId) {
        List<ModifierOption> list = selected.get(groupId);
        if (list == null) return;

        list.removeIf(o -> o.getId() == optionId);

        if (list.isEmpty()) selected.remove(groupId);
    }

    public int getSelectedCount(long groupId) {
        List<ModifierOption> list = selected.get(groupId);
        return list == null ? 0 : list.size();
    }

    public List<ModifierOption> getAllSelectedOptions() {
        List<ModifierOption> all = new ArrayList<>();
        for (List<ModifierOption> list : selected.values()) {
            all.addAll(list);
        }
        return all;
    }
}