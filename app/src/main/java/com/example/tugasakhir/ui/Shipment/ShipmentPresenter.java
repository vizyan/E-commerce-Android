package com.example.tugasakhir.ui.Shipment;

import android.util.Log;

import com.example.tugasakhir.data.model.RajaOngkir.ResponseCity;
import com.example.tugasakhir.data.model.RajaOngkir.ResponseListCity;
import com.example.tugasakhir.data.model.RajaOngkir.ResponseListCost;
import com.example.tugasakhir.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shadow.com.google.gson.JsonObject;

public class ShipmentPresenter {
    private ShipmentView shipmentView;
    final String TAG = "ShipmentPresenter";

    public ShipmentPresenter(ShipmentView shipmentView){
        this.shipmentView = shipmentView;
    }

    public void searchCity(String name){

        RetrofitClient.getInstance()
                .getApi()
                .searchCity(name)
                .enqueue(new Callback<ResponseListCity>() {
                    @Override
                    public void onResponse(Call<ResponseListCity> call, Response<ResponseListCity> response) {
                        if (response.body().getStatus()){
                            shipmentView.successListCity(response.body().getData());
                        } else {
                            shipmentView.failed(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseListCity> call, Throwable t) {
                        shipmentView.failed(t.getMessage());
                    }
                });
    }

    public void searchCityById(String id){
        RetrofitClient.getInstance()
                .getApi()
                .searchCityById(id)
                .enqueue(new Callback<ResponseCity>() {
                    @Override
                    public void onResponse(Call<ResponseCity> call, Response<ResponseCity> response) {
                        if (response.body().getStatus()){
                            shipmentView.successCityById(response.body().getData());
                        } else {
                            shipmentView.failed(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseCity> call, Throwable t) {

                    }
                });
    }

    public void getShipment(){
        String origin = shipmentView.getOrigin();
        String destination = shipmentView.getDestination();
        String weight = shipmentView.getWeight();
        String courier = shipmentView.getCourier();

        System.out.println(origin+":"+destination+":"+weight+":"+courier);
        RetrofitClient.getInstance()
                .getApi()
                .addShip(origin, destination, weight, courier.toLowerCase())
                .enqueue(new Callback<ResponseListCost>() {
                    @Override
                    public void onResponse(Call<ResponseListCost> call, Response<ResponseListCost> response) {
                        if (response.isSuccessful()){
                            shipmentView.successShip(response.body().getData().get(0).getCosts());
                            Log.e(TAG, "onResponse: getShipment "+response.body().getData().get(0).getCosts().get(0).getCost().get(0).getValue());
                            Log.e(TAG, "onResponse: getShipment "+response.errorBody());
                            Log.e(TAG, "onResponse: getShipment "+response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseListCost> call, Throwable t) {
                        shipmentView.failed(t.getMessage());
                        Log.e(TAG, "onFailure: getShipment "+t.getMessage());
                    }
                });
    }


}
