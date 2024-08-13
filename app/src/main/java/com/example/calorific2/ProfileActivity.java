package com.example.calorific2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.calorific2.Manegment.MyApplication;
import com.example.calorific2.Manegment.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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

    private void saveUserToFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    // הצלחה - ביצוע פעולה כלשהי אם יש צורך
                    app.setUser(user); // עדכון ה-user באפליקציה
                })
                .addOnFailureListener(e -> {
                    // כשלון - טיפול בשגיאה
                    e.printStackTrace();
                });
    }

    private void updateUserData() {

        if(user == null)
            user = new User();

        user.setFirstName(Objects.requireNonNull(et_first_name.getText()).toString());
        user.setLastName(Objects.requireNonNull(et_last_name.getText()).toString());
        user.setAge(Integer.parseInt(Objects.requireNonNull(et_age.getText()).toString()));

        int weight = (int) Double.parseDouble(Objects.requireNonNull(et_weight.getText()).toString());
        user.setWeight(weight);

        user.setCaloriesAmountPerDay(Integer.parseInt(Objects.requireNonNull(et_calorie_goal.getText()).toString()));

        saveUserToFirestore();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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
