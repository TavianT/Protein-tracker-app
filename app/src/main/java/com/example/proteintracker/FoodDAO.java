package com.example.proteintracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDAO {
    @Query("SELECT * FROM food")
    List<Food> getAll();

    @Insert
    void insertFood(Food food);

    @Delete
    void deleteFood(Food food);
}
