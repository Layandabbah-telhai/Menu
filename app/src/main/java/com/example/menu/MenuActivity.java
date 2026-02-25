package com.example.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menu.models.Menu;
import com.example.menu.repositories.MenuRepository;
import com.example.menu.data.MenuDbHelper;
import com.example.menu.data.SeedData;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private List<Menu> menus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // create db + seed (only if empty)
        MenuDbHelper helper = new MenuDbHelper(this);
        SeedData.seedIfEmpty(helper);

        ListView listView = findViewById(R.id.listMenus);

        MenuRepository repo = new MenuRepository(this);
        menus = repo.getMenus();

        List<String> names = new ArrayList<>();
        for (Menu m : menus) {
            names.add(m.getName());
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            long menuId = menus.get(position).getId();
            Intent intent = new Intent(MenuActivity.this, SectionActivity.class);
            intent.putExtra("menuId", menuId);
            startActivity(intent);
        });
    }
}