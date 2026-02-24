package com.example.menu.logic;

import com.example.menu.models.ModifierOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// שומר מה המשתמש בחר בכל groupId
public class SelectedModifiersManager {

    private final Map<Long, List<ModifierOption>> selectedByGroup = new HashMap<>();

    // להחליף את הבחירה של קבוצה (נוח ל-radio)
    public void setSelectedForGroup(long groupId, List<ModifierOption> selectedOptions) {
        if (selectedOptions == null) selectedOptions = new ArrayList<>();
        selectedByGroup.put(groupId, selectedOptions);
    }

    // להוסיף/להסיר אופציה (נוח ל-checkbox)
    public void toggleOption(long groupId, ModifierOption option) {
        List<ModifierOption> list = selectedByGroup.get(groupId);
        if (list == null) {
            list = new ArrayList<>();
            selectedByGroup.put(groupId, list);
        }

        // אם קיימת -> להסיר, אחרת להוסיף
        int idx = indexOf(list, option.getId());
        if (idx >= 0) list.remove(idx);
        else list.add(option);
    }

    public int getSelectedCount(long groupId) {
        List<ModifierOption> list = selectedByGroup.get(groupId);
        return list == null ? 0 : list.size();
    }

    // מחזיר את כל האופציות שנבחרו מכל הקבוצות (בשביל חישוב מחיר)
    public List<ModifierOption> getAllSelectedOptions() {
        List<ModifierOption> all = new ArrayList<>();
        for (List<ModifierOption> list : selectedByGroup.values()) {
            all.addAll(list);
        }
        return all;
    }

    private int indexOf(List<ModifierOption> list, long optionId) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == optionId) return i;
        }
        return -1;
    }
}