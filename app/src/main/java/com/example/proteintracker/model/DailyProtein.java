package com.example.proteintracker.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class DailyProtein {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "date")
    public LocalDateTime date;

    @ColumnInfo(name = "foodId")
    public int foodId;

    public DailyProtein(LocalDateTime date, int foodId) {
        this.date = date;
        this.foodId = foodId;
    }




}
