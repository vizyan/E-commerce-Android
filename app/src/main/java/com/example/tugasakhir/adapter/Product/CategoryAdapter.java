package com.example.tugasakhir.adapter.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.Product.DataCategory;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    LayoutInflater inflater;
    ArrayList<DataCategory> dataCategories;
    private ProductListener productListener;

    public CategoryAdapter(ArrayList<DataCategory> dataCategories){
        this.dataCategories = dataCategories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        DataCategory dataCategory = dataCategories.get(position);
        holder.bind(dataCategory, productListener);
    }

    private DataCategory getItem(int position) {
        return dataCategories.get(position);
    }

    @Override
    public int getItemCount() {
        if (dataCategories == null) return 0;
        return dataCategories.size();
    }

    public void setAdapterListener(ProductListener listener) {
        this.productListener = listener;
    }
}
