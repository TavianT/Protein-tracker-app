package com.example.proteintracker.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class Target {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "target")
    public double target;

    @ColumnInfo(name = "date")
    public LocalDate date = LocalDate.now();

    public Target(double target) {
        this.target = target;
    }
}
