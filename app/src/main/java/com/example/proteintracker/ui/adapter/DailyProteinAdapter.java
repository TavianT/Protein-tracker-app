package com.example.proteintracker.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proteintracker.R;
import com.example.proteintracker.controller.DailyProteinController;
import com.example.proteintracker.model.DailyProtein;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DailyProteinAdapter extends RecyclerView.Adapter<DailyProteinAdapter.DailyProteinViewHolder>{

    Context context;
    List<DailyProtein> dpList;
    List<String> food;
    List<Double> grams;

    public DailyProteinAdapter(Context context, List<DailyProtein> dpList, List<String> food, List<Double> grams) {
        this.context = context;
        this.dpList = dpList;
        this.food = food;
        this.grams = grams;
    }

    @NonNull
    @Override
    public DailyProteinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.daily_protein_row, parent, false);
        return new DailyProteinViewHolder(v);
    }
    
    @Override
    public void onBindViewHolder(@NonNull DailyProteinViewHolder holder, int position) {
        try {
            holder.dpCardTextView.setText(food.get(holder.getAdapterPosition()));
            LocalDateTime time = dpList.get(holder.getAdapterPosition()).date;
            int minute = time.getMinute();
            String minuteString = "";
            if (minute < 10) {
                minuteString += "0";
            }
            minuteString += String.valueOf(minute);
            String timeString = String.valueOf(time.getHour())  + ":" + minuteString;
            String infoText = grams.get(holder.getAdapterPosition()).toString() + " grams at " + timeString;
            holder.dpInfoCardView.setText(infoText);
        } catch (IndexOutOfBoundsException e) {
            Log.e("DpAdapter", e.toString());
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DailyProteinController controller = new DailyProteinController(context);
                controller.deleteDailyProtien(dpList.get(holder.getAdapterPosition()));
                grams.remove(holder.getAdapterPosition());
                food.remove(holder.getAdapterPosition());
                dpList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), dpList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dpList.size();
    }

    public class DailyProteinViewHolder extends RecyclerView.ViewHolder {
        TextView dpCardTextView, dpInfoCardView;
        FloatingActionButton deleteButton;
        ConstraintLayout layout;

        public DailyProteinViewHolder(@NonNull View itemView) {
            super(itemView);
            dpCardTextView = itemView.findViewById(R.id.dpFoodCardTextView);
            dpInfoCardView = itemView.findViewById(R.id.dpInfoCardTextView);
            deleteButton = itemView.findViewById(R.id.deleteDpButton);
            layout = itemView.findViewById(R.id.dailyProteinRowLayout);
        }
    }
}
