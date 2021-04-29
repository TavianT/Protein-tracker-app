package com.example.proteintracker;

import android.util.Log;

public class Validator {
    public static boolean validateFoodForm(String food, String protein) {
        boolean valid = false;
        if (!food.isEmpty()) {
            if (!protein.isEmpty()) {
                double proteinDouble = Double.parseDouble(protein);
                Log.d("Double String", protein);
                Log.d("Double", String.valueOf(proteinDouble));
                if (proteinDouble > 0) {
                    valid = true;
                }
            }

        }
        return valid;
    }
}
