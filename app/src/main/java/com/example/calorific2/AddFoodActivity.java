package com.example.calorific2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.calorific2.Adapters.MyAdapter;
import com.example.calorific2.Manegment.FoodItem;
import com.example.calorific2.Manegment.MyApplication;
import com.example.calorific2.Manegment.User;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddFoodActivity extends BaseActivity {
    private Button btn_prepared_meals;
    private EditText search_food;
    private MaterialTextView tv_add_food;
    private RecyclerView rv_food_results;
    private MyAdapter adapter;
    private List<JSONObject> jsonResultsList = new ArrayList<>();
    private User user;
    private MyApplication app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_add_food);
        this.app = (MyApplication) getApplicationContext();
        this.user = app.getUser();
        findAddFoodViews();
        initViews();
        setupRecyclerView();
    }

    private void sendHttpRequest(String query) {
        String url = "https://api.edamam.com/api/food-database/v2/parser?app_id=5959fc61&app_key=85b5888cbffcdf412b874357fe018a5e&ingr=" + query;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonResultsList.clear();

                    if (response.has("hints")) {
                        for (int i = 0; i < response.getJSONArray("hints").length(); i++) {
                            JSONObject foodItemJson = response.getJSONArray("hints").getJSONObject(i).getJSONObject("food");
                            jsonResultsList.add(foodItemJson);
                        }
                    } else {
                        JSONObject noResult = new JSONObject();
                        noResult.put("label", "No results found");
                        jsonResultsList.add(noResult);
                    }

                    adapter.updateData(jsonResultsList);
                    rv_food_results.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("API Error", "Error fetching data: " + volleyError.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(request);
    }

    private void findAddFoodViews() {
        btn_prepared_meals = findViewById(R.id.btn_prepared_meals);
        search_food = findViewById(R.id.et_search_food);
        tv_add_food = findViewById(R.id.tv_add_food);
        rv_food_results = findViewById(R.id.rv_food_results);
    }

    private void initViews() {
        btn_prepared_meals.setOnClickListener(v -> moveToAddANewMealActivity());

        search_food.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // לא נדרש פעולה כאן
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    // שליחת בקשת HTTP כאשר המשתמש מתחיל להקליד
                    sendHttpRequest(s.toString());
                } else {
                    rv_food_results.setVisibility(View.GONE); // הסתרת ה-RecyclerView אם השדה ריק
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // לא נדרש פעולה כאן
            }
        });
    }

    private void setupRecyclerView() {
        // מימוש OnItemClickListener
        adapter = new MyAdapter(new ArrayList<>(), item -> {
            handleItemClick(item);
        });
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

            FoodItem foodItem = new FoodItem(label, calories, protein, fat, carbs, imageUrl);
            Intent intent = new Intent(this, QuantitySelectionActivity.class);
            intent.putExtra("selected_food_item", foodItem);
            intent.putExtra("user", user);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
