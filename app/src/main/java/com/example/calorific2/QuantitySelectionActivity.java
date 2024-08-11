package com.example.calorific2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.calorific2.Manegment.FoodItem;
import com.example.calorific2.Manegment.Meal;
import com.example.calorific2.Manegment.User;

public class QuantitySelectionActivity extends BaseActivity {

    private TextView tv_selected_food;
    private EditText quantityEditText;
    private Button addButton;
    private ImageView foodImageView;
    private FoodItem selectedFoodItem;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_quantity_selection);
        findQuantitySelectionViews();

        this.selectedFoodItem = (FoodItem) getIntent().getSerializableExtra("selected_food_item");
        this.user = (User) getIntent().getSerializableExtra("user");

        if (selectedFoodItem != null) {
            Log.d("Selected Item", "Item: " + selectedFoodItem.getLabel());
            Log.d("Nutritional Info", "Calories: " + selectedFoodItem.getCalories() +
                    ", Protein: " + selectedFoodItem.getProtein() +
                    ", Fat: " + selectedFoodItem.getFat() +
                    ", Carbs: " + selectedFoodItem.getCarbs());
            Log.d("Image URL", "Image URL: " + selectedFoodItem.getImageUrl());
        }
        initViews();
    }

    private void findQuantitySelectionViews() {
        tv_selected_food = findViewById(R.id.tv_selected_food);
        quantityEditText = findViewById(R.id.et_quantity);
        addButton = findViewById(R.id.btn_add);
        foodImageView = findViewById(R.id.iv_food_image); // מציאת ה-ImageView מה-XML
    }

    private void initViews() {
        tv_selected_food.setText(this.selectedFoodItem.getLabel());

        Glide.with(this)
                .load(this.selectedFoodItem.getImageUrl())
//                .placeholder(R.drawable.placeholder_image) // תמונה שתוצג בזמן הטעינה
//                .error(R.drawable.error_image) // תמונה שתוצג אם יש בעיה בטעינה
                .into(foodImageView);

        addButton.setOnClickListener(v -> addFoodToLog());
    }

    private void addFoodToLog() {
        String selectedFood = tv_selected_food.getText().toString();
        String quantityString  = quantityEditText.getText().toString();

        if (quantityString.isEmpty()) {
            Toast.makeText(this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, selectedFood + " added with quantity " + quantityString, Toast.LENGTH_SHORT).show();

            user.getMeals().add(new Meal(selectedFoodItem.getLabel(), selectedFoodItem.getCalories(), selectedFoodItem.getProtein(), selectedFoodItem.getFat()
            , selectedFoodItem.getCarbs()));

            double quantity = Double.parseDouble(quantityString);

            double foodCaloriesPerOneGram = selectedFoodItem.getCalories() / 100;
            double foodProteinPerOneGram = selectedFoodItem.getProtein() / 100;
            double foodCarbsPerOneGram = selectedFoodItem.getCarbs() / 100;
            double foodFatPerOneGram = selectedFoodItem.getFat() / 100;

            user.setCaloriesCunsumption(user.getCaloriesCunsumption() + foodCaloriesPerOneGram * quantity);
            user.setGramOfProtein(user.getGramOfProtein() + foodProteinPerOneGram * quantity);
            user.setGramOfFat(user.getGramOfFat() + foodCarbsPerOneGram * quantity);
            user.setGramOfCarbs(user.getGramOfCarbs() + foodFatPerOneGram * quantity);

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
    }
}
