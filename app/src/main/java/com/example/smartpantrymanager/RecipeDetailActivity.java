package com.example.smartpantrymanager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        String recipeName = getIntent().getStringExtra("RECIPE_NAME");
        String recipeInstructions = getIntent().getStringExtra("RECIPE_INSTRUCTIONS");
        ((TextView)findViewById(R.id.tvRecipeTitle)).setText(recipeName);
        ((TextView)findViewById(R.id.tvInstructions)).setText(recipeInstructions);
    }
}