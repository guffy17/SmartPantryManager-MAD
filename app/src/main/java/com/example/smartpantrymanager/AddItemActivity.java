package com.example.smartpantrymanager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.Executors;

public class AddItemActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        EditText etName = findViewById(R.id.etName);
        EditText etQuantity = findViewById(R.id.etQuantity);
        EditText etUnit = findViewById(R.id.etUnit);

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String qtyStr = etQuantity.getText().toString().trim();
            String unit = etUnit.getText().toString().trim();

            if (name.isEmpty() || qtyStr.isEmpty() || unit.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            double quantity = Double.parseDouble(qtyStr);
            PantryItem newItem = new PantryItem(name, quantity, unit, null);

            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase.getInstance(this).pantryItemDao().insert(newItem);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
        });
    }
}