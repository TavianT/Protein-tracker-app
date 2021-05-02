package com.example.proteintracker.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proteintracker.controller.DailyProteinController;
import com.example.proteintracker.controller.FoodController;
import com.example.proteintracker.model.Food;
import com.example.proteintracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodListViewHolder> {

    Context context;
    List<Food> foodList;
    List<String> food;
    List<Double> protein;

    public FoodListAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        this.food = new ArrayList<String>();
        this.protein = new ArrayList<Double>();
        for (final Food food : this.foodList) {
            this.food.add(food.foodName);
            this.protein.add(food.protein);
        }
    }

    @NonNull
    @Override
    public FoodListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.food_row,parent,false);
        return new FoodListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListViewHolder holder, int position) {
        holder.foodTextView.setText(food.get(position));
        holder.gramsTextView.setText(protein.get(position).toString() + " grams");

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int foodId = foodList.get(holder.getAdapterPosition()).id;
                DailyProteinController controller = new DailyProteinController(context);
                boolean process_ok = controller.CreateDailyProtein(foodId, LocalDateTime.now());
                if (process_ok) {
                    try {
                        Navigation.findNavController(view).navigate(R.id.action_addFromFoodListFragment_to_viewDailyProteinFragment);
                    } catch (Exception e) {
                        Log.e("Nav error", e.toString());
                    }
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodController controller = new FoodController(context);
                controller.deleteFood(foodList.get(holder.getAdapterPosition()));
                protein.remove(holder.getAdapterPosition());
                food.remove(holder.getAdapterPosition());
                foodList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), foodList.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return food.size();
    }

    public class FoodListViewHolder extends RecyclerView.ViewHolder{
        TextView foodTextView, gramsTextView;
        FloatingActionButton deleteButton;
        ConstraintLayout layout;
        public FoodListViewHolder(@NonNull View itemView) {
            super(itemView);
            foodTextView = itemView.findViewById(R.id.foodCardTextView);
            gramsTextView = itemView.findViewById(R.id.gramsCardTextView);
            deleteButton = itemView.findViewById(R.id.deleteFoodButton);
            layout = itemView.findViewById(R.id.foodRowLayout);

        }
    }
}
