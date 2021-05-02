package com.example.proteintracker.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proteintracker.R;
import com.example.proteintracker.controller.DailyProteinController;
import com.example.proteintracker.controller.FoodController;
import com.example.proteintracker.model.DailyProtein;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    //temp
    final double PROTEIN_TARGET = 150;
    double consumedGrams, remainingGrams;

    TextView targetTextView, remainingTextView, consumedTextView;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        targetTextView = v.findViewById(R.id.targetGramsTextView);
        remainingTextView = v.findViewById(R.id.remainingGramsTextView);
        consumedTextView = v.findViewById(R.id.consumedGramsTextView);
        List<DailyProtein> dpList = new ArrayList<DailyProtein>();
        DailyProteinController dpController = new DailyProteinController(requireContext());
        dpList = dpController.getCurrentDailyProtein();
        FoodController foodController = new FoodController(requireContext());
        for (final DailyProtein dp: dpList) {
            double protein = foodController.getProteinById(dp.foodId);
            consumedGrams += protein;
        }
        remainingGrams = PROTEIN_TARGET - consumedGrams;
        targetTextView.setText(String.valueOf(PROTEIN_TARGET) + " g");
        remainingTextView.setText(String.valueOf(remainingGrams) + " g");
        consumedTextView.setText(String.valueOf(consumedGrams) + " g");
        return v;
    }
}