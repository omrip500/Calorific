package com.example.calorific2.Manegment;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String firstName;
    private String lastName;
    private int age;
    private double weight;
    private int caloriesAmountPerDay;
    private double gramOfCarbs;
    private double gramOfFat;
    private double gramOfProtein;
    private double caloriesBurned;
    private double caloriesCunsumption;

    private ArrayList<Meal> meals;

    public User(String firstName, String lastName, int age, double weight, int caloriesAmountPerDay,
                double gramOfCarbs, double gramOfFat, double gramOfProtein, double caloriesBurned, int caloriesCunsumption) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.weight = weight;
        this.caloriesAmountPerDay = caloriesAmountPerDay;
        this.gramOfCarbs = gramOfCarbs;
        this.gramOfFat = gramOfFat;
        this.gramOfProtein = gramOfProtein;
        this.caloriesBurned = caloriesBurned;
        this.caloriesCunsumption = caloriesCunsumption;
        this.meals = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public double getCaloriesCunsumption() {
        return caloriesCunsumption;
    }

    public User setCaloriesCunsumption(double caloriesCunsumption) {
        this.caloriesCunsumption = caloriesCunsumption;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public int getCaloriesAmountPerDay() {
        return caloriesAmountPerDay;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }

    public User setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public User setCaloriesAmountPerDay(int caloriesAmountPerDay) {
        this.caloriesAmountPerDay = caloriesAmountPerDay;
        return this;
    }

    public double getGramOfCarbs() {
        return gramOfCarbs;
    }

    public double getGramOfFat() {
        return gramOfFat;
    }

    public double getGramOfProtein() {
        return gramOfProtein;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public User setGramOfCarbs(double gramOfCarbs) {
        this.gramOfCarbs = gramOfCarbs;
        return this;
    }

    public User setGramOfFat(double gramOfFat) {
        this.gramOfFat = gramOfFat;
        return this;
    }

    public User setGramOfProtein(double gramOfProtein) {
        this.gramOfProtein = gramOfProtein;
        return this;
    }

    public User setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
        return this;
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public User setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
        return this;
    }
}
