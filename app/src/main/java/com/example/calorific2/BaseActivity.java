package com.example.calorific2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.calorific2.Manegment.MyApplication;
import com.example.calorific2.Manegment.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawerLayout;
    private androidx.appcompat.widget.Toolbar toolbar;
    private NavigationView navigationView;

    private User user;
    private MyApplication app;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        this.app = (MyApplication) getApplicationContext();
        this.user = app.getUser();

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
        if (user == null || user.getFirstName() == null || user.getLastName() == null || user.getAge() == 0) {
            showUserDetailsRequiredAlert();
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }

        int id = menuItem.getItemId();
        Class<? extends BaseActivity> currentActivity = this.getClass();

        if (id == R.id.nav_home && currentActivity != MainActivity.class) {
            Intent homeIntent = new Intent(this, MainActivity.class);
            startActivity(homeIntent);
        } else if (id == R.id.nav_meal && currentActivity != AddFoodActivity.class) {
            Intent addFoodIntent = new Intent(this, AddFoodActivity.class);
            startActivity(addFoodIntent);
        } else if (id == R.id.nav_ready_meals && currentActivity != ReadyMealsActivity.class) {
            Intent readyMealsIntent = new Intent(this, ReadyMealsActivity.class);
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
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showUserDetailsRequiredAlert() {
        android.widget.Toast.makeText(this, "Please complete your profile details before proceeding.", Toast.LENGTH_LONG).show();
    }






}

