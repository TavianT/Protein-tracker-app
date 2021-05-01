package com.example.proteintracker.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.proteintracker.model.Food;
import com.example.proteintracker.controller.FoodController;
import com.example.proteintracker.ui.adapter.FoodListAdapter;
import com.example.proteintracker.R;

import java.util.ArrayList;
import java.util.List;

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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_from_food_list, container, false);
        foodNames = new ArrayList<String>();
        protein = new ArrayList<Double>();
        foodList = new ArrayList<Food>();
        recyclerView = v.findViewById(R.id.foodListRecyclerView);
        FoodController controller = new FoodController(requireContext());
        foodList = controller.getAllFood();
        for (final Food food : foodList) {
            foodNames.add(food.foodName);
            Log.d("foodNames", food.foodName);
            protein.add(food.protein);
        }
        FoodListAdapter adapter = new FoodListAdapter(requireContext(), foodList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return v;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.add_from_food_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addFood:
                Navigation.findNavController(requireView()).navigate(R.id.action_addFromFoodListFragment_to_addFoodFragment);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}