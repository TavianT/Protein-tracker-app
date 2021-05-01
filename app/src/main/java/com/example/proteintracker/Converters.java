package com.example.proteintracker;

import android.util.Log;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converters
{
    @TypeConverter
    public static LocalDateTime fromString(String dateString)
    {
        LocalDateTime date = LocalDateTime.parse(dateString);
        return date;
    }

    @TypeConverter
    public static String localDateTimeToString(LocalDateTime date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String dateString = formatter.format(date);
        Log.d("Date to String converter", "localDateTimeToString: " + dateString);
        return dateString;
    }
}
