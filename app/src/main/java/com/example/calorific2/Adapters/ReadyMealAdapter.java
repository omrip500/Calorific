
package com.example.calorific2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.calorific2.Management.ReadyMeal;
import com.example.calorific2.R;

import java.util.ArrayList;

public class ReadyMealAdapter extends ArrayAdapter<ReadyMeal> {

    private final OnMealActionListener mealActionListener;

    public ReadyMealAdapter(Context context, ArrayList<ReadyMeal> readyMeals, OnMealActionListener listener) {
        super(context, 0, readyMeals);
        this.mealActionListener = listener;
    }

    @NonNull
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ReadyMeal readyMeal = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ready_meal, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tv_meal_name);
        TextView tvCalories = convertView.findViewById(R.id.tv_meal_calories);
        TextView tvProtein = convertView.findViewById(R.id.tv_meal_protein);
        TextView tvCarbs = convertView.findViewById(R.id.tv_meal_carbs);
        TextView tvFat = convertView.findViewById(R.id.tv_meal_fat);
        ImageView ivEdit = convertView.findViewById(R.id.iv_edit);
        ImageView ivDelete = convertView.findViewById(R.id.iv_delete);

        assert readyMeal != null;
        tvName.setText(readyMeal.getName());
        tvCalories.setText(readyMeal.getCalories() + " kcal");
        tvProtein.setText(readyMeal.getProteinInGrams() + "g Protein");
        tvCarbs.setText(readyMeal.getCarbsInGrams() + "g Carbs");
        tvFat.setText(readyMeal.getFatInGrams() + "g Fat");

        // Set onClick listeners for edit and delete buttons
        ivEdit.setOnClickListener(v -> {
            if (mealActionListener != null) {
                mealActionListener.onEditMeal(readyMeal);
            }
        });

        ivDelete.setOnClickListener(v -> {
            if (mealActionListener != null) {
                mealActionListener.onDeleteMeal(readyMeal);
            }
        });

        // Set onClick listener for the entire item to handle meal selection
        convertView.setOnClickListener(v -> {
            if (mealActionListener != null) {
                mealActionListener.onMealSelected(readyMeal);
            }
        });

        return convertView;
    }


    // Interface for handling edit and delete actions
    public interface OnMealActionListener {
        void onEditMeal(ReadyMeal meal);
        void onDeleteMeal(ReadyMeal meal);
        void onMealSelected(ReadyMeal meal);
    }
}