package com.example.proteintracker;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FoodController {
    Context context;

    FoodController(Context context){
        this.context = context;
    }

    public boolean createFood(String foodString, Double proteinGrams) {
        boolean process_ok = true;
        Food food = new Food(foodString, proteinGrams);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            AppDatabase db = AppDatabase.getInstance(context);
            FoodDAO dao = db.foodDAO();
            dao.insertFood(food);
            Log.d("DB", "Food inserted");
        });
        try {
            Log.i("Executor obj", "attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Log.e("Executor obj", "Task interrupted: " + e.toString());
            process_ok = false;
        } finally {
            if (!executor.isTerminated()) {
                Log.e("Executor obj", "Cancel non finished tasks");
            }
            executor.shutdownNow();
            Log.d("Executor obj", "shutdown finished");

        }
        return process_ok;
    }
    public List<Food> getAllFood() {
        List<Food> foodList = new ArrayList<Food>();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            AppDatabase db = AppDatabase.getInstance(context);
            FoodDAO dao = db.foodDAO();
            foodList.addAll(dao.getAll());
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
        return foodList;
    }
}
