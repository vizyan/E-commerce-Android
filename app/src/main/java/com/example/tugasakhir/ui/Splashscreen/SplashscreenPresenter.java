package com.example.tugasakhir.ui.Splashscreen;

import android.util.Log;

import com.example.tugasakhir.data.model.User.ResponseUser;
import com.example.tugasakhir.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashscreenPresenter {
    private SplashscreenView splashscreenView;
    final String TAG = "SplashscreenPresenter";

    public SplashscreenPresenter(SplashscreenView splashscreenView) {
        this.splashscreenView = splashscreenView;
    }

    public void login(String email, String password){

        RetrofitClient.getInstance()
                .getApi()
                .loginUser(email, password)
                .enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus()){
                                splashscreenView.success(response.body().getData());
                            } else {
                                splashscreenView.failed(response.body().getMessage());
                            }
                        } else {
                            Log.e(TAG, " Ini Apa? " + response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        splashscreenView.failed(t.getMessage());
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }
}
