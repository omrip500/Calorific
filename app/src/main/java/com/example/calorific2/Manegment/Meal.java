package com.example.calorific2.Manegment;

import java.io.Serializable;

public class Meal implements Serializable {
    private String readyMealId; // מזהה של הארוחה המוכנה, אם הארוחה נוצרה מ-ReadyMeal
    private String name;
    private double calories;
    private double protein;
    private double fat;
    private double carbs;

    public Meal(String readyMealId, String name, double calories, double protein, double fat, double carbs) {
        this.readyMealId = readyMealId;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    public Meal(String name, double calories, double protein, double fat, double carbs) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    // Getters and setters for each field

    public String getReadyMealId() {
        return readyMealId;
    }

    public void setReadyMealId(String readyMealId) {
        this.readyMealId = readyMealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }
}
