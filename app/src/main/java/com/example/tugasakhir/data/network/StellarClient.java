package com.example.tugasakhir.data.network;

import com.example.tugasakhir.util.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StellarClient {

    private static StellarClient stellarClient;

    private StellarClient(){}

    public static StellarClient getInstance(){
        if(stellarClient == null){
            stellarClient = new StellarClient();
        }
        return stellarClient;
    }

    public Api getApi(){
        return getRetrofit().create(Api.class);
    }

    public Retrofit getRetrofit(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
                .readTimeout(15, TimeUnit.SECONDS)
//                .connectionSpecs(Collections.singletonList(ConnectionSpec.COMPATIBLE_TLS))
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT, ConnectionSpec.COMPATIBLE_TLS))
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(Constant.STELLAR_TEST_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }
}
