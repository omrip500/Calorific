package com.example.calorific2;

import android.os.Bundle;


public class ViewDaySummaryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_view_day_summary);  // טוען את activity_add_food.xml לתוך content_frame
    }
}