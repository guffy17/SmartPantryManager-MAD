package com.example.smartpantrymanager;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface RecipeDao {
    @Insert long insert(Recipe recipe);
    @Insert void insertIngredients(List<RecipeIngredient> ingredients);
    @Query("SELECT * FROM recipes") List<Recipe> getAllRecipesSync();
    @Query("SELECT * FROM recipe_ingredients WHERE recipeId = :recipeId") List<RecipeIngredient> getIngredientsForRecipe(int recipeId);
}
