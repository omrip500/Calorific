package com.example.calorific2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.calorific2.Management.Exercise;
import com.example.calorific2.Management.MyApplication;
import com.example.calorific2.Management.User;
import com.example.calorific2.Utils.CsvUtil;
import com.example.calorific2.Utils.FirestoreUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddExerciseActivity extends BaseActivity {

    private User user;
    private MyApplication app;

    private AutoCompleteTextView actExerciseType;
    private EditText etCaloriesBurned;
    private Button btnSaveExercise;

    private Map<String, Double> exerciseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_add_exercise);

        this.app = (MyApplication) getApplicationContext();
        this.user = app.getUser();

        findAddExerciseActivityViews();
        initViews();
    }

    private void findAddExerciseActivityViews() {
        actExerciseType = findViewById(R.id.actv_exercise_type);
        etCaloriesBurned = findViewById(R.id.et_calories_burned);
        btnSaveExercise = findViewById(R.id.btn_save_exercise);
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        exerciseData = CsvUtil.loadExercises(this);

        List<String> exerciseNames = new ArrayList<>(exerciseData.keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, exerciseNames);
        actExerciseType.setAdapter(adapter);
        actExerciseType.setThreshold(1);

        actExerciseType.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                actExerciseType.showDropDown();
            }
        });

        btnSaveExercise.setOnClickListener(v -> {
            String exerciseName = actExerciseType.getText().toString();
            double weight = user.getWeight();

            Double caloriesPerKgPerMin = exerciseData.get(exerciseName);
            if (caloriesPerKgPerMin != null) {
                double caloriesBurned = caloriesPerKgPerMin * weight;

                int caloriesBurnedInt = (int) caloriesBurned;
                etCaloriesBurned.setText(String.valueOf(caloriesBurnedInt));

                user.setCaloriesBurned(user.getCaloriesBurned() + caloriesBurned);

                Exercise exercise = new Exercise(exerciseName, caloriesBurned);

                user.addExercise(exercise);

                FirestoreUtils.saveUserToFirestore(user, app).addOnSuccessListener(aVoid -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }).addOnFailureListener(Throwable::printStackTrace);
            } else {
                etCaloriesBurned.setText("Exercise not found");
            }
        });
    }
}
