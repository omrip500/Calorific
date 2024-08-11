package com.example.calorific2.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<JSONObject> items;
    private OnItemClickListener listener;

    public MyAdapter(List<JSONObject> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public void updateData(List<JSONObject> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject item = items.get(position);
            String label = item.getString("label");
            holder.foodItemTextView.setText(label);
            holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
        } catch (Exception e) {
            e.printStackTrace();
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
