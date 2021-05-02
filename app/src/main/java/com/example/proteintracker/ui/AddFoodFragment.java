package com.example.proteintracker.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proteintracker.controller.FoodController;
import com.example.proteintracker.R;
import com.example.proteintracker.Validator;

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
                FoodController controller = new FoodController(requireContext());
                boolean process_ok = controller.createFood(foodNameString, proteinDouble);
                if (process_ok) {
                    Toast.makeText(requireContext(),"Food added successfully", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(requireView()).popBackStack();
                } else {
                    Toast.makeText(requireContext(),"Error adding food to database, please retry later", Toast.LENGTH_LONG).show();
                }
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