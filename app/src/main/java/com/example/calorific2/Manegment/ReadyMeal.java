package com.example.calorific2.Manegment;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class ReadyMeal implements Serializable {

    private String id;
    private String name;
    private double calories;
    private double proteinInGrams;
    private double carbsInGrams;
    private double fatInGrams;

    public ReadyMeal(String name, double calories, double proteinInGrams, double carbsInGrams, double fatInGrams) {
        this.id = UUID.randomUUID().toString(); // יצירת מזהה ייחודי
        this.name = name;
        this.calories = calories;
        this.proteinInGrams = proteinInGrams;
        this.carbsInGrams = carbsInGrams;
        this.fatInGrams = fatInGrams;
    }

    // Getters and Setters...

    public String getName() {
        return name;
    }

    public ReadyMeal setName(String name) {
        this.name = name;
        return this;
    }

    public double getCalories() {
        return calories;
    }

    public ReadyMeal setCalories(double calories) {
        this.calories = calories;
        return this;
    }

    public double getProteinInGrams() {
        return proteinInGrams;
    }

    public ReadyMeal setProteinInGrams(double proteinInGrams) {
        this.proteinInGrams = proteinInGrams;
        return this;
    }

    public double getCarbsInGrams() {
        return carbsInGrams;
    }

    public ReadyMeal setCarbsInGrams(double carbsInGrams) {
        this.carbsInGrams = carbsInGrams;
        return this;
    }

    public double getFatInGrams() {
        return fatInGrams;
    }

    public ReadyMeal setFatInGrams(double fatInGrams) {
        this.fatInGrams = fatInGrams;
        return this;
    }

    public String getId() {
        return id;
    }

    public ReadyMeal setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReadyMeal readyMeal = (ReadyMeal) o;
        return Objects.equals(id, readyMeal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
