package com.example.tugasakhir.adapter.City;

import android.widget.ImageView;

import com.example.tugasakhir.data.model.Product.DataProduct;
import com.example.tugasakhir.data.model.RajaOngkir.DataCity;

/**
 * Created by Vizyan on 1/14/2018.
 */

public interface CityListener {

    void onCityClick(DataCity dataCity);

    void displayImgProject(ImageView imgProject, DataProduct dataProduct);
}
