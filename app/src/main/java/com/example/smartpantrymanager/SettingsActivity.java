package com.example.smartpantrymanager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Switch switchAlerts = findViewById(R.id.switchAlerts);
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        switchAlerts.setChecked(prefs.getBoolean("alerts", false));
        switchAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("alerts", isChecked).apply();
        });
    }
}