package com.example.tugasakhir.ui.Dashboard;

import android.util.Log;

import com.example.tugasakhir.data.model.Cart.ResponseListCarts;
import com.example.tugasakhir.data.model.Product.ResponseListProducts;
import com.example.tugasakhir.data.model.ResponseData;
import com.example.tugasakhir.data.model.Stellar.Balance;
import com.example.tugasakhir.data.model.Stellar.Example;
import com.example.tugasakhir.data.model.Stellar.ExchangeBtc.Xlmtobtc;
import com.example.tugasakhir.data.model.User.DataUser;
import com.example.tugasakhir.data.model.User.ResponseUser;
import com.example.tugasakhir.data.network.RetrofitClient;
import com.example.tugasakhir.data.network.StellarClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardPresenter {

    DashboardView dashboardView;
    private String TAG = "DashboardPresenter";

    public DashboardPresenter(DashboardView dashboardView){
        this.dashboardView = dashboardView;
    }

    // -----------------------------------------------------------------------------
    // API product
    // -----------------------------------------------------------------------------

    public void getAllProduct(){
        RetrofitClient.getInstance()
                .getApi()
                .getAllProducts()
                .enqueue(new Callback<ResponseListProducts>() {
                    @Override
                    public void onResponse(Call<ResponseListProducts> call, Response<ResponseListProducts> response) {
                        if (response.isSuccessful()){
                            System.out.println(TAG+"->getAllProduct(): onResponse: " + response.body().getMessage());
                            dashboardView.successProduct(response.body().getData());
                        } else {
                            dashboardView.failed(response.body().getMessage());
                            Log.e(TAG, "->getAllProduct(): onResponse: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseListProducts> call, Throwable t) {
                        dashboardView.failed(t.getMessage());
                        Log.e(TAG, "->getAllProduct(): onFailure: " + t.getMessage());
                    }
                });
    }

    public void getProductByCat(int id){
        RetrofitClient.getInstance()
                .getApi()
                .getProductByCat(id)
                .enqueue(new Callback<ResponseListProducts>() {
                    @Override
                    public void onResponse(Call<ResponseListProducts> call, Response<ResponseListProducts> response) {
                        if (response.isSuccessful()){
                            System.out.println(TAG+"->getProductByCat(): onResponse: " + response.body().getMessage());
                            dashboardView.successProduct(response.body().getData());
                        } else {
                            dashboardView.failed(response.body().getMessage());
                            Log.e(TAG, "->getProductByCat(): onResponse: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseListProducts> call, Throwable t) {
                        dashboardView.failed(t.getMessage());
                        Log.e(TAG, "->getProductByCat(): onFailure: " + t.getMessage());
                    }
                });
    }

    // -----------------------------------------------------------------------------
    // API user
    // -----------------------------------------------------------------------------

    public void getAccountById(int id){
        RetrofitClient.getInstance()
                .getApi()
                .getUserById(id)
                .enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        if (response.isSuccessful()){
                            getStellarDetail(response.body().getData());
//                            dashboardView.successUser(response.body().getData());
                        } else {
                            dashboardView.failed(response.body().getMessage());
                            Log.e(TAG, "->getAccountById(): onResponse: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        dashboardView.failed(t.getMessage());
                        Log.e(TAG, "->getAccountById(): onFailure: " + t.getMessage());
                    }
                });
    }

    public void login(String email, String password){
//      String token = randomStrGenerator.getRandomString(10);

        RetrofitClient.getInstance()
                .getApi()
                .loginUser(email, password)
                .enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus()){
                                getStellarDetail(response.body().getData());
                            } else {
                                dashboardView.failed(response.body().getMessage());
                            }
                        } else {
                            dashboardView.failed(response.errorBody().toString());
                            Log.e(TAG, " Ini Apa? " + response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        dashboardView.failed(t.getMessage());
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    public void getStellarDetail(final DataUser dataUser){

        StellarClient.getInstance()
                .getApi()
                .getDetailAccountXlm(dataUser.getStellarId())
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response.isSuccessful()){
                            for (Balance balance : response.body().getBalances())
                            {
                                String a = balance.getBalance();
                                dashboardView.successUser(dataUser, a);
                            }
                        } else {
                            dashboardView.failed(response.message());
                            Log.e(TAG, "->getStellarDetail(): onResponse: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        dashboardView.failed(t.getMessage());
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    public void exchangeBtc(){
        RetrofitClient.getInstance()
                .getApi()
                .exchangeBtc("https://apiv2.bitcoinaverage.com/indices/crypto/ticker/XLMBTC")
                .enqueue(new Callback<Xlmtobtc>() {
                    @Override
                    public void onResponse(Call<Xlmtobtc> call, Response<Xlmtobtc> response) {
                        if (response.isSuccessful()){
                            dashboardView.successExchange(response.body().getAverages());
                        } else {
                            dashboardView.failed(response.errorBody().toString());
                            Log.e(TAG, "onResponse: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<Xlmtobtc> call, Throwable t) {
                        dashboardView.failed(t.getMessage());
                        Log.e(TAG, "onFailure: " + t.getMessage().toString());
                    }
                });
    }

    // -----------------------------------------------------------------------------
    // API cart
    // -----------------------------------------------------------------------------

    public void getCartByUser(int id){
        RetrofitClient.getInstance()
                .getApi()
                .getCartByUser(id)
                .enqueue(new Callback<ResponseListCarts>() {
                    @Override
                    public void onResponse(Call<ResponseListCarts> call, Response<ResponseListCarts> response) {
                        if (response.isSuccessful()){
                            dashboardView.successCart(response.body().getData());
                        } else {
                            dashboardView.failed(response.body().getMessage());
                            Log.e(TAG, "onResponse: " + response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseListCarts> call, Throwable t) {
                        dashboardView.failed(t.getMessage());
                        Log.e(TAG, "onFailure: " + t.getMessage().toString());
                    }
                });
    }

    public void deleteCart(int id){
        RetrofitClient.getInstance()
                .getApi()
                .getDeleteItemCart(id)
                .enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if (response.isSuccessful()){
                            dashboardView.success(response.body().getData());
                        } else {
                            dashboardView.failed(response.body().getMessage());
                            Log.e(TAG, "onResponse: " + response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        dashboardView.failed(t.getMessage());
                        Log.e(TAG, "onFailure: " + t.getMessage().toString());
                    }
                });
    }

}
