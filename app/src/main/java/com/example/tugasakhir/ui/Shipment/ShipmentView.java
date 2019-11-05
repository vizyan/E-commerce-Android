package com.example.tugasakhir.ui.Shipment;

import com.example.tugasakhir.data.model.RajaOngkir.DataCity;
import com.example.tugasakhir.data.model.RajaOngkir.DataCost;

import java.util.List;

public interface ShipmentView {
    void successListCity(List<DataCity> data);

    void failed(String message);

    void successCityById(DataCity data);

    String getOrigin();

    String getDestination();

    String getWeight();

    String getCourier();

    void successShip(List<DataCost> dataCosts);
}
