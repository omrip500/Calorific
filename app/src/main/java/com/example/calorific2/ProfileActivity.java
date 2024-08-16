package com.example.calorific2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.calorific2.Management.MyApplication;
import com.example.calorific2.Management.User;
import com.example.calorific2.Utils.FirestoreUtils;

import java.util.Objects;

public class ProfileActivity extends BaseActivity {

    private User user;
    private MyApplication app;

    private com.google.android.material.textfield.TextInputEditText et_first_name;
    private com.google.android.material.textfield.TextInputEditText et_last_name;
    private com.google.android.material.textfield.TextInputEditText et_age;
    private com.google.android.material.textfield.TextInputEditText et_weight;
    private com.google.android.material.textfield.TextInputEditText et_calorie_goal;

    private Button btn_save_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_profile);
        this.app = (MyApplication) getApplicationContext();
        this.user = app.getUser();
        findProfileActivityViews();
        initViews();
    }

    private void initViews() {
        btn_save_profile.setOnClickListener(v -> updateUserData());
        et_first_name.setText(user == null ? "" : user.getFirstName());
        et_last_name.setText(user == null ? "" : user.getLastName());
        et_age.setText(user == null ? String.valueOf(0) : String.valueOf(user.getAge()));
        et_weight.setText(user == null ? String.valueOf(0) : String.valueOf(user.getWeight()));
        et_calorie_goal.setText(user == null ? String.valueOf(0) : String.valueOf(user.getCaloriesAmountPerDay()));
    }

    private void updateUserData() {
        // Validate that none of the fields are empty
        if (Objects.requireNonNull(et_first_name.getText()).toString().trim().isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Please enter a first name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Objects.requireNonNull(et_last_name.getText()).toString().trim().isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Please enter a last name", Toast.LENGTH_SHORT).show();
            return;
        }

        String firstName = Objects.requireNonNull(et_first_name.getText()).toString().trim();
        String lastName = Objects.requireNonNull(et_last_name.getText()).toString().trim();
        String ageText = Objects.requireNonNull(et_age.getText()).toString().trim();
        String weightText = Objects.requireNonNull(et_weight.getText()).toString().trim();
        String calorieGoalText = Objects.requireNonNull(et_calorie_goal.getText()).toString().trim();

        // Validate that the first name and last name are not numbers
        if (firstName.matches("\\d+")) {
            Toast.makeText(ProfileActivity.this, "First name cannot be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (lastName.matches("\\d+")) {
            Toast.makeText(ProfileActivity.this, "Last name cannot be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ageText.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Please enter an age", Toast.LENGTH_SHORT).show();
            return;
        }

        if (weightText.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Please enter a weight", Toast.LENGTH_SHORT).show();
            return;
        }

        if (calorieGoalText.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Please enter a calorie goal", Toast.LENGTH_SHORT).show();
            return;
        }

        int age, weight, calorieGoal;

        // Validate that the age, weight, and calorie goal are numbers
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            Toast.makeText(ProfileActivity.this, "Age must be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            weight = (int) Double.parseDouble(weightText);
        } catch (NumberFormatException e) {
            Toast.makeText(ProfileActivity.this, "Weight must be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            calorieGoal = Integer.parseInt(calorieGoalText);
        } catch (NumberFormatException e) {
            Toast.makeText(ProfileActivity.this, "Calorie goal must be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proceed with updating user data
        if (user == null)
            user = new User();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setWeight(weight);
        user.setCaloriesAmountPerDay(calorieGoal);

        FirestoreUtils.saveUserToFirestore(user, app).addOnSuccessListener(aVoid -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }).addOnFailureListener(Throwable::printStackTrace);
    }

    private void findProfileActivityViews() {
        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_age = findViewById(R.id.et_age);
        et_weight = findViewById(R.id.et_weight);
        et_calorie_goal = findViewById(R.id.et_calorie_goal);
        btn_save_profile = findViewById(R.id.btn_save_profile);
    }
}
