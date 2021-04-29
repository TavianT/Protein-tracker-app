package com.example.proteintracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proteintracker.Validator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addFoodFragment extends Fragment implements View.OnClickListener {

    EditText foodName, proteinGrams;
    Button submitButton;

    public addFoodFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static addFoodFragment newInstance(String param1, String param2) {
        addFoodFragment fragment = new addFoodFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            }
        }
    }
}