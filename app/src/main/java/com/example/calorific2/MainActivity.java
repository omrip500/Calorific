package com.example.calorific2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.calorific2.Manegment.User;

public class MainActivity extends BaseActivity {


    private User user;

    private Button btn_add_food;
    private Button btn_view_summary;
    private Button btn_ready_meals;
    private Button btn_add_exercise;
    private Button btn_update_user_data;
    private com.google.android.material.textview.MaterialTextView tv_greeting;
    private com.google.android.material.textview.MaterialTextView tv_carbs;
    private com.google.android.material.textview.MaterialTextView tv_fat;
    private com.google.android.material.textview.MaterialTextView tv_protein;
    private com.google.android.material.textview.MaterialTextView tv_burned;
    private com.google.android.material.textview.MaterialTextView tv_calorie_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_main);

        this.user = (User) getIntent().getSerializableExtra("user");

        if(user == null)
            user = new User("Omri", "Peer", 23, 70,
                2000, 0, 0, 0, 0, 0);


        // Find and initialize views
        mainActivityfindViews();
        initViews();
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        btn_add_food.setOnClickListener(v -> moveToAddFoodActivity());
        btn_view_summary.setOnClickListener(v -> moveToViewSummaryActivity());
        btn_ready_meals.setOnClickListener(v -> moveToReadyMealsActivity());
        btn_add_exercise.setOnClickListener(v -> moveToAddExerciseActivity());
        btn_update_user_data.setOnClickListener(v -> moveToUpdateUserDataActivity());

        int caloriesRemaining = (int) user.getCaloriesAmountPerDay() - (int) user.getCaloriesCunsumption();
        if(caloriesRemaining < 0)
            caloriesRemaining = 0;

        tv_greeting.setText("Hello " + user.getFirstName());
        tv_carbs.setText("Carbs:\n" + (int) user.getGramOfCarbs());
        tv_fat.setText("Fat:\n" + (int) user.getGramOfFat());
        tv_protein.setText("Protein:\n" + (int) user.getGramOfProtein());
        tv_burned.setText("🔥:\n" + (int) user.getCaloriesBurned());
        tv_calorie_info.setText((int) user.getCaloriesCunsumption() + "/" + (int) user.getCaloriesAmountPerDay() +
                "\ncalories\nRemaining: " + caloriesRemaining);
    }

    private void moveToAddFoodActivity() {
        Intent addFoodIntent = new Intent(this, AddFoodActivity.class);
        addFoodIntent.putExtra("user", user);
        startActivity(addFoodIntent);
    }

    private void moveToViewSummaryActivity() {
        Intent viewSummaryIntent = new Intent(this, ViewDaySummaryActivity.class);
        startActivity(viewSummaryIntent);
    }

    private void moveToReadyMealsActivity() {
        Intent readyMealsIntent = new Intent(this, AddFromPreparedMealsActivity.class);
        startActivity(readyMealsIntent);
    }

    private void moveToAddExerciseActivity() {
        Intent addExerciseIntent = new Intent(this, AddExerciseActivity.class);
        startActivity(addExerciseIntent);
    }

    private void moveToUpdateUserDataActivity() {
        Intent addFoodIntent = new Intent(this, ProfileActivity.class);
        addFoodIntent.putExtra("user", user);
        startActivity(addFoodIntent);
    }

    private void mainActivityfindViews() {
        btn_add_food = findViewById(R.id.btn_add_food);
        btn_view_summary = findViewById(R.id.btn_view_summary);
        btn_ready_meals = findViewById(R.id.btn_ready_meals);
        btn_add_exercise = findViewById(R.id.btn_add_exercise);
        btn_update_user_data = findViewById(R.id.btn_update_user_data);
        tv_greeting = findViewById(R.id.tv_greeting);
        tv_carbs = findViewById(R.id.tv_carbs);
        tv_fat = findViewById(R.id.tv_fat);
        tv_burned = findViewById(R.id.tv_burned);
        tv_protein = findViewById(R.id.tv_protein);
        tv_calorie_info = findViewById(R.id.tv_calorie_info);
    }
}
