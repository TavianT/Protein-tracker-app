package com.example.proteintracker.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.proteintracker.model.DailyProtein;

import java.util.List;

@Dao
public interface DailyProteinDao {
    @Query("SELECT * FROM dailyprotein")
    List<DailyProtein> getAll();

    @Insert
    void insertDailyProtein(DailyProtein dp);

    @Delete
    void deleteDailyProtein(DailyProtein dp);
}
