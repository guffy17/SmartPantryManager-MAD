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

@Database(entities = {PantryItem.class, Recipe.class, RecipeIngredient.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PantryItemDao pantryItemDao();
    public abstract RecipeDao recipeDao();
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "smart_pantry_db").addCallback(seedCallback).build();
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
                long r1 = dao.insert(new Recipe("Savory Pantry Surprise", "1. Mix everything in a bowl.\n2. Bake at 180C for 20 mins."));
                List<RecipeIngredient> ri1 = new ArrayList<>();
                ri1.add(new RecipeIngredient((int) r1, "flour", 150, "g"));
                ri1.add(new RecipeIngredient((int) r1, "milk", 100, "ml"));
                ri1.add(new RecipeIngredient((int) r1, "egg", 1, "pieces"));
                dao.insertIngredients(ri1);
            });
        }
    };
}