package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private PantryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PantryAdapter();
        recyclerView.setAdapter(adapter);

        // Observe the database
        AppDatabase.getInstance(this).pantryItemDao().getAllItems().observe(this, pantryItems -> {
            adapter.setItems(pantryItems);
        });

        // Navigation buttons
        findViewById(R.id.fabAdd).setOnClickListener(v -> startActivity(new Intent(this, AddItemActivity.class)));
        findViewById(R.id.btnGoToRecipes).setOnClickListener(v -> startActivity(new Intent(this, SuggestedRecipesActivity.class)));
        findViewById(R.id.btnGoToSettings).setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }
}