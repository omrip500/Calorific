package com.example.calorific2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.calorific2.Manegment.MyApplication;
import com.example.calorific2.Manegment.ReadyMeal;
import com.example.calorific2.Manegment.User;
import com.google.android.material.textfield.TextInputEditText;

public class AddNewMealActivity extends BaseActivity {

    private User user;

    private MyApplication app;
    private ReadyMeal meal; // משתנה לשמירת המנה שמבצעים עליה עריכה, אם קיימת

    private TextInputEditText mealNameInput;
    private TextInputEditText caloriesInput;
    private TextInputEditText proteinInput;
    private TextInputEditText carbsInput;
    private TextInputEditText fatsInput;

    private Button chooseImageButton;
    private Button saveMealButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_add_new_meal);

        this.app = (MyApplication) getApplicationContext();
        this.user = app.getUser();
        this.meal = (ReadyMeal) getIntent().getSerializableExtra("meal"); // קבלת המנה אם נמצאת בעריכה

        findAddNewMealViews();
        initViews();

        if (meal != null) {
            populateFieldsWithMealData(meal); // מילוי השדות במידע המנה הקיים
        }
    }

    private void findAddNewMealViews() {
        mealNameInput = findViewById(R.id.et_meal_name);
        caloriesInput = findViewById(R.id.et_calories);
        proteinInput = findViewById(R.id.et_protein);
        carbsInput = findViewById(R.id.et_carbs);
        fatsInput = findViewById(R.id.et_fats);
        chooseImageButton = findViewById(R.id.btn_choose_image);
        saveMealButton = findViewById(R.id.btn_save_meal);
    }

    private void initViews() {
        saveMealButton.setOnClickListener(v -> {
            String name = mealNameInput.getText().toString().trim();
            double calories = parseDouble(caloriesInput.getText().toString().trim());
            double protein = parseDouble(proteinInput.getText().toString().trim());
            double carbs = parseDouble(carbsInput.getText().toString().trim());
            double fats = parseDouble(fatsInput.getText().toString().trim());

            if (meal == null) {
                // יצירת מנה חדשה
                meal = new ReadyMeal(name, calories, protein, carbs, fats);
                user.getReadyMeals().add(meal);
            } else {
                // עריכת מנה קיימת
                double oldCalories = meal.getCalories();
                double oldProtein = meal.getProteinInGrams();
                double oldCarbs = meal.getCarbsInGrams();
                double oldFats = meal.getFatInGrams();

                meal.setName(name)
                        .setCalories(calories)
                        .setProteinInGrams(protein)
                        .setCarbsInGrams(carbs)
                        .setFatInGrams(fats);

                // עדכון המנה הקיימת ברשימה של המשתמש
                int index = user.getReadyMeals().indexOf(meal);
                if (index >= 0) {
                    user.getReadyMeals().set(index, meal);
                }

                // עדכון הערכים אצל המשתמש
                user.setCaloriesCunsumption(user.getCaloriesCunsumption() - oldCalories + calories);
                user.setGramOfProtein(user.getGramOfProtein() - oldProtein + protein);
                user.setGramOfCarbs(user.getGramOfCarbs() - oldCarbs + carbs);
                user.setGramOfFat(user.getGramOfFat() - oldFats + fats);
            }

            Toast.makeText(AddNewMealActivity.this, "Meal saved: " + meal.getName(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // סיום הפעילות כדי לוודא שהיא לא נשארת ברקע
        });
    }

    private void populateFieldsWithMealData(ReadyMeal meal) {
        mealNameInput.setText(meal.getName());
        caloriesInput.setText(String.valueOf(meal.getCalories()));
        proteinInput.setText(String.valueOf(meal.getProteinInGrams()));
        carbsInput.setText(String.valueOf(meal.getCarbsInGrams()));
        fatsInput.setText(String.valueOf(meal.getFatInGrams()));
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
