package com.example.proteintracker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.proteintracker.model.DailyProtein;
import com.example.proteintracker.model.Food;
import com.example.proteintracker.model.dao.DailyProteinDao;
import com.example.proteintracker.model.dao.FoodDao;

@Database(entities = {Food.class, DailyProtein.class}, version = 2) //FIXME:
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase
{
    public abstract FoodDao foodDAO();
    public abstract DailyProteinDao dailyProteinDao();

    private static volatile AppDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE 'dailyprotein' ('id' INTEGER, 'foodId' INTEGER, 'date' TEXT, PRIMARY KEY('id'))");
        }
    };

    public static AppDatabase getInstance(Context context)
    {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database").addMigrations(MIGRATION_1_2).build();
                }
            }
        }
        return INSTANCE;
    }
}
