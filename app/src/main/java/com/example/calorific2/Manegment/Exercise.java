package com.example.calorific2.Manegment;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String name;
    private double caloriesBurned;

    public Exercise(String name, double caloriesBurned) {
        this.name = name;
        this.caloriesBurned = caloriesBurned;
    }

    public String getName() {
        return name;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }
}
