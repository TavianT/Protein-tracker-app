package com.example.proteintracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

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

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        myToolbar.inflateMenu(R.menu.menu);
//        myToolbar.setOnMenuItemClickListener(this::onOptionsItemSelected);
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
            case R.id.action_addDailyProtein:
                Log.d("Action", "onOptionsItemSelected: addDailyProtein selected");
                break;
            case R.id.action_addFood:
                Log.d("Action", "onOptionsItemSelected: addFood selected");
                Navigation.findNavController(this, R.id.fragment).navigate(R.id.action_homeFragment_to_addFoodFragment);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}