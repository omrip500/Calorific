package com.example.calorific2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.calorific2.Manegment.Exercise;
import com.example.calorific2.Manegment.Meal;
import com.example.calorific2.Manegment.MyApplication;
import com.example.calorific2.Manegment.User;
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
        tvCaloriesBurned = findViewById(R.id.tv_calories_burned); // Assuming you have this view in your layout
        llMealsList = findViewById(R.id.ll_meals_list);
        llExercisesList = findViewById(R.id.ll_exercises_list);
    }

    private void initViews() {
        updateNutritionalSummary();
        updateCaloriesBurned();
        populateMealsList();
        populateExercisesList();
    }

    private void updateNutritionalSummary() {
        tvTotalCarbs.setText("Total Carbohydrates: " + user.getGramOfCarbs() + " g");
        tvTotalProtein.setText("Total Protein: " + user.getGramOfProtein() + " g");
        tvTotalFat.setText("Total Fat: " + user.getGramOfFat() + " g");
        tvTotalCalories.setText("Total Calories: " + user.getCaloriesCunsumption() + " kcal");
    }

    private void updateCaloriesBurned() {
        tvCaloriesBurned.setText("Calories Burned: " + user.getCaloriesBurned() + " kcal");
    }

    private void populateMealsList() {
        llMealsList.removeAllViews(); // Clear previous views

        for (Meal meal : user.getMeals()) {
            View mealView = getLayoutInflater().inflate(R.layout.meal_item, null);
            MaterialTextView mealName = mealView.findViewById(R.id.tv_meal_name);
            Button btnRemoveMeal = mealView.findViewById(R.id.btn_remove_meal);

            mealName.setText(meal.getName());

            btnRemoveMeal.setOnClickListener(v -> {
                user.getMeals().remove(meal);
                updateUserAfterMealRemoval(meal);
            });

            llMealsList.addView(mealView);
        }
    }

    private void populateExercisesList() {
        llExercisesList.removeAllViews(); // Clear previous views

        for (Exercise exercise : user.getExercises()) {
            View exerciseView = getLayoutInflater().inflate(R.layout.exercise_item, null);
            MaterialTextView exerciseName = exerciseView.findViewById(R.id.tv_exercise_name);
            Button btnRemoveExercise = exerciseView.findViewById(R.id.btn_remove_exercise);

            exerciseName.setText(exercise.getName());

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
        user.setCaloriesCunsumption(totalCalories);
        user.setGramOfCarbs(totalCarbs);
        user.setGramOfProtein(totalProtein);
        user.setGramOfFat(totalFat);

        // If all meals are removed, reset the nutritional values
        if (user.getMeals().isEmpty()) {
            user.setCaloriesCunsumption(0);
            user.setGramOfCarbs(0);
            user.setGramOfProtein(0);
            user.setGramOfFat(0);
        }

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

        // Update the UI for calories burned
        updateCaloriesBurned();
        populateExercisesList(); // Refresh the list
    }
}
