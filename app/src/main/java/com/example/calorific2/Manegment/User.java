package com.example.calorific2.Manegment;

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
    private double caloriesCunsumption;
    private ArrayList<Meal> meals;
    private ArrayList<ReadyMeal> readyMeals;

    private ArrayList<Exercise> exercises;

    private String lastDateUsingTheApp;


    public User(String firstName, String lastName, int age, double weight, int caloriesAmountPerDay,
                double gramOfCarbs, double gramOfFat, double gramOfProtein, double caloriesBurned, int caloriesCunsumption) {
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
        this.caloriesCunsumption = caloriesCunsumption;
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

    public User setLastDateUsingTheApp(String lastDateUsingTheApp) {
        this.lastDateUsingTheApp = lastDateUsingTheApp;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
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

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public ArrayList<ReadyMeal> getReadyMeals() {
        return readyMeals;
    }

    public User setReadyMeals(ArrayList<ReadyMeal> readyMeals) {
        this.readyMeals = readyMeals;
        return this;
    }

    public User setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public void resetFieldsForANewDay() {
        this.gramOfCarbs = 0;
        this.gramOfFat = 0;
        this.gramOfProtein = 0;
        this.caloriesBurned = 0;
        this.caloriesCunsumption = 0;
        this.meals = new ArrayList<>();
        this.readyMeals = new ArrayList<>();
        this.exercises = new ArrayList<>();
    }
}
