package com.example.proteintracker.controller;

import android.content.Context;
import android.util.Log;

import com.example.proteintracker.AppDatabase;
import com.example.proteintracker.model.DailyProtein;
import com.example.proteintracker.model.dao.DailyProteinDao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DailyProteinController {
    Context context;

    public DailyProteinController(Context context) {
        this.context = context;
    }

    public boolean CreateDailyProtein(int foodId, LocalDateTime date) {
        boolean process_ok = true;
        DailyProtein dp = new DailyProtein(date, foodId);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            AppDatabase db = AppDatabase.getInstance(context);
            DailyProteinDao dao = db.dailyProteinDao();
            dao.insertDailyProtein(dp);
            Log.d("DB", "Daily protein inserted");
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

    public List<DailyProtein> getAllDailyProtein() {
        List<DailyProtein> dailyProteinList = new ArrayList<DailyProtein>();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            AppDatabase db = AppDatabase.getInstance(context);
            DailyProteinDao dao = db.dailyProteinDao();
            dailyProteinList.addAll(dao.getAll());
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
        return dailyProteinList;
    }

    public List<DailyProtein> getCurrentDailyProtein() {
        List<DailyProtein> dailyProteinList = new ArrayList<DailyProtein>();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            AppDatabase db = AppDatabase.getInstance(context);
            DailyProteinDao dao = db.dailyProteinDao();
            dailyProteinList.addAll(dao.getAllCurrent());
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
        return dailyProteinList;
    }

    public void deleteDailyProtien(DailyProtein dp) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            AppDatabase db = AppDatabase.getInstance(context);
            DailyProteinDao dao = db.dailyProteinDao();
            dao.deleteDailyProtein(dp);
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
    }
}
