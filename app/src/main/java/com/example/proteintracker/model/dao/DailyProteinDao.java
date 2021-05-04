package com.example.proteintracker.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.proteintracker.model.DailyProtein;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface DailyProteinDao {
    @Query("SELECT * FROM dailyprotein")
    List<DailyProtein> getAll();

    @Query("SELECT * FROM dailyprotein WHERE date >= datetime('now', 'start of day')")
    List<DailyProtein> getAllCurrent();

    @Insert
    void insertDailyProtein(DailyProtein dp);

    @Delete
    void deleteDailyProtein(DailyProtein dp);
}
