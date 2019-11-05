package com.example.tugasakhir.ui.Transaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.tugasakhir.R;
import com.example.tugasakhir.adapter.Cart.CartAdapter;
import com.example.tugasakhir.adapter.Transaction.TransAdapter;
import com.example.tugasakhir.adapter.Transaction.TransListener;
import com.example.tugasakhir.data.model.Transaction.DataTrans;
import com.example.tugasakhir.data.model.User.DataUser;
import com.google.gson.Gson;

import java.util.List;

public class ListTransactionActivity extends AppCompatActivity implements TransactionView, TransListener {

    private TransactionPresenter transactionPresenter;
    private List<DataTrans> dataTransList;
    private DataUser dataUser;
    private TransAdapter transAdapter;
    RecyclerView rvListTrans;
    SwipeRefreshLayout srlListTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaction);

        initPresenter();
        initView();
        initPreference();
        initDataPresenter();
        initRefresh();
    }

    private void initPresenter() {
        transactionPresenter = new TransactionPresenter(this);
    }

    private void initView() {
        rvListTrans = findViewById(R.id.rvListTrans);
        srlListTrans = findViewById(R.id.srlListTrans);
    }

    private void initPreference() {
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        String userPref = preferences.getString("dataUser", "");

        if (userPref.isEmpty()){
            finish();
        }
        else {
            Gson gson = new Gson();
            dataUser = gson.fromJson(userPref, DataUser.class);
        }
    }

    private void initDataPresenter() {
        transactionPresenter.getTransByUser(dataUser.getId());
    }

    private void initRefresh() {
        srlListTrans.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDataPresenter();
            }
        });
    }

    private void initAdapter(){
        srlListTrans.setRefreshing(false);
        initDataAdapter();
    }

    private void initDataAdapter() {
        transAdapter = new TransAdapter(dataTransList);
        transAdapter.setAdapterListener(this);
        rvListTrans.setLayoutManager(new LinearLayoutManager(this));
        rvListTrans.setAdapter(transAdapter);
    }

    @Override
    public String getAmount() {
        return null;
    }

    @Override
    public void successTransaction(String hash) {

    }

    @Override
    public void successListTrans(List<DataTrans> data) {
        dataTransList = data;
        initAdapter();
        srlListTrans.setRefreshing(false);
    }

    @Override
    public void failed(String message) {
        srlListTrans.setRefreshing(false);
    }

    @Override
    public String getNote() {
        return null;
    }

    @Override
    public void onTransClick(DataTrans dataTrans) {

    }
}
