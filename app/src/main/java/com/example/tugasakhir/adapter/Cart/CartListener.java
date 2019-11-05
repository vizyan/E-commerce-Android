package com.example.tugasakhir.adapter.Cart;

import android.widget.ImageView;

import com.example.tugasakhir.data.model.Cart.DataCart;

/**
 * Created by Vizyan on 1/14/2018.
 */

public interface CartListener {

    void onItemClick(DataCart dataCart);

    void deleteItem(DataCart dataCart);
}
