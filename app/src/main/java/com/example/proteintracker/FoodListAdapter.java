package com.example.proteintracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodListViewHolder> {

    Context context;
    List<String> food;
    List<Double> protein;

    public FoodListAdapter(Context context, List<String> foodList, List<Double> proteinList) {
        this.context = context;
        this.food = foodList;
        this.protein = proteinList;
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

    }

    @Override
    public int getItemCount() {
        return food.size();
    }

    public class FoodListViewHolder extends RecyclerView.ViewHolder{
        TextView foodTextView, gramsTextView;
        FloatingActionButton deleteButton;
        public FoodListViewHolder(@NonNull View itemView) {
            super(itemView);
            foodTextView = itemView.findViewById(R.id.foodCardTextView);
            gramsTextView = itemView.findViewById(R.id.gramsCardTextView);
            deleteButton = itemView.findViewById(R.id.deleteFoodButton);

        }
    }
}
