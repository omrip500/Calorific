package com.example.calorific2.Manegment;

import java.io.Serializable;

public class Meal implements Serializable {
    private String readyMealId;
    private String name;
    private double calories;
    private double protein;
    private double fat;
    private double carbs;
    private double quantity;

    // with quantity - for adding from api
    public Meal(String readyMealId, String name, double calories, double protein, double fat, double carbs, double quantity) {
        this.readyMealId = readyMealId;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.quantity = quantity;
    }

    // with no quantity - for adding from ready meals
    public Meal(String readyMealId, String name, double calories, double protein, double fat, double carbs) {
        this.readyMealId = readyMealId;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.quantity = quantity;
    }

    public Meal(String name, double calories, double protein, double fat, double carbs, double quantity) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.quantity = quantity;
    }

    public Meal() {

    }

    // Getters and setters for each field


    public double getQuantity() {
        return quantity;
    }

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
