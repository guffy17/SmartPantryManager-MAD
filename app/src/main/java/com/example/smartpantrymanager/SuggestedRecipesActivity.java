package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SuggestedRecipesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested);

        TextView tvEmpty = findViewById(R.id.tvEmpty);

        // Show a loading message
        tvEmpty.setText("Checking pantry...");

        // Run the database logic in the background so the UI doesn't freeze
        Executors.newSingleThreadExecutor().execute(() -> {
            List<PantryItem> pantry = AppDatabase.getInstance(this).pantryItemDao().getAllItemsSync();
            List<Recipe> allRecipes = AppDatabase.getInstance(this).recipeDao().getAllRecipesSync();
            List<Recipe> matched = new ArrayList<>();

            // THIS IS THE STRICT MATCHING LOGIC
            for (Recipe r : allRecipes) {
                List<RecipeIngredient> required = AppDatabase.getInstance(this).recipeDao().getIngredientsForRecipe(r.id);
                boolean canMake = true;

                for (RecipeIngredient req : required) {
                    boolean found = false;
                    for (PantryItem p : pantry) {
                        // Check if ingredient name matches and quantity is enough
                        if (p.name.toLowerCase().contains(req.ingredientName.toLowerCase())) {
                            if (p.quantity >= req.requiredQuantity) {
                                found = true;
                                break;
                            }
                        }
                    }
                    if (!found) {
                        canMake = false;
                        break;
                    }
                }
                if (canMake && !required.isEmpty()) matched.add(r);
            }

            // Update the screen with the results
            runOnUiThread(() -> {
                if (matched.isEmpty()) {
                    tvEmpty.setText("No recipes match your pantry yet.");
                } else {
                    Recipe bestMatch = matched.get(0);
                    tvEmpty.setText("Recipe: " + bestMatch.name + "\n\nInstructions:\n" + bestMatch.instructions);

                    tvEmpty.setOnClickListener(v -> {
                        Intent intent = new Intent(this, RecipeDetailActivity.class);
                        intent.putExtra("RECIPE_NAME", bestMatch.name);
                        intent.putExtra("RECIPE_INSTRUCTIONS", bestMatch.instructions);
                        startActivity(intent);
                    });
                }
            });
        });
    }
}