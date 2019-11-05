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
import com.example.tugasakhir.ui.Transaction.TransactionActivity;
import com.example.tugasakhir.util.Constant;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductActivity extends AppCompatActivity implements ProductView {

    private ProductPresenter productPresenter;
    private DataProduct dataProduct;
    private DataUser dataUser;
    NumberFormat numberFormat;
    Button btnBuy, btnCart;
    TextView tvPName, tvPPrice, tvPDesc, tvPStock, tvPCat, tvPWeight;
    ImageView ivPPhoto;
    String idProduct;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initPresenter();

        initView();
//        initActionBar();
        initData();
        initTrans();
    }

    private void initPresenter() {
        this.productPresenter = new ProductPresenter(this);
    }

    private void initView() {
        btnBuy = findViewById(R.id.btnPDBuy);
        btnCart = findViewById(R.id.btnPDCart);
        tvPName = findViewById(R.id.tvPDName);
        tvPPrice = findViewById(R.id.tvPDPrice);
        tvPDesc = findViewById(R.id.tvPDDesc);
        tvPStock = findViewById(R.id.tvPDStock);
        tvPCat = findViewById(R.id.tvPDCategory);
        tvPWeight = findViewById(R.id.tvPDWeight);
        ivPPhoto = findViewById(R.id.ivPDPhoto);
        toolbar = findViewById(R.id.toolbar);
    }

    public void initActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Produk");
    }

    private void initData() {
        idProduct = getIntent().getStringExtra(Constant.Extra.PRODUCT_ID);
        if (idProduct == "0") {
            finish();
        } else {
            productPresenter.getProductById(Integer.parseInt(idProduct));
        }
    }

    private void initTrans() {
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transCart = new Intent(ProductActivity.this, CartTransActivity.class);
                transCart.putExtra(Constant.Extra.PRODUCT_ID, dataProduct.getId().toString());
                transCart.putExtra(Constant.Extra.CART_OR_SINGLE, "cart");
                startActivity(transCart);
            }
        });

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transCart = new Intent(ProductActivity.this, CartTransActivity.class);
                transCart.putExtra(Constant.Extra.PRODUCT_ID, dataProduct.getId().toString());
                transCart.putExtra(Constant.Extra.CART_OR_SINGLE, "buy");
                startActivity(transCart);
            }
        });
    }

    @Override
    public void successProduct(DataProduct data) {
        numberFormat = NumberFormat.getInstance(Locale.ITALY);
        String price = numberFormat.format(data.getPrice());

        dataProduct = data;
        tvPName.setText(data.getName());
        tvPPrice.setText("Rp "+price);
        tvPDesc.setText(data.getDescription());
        tvPStock.setText("Stok : "+data.getStock());
        tvPCat.setText("Kategori : "+data.getCatName());
        tvPWeight.setText("Berat : "+data.getWeight()+"gr");
        Picasso.get()
                .load(data.getPhoto())
                .resize(0, 300)
                .centerInside()
                .into(ivPPhoto);
    }


    @Override
    public void success() {

    }

    @Override
    public void failed(String message) {

    }
}
