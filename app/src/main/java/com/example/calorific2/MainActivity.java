package com.example.calorific2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;

import com.example.calorific2.Manegment.MyApplication;
import com.example.calorific2.Manegment.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


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
    private MyApplication app;

    // See: https://developer.android.com/training/basics/intents/result
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    private void signIn()
    {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_logo)
                .build();
        signInLauncher.launch(signInIntent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_main);

        this.app = (MyApplication) getApplicationContext();
        this.user = app.getUser();


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
        startActivity(addFoodIntent);
    }

    private void moveToViewSummaryActivity() {
        Intent viewSummaryIntent = new Intent(this, ViewDaySummaryActivity.class);
        startActivity(viewSummaryIntent);
    }

    private void moveToReadyMealsActivity() {
        Intent readyMealsIntent = new Intent(this, ReadyMealsActivity.class);
        startActivity(readyMealsIntent);
    }

    private void moveToAddExerciseActivity() {
        Intent addExerciseIntent = new Intent(this, AddExerciseActivity.class);
        startActivity(addExerciseIntent);
    }

    private void moveToUpdateUserDataActivity() {
        Intent addFoodIntent = new Intent(this, ProfileActivity.class);
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
