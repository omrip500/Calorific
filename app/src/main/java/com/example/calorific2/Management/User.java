package com.example.calorific2.Management;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private double caloriesConsumption;
    private ArrayList<Meal> meals;
    private ArrayList<ReadyMeal> readyMeals;

    private ArrayList<Exercise> exercises;

    private String lastDateUsingTheApp;


    @SuppressLint("SimpleDateFormat")
    public User(String firstName, String lastName, int age, double weight, int caloriesAmountPerDay,
                double gramOfCarbs, double gramOfFat, double gramOfProtein, double caloriesBurned, int caloriesConsumption) {
        Date currentDate = new Date();
        this.lastDateUsingTheApp = new SimpleDateFormat("dd.MM.yyyy").format(currentDate);
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.weight = weight;
        this.caloriesAmountPerDay = caloriesAmountPerDay;
        this.gramOfCarbs = gramOfCarbs;
        this.gramOfFat = gramOfFat;
        this.gramOfProtein = gramOfProtein;
        this.caloriesBurned = caloriesBurned;
        this.caloriesConsumption = caloriesConsumption;
        this.meals = new ArrayList<>();
        this.readyMeals = new ArrayList<>();
        this.exercises = new ArrayList<>();


    }

    @SuppressLint("SimpleDateFormat")
    public User() {
        Date currentDate = new Date();
        this.meals = new ArrayList<>();
        this.readyMeals = new ArrayList<>();
        this.exercises = new ArrayList<>();
        this.lastDateUsingTheApp = new SimpleDateFormat("dd.MM.yyyy").format(currentDate);
    }

    public String getLastDateUsingTheApp() {
        return lastDateUsingTheApp;
    }

    public void setLastDateUsingTheApp(String lastDateUsingTheApp) {
        this.lastDateUsingTheApp = lastDateUsingTheApp;
    }

    public String getFirstName() {
        return firstName;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public double getCaloriesConsumption() {
        return caloriesConsumption;
    }

    public void setCaloriesConsumption(double caloriesConsumption) {
        this.caloriesConsumption = caloriesConsumption;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setCaloriesAmountPerDay(int caloriesAmountPerDay) {
        this.caloriesAmountPerDay = caloriesAmountPerDay;
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

    public void setGramOfCarbs(double gramOfCarbs) {
        this.gramOfCarbs = gramOfCarbs;
    }

    public void setGramOfFat(double gramOfFat) {
        this.gramOfFat = gramOfFat;
    }

    public void setGramOfProtein(double gramOfProtein) {
        this.gramOfProtein = gramOfProtein;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public ArrayList<ReadyMeal> getReadyMeals() {
        return readyMeals;
    }

    public void resetFieldsForANewDay() {
        this.gramOfCarbs = 0;
        this.gramOfFat = 0;
        this.gramOfProtein = 0;
        this.caloriesBurned = 0;
        this.caloriesConsumption = 0;
        this.meals = new ArrayList<>();
        this.exercises = new ArrayList<>();
    }
}
