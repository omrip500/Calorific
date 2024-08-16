package com.example.calorific2.Management;

import java.io.Serializable;

public class FoodItem implements Serializable {
    private final String label;
    private double calories;
    private double protein;
    private double fat;
    private final double carbs;
    private final String imageUrl;

    public FoodItem(String label, double calories, double protein, double fat, double carbs, String imageUrl) {
        this.label = label;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.imageUrl = imageUrl;
    }

    public String getLabel() {
        return label;
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


    public String getImageUrl() {
        return imageUrl;
    }

}
