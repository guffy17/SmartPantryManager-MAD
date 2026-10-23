package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SuggestedRecipesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested);

        TextView tvEmpty = findViewById(R.id.tvEmpty);

        // Just show text directly on the screen
        String output = "Recipe: Fluffy Pantry Cakes\n\nInstructions:\n1. Mix the flour and milk into a batter.\n2. Whisk in the egg.\n3. Fry in a hot pan until golden.";
        tvEmpty.setText(output);

        // Allow clicking the text to open the detail screen
        tvEmpty.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("RECIPE_NAME", "Fluffy Pantry Cakes");
            intent.putExtra("RECIPE_INSTRUCTIONS", "1. Mix flour and milk.\n2. Fry in a pan.");
            startActivity(intent);
        });
    }
}