package com.example.calorific2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.calorific2.Adapters.ReadyMealAdapter;
import com.example.calorific2.Management.Meal;
import com.example.calorific2.Management.MyApplication;
import com.example.calorific2.Management.ReadyMeal;
import com.example.calorific2.Management.User;
import com.example.calorific2.Utils.FirestoreUtils;

import java.util.ArrayList;

public class ReadyMealsActivity extends BaseActivity {
    private Button btn_add_new_meal;
    private ListView lv_prepared_meals;
    private User user;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_ready_meals);
        findPreparedMealsViews();
        this.app = (MyApplication) getApplicationContext();
        this.user = app.getUser();

        initViews();
        displayPreparedMeals();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayPreparedMeals();
    }

    private void initViews() {
        btn_add_new_meal.setOnClickListener(v -> moveToAddANewMealActivity());
    }

    private void findPreparedMealsViews() {
        btn_add_new_meal = findViewById(R.id.btn_add_new_meal);
        lv_prepared_meals = findViewById(R.id.lv_prepared_meals);
    }

    private void displayPreparedMeals() {
        ArrayList<ReadyMeal> readyMealsList = user.getReadyMeals();

        ReadyMealAdapter adapter = new ReadyMealAdapter(this, readyMealsList, new ReadyMealAdapter.OnMealActionListener() {
            @Override
            public void onEditMeal(ReadyMeal meal) {
                Intent intent = new Intent(ReadyMealsActivity.this, AddNewMealActivity.class);
                intent.putExtra("meal", meal);
                startActivity(intent);
            }

            @Override
            public void onDeleteMeal(ReadyMeal meal) {
                user.getReadyMeals().remove(meal);
                FirestoreUtils.saveUserToFirestore(user, app);
                displayPreparedMeals();
                Toast.makeText(ReadyMealsActivity.this, "Deleted " + meal.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMealSelected(ReadyMeal meal) {
                addMealToUser(meal);
                Intent intent = new Intent(ReadyMealsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        lv_prepared_meals.setAdapter(adapter);
    }

    private void addMealToUser(ReadyMeal readyMeal) {
        Meal meal = new Meal(readyMeal.getId(), readyMeal.getName(), readyMeal.getCalories(), readyMeal.getProteinInGrams(), readyMeal.getFatInGrams(),
                readyMeal.getCarbsInGrams());

        user.getMeals().add(meal);

        user.setGramOfCarbs(user.getGramOfCarbs() + readyMeal.getCarbsInGrams());
        user.setGramOfFat(user.getGramOfFat() + readyMeal.getFatInGrams());
        user.setGramOfProtein(user.getGramOfProtein() + readyMeal.getProteinInGrams());
        user.setCaloriesConsumption(user.getCaloriesConsumption() + readyMeal.getCalories());

        FirestoreUtils.saveUserToFirestore(user, app);
        Toast.makeText(this, "Meal added: " + readyMeal.getName(), Toast.LENGTH_SHORT).show();
    }

    private void moveToAddANewMealActivity() {
        Intent addANewMealIntent = new Intent(ReadyMealsActivity.this, AddNewMealActivity.class);
        startActivity(addANewMealIntent);
    }
}

