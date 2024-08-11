package com.example.calorific2;

import android.os.Bundle;


public class AddNewMealActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_add_new_meal);  // טוען את activity_add_food.xml לתוך content_frame
    }
}