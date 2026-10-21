package com.example.smartpantrymanager;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_ingredients",
        foreignKeys = @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "recipeId", onDelete = ForeignKey.CASCADE),
        indices = {@Index("recipeId")})
public class RecipeIngredient {
    @PrimaryKey(autoGenerate = true) public int id;
    public int recipeId;
    public String ingredientName;
    public double requiredQuantity;
    public String unit;
    public RecipeIngredient(int recipeId, String ingredientName, double requiredQuantity, String unit) {
        this.recipeId = recipeId; this.ingredientName = ingredientName; this.requiredQuantity = requiredQuantity; this.unit = unit;
    }
}