package com.example.tugasakhir.data.network;

import com.example.tugasakhir.data.model.Cart.ResponseListCarts;
import com.example.tugasakhir.data.model.Product.ResponseListProducts;
import com.example.tugasakhir.data.model.Product.ResponseProduct;
import com.example.tugasakhir.data.model.RajaOngkir.ResponseCity;
import com.example.tugasakhir.data.model.RajaOngkir.ResponseListCost;
import com.example.tugasakhir.data.model.RajaOngkir.ResponseListCity;
import com.example.tugasakhir.data.model.ResponseData;
import com.example.tugasakhir.data.model.Stellar.Example;
import com.example.tugasakhir.data.model.Stellar.ExchangeBtc.Xlmtobtc;
import com.example.tugasakhir.data.model.Transaction.ResponseListTrans;
import com.example.tugasakhir.data.model.User.ResponseUser;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {

    @Headers("Content-Type: application/json")

    /* ---- USER ---- */

    @GET("/api/users/{id}")
    Call<ResponseUser> getUserById(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("/api/users/")
    Call<ResponseUser> addUser(
            @Field("email") String email,
            @Field("name") String name,
            @Field("stellarId") String stellarId,
            @Field("secretSeed") String secretSeed,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("/api/users/login/")
    Call<ResponseUser> loginUser(
            @Field("email") String email,
            @Field("password") String password
//            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("/api/users/{id}")
    Call<ResponseUser> editUser(
            @Path("id") int id,
            @Field("password") String password,
            @Field("name") String name,
            @Field("address") String address,
            @Field("phone") String phone
//            @Field("token") String token
    );

    @DELETE("/api/users/{id}")
    Call<ResponseData> deleteUser(
            @Path("id") int id
    );

    /* ---- PRODUK ---- */

    @GET("/api/products/")
    Call<ResponseListProducts> getAllProducts();

    @GET("/api/products/{id}")
    Call<ResponseProduct> getProductById(
            @Path("id") int id
    );

    @GET("/api/products/")
    Call<ResponseListProducts> searchProduct(
            @Query("keyqoed") String keyword
    );

    /* ---- CART ---- */

    @GET("/api/cart/{id}")
    Call<ResponseListCarts> getCartByUser(
            @Path("id") int id
    );

    @DELETE("/api/cart/{id}")
    Call<ResponseData> getDeleteItemCart(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("/api/cart/")
    Call<ResponseData> addCart(
            @Field("id_user") int id_user,
            @Field("id_product") int id_product,
            @Field("much") int much,
            @Field("total") int total
    );

    /* ---- TRANSACTION ---- */

    @GET("/api/trans/user/{id}")
    Call<ResponseListTrans> getTransByUser(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("/api/trans/")
    Call<ResponseData> addTrans(
            @Field("id_user") int id_user,
            @Field("total") int total,
            @Field("trans_hash") String hash,
            @Field("resi") String resi
    );

    @GET("/api/trans/detail/{id}")
    Call<ResponseListTrans> getDetailByTrans(
            @Path("id") int id
    );

    @FormUrlEncoded
    @POST("/api/trans/detail/")
    Call<ResponseData> addDetailTrans(
            @Field("id_cart") int id_user,
            @Field("id_trans") int id_trans
    );

    /* ---- STELLAR ---- */

    @GET("/accounts/{account_id}")
    Call<Example> getDetailAccountXlm(
            @Path("account_id") String stellarId
    );

    @GET
    Call<Xlmtobtc> exchangeBtc(
            @Url String url
    );

    /* ---- SHIPMENT ---- */

    @GET("/api/ongkir/")
    Call<ResponseListCity> searchCity(
            @Query("city") String city
    );

    @GET("/api/ongkir/{id}")
    Call<ResponseCity> searchCityById(
            @Path("id") String idCity
    );

    @FormUrlEncoded
    @POST("/api/ongkir/")
    Call<ResponseListCost> addShip(
            @Field("origin") String origin,
            @Field("destination") String destination,
            @Field("weight") String weight,
            @Field("courier") String courier
    );
}