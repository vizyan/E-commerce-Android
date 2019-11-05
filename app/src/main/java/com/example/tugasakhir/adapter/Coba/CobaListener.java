package com.example.tugasakhir.adapter.Coba;

import android.widget.ImageView;

import com.example.tugasakhir.data.model.Product.DataProduct;
import com.example.tugasakhir.data.model.RajaOngkir.DataCity;

/**
 * Created by Vizyan on 1/14/2018.
 */

public interface CobaListener {

    void onProductClick(DataProduct dataProduct);

    void displayImgProduct(ImageView imgProject, DataProduct dataProduct);
}
