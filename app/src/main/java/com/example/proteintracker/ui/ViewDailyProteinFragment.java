package com.example.proteintracker.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.proteintracker.R;
import com.example.proteintracker.controller.DailyProteinController;
import com.example.proteintracker.controller.FoodController;
import com.example.proteintracker.model.DailyProtein;
import com.example.proteintracker.model.Food;
import com.example.proteintracker.ui.adapter.DailyProteinAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewDailyProteinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewDailyProteinFragment extends Fragment {
    List<DailyProtein> dpList;
    RecyclerView recyclerView;
    List<String> foodNames;
    List<Double> protein;

    public ViewDailyProteinFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ViewDailyProteinFragment newInstance(String param1, String param2) {
        ViewDailyProteinFragment fragment = new ViewDailyProteinFragment();
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
        View v = inflater.inflate(R.layout.fragment_view_daily_protein, container, false);
        dpList = new ArrayList<DailyProtein>();
        protein = new ArrayList<Double>();
        foodNames = new ArrayList<String>();
        recyclerView = v.findViewById(R.id.dailyProteinRecyclerView);
        DailyProteinController controller = new DailyProteinController(requireContext());
        dpList = controller.getAllDailyProtein(); //FIXME: change to current date
        FoodController foodController = new FoodController(requireContext());
        for (final DailyProtein dp: dpList) {
            Food food = foodController.getFoodById(dp.foodId);
            foodNames.add(food.foodName);
            protein.add(food.protein);
        }
        DailyProteinAdapter adapter = new DailyProteinAdapter(requireContext(), dpList, foodNames, protein);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.daily_protein_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_addFromFoodList:
                Navigation.findNavController(requireView()).navigate(R.id.action_viewDailyProteinFragment_to_addFromFoodListFragment);
                break;
            case R.id.action_addFood:
                Navigation.findNavController(requireView()).navigate(R.id.action_viewDailyProteinFragment_to_addFoodFragment);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}