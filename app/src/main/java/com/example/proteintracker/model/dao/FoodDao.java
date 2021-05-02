package com.example.proteintracker.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.proteintracker.model.Food;

import java.util.List;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM food")
    List<Food> getAll();

    @Query("SELECT * FROM food WHERE id = :id") //check if single = or double
    Food getFood(int id);

    @Query("SELECT protein FROM food WHERE id IN(:ids)")
    List<Double> getAllProtein(List<Integer> ids);

    @Query("SELECT * FROM food WHERE id IN(:ids)")
    List<Food> getAllFoodsById(List<Integer> ids);

    @Insert
    void insertFood(Food food);

    @Delete
    void deleteFood(Food food);
}
