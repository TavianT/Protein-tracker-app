package com.example.proteintracker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Food.class}, version = 1)
//@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase
{
    public abstract FoodDAO foodDAO();

    private static volatile AppDatabase INSTANCE;

    static AppDatabase getInstance(Context context)
    {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
