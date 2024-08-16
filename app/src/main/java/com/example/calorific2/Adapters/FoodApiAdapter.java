package com.example.calorific2.Adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.List;

public class FoodApiAdapter extends RecyclerView.Adapter<FoodApiAdapter.ViewHolder> {
    private final List<JSONObject> items;
    private final OnItemClickListener listener;

    public FoodApiAdapter(List<JSONObject> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<JSONObject> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        // Adding ripple effect
        TypedValue outValue = new TypedValue();
        parent.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        view.setBackgroundResource(outValue.resourceId);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject item = items.get(position);
            String label = item.getString("label");
            holder.foodItemTextView.setText(label);

            holder.itemView.setOnClickListener(v -> {
                // Adding a scale animation on click
                v.animate().scaleX(1.05f).scaleY(1.05f).setDuration(100).withEndAction(() -> {
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    listener.onItemClick(item);
                }).start();
            });
        } catch (Exception e) {
            Log.e("TAG", "An unexpected error occurred: ", e);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView foodItemTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodItemTextView = itemView.findViewById(android.R.id.text1);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(JSONObject foodItem);
    }
}
