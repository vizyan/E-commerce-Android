package com.example.tugasakhir.ui.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.Product.DataProduct;
import com.example.tugasakhir.data.model.User.DataUser;
import com.example.tugasakhir.ui.Auth.SigninActivity;
import com.example.tugasakhir.ui.Dashboard.DashboardActivity;
import com.example.tugasakhir.ui.Shipment.ShipmentActivity;
import com.example.tugasakhir.util.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

public class CartTransActivity extends AppCompatActivity implements ProductView {

    private ProductPresenter productPresenter;
    private DataProduct dataProduct;
    ImageView ivTPPhoto;
    Button btnAddToCart;
    DataUser dataUser;
    TextView tvTPName, tvTPPrice, tvTPCount, tvTPStock, tvTPTotal;
    FloatingActionButton fabMinus, fabPlus;
    int count, total, weight;
    String idProduct, cart;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_trans);

        initPresenter();
        initView();
//        initActionBar();
        initDataIntent();
        initPreference();
        initCount();
        initCart();
    }

    private void initCart() {
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productPresenter.addToCart(dataUser.getId(), Integer.parseInt(idProduct), count, total);
            }
        });

    }

    private void initDataIntent() {
        idProduct = getIntent().getStringExtra(Constant.Extra.PRODUCT_ID);
        cart = getIntent().getStringExtra(Constant.Extra.CART_OR_SINGLE);
        if (idProduct == null) {
            finish();
        } else {
            productPresenter.getProductById(Integer.parseInt(idProduct));
            if (cart.matches("cart")){
                btnAddToCart.setText("Tambah ke keranjang");
            } else {
                btnAddToCart.setText("beli");
            }
        }
    }

    public void initView() {
        ivTPPhoto = findViewById(R.id.ivTPPhoto);
        tvTPName = findViewById(R.id.tvTPName);
        tvTPPrice = findViewById(R.id.tvTPPrice);
        tvTPCount =findViewById(R.id.tvTPCount);
        tvTPStock = findViewById(R.id.tvTPStok);
        tvTPTotal = findViewById(R.id.tvTPTotal);
        fabPlus = findViewById(R.id.fabPlus);
        fabMinus = findViewById(R.id.fabMinus);
        btnAddToCart = findViewById(R.id.btnAddToCart);
//        toolbar = findViewById(R.id.toolbar);
    }

    public void initActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Produk");
    }

    public void initPresenter() {
        productPresenter = new ProductPresenter(this);
    }

    public void initCount(){
        fabMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                if (count<=0){
                    count=0;
                }
                tvTPCount.setText(""+count);
                total = count*dataProduct.getPrice();
                tvTPTotal.setText("Rp "+total);
            }
        });

        fabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count>=dataProduct.getStock()){
                    count=dataProduct.getStock();
                }
                tvTPCount.setText(""+count);
                total = count*dataProduct.getPrice();
                tvTPTotal.setText("Rp "+total);
            }
        });
    }

    public void initPreference(){
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        String userPref = preferences.getString("dataUser", "");

        if (userPref.isEmpty()){
            Intent login = new Intent(CartTransActivity.this, SigninActivity.class);
            startActivity(login);
        }
        else {
            Gson gson = new Gson();
            dataUser = gson.fromJson(userPref, DataUser.class);
        }
    }

    @Override
    public void successProduct(DataProduct data) {
        dataProduct = data;
        tvTPName.setText(data.getName());
        tvTPPrice.setText("Rp "+data.getPrice());
        tvTPStock.setText("Stok tersedia : "+data.getStock());
    }

    @Override
    public void success() {
        if (cart.matches("cart")){
            Intent shop = new Intent(CartTransActivity.this, DashboardActivity.class);
            startActivity(shop);
            finish();
        } else {
            Intent ship = new Intent(CartTransActivity.this, ShipmentActivity.class);
            weight = dataProduct.getWeight() * count;
            ship.putExtra(Constant.Extra.WEIGHT, ""+weight);
            ship.putExtra(Constant.Extra.TOTAL, ""+total);
            startActivity(ship);
        }
    }

    @Override
    public void failed(String message) {

    }
}
