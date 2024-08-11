package com.example.calorific2.Manegment;

import java.io.Serializable;

public class Meal implements Serializable {
    private String name;
    private double calories;
    private double protein;
    private double fat;
    private double carbs;

    public Meal(String name, double calories, double protein, double fat, double carbs) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    // Getters and setters for each field
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }
}
