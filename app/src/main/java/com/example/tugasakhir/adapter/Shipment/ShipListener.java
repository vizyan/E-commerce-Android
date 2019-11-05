package com.example.tugasakhir.adapter.Shipment;

import android.widget.ImageView;

import com.example.tugasakhir.data.model.Product.DataProduct;
import com.example.tugasakhir.data.model.RajaOngkir.DataCost;
import com.example.tugasakhir.data.model.Stellar.Data;

/**
 * Created by Vizyan on 1/14/2018.
 */

public interface ShipListener {

    void onShipClick(DataCost dataCost);

    void displayImgProject(ImageView imgProject, DataProduct dataProduct);
}
