package com.example.calorific2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.calorific2.Management.Meal;
import com.example.calorific2.Management.MyApplication;
import com.example.calorific2.Management.ReadyMeal;
import com.example.calorific2.Management.User;
import com.example.calorific2.Utils.FirestoreUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AddNewMealActivity extends BaseActivity {

    private User user;

    private MyApplication app;
    private ReadyMeal meal;

    private TextInputEditText mealNameInput;
    private TextInputEditText caloriesInput;
    private TextInputEditText proteinInput;
    private TextInputEditText carbsInput;
    private TextInputEditText fatsInput;
    private Button saveMealButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_add_new_meal);

        this.app = (MyApplication) getApplicationContext();
        this.user = app.getUser();
        this.meal = (ReadyMeal) getIntent().getSerializableExtra("meal");

        findAddNewMealViews();
        initViews();

        if (meal != null) {
            populateFieldsWithMealData(meal);
        }
    }

    private void findAddNewMealViews() {
        mealNameInput = findViewById(R.id.et_meal_name);
        caloriesInput = findViewById(R.id.et_calories);
        proteinInput = findViewById(R.id.et_protein);
        carbsInput = findViewById(R.id.et_carbs);
        fatsInput = findViewById(R.id.et_fats);
        saveMealButton = findViewById(R.id.btn_save_meal);
    }

    private void initViews() {
        saveMealButton.setOnClickListener(v -> {
            // Get input data from fields
            String name = Objects.requireNonNull(mealNameInput.getText()).toString().trim();
            String caloriesText = Objects.requireNonNull(caloriesInput.getText()).toString().trim();
            String proteinText = Objects.requireNonNull(proteinInput.getText()).toString().trim();
            String carbsText = Objects.requireNonNull(carbsInput.getText()).toString().trim();
            String fatsText = Objects.requireNonNull(fatsInput.getText()).toString().trim();

            // Validate that none of the fields are empty
            if (name.isEmpty()) {
                Toast.makeText(AddNewMealActivity.this, "Please enter a meal name", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate that the meal name is not just a number
            if (name.matches("\\d+")) {  // Checks if the name consists of only digits
                Toast.makeText(AddNewMealActivity.this, "Meal name cannot be a number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (caloriesText.isEmpty()) {
                Toast.makeText(AddNewMealActivity.this, "Please enter a calorie value", Toast.LENGTH_SHORT).show();
                return;
            }

            if (proteinText.isEmpty()) {
                Toast.makeText(AddNewMealActivity.this, "Please enter the amount of protein", Toast.LENGTH_SHORT).show();
                return;
            }

            if (carbsText.isEmpty()) {
                Toast.makeText(AddNewMealActivity.this, "Please enter the amount of carbs", Toast.LENGTH_SHORT).show();
                return;
            }

            if (fatsText.isEmpty()) {
                Toast.makeText(AddNewMealActivity.this, "Please enter the amount of fats", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate that numerical fields contain valid numbers
            double calories, protein, carbs, fats;
            try {
                calories = Double.parseDouble(caloriesText);
            } catch (NumberFormatException e) {
                Toast.makeText(AddNewMealActivity.this, "Calories must be a number", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                protein = Double.parseDouble(proteinText);
            } catch (NumberFormatException e) {
                Toast.makeText(AddNewMealActivity.this, "Protein must be a number", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                carbs = Double.parseDouble(carbsText);
            } catch (NumberFormatException e) {
                Toast.makeText(AddNewMealActivity.this, "Carbs must be a number", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                fats = Double.parseDouble(fatsText);
            } catch (NumberFormatException e) {
                Toast.makeText(AddNewMealActivity.this, "Fats must be a number", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save the meal data
            if (meal == null) {
                meal = new ReadyMeal(name, calories, protein, carbs, fats);
                user.getReadyMeals().add(meal);
            } else {
                double oldCalories = meal.getCalories();
                double oldProtein = meal.getProteinInGrams();
                double oldCarbs = meal.getCarbsInGrams();
                double oldFats = meal.getFatInGrams();

                meal.setName(name)
                        .setCalories(calories)
                        .setProteinInGrams(protein)
                        .setCarbsInGrams(carbs)
                        .setFatInGrams(fats);

                int index = user.getReadyMeals().indexOf(meal);
                if (index >= 0) {
                    user.getReadyMeals().set(index, meal);
                }

                for (Meal userMeal : user.getMeals()) {
                    if (userMeal.getReadyMealId() != null && userMeal.getReadyMealId().equals(meal.getId())) {
                        user.setCaloriesConsumption(user.getCaloriesConsumption() - oldCalories + calories);
                        user.setGramOfProtein(user.getGramOfProtein() - oldProtein + protein);
                        user.setGramOfCarbs(user.getGramOfCarbs() - oldCarbs + carbs);
                        user.setGramOfFat(user.getGramOfFat() - oldFats + fats);
                    }
                }
            }

            // Save user data to Firestore
            FirestoreUtils.saveUserToFirestore(user, app);
            Intent intent = new Intent(this, ReadyMealsActivity.class);
            startActivity(intent);
            finish();

            // Show a confirmation toast
            Toast.makeText(AddNewMealActivity.this, "Meal saved: " + meal.getName(), Toast.LENGTH_SHORT).show();
        });
    }



    private void populateFieldsWithMealData(ReadyMeal meal) {
        mealNameInput.setText(meal.getName());
        caloriesInput.setText(String.valueOf(meal.getCalories()));
        proteinInput.setText(String.valueOf(meal.getProteinInGrams()));
        carbsInput.setText(String.valueOf(meal.getCarbsInGrams()));
        fatsInput.setText(String.valueOf(meal.getFatInGrams()));
    }
}
