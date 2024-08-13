package com.example.calorific2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.calorific2.Manegment.Exercise;
import com.example.calorific2.Manegment.MyApplication;
import com.example.calorific2.Manegment.User;
import com.example.calorific2.Utils.CsvUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddExerciseActivity extends BaseActivity {

    private User user;
    private MyApplication app;

    private AutoCompleteTextView actvExerciseType;
    private EditText etDuration;
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
        actvExerciseType = findViewById(R.id.actv_exercise_type);
        etCaloriesBurned = findViewById(R.id.et_calories_burned);
        btnSaveExercise = findViewById(R.id.btn_save_exercise);
    }

    private void initViews() {
        exerciseData = CsvUtil.loadExercises(this);

        List<String> exerciseNames = new ArrayList<>(exerciseData.keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, exerciseNames);
        actvExerciseType.setAdapter(adapter);
        actvExerciseType.setThreshold(1);

        actvExerciseType.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                actvExerciseType.showDropDown();
            }
        });

        btnSaveExercise.setOnClickListener(v -> {
            String exerciseName = actvExerciseType.getText().toString();
            double weight = user.getWeight();

            if (exerciseData.containsKey(exerciseName)) {
                
                double caloriesPerKgPerMin = exerciseData.get(exerciseName);
                double caloriesBurned = caloriesPerKgPerMin * weight;

                etCaloriesBurned.setText(String.valueOf(caloriesBurned));

                user.setCaloriesBurned(user.getCaloriesBurned() + caloriesBurned);

                Exercise exercise = new Exercise(exerciseName, caloriesBurned);
                user.addExercise(exercise);
            }

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        });
    }


}
