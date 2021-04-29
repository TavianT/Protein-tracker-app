package com.example.proteintracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFromFoodListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFromFoodListFragment extends Fragment {

    RecyclerView recyclerView;
    List<Food> foodList;
    List<String> foodNames;
    List<Double> protein;

    public AddFromFoodListFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static AddFromFoodListFragment newInstance(String param1, String param2) {
        AddFromFoodListFragment fragment = new AddFromFoodListFragment();
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
        View v = inflater.inflate(R.layout.fragment_add_from_food_list, container, false);
        foodNames = new ArrayList<String>();
        protein = new ArrayList<Double>();
        recyclerView = v.findViewById(R.id.foodListRecyclerView);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            AppDatabase db = AppDatabase.getInstance(requireContext());
            FoodDAO dao = db.foodDAO();
            foodList = dao.getAll();
        });
        try {
            Log.i("Executor obj", "attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Log.e("Executor obj", "Task interrupted: " + e.toString());
        } finally {
            if (!executor.isTerminated()) {
                Log.e("Executor obj", "Cancel non finished tasks");
            }
            executor.shutdownNow();
            Log.d("Executor obj", "shutdown finished");

        }
        for (final Food food : foodList) {
            foodNames.add(food.foodName);
            Log.d("Food Table", food.foodName);
            protein.add(food.protein);
        }
        FoodListAdapter adapter = new FoodListAdapter(requireContext(),foodNames,protein);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return v;
    }
}