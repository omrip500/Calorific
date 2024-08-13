package com.example.calorific2.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class CsvUtil {

    public static Map<String, Double> loadExercises(Context context) {
        Map<String, Double> exercises = new LinkedHashMap<>();
        AssetManager assetManager = context.getAssets();

        try {
            InputStream is = assetManager.open("exercise_dataset.csv");
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            String[] columns;

            while ((columns = reader.readNext()) != null) {
                String exerciseName = columns[0];
                String caloriesStr = columns[5];

                if (caloriesStr != null && !caloriesStr.trim().isEmpty()) {
                    try {
                        double caloriesPerKgPerMin = Double.parseDouble(caloriesStr);
                        exercises.put(exerciseName, caloriesPerKgPerMin);
                    } catch (NumberFormatException e) {
                        Log.e("CsvUtil", "Error parsing calories for activity: " + exerciseName + ", value: " + caloriesStr);
                    }
                } else {
                    Log.w("CsvUtil", "Empty or invalid calories value for activity: " + exerciseName);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        Log.d("CsvUtil", "Loaded exercises: " + exercises.size());
        return exercises;
    }
}
