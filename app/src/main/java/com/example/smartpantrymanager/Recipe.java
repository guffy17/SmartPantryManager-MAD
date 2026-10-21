package com.example.smartpantrymanager;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipe {
    @PrimaryKey(autoGenerate = true) public int id;
    public String name;
    public String instructions;
    public Recipe(String name, String instructions) {
        this.name = name; this.instructions = instructions;
    }
}
