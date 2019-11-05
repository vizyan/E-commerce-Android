package com.example.tugasakhir.data.network;

import com.example.tugasakhir.util.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient retrofitClient;

    private RetrofitClient(){}

    public static RetrofitClient getInstance(){
        if(retrofitClient == null){
            retrofitClient = new RetrofitClient();
        }

        return retrofitClient;
    }

    public Api getApi(){
//        Api api = getRetrofit().create(Api.class);
//        api.exchangeBtc("https://apiv2.bitcoinaverage.com/indices/crypto/ticker/XLMBTC");
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        return retrofit;
    }
}
