package com.example.menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menu.models.Item;
import com.example.menu.repositories.MenuRepository;

import java.util.List;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        long sectionId = getIntent().getLongExtra("sectionId", -1);
        Log.d("ITEM_DEBUG", "sectionId=" + sectionId);

        RecyclerView rv = findViewById(R.id.rvItems);
        rv.setLayoutManager(new LinearLayoutManager(this));

        MenuRepository repo = new MenuRepository(this);
        List<Item> items = repo.getItems(sectionId);

        ItemAdapter adapter = new ItemAdapter(items, item -> {
            android.content.Intent intent = new android.content.Intent(this, ItemDetailsActivity.class);
            intent.putExtra("itemId", item.getId());
            startActivity(intent);
        });

        rv.setAdapter(adapter);
    }
}