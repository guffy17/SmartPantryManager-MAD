package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SuggestedRecipesActivity extends AppCompatActivity {
    private RecyclerView recycler;
    private TextView tvEmpty;
    private RecipeAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested);

        recycler = findViewById(R.id.recyclerSuggested);
        tvEmpty = findViewById(R.id.tvEmpty);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecipeAdapter();
        recycler.setAdapter(adapter);

        loadSuggestions();
    }

    private void loadSuggestions() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<PantryItem> pantry = AppDatabase.getInstance(this).pantryItemDao().getAllItemsSync();
            List<Recipe> allRecipes = AppDatabase.getInstance(this).recipeDao().getAllRecipesSync();
            List<Recipe> matched = new ArrayList<>();

            for (Recipe r : allRecipes) {
                List<RecipeIngredient> required = AppDatabase.getInstance(this).recipeDao().getIngredientsForRecipe(r.id);
                boolean canMake = true;

                for (RecipeIngredient req : required) {
                    boolean found = false;
                    for (PantryItem p : pantry) {
                        if (p.name.toLowerCase().contains(req.ingredientName.toLowerCase())) {
                            if (p.quantity >= req.requiredQuantity) {
                                found = true; break;
                            }
                        }
                    }
                    if (!found) { canMake = false; break; }
                }
                if (canMake && !required.isEmpty()) matched.add(r);
            }

            runOnUiThread(() -> {
                adapter.setRecipes(matched);
                if (matched.isEmpty()) {
                    tvEmpty.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                } else {
                    tvEmpty.setVisibility(View.GONE);
                    recycler.setVisibility(View.VISIBLE);
                }
            });
        });
    }

    class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
        private List<Recipe> recipes = new ArrayList<>();
        public void setRecipes(List<Recipe> recipes) { this.recipes = recipes; notifyDataSetChanged(); }

        @androidx.annotation.NonNull @Override
        public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull android.view.ViewGroup parent, int viewType) {
            return new ViewHolder(android.view.LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false));
        }

        @Override public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, int position) {
            holder.tvName.setText(recipes.get(position).name);
        }

        @Override public int getItemCount() { return recipes.size(); }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName;
            public ViewHolder(android.view.View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvRecipeName);
            }
        }
    }
}