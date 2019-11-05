package com.example.tugasakhir.adapter.Cart;

import android.widget.ImageView;

import com.example.tugasakhir.data.model.Cart.DataCart;

/**
 * Created by Vizyan on 1/14/2018.
 */

public interface CartListener {

    void onCartClick(DataCart dataCart);

    void displayImgProject(ImageView imgProject, DataCart dataCart);

    void deleteItem(DataCart dataCart);
}
