package com.example.tugasakhir.ui.Transaction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.Product.DataProduct;
import com.example.tugasakhir.data.model.Transaction.DataTrans;
import com.example.tugasakhir.data.model.User.DataUser;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

public class DetailTransactionActivity extends AppCompatActivity implements TransactionView{

    String password;
    private DataUser dataUser;
    private TransactionPresenter transactionPresenter;
    Button btnOk;
    TextView tvCekTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);

        intiPresenter();
        initPreference();
        initView();
        initTrans();
    }

    private void intiPresenter() {
        transactionPresenter = new TransactionPresenter(this);
    }

    private void initTrans() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    transactionPresenter.addTransaction(dataUser.getStellarId(), dataUser.getSecretSeed());
                } catch (IOException e) {
                    Log.e("Lalala", "onClick: Error");
                }
            }
        });
    }

    private void initView() {
        btnOk = findViewById(R.id.btnOk);
        tvCekTrans = findViewById(R.id.tvCekTrans);
    }

    public void initPreference(){
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        String userPref = preferences.getString("dataUser", "");
        String balancePref = preferences.getString("balance", "");
        password = preferences.getString("password", "");

        if (userPref.isEmpty()){ }
        else {
            Gson gson = new Gson();
            dataUser = gson.fromJson(userPref, DataUser.class);
        }
    }

    public void savePreference(String balance){
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("balance", balance);
        editor.commit();
        editor.apply();
    }

    @Override
    public void successTransaction(String hash) {
        Toast.makeText(this, "Transaction Success : " +hash, Toast.LENGTH_SHORT).show();
        tvCekTrans.setText("transaction success");
    }

    @Override
    public void successListTrans(List<DataTrans> data) {

    }

    @Override
    public void failed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        tvCekTrans.setText("transaction failed");
    }

    @Override
    public String getAmount() {
        return null;
    }

    @Override
    public String getNote() {
        return null;
    }

    @Override
    public void successCart() {

    }

    @Override
    public void successProduct(DataProduct data) {

    }
}
