package com.example.tugasakhir.ui.User;

import android.util.Log;

import com.example.tugasakhir.data.model.ResponseData;
import com.example.tugasakhir.data.model.Stellar.Balance;
import com.example.tugasakhir.data.model.Stellar.Example;
import com.example.tugasakhir.data.model.User.DataUser;
import com.example.tugasakhir.data.model.User.ResponseUser;
import com.example.tugasakhir.data.network.RetrofitClient;
import com.example.tugasakhir.data.network.StellarClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPresenter {

    private UserView userView;
    final String TAG = "UserPresenter";

    public UserPresenter(UserView userView){
        this.userView = userView;
    }

    public void getUserById(int id){
        RetrofitClient.getInstance()
                .getApi()
                .getUserById(id)
                .enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus()){
                                System.out.println(TAG + "->getUserById(): onResponse: " + response.body().getMessage());
                                userView.successUser(response.body().getData());
//                                getStellarDetail(response.body().getData());
                            } else {
                                System.out.println(TAG + "->getUserById(): onResponse: " + response.body().getMessage());
                                userView.failed(response.body().getMessage());
                            }
                        } else {
                            userView.failed(response.errorBody().toString());
                            Log.e(TAG, "->getUserById(): onResponse: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        userView.failed(t.getMessage());
                        Log.e(TAG, "->getUserById(): onFailure: "+t.getMessage());
                    }
                });
    }

    public void editUser(int id){
        String name = userView.getName();
        String password = userView.getPassword();
        String phone = userView.getPhone();
        String address = userView.getAddress();

        RetrofitClient.getInstance()
                .getApi()
                .editUser(id, password, name, address, phone)
                .enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus()){
                                System.out.println(TAG + "->editUser(): onResponse: " + response.body().getMessage());
                                userView.successEdit(response.body().getData());
                            } else {
                                System.out.println(TAG + "->editUser(): onResponse: " + response.body().getMessage());
                                userView.failed(response.body().getMessage());
                            }
                        } else {
                            userView.failed(response.errorBody().toString());
                            Log.e(TAG, "->editUser(): onResponse: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        userView.failed(t.getMessage());
                        Log.e(TAG, "->editUser(): onFailure: "+t.getMessage());
                    }
                });
    }

    public void deleteUser(int id){
        RetrofitClient.getInstance()
                .getApi()
                .deleteUser(id)
                .enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus()){
                                System.out.println(TAG + "->deleteUser(): onResponse: " + response.body().getMessage());
                                userView.successDelete();
                            } else {
                                System.out.println(TAG + "->deleteUser(): onResponse: " + response.body().getMessage());
                                userView.failed(response.body().getMessage());
                            }
                        } else {
                            userView.failed(response.errorBody().toString());
                            Log.e(TAG, "->deleteUser(): onResponse: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable t) {
                        userView.failed(t.getMessage());
                        Log.e(TAG, "->deleteUser(): onFailure: " + t.getMessage());
                    }
                });
    }
}
