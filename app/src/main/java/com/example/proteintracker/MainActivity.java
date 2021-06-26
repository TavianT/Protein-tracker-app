package com.example.proteintracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart) {
            //TODO: Set default target value
            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat("proteinTarget", 200);
            editor.putBoolean("firstStart", false);
            editor.apply();

        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_viewDailyProtein:
                try {
                    Navigation.findNavController(this, R.id.fragment).navigate(R.id.action_homeFragment_to_viewDailyProteinFragment);
                }catch (Exception e) {
                    Log.e("Nav Error", e.toString());
                }
                break;
            case R.id.action_addFood:
                try {
                    Navigation.findNavController(this, R.id.fragment).navigate(R.id.action_homeFragment_to_addFoodFragment);
                } catch (Exception e) {
                    Log.e("Nav Error", e.toString());
                }
                break;
            case R.id.action_setProteinTarget:
                try {
                    Navigation.findNavController(this, R.id.fragment).navigate(R.id.action_homeFragment_to_setProteinFragment);
                } catch (Exception e) {
                    Log.e("Nav Error", e.toString());
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}