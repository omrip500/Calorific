package com.example.calorific2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawerLayout;
    private androidx.appcompat.widget.Toolbar toolbar;
    private NavigationView navigationView;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        findViews();
        navigationView.setNavigationItemSelectedListener(this);
        initToolbarAndNavBar();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    finish();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    // Set the content for the derived activity
    protected void setContentLayout(int layoutResID) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup contentFrame = findViewById(R.id.content_frame);
        inflater.inflate(layoutResID, contentFrame, true);
    }

    private void initToolbarAndNavBar() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    void findViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Class currentActivity = this.getClass();

        if (id == R.id.nav_home && currentActivity != MainActivity.class) {
            Intent homeIntent = new Intent(this, MainActivity.class);
            startActivity(homeIntent);
        } else if (id == R.id.nav_meal && currentActivity != AddFoodActivity.class) {
            Intent addFoodIntent = new Intent(this, AddFoodActivity.class);
            startActivity(addFoodIntent);
        } else if (id == R.id.nav_ready_meals && currentActivity != AddFromPreparedMealsActivity.class) {
            Intent readyMealsIntent = new Intent(this, AddFromPreparedMealsActivity.class);
            startActivity(readyMealsIntent);
        } else if (id == R.id.nav_exercise && currentActivity != AddExerciseActivity.class) {
            Intent addExerciseIntent = new Intent(this, AddExerciseActivity.class);
            startActivity(addExerciseIntent);
        } else if (id == R.id.nav_summary && currentActivity != ViewDaySummaryActivity.class) {
            Intent viewSummaryIntent = new Intent(this, ViewDaySummaryActivity.class);
            startActivity(viewSummaryIntent);
        } else if (id == R.id.nav_profile && currentActivity != ProfileActivity.class) {
            Intent updateUserDataIntent = new Intent(this, ProfileActivity.class);
            startActivity(updateUserDataIntent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }





}

