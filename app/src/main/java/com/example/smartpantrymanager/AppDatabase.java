package com.example.smartpantrymanager;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(
        entities = {PantryItem.class, Recipe.class, RecipeIngredient.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PantryItemDao pantryItemDao();
    public abstract RecipeDao recipeDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "smart_pantry_db"
                    ).addCallback(seedCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback seedCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                RecipeDao dao = INSTANCE.recipeDao();

                // Recipe 1: Fluffy Pantry Cakes
                long r1 = dao.insert(new Recipe("Fluffy Pantry Cakes", "1. Mix the flour and milk into a batter.\n2. Whisk in the egg.\n3. Fry in a hot pan until golden."));
                List<RecipeIngredient> ri1 = new ArrayList<>();
                ri1.add(new RecipeIngredient((int) r1, "flour", 150, "g"));
                ri1.add(new RecipeIngredient((int) r1, "milk", 100, "ml"));
                ri1.add(new RecipeIngredient((int) r1, "egg", 1, "pieces"));
                dao.insertIngredients(ri1);

                // Recipe 2: Savory Midnight Mash
                long r2 = dao.insert(new Recipe("Savory Midnight Mash", "1. Boil the potato until soft.\n2. Mash it with the butter and milk.\n3. Add a pinch of salt."));
                List<RecipeIngredient> ri2 = new ArrayList<>();
                ri2.add(new RecipeIngredient((int) r2, "potato", 2, "pieces"));
                ri2.add(new RecipeIngredient((int) r2, "butter", 2, "tbsp"));
                ri2.add(new RecipeIngredient((int) r2, "milk", 50, "ml"));
                ri2.add(new RecipeIngredient((int) r2, "salt", 1, "pinch"));
                dao.insertIngredients(ri2);

                // Recipe 3: Cheesy Tomato Surprise
                long r3 = dao.insert(new Recipe("Cheesy Tomato Surprise", "1. Slice the tomatoes.\n2. Cover completely with cheese.\n3. Microwave for 2 minutes."));
                List<RecipeIngredient> ri3 = new ArrayList<>();
                ri3.add(new RecipeIngredient((int) r3, "tomato", 2, "pieces"));
                ri3.add(new RecipeIngredient((int) r3, "cheese", 100, "g"));
                dao.insertIngredients(ri3);

                // Recipe 4: Rustic Rice Bowl
                long r4 = dao.insert(new Recipe("Rustic Rice Bowl", "1. Cook the rice.\n2. Chop the onion and fry it.\n3. Mix together in a bowl."));
                List<RecipeIngredient> ri4 = new ArrayList<>();
                ri4.add(new RecipeIngredient((int) r4, "rice", 200, "g"));
                ri4.add(new RecipeIngredient((int) r4, "onion", 1, "pieces"));
                ri4.add(new RecipeIngredient((int) r4, "oil", 1, "tbsp"));
                dao.insertIngredients(ri4);

                // Recipe 5: Simple Tomato Pasta
                long r5 = dao.insert(new Recipe("Simple Tomato Pasta", "1. Boil the pasta.\n2. Fry the tomato in oil.\n3. Mix pasta and tomato."));
                List<RecipeIngredient> ri5 = new ArrayList<>();
                ri5.add(new RecipeIngredient((int) r5, "pasta", 150, "g"));
                ri5.add(new RecipeIngredient((int) r5, "tomato", 1, "pieces"));
                ri5.add(new RecipeIngredient((int) r5, "oil", 1, "tbsp"));
                dao.insertIngredients(ri5);
            });
        }
    };
}