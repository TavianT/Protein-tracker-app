package com.example.proteintracker.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
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
    final double PROTEIN_TARGET = 150;
    double consumedGrams, remainingGrams;
    Pie pie;

    TextView targetTextView, remainingTextView, consumedTextView;
    AnyChartView pieChartView;

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
        //pie = AnyChart.pie();
        targetTextView = v.findViewById(R.id.targetGramsTextView);
        remainingTextView = v.findViewById(R.id.remainingGramsTextView);
        consumedTextView = v.findViewById(R.id.consumedGramsTextView);
        pieChartView = v.findViewById(R.id.pieChart);
        List<DailyProtein> dpList = new ArrayList<DailyProtein>();
        DailyProteinController dpController = new DailyProteinController(requireContext());
        dpList = dpController.getCurrentDailyProtein();
        FoodController foodController = new FoodController(requireContext());
        List<Integer> foodIds = new ArrayList<>();
        for (final DailyProtein dp: dpList) {
            foodIds.add(dp.foodId);
        }
        List<Double> proteins = new ArrayList<>(foodController.getProteinById(foodIds));
        for (final double protein: proteins) {
            consumedGrams += protein;
        }
        if (consumedGrams < PROTEIN_TARGET) {
            remainingGrams = PROTEIN_TARGET - consumedGrams;
        } else {
            remainingGrams = 0;
        }
        Log.d("remaining", String.valueOf(remainingGrams));
        Log.d("consumed", String.valueOf(consumedGrams));
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String targetString = String.valueOf(decimalFormat.format(PROTEIN_TARGET)) + " g";
        String remainingString = String.valueOf(decimalFormat.format(remainingGrams)) + " g";
        String consumedString = String.valueOf(decimalFormat.format(consumedGrams)) + " g";
        targetTextView.setText(targetString);
        remainingTextView.setText(remainingString);
        consumedTextView.setText(consumedString);
        boolean pieDone = setPieChart();
        if (pieDone) {
            Log.d("pie done", String.valueOf(pieDone));
        }
        //updatePieChart();
        return v;
    }

    private boolean setPieChart() {
        pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<DataEntry>();
        Log.d("remaining in pie", String.valueOf(remainingGrams));
        Log.d("consumed in pie", String.valueOf(consumedGrams));
        dataEntries.add(new ValueDataEntry("Consumed", consumedGrams));
        dataEntries.add(new ValueDataEntry("Remaining", remainingGrams));
        pie.data(dataEntries);
        pie.labels().enabled(false);
        //pie.tooltip().enabled(false);
        pie.legend().enabled(false);
        pie.outline().enabled(false);

        pieChartView.setChart(pie);
        return true;
    }

    private void updatePieChart() {
        final int delay = 500;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                setPieChart();
            }
        };
        handler.postDelayed(runnable, delay);
    }
}