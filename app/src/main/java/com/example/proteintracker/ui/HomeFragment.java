package com.example.proteintracker.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.graphics.vector.Fill;
import com.anychart.graphics.vector.GradientKey;
import com.example.proteintracker.R;
import com.example.proteintracker.controller.DailyProteinController;
import com.example.proteintracker.controller.FoodController;
import com.example.proteintracker.model.DailyProtein;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    //temp
    //final double PROTEIN_TARGET = 150;
    double consumedGrams, remainingGrams, proteinTarget;

    TextView targetTextView, remainingTextView, consumedTextView, percentageTextView;
    ProgressBar progressBar;
    boolean redraw = false;

    public HomeFragment() {
        // Required empty public constructor
    }

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
        consumedGrams = 0;
        remainingGrams = 0;
        targetTextView = v.findViewById(R.id.targetGramsTextView);
        remainingTextView = v.findViewById(R.id.remainingGramsTextView);
        consumedTextView = v.findViewById(R.id.consumedGramsTextView);
        percentageTextView = v.findViewById(R.id.proteinPercentTextView);
        progressBar = v.findViewById(R.id.proteinPercentProgressBar);
        List<DailyProtein> dpList = new ArrayList<DailyProtein>();
        DailyProteinController dpController = new DailyProteinController(requireContext());
        dpList = dpController.getCurrentDailyProtein();
        FoodController foodController = new FoodController(requireContext());
        List<Double> proteins = new ArrayList<>();
        for (final DailyProtein dp: dpList) {
            proteins.add(foodController.getProteinById(dp.foodId));
        }
        for (final double protein: proteins) {
            consumedGrams += protein;
        }
        SharedPreferences prefs = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        proteinTarget = Double.parseDouble(prefs.getString("proteinTarget","150"));
        if (consumedGrams < proteinTarget) {
            remainingGrams = proteinTarget - consumedGrams;
        } else {
            remainingGrams = 0;
        }
        setProgressBar();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String targetString = String.valueOf(decimalFormat.format(proteinTarget)) + " g";
        String remainingString = String.valueOf(decimalFormat.format(remainingGrams)) + " g";
        String consumedString = String.valueOf(decimalFormat.format(consumedGrams)) + " g";
        targetTextView.setText(targetString);
        remainingTextView.setText(remainingString);
        consumedTextView.setText(consumedString);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (redraw) {
            redraw = false;
            setProgressBar();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        redraw = true;
    }

    void setProgressBar() {
        if (remainingGrams == 0) {
            progressBar.setProgress(100);
            percentageTextView.setText("100%");
        } else {
            int progress = (int) ((consumedGrams / proteinTarget) * 100);
            Log.e("consumedGrams", String.valueOf(consumedGrams));
            Log.e("proteinTarget", String.valueOf(proteinTarget));
            Log.e("progress", String.valueOf(progress));
            progressBar.setProgress(progress);
            percentageTextView.setText(String.valueOf(progress) + "%");
        }
    }
}