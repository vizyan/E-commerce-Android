package com.example.tugasakhir.ui.Shipment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.adapter.Cart.CartAdapter;
import com.example.tugasakhir.adapter.City.CityAdapter;
import com.example.tugasakhir.adapter.City.CityListener;
import com.example.tugasakhir.adapter.Shipment.ShipAdapter;
import com.example.tugasakhir.adapter.Shipment.ShipListener;
import com.example.tugasakhir.data.model.Cart.DataCart;
import com.example.tugasakhir.data.model.Product.DataProduct;
import com.example.tugasakhir.data.model.RajaOngkir.DataCity;
import com.example.tugasakhir.data.model.RajaOngkir.DataCost;
import com.example.tugasakhir.data.model.Stellar.Data;
import com.example.tugasakhir.data.model.User.DataUser;
import com.example.tugasakhir.util.Constant;
import com.google.gson.Gson;

import java.util.List;

import shadow.org.apache.commons.io.input.BOMInputStream;

public class ShipmentActivity extends AppCompatActivity implements ShipmentView, ShipListener, CityListener {

    private DataCart dataCart;
    private DataUser dataUser;
    private DataProduct dataProduct;
    private List<DataCity> dataCityList;
    private List<DataCost> dataCostList;
    private ShipmentPresenter shipmentPresenter;
    CityAdapter cityAdapter;
    ShipAdapter shipAdapter;
    Button btnShCheckout, btnShSearch, btnShip;
    EditText etShAddress, etShCity;
    Spinner spShCourier;
    RecyclerView rvShCity, rvShCost;
    String name, address, idCity, courier, password, balance, weight;

    AlertDialog alertDialog;
    AlertDialog.Builder dialogBuilder;

    Toolbar toolbar;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment);

        initPresenter();
        initDataIntent();
        initPreference();
        initView();
//        initActionBar();
        initCity();
        initShipment();
        initCheckout();
    }

    private void initCheckout() {
        btnShCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initCity() {
        btnShSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etShCity.getText().toString();
                shipmentPresenter.searchCity(name);
                showProgressDialog();
            }
        });
    }

    private void initView() {
        btnShSearch = findViewById(R.id.btnShSearch);
        btnShCheckout = findViewById(R.id.btnShCheckout);
        btnShip = findViewById(R.id.btnShip);
        etShCity = findViewById(R.id.etShCity);
        etShAddress = findViewById(R.id.etShAddress);
        spShCourier = findViewById(R.id.spShCourier);
        rvShCity = findViewById(R.id.rvShCity);
        rvShCost = findViewById(R.id.rvShPrice);
        toolbar = findViewById(R.id.toolbar);
    }

    public void initActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pengiriman");
    }

    private void initPreference() {
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        String userPref = preferences.getString("dataUser", "");
        password = preferences.getString("password", "");
        balance = preferences.getString("balance", "");

        if (userPref.isEmpty()){
            finish();
        }
        else {
            Gson gson = new Gson();
            dataUser = gson.fromJson(userPref, DataUser.class);
        }
    }

    private void initDataIntent() {
        total = Integer.parseInt(getIntent().getStringExtra(Constant.Extra.TOTAL));
        weight = getIntent().getStringExtra(Constant.Extra.WEIGHT);
    }

    private void initPresenter() {
        shipmentPresenter = new ShipmentPresenter(this);
    }



    private void initShipment() {
        btnShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initValidation();

                if (initValidation()){
                    total = Integer.parseInt(getIntent().getStringExtra(Constant.Extra.TOTAL));
                    shipmentPresenter.getShipment();
                    showProgressDialog();
                }
            }
        });
    }

    private Boolean initValidation() {
        validAddress();
        validDestination();
        validWeight();
        validCourier();

        if (validAddress() && validCourier() && validDestination() && validCourier()){
            return true;
        }
        return false;
    }

    private Boolean validAddress() {
        address = etShAddress.getText().toString();
        if (address.isEmpty()){
            return false;
        }
        return true;
    }

    private Boolean validDestination() {
        if (idCity.isEmpty()){
            return false;
        }
        return true;
    }

    private Boolean validWeight() {

        if (weight.isEmpty()){
            return false;
        }
        return true;
    }

    private Boolean validCourier() {
        courier = spShCourier.getSelectedItem().toString();
        if (courier.isEmpty()){
            return false;
        }
        return true;
    }

    public void initCityAdapter(){
        cityAdapter = new CityAdapter(dataCityList);
        initDataCity();
    }

    private void initDataCity() {
        cityAdapter.setAdapterListener(this);
        rvShCity.setLayoutManager(new LinearLayoutManager(this));
        rvShCity.setAdapter(cityAdapter);
    }

    public void initCostAdapter(){
        shipAdapter = new ShipAdapter(dataCostList);
        initDataCost();
    }

    private void initDataCost() {
        shipAdapter.setAdapterListener(this);
        rvShCost.setLayoutManager(new LinearLayoutManager(this));
        rvShCost.setAdapter(shipAdapter);
    }

    public void showProgressDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialogView = inflater.inflate(R.layout.progress_dialog_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void hideProgressDialog(){
        alertDialog.dismiss();
    }


    @Override
    public void displayImgProject(ImageView imgProject, DataProduct dataProduct) {

    }

    @Override
    public void successListCity(List<DataCity> data) {
        dataCityList = data;
        initCityAdapter();
        rvShCity.setVisibility(View.VISIBLE);
        hideProgressDialog();
    }

    @Override
    public void successCityById(DataCity data) {
        rvShCity.setVisibility(View.INVISIBLE);
        hideProgressDialog();
    }

    @Override
    public void successShip(List<DataCost> dataCosts) {
        dataCostList = dataCosts;
        initCostAdapter();
        hideProgressDialog();
    }

    @Override
    public void onCityClick(DataCity dataCity) {
        idCity = dataCity.getCityId();
        cityAdapter.getFilter().filter(dataCity.getCityId());
    }


    @Override
    public void onShipClick(DataCost dataCost) {
        shipAdapter.getFilter().filter(dataCost.getService());
        total = total + dataCost.getCost().get(0).getValue();
        System.out.println("Ini harganya : "+total);
    }

    @Override
    public void failed(String message) {
        hideProgressDialog();
    }

    @Override
    public String getOrigin() {
        return "501";
    }

    @Override
    public String getDestination() {
        return idCity;
    }

    @Override
    public String getWeight() {
        return weight;
    }

    @Override
    public String getCourier() {
        return courier;
    }


}
