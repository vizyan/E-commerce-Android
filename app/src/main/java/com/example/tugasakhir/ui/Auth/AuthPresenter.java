package com.example.tugasakhir.ui.Auth;

import android.util.Log;

import com.example.tugasakhir.data.model.Stellar.Balance;
import com.example.tugasakhir.data.model.Stellar.Example;
import com.example.tugasakhir.data.model.User.DataUser;
import com.example.tugasakhir.data.model.User.ResponseUser;
import com.example.tugasakhir.data.network.RetrofitClient;
import com.example.tugasakhir.data.network.StellarClient;
import com.example.tugasakhir.util.Constant;
import com.example.tugasakhir.util.RandomStrGenerator;

import org.stellar.sdk.KeyPair;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthPresenter {

    private AuthView authView;
    private RandomStrGenerator randomStrGenerator;
    final String TAG = "AuthPresenter";

    public AuthPresenter(AuthView authView){
        this.authView = authView;
    }

    public void addAccXlm() {
        String success = null;
        KeyPair pair = KeyPair.random();

        String secretSeed = new String(pair.getSecretSeed());
        String stellarId = pair.getAccountId();

        String friendbotUrl = String.format(
                Constant.FRIENDBOT_URL,
                stellarId);

        try {
            InputStream response = new URL(friendbotUrl).openStream();
            String body = new Scanner(response, "UTF-8").useDelimiter("\\A").next();
            System.out.println("SUCCESS! You have a new account : \n" + body);
            success = "success";
        } catch (IOException e) {
            Log.e(TAG, "->AddAccXlm(): " + e.getMessage());
        }

        if (success.isEmpty()){
            authView.failed("Failed connect to server stellar");
            Log.e(TAG, "->AddAccXlm(): Stellar failed");
        } else {
            addUser(secretSeed, stellarId);
        }
    }
    
    public void addUser(String secretSeed, String stellarId){
        String name = authView.getName();
        String pass = authView.getPassword();
        String email = authView.getEmail();

        RetrofitClient.getInstance()
                .getApi()
                .addUser(email, name, stellarId, secretSeed, pass)
                .enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus()){
                                System.out.println(TAG + "->AddUser(): " +response.body().getMessage());
                                authView.successSignUp(response.body().getData());
                            } else {
                                System.out.println(TAG + "->AddUser(): " +response.body().getMessage());
                                authView.failed(response.body().getMessage());
                            }
                        } else {
                            authView.failed(response.errorBody().toString());
                            Log.e(TAG, "->AddUser(): onResponse: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        authView.failed(t.getMessage());
                        Log.e(TAG, "->AddUser(): onResponse:" + t.getMessage());
                    }
                });
    }

    public void login(){
        String email = authView.getEmail();
        String password = authView.getPassword();
//        String token = randomStrGenerator.getRandomString(10);

        RetrofitClient.getInstance()
                .getApi()
                .loginUser(email, password)
                .enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus()){
                                System.out.println(TAG + "->login(): " +response.body().getMessage());
                                getStellarDetail(response.body().getData());
                            } else {
                                System.out.println(TAG + "->login(): " +response.body().getMessage());
                                authView.failed(response.body().getMessage());
                            }
                        } else {
                            authView.failed(response.errorBody().toString());
                            Log.e(TAG, "->login(): " + response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        authView.failed(t.getMessage());
                        Log.e(TAG, "->login(): onFailure: " + t.getMessage());
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
                                System.out.println(TAG + "->getStellarDetail: " +a);
                                authView.successSignin(dataUser, a);
                            }
                        } else {
                            authView.failed(response.errorBody().toString());
                            Log.e(TAG, "->getStellarDetail(): onResponse: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        authView.failed(t.getMessage());
                        Log.e(TAG, "->getStellarDetail(): onFailure: " + t.getMessage());
                    }
                });
    }
}
