package com.example.tugasakhir.adapter.Product;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.Product.DataCategory;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    private TextView tvItemName;
    private ConstraintLayout clItemCat;

    public CategoryViewHolder(View view) {
        super(view);
        initView(itemView);
    }

    private void initView(View itemView) {
        tvItemName = itemView.findViewById(R.id.tvItemCat);
        clItemCat= itemView.findViewById(R.id.clItemCat);
    }

    public void bind(final DataCategory dataCategory, final ProductListener productListener) {
        tvItemName.setText(dataCategory.getName());

        clItemCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productListener.onCategoryClick(dataCategory);
            }
        });
    }
}
