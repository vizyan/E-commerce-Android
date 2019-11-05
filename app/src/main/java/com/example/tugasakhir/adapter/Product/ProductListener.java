package com.example.tugasakhir.adapter.Product;

import android.widget.ImageView;

import com.example.tugasakhir.data.model.Product.DataCategory;
import com.example.tugasakhir.data.model.Product.DataProduct;

/**
 * Created by Vizyan on 1/14/2018.
 */

public interface ProductListener {

    void onProductClick(DataProduct dataProduct);

    void onCategoryClick(DataCategory dataCategory);
}
