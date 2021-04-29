package com.example.proteintracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFoodFragment extends Fragment implements View.OnClickListener {

    EditText foodName, proteinGrams;
    Button submitButton;

    public AddFoodFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddFoodFragment newInstance(String param1, String param2) {
        AddFoodFragment fragment = new AddFoodFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_food, container, false);
        foodName = v.findViewById(R.id.editTextFoodName);
        proteinGrams = v.findViewById(R.id.editTextProteinGrams);
        submitButton = v.findViewById(R.id.submitFoodButton);

        submitButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        if (view == submitButton) {
            String foodNameString = foodName.getText().toString().trim();
            String proteinString = proteinGrams.getText().toString().trim();
            if (!Validator.validateFoodForm(foodNameString, proteinString)) {
                Toast.makeText(requireContext(),"Food name or protein invalid. Please retry.", Toast.LENGTH_LONG).show();
            } else {
                double proteinDouble = Double.parseDouble(proteinString);
                Food food = new Food(foodNameString, proteinDouble);
                new Thread(() -> {
                    AppDatabase db = AppDatabase.getInstance(requireContext());
                    FoodDAO dao = db.foodDAO();
                    dao.insertFood(food);
                }).start();
                Log.d("DB", "Food inserted");
                Toast.makeText(requireContext(),"Food added successfully", Toast.LENGTH_LONG).show();
                foodName.setText("");
                proteinGrams.setText("");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_viewDailyProtein:
                try {
                    Navigation.findNavController(requireView()).navigate(R.id.action_addFoodFragment_to_viewDailyProteinFragment);
                } catch (Exception e) {
                    Log.e("Nav Error", e.toString());
                }
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }
}