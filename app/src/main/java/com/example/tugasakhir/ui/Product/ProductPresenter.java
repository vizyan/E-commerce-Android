package com.example.tugasakhir.ui.Product;

import android.util.Log;

import com.example.tugasakhir.data.model.Product.ResponseProduct;
import com.example.tugasakhir.data.model.ResponseData;
import com.example.tugasakhir.data.model.User.ResponseUser;
import com.example.tugasakhir.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPresenter {

    private ProductView productView;
    final String TAG = "ProductPresenter";

    public ProductPresenter(ProductView productView){
        this.productView = productView;
    }

    public void getProductById(int id){
        RetrofitClient.getInstance()
                .getApi()
                .getProductById(id)
                .enqueue(new Callback<ResponseProduct>() {
                    @Override
                    public void onResponse(Call<ResponseProduct> call, Response<ResponseProduct> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus()){
                                productView.successProduct(response.body().getData());
                                System.out.println(TAG + "->getProductById: " + response.body().getMessage());
                            } else {
                                System.out.println(TAG + "->getProductById: " + response.body().getMessage());
                                productView.failed(response.body().getMessage());
                            }
                        } else {
                            productView.failed(response.errorBody().toString());
                            Log.e(TAG, "->getProductById: onResponse: " + response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseProduct> call, Throwable t) {
                        productView.failed(t.getMessage());
                        Log.e(TAG, "->getProductById: onFailure: " + t.getMessage());
                    }
                });
    }

    public void addToCart(int id_user, int id_product, int much, int total){
        RetrofitClient.getInstance()
                .getApi()
                .addCart(id_user, id_product, much, total)
                .enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus()){
                                productView.success();
                                System.out.println(TAG + "->addToCart: " + response.body().getMessage());
                            } else {
                                System.out.println(TAG + "->addToCart: " + response.body().getMessage());
                                productView.failed(response.body().getMessage());
                            }
                        } else {
                            productView.failed(response.errorBody().toString());
                            Log.e(TAG, "->addToCart: onResponse: " + response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {

                    }
                });
    }
}
