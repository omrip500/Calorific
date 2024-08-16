package com.example.calorific2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.calorific2.Adapters.FoodApiAdapter;
import com.example.calorific2.Management.FoodItem;
import com.example.calorific2.Management.MyApplication;
import com.example.calorific2.Management.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddFoodActivity extends BaseActivity {
    private Button btn_prepared_meals;
    private EditText search_food;
    private RecyclerView rv_food_results;
    private FoodApiAdapter adapter;
    private final List<JSONObject> jsonResultsList = new ArrayList<>();
    private User user;

    private static final long TYPING_DELAY = 500;
    private final Handler typingHandler = new Handler();
    private final Runnable sendRequestRunnable = () -> sendHttpRequest(search_food.getText().toString().trim());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_add_food);
        MyApplication app = (MyApplication) getApplicationContext();
        this.user = app.getUser();
        findAddFoodViews();
        initViews();
        setupRecyclerView();
    }

    private void sendHttpRequest(String query) {
        String url = "https://api.edamam.com/api/food-database/v2/parser?app_id=5959fc61&app_key=85b5888cbffcdf412b874357fe018a5e&ingr=" + query;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                jsonResultsList.clear();
                adapter.updateData(new ArrayList<>());

                if (response.has("hints")) {
                    for (int i = 0; i < response.getJSONArray("hints").length(); i++) {
                        JSONObject foodItemJson = response.getJSONArray("hints").getJSONObject(i).getJSONObject("food");
                        if (foodItemJson.has("image") && !foodItemJson.getString("image").isEmpty()) {
                            jsonResultsList.add(foodItemJson);
                        }
                    }
                } else {
                    JSONObject noResult = new JSONObject();
                    noResult.put("label", "No results found");
                    jsonResultsList.add(noResult);
                }

                adapter.updateData(jsonResultsList);
                rv_food_results.setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                Log.e("API Error", "Error parsing JSON response: " + e.getMessage());
            }
        }, volleyError -> Log.e("API Error", "Error fetching data: " + volleyError.getMessage()));

        Volley.newRequestQueue(this).add(request);
    }

    private void findAddFoodViews() {
        btn_prepared_meals = findViewById(R.id.btn_prepared_meals);
        search_food = findViewById(R.id.et_search_food);
        rv_food_results = findViewById(R.id.rv_food_results);
    }

    private void initViews() {
        btn_prepared_meals.setOnClickListener(v -> moveToAddANewMealActivity());

        search_food.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                typingHandler.removeCallbacks(sendRequestRunnable);
                if (!s.toString().isEmpty()) {
                    typingHandler.postDelayed(sendRequestRunnable, TYPING_DELAY);
                } else {
                    rv_food_results.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new FoodApiAdapter(new ArrayList<>(), this::handleItemClick);
        rv_food_results.setAdapter(adapter);
        rv_food_results.setVisibility(View.GONE);
    }

    private void moveToAddANewMealActivity() {
        Intent addANewMealIntent = new Intent(this, ReadyMealsActivity.class);
        startActivity(addANewMealIntent);
    }

    private void handleItemClick(JSONObject foodItemJson) {
        try {
            String label = foodItemJson.getString("label");
            double calories = foodItemJson.getJSONObject("nutrients").getDouble("ENERC_KCAL");
            double protein = foodItemJson.getJSONObject("nutrients").getDouble("PROCNT");
            double fat = foodItemJson.getJSONObject("nutrients").getDouble("FAT");
            double carbs = foodItemJson.getJSONObject("nutrients").getDouble("CHOCDF");
            String imageUrl = foodItemJson.getString("image");

            Log.d("FoodItem", "Label: " + label + ", Calories: " + calories + ", Protein: " + protein + ", Fat: " + fat + ", Carbs: " + carbs);


            FoodItem foodItem = new FoodItem(label, calories, protein, fat, carbs, imageUrl);
            Intent intent = new Intent(this, QuantitySelectionActivity.class);
            intent.putExtra("selected_food_item", foodItem);
            intent.putExtra("user", user);
            startActivity(intent);
        } catch (JSONException e) {
            Log.e("HandleItemClick", "Error processing food item JSON: " + e.getMessage());
        }
    }
}
