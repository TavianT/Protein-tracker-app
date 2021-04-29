package com.example.proteintracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Food {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "foodName")
    public String foodName;

    @ColumnInfo(name = "protein")
    public double protein;

    Food(String foodName, double protein) {
        this.foodName = foodName;
        this.protein = protein;
    }
}
