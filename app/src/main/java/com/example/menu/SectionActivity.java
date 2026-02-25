package com.example.menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menu.models.Section;
import com.example.menu.repositories.MenuRepository;

import java.util.List;

public class SectionActivity extends AppCompatActivity {

    private RecyclerView rvSections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        // קבלת menuId מהמסך הקודם
        long menuId = getIntent().getLongExtra("menuId", -1);
        Log.d("SECTION_DEBUG", "menuId = " + menuId);

        // חיבור ה-RecyclerView
        rvSections = findViewById(R.id.rvSections);
        rvSections.setLayoutManager(new LinearLayoutManager(this));

        // שליפת נתונים מה-Repository
        MenuRepository repo = new MenuRepository(this);
        List<Section> sections = repo.getSections(menuId);

        // חיבור Adapter
        SectionAdapter adapter = new SectionAdapter(sections, section -> {

            Log.d("SECTION_DEBUG", "Clicked sectionId = " + section.getId());

            Intent intent = new Intent(SectionActivity.this, ItemActivity.class);
            intent.putExtra("sectionId", section.getId());
            startActivity(intent);
        });

        rvSections.setAdapter(adapter);
    }
}