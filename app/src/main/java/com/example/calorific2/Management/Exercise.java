package com.example.calorific2.Management;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String name;
    private double caloriesBurned;

    public Exercise(String name, double caloriesBurned) {
        this.name = name;
        this.caloriesBurned = caloriesBurned;
    }

    public Exercise() {
        
    }

    public String getName() {
        return name;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }
}
