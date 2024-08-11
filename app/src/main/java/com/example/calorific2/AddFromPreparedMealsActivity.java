package com.example.calorific2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class AddFromPreparedMealsActivity extends BaseActivity {
    private Button btn_add_new_meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_add_from_prepared_meals);  // טוען את activity_add_food.xml לתוך content_frame
        findPreparedMealsViews();
        initViews();
    }

    private void initViews() {
        btn_add_new_meal.setOnClickListener(v -> moveToAddANewMealActivity());
    }

    private void moveToAddANewMealActivity() {
        Intent addANewMealIntent  = new Intent(this, AddNewMealActivity.class);
        startActivity(addANewMealIntent);
    }

    private void findPreparedMealsViews() {
        btn_add_new_meal = findViewById(R.id.btn_add_new_meal);
    }
}