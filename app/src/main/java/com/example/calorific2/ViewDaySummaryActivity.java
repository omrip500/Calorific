package com.example.calorific2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.calorific2.Management.Exercise;
import com.example.calorific2.Management.Meal;
import com.example.calorific2.Management.MyApplication;
import com.example.calorific2.Management.User;
import com.example.calorific2.Utils.FirestoreUtils;
import com.google.android.material.textview.MaterialTextView;

public class ViewDaySummaryActivity extends BaseActivity {

    private User user;
    private MyApplication app;

    // Views
    private MaterialTextView tvTotalCarbs, tvTotalProtein, tvTotalFat, tvTotalCalories, tvCaloriesBurned;
    private LinearLayout llMealsList, llExercisesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_view_day_summary);

        this.app = (MyApplication) getApplicationContext();
        this.user = app.getUser();

        findViewDaySummaryViews();
        initViews();
    }

    private void findViewDaySummaryViews() {
        tvTotalCarbs = findViewById(R.id.tv_total_carbs);
        tvTotalProtein = findViewById(R.id.tv_total_protein);
        tvTotalFat = findViewById(R.id.tv_total_fat);
        tvTotalCalories = findViewById(R.id.tv_total_calories);
        tvCaloriesBurned = findViewById(R.id.tv_calories_burned);
        llMealsList = findViewById(R.id.ll_meals_list);
        llExercisesList = findViewById(R.id.ll_exercises_list);
    }

    private void initViews() {
        updateNutritionalSummary();
        updateCaloriesBurned();
        populateMealsList();
        populateExercisesList();
    }

    @SuppressLint("SetTextI18n")
    private void updateNutritionalSummary() {
        tvTotalCarbs.setText("Total Carbohydrates: " + (int) user.getGramOfCarbs() + " g");
        tvTotalProtein.setText("Total Protein: " + (int) user.getGramOfProtein() + " g");
        tvTotalFat.setText("Total Fat: " + (int) user.getGramOfFat() + " g");
        tvTotalCalories.setText("Total Calories: " + (int) user.getCaloriesConsumption() + " kcal");
    }

    @SuppressLint("SetTextI18n")
    private void updateCaloriesBurned() {
        tvCaloriesBurned.setText("Calories Burned: " + (int) user.getCaloriesBurned() + " kcal");
    }

    @SuppressLint("SetTextI18n")
    private void populateMealsList() {
        llMealsList.removeAllViews(); // Clear previous views

        for (Meal meal : user.getMeals()) {
            @SuppressLint("InflateParams") View mealView = getLayoutInflater().inflate(R.layout.meal_item, null);
            MaterialTextView mealName = mealView.findViewById(R.id.tv_meal_name);
            MaterialTextView mealCalories = mealView.findViewById(R.id.tv_meal_calories);
            MaterialTextView mealProtein = mealView.findViewById(R.id.tv_meal_protein);
            MaterialTextView mealCarbs = mealView.findViewById(R.id.tv_meal_carbs);
            MaterialTextView mealFat = mealView.findViewById(R.id.tv_meal_fat);
            Button btnRemoveMeal = mealView.findViewById(R.id.btn_remove_meal);

            mealName.setText(meal.getName());
            mealCalories.setText("Calories: " + (int) meal.getCalories() + " kcal");
            mealProtein.setText("Protein: " + (int) meal.getProtein() + " g");
            mealCarbs.setText("Carbohydrates: " + (int) meal.getCarbs() + " g");
            mealFat.setText("Fat: " + (int) meal.getFat() + " g");

            btnRemoveMeal.setOnClickListener(v -> {
                user.getMeals().remove(meal);
                updateUserAfterMealRemoval(meal);
            });

            llMealsList.addView(mealView);
        }
    }

    @SuppressLint("SetTextI18n")
    private void populateExercisesList() {
        llExercisesList.removeAllViews(); // Clear previous views

        for (Exercise exercise : user.getExercises()) {
            @SuppressLint("InflateParams") View exerciseView = getLayoutInflater().inflate(R.layout.exercise_item, null);
            MaterialTextView exerciseName = exerciseView.findViewById(R.id.tv_exercise_name);
            MaterialTextView exerciseCalories = exerciseView.findViewById(R.id.tv_exercise_calories);
            Button btnRemoveExercise = exerciseView.findViewById(R.id.btn_remove_exercise);

            exerciseName.setText(exercise.getName());
            exerciseCalories.setText("Calories Burned: " + (int) exercise.getCaloriesBurned() + " kcal");

            btnRemoveExercise.setOnClickListener(v -> {
                user.getExercises().remove(exercise);
                updateUserAfterExerciseRemoval(exercise);
            });

            llExercisesList.addView(exerciseView);
        }
    }

    private void updateUserAfterMealRemoval(Meal meal) {
        // Remove the meal from the user's list of meals
        user.getMeals().remove(meal);

        // Recalculate the user's nutritional data based on remaining meals
        double totalCalories = 0;
        double totalCarbs = 0;
        double totalProtein = 0;
        double totalFat = 0;

        for (Meal remainingMeal : user.getMeals()) {
            totalCalories += remainingMeal.getCalories();
            totalCarbs += remainingMeal.getCarbs();
            totalProtein += remainingMeal.getProtein();
            totalFat += remainingMeal.getFat();
        }

        // Update the user's nutritional data
        user.setCaloriesConsumption(totalCalories);
        user.setGramOfCarbs(totalCarbs);
        user.setGramOfProtein(totalProtein);
        user.setGramOfFat(totalFat);

        // If all meals are removed, reset the nutritional values
        if (user.getMeals().isEmpty()) {
            user.setCaloriesConsumption(0);
            user.setGramOfCarbs(0);
            user.setGramOfProtein(0);
            user.setGramOfFat(0);
        }
        FirestoreUtils.saveUserToFirestore(user, app);

        // Update the UI
        updateNutritionalSummary();
        populateMealsList(); // Refresh the list
    }

    private void updateUserAfterExerciseRemoval(Exercise exercise) {
        // Remove the exercise from the user's list of exercises
        user.getExercises().remove(exercise);

        // Recalculate the user's calories burned based on remaining exercises
        double totalCaloriesBurned = 0;

        for (Exercise remainingExercise : user.getExercises()) {
            totalCaloriesBurned += remainingExercise.getCaloriesBurned();
        }

        // Update the user's calories burned data
        user.setCaloriesBurned(totalCaloriesBurned);

        // If all exercises are removed, reset the calories burned value
        if (user.getExercises().isEmpty()) {
            user.setCaloriesBurned(0);
        }

        FirestoreUtils.saveUserToFirestore(user, app);
        // Update the UI for calories burned
        updateCaloriesBurned();
        populateExercisesList(); // Refresh the list
    }
}
