package com.example.tugasakhir.ui.Dashboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.Cart.DataCart;
import com.example.tugasakhir.data.model.Product.DataProduct;
import com.example.tugasakhir.data.model.Stellar.ExchangeBtc.Averages;
import com.example.tugasakhir.data.model.User.DataUser;
import com.example.tugasakhir.ui.Transaction.ListTransactionActivity;
import com.example.tugasakhir.ui.User.UserActivity;
import com.example.tugasakhir.util.CircleTransform;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class UserFragment extends Fragment implements DashboardView {

    private DashboardPresenter dashboardPresenter;
    private DataUser dataUser;
    NumberFormat numberFormat;

    Button btnUserDetail, btnLogout;
    ConstraintLayout layout, clUserTrans;
    ImageView ivUserPhoto;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvUserName, tvUserEmail, tvBalanceXlm, tvBalanceIdr;

    double btcPrice;
    int id;
    String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initPresenter();
        initView();
        initPreference();
//        initExchange();
        initRefresh();
        initUserDetail();
        initTransList();
        initSignout();
    }

    public void initPresenter() {
        this.dashboardPresenter = new DashboardPresenter(this);
    }

    public void initView() {
        layout = getActivity().findViewById(R.id.layout_fragment_account);
        layout.setVisibility(View.INVISIBLE);
        clUserTrans = getActivity().findViewById(R.id.clUserTrans);
        ivUserPhoto = getActivity().findViewById(R.id.ivUserPhoto);
        tvUserEmail = getActivity().findViewById(R.id.tvUserEmail);
        tvUserName = getActivity().findViewById(R.id.tvUserName);
        tvBalanceIdr = getActivity().findViewById(R.id.tvBalanceIdr);
        tvBalanceXlm = getActivity().findViewById(R.id.tvBalanceXlm);
        btnUserDetail = getActivity().findViewById(R.id.btnUserDetail);
        btnLogout = getActivity().findViewById(R.id.btnUserLogout);
        swipeRefreshLayout = getActivity().findViewById(R.id.srlFragmentUser);
    }

    public void initPreference(){
        SharedPreferences preferences = getActivity().getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        String userPref = preferences.getString("dataUser", "");
        String balancePref = preferences.getString("balance", "");
        password = preferences.getString("password", "");

        if (userPref.isEmpty()){ }
        else {
            Gson gson = new Gson();
            dataUser = gson.fromJson(userPref, DataUser.class);
            initDataView(dataUser, balancePref);
            id = dataUser.getId();
        }
    }

    public void initExchange(){
        dashboardPresenter.exchangeBtc();
    }

    public void initRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashboardPresenter.getAccountById(id);
            }
        });
    }

    public void initUserDetail() {
        btnUserDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailUser = new Intent(getActivity(), UserActivity.class);
                startActivity(detailUser);
            }
        });
    }

    public void initSignout() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("LoginPreference", MODE_PRIVATE);
                preferences.edit().clear().apply();

                Intent intent = getActivity().getIntent();
                getActivity().overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });
    }

    public void initDataView(DataUser dataUser, String balance){
        numberFormat = NumberFormat.getInstance(Locale.ITALY);
        double xlmBalance = Double.parseDouble(balance);
        String balanceXlm =  "" + numberFormat.format(xlmBalance) + " Lumens";
        String balanceBtc = "Rp " + numberFormat.format(xlmBalance * 800);
        tvUserName.setText(dataUser.getName());
        tvUserEmail.setText(dataUser.getEmail());
        tvBalanceXlm.setText(balanceXlm);
        tvBalanceIdr.setText(balanceBtc);
        layout.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(dataUser.getPhoto())
                .resize(75, 75)
                .centerCrop()
                .transform(new CircleTransform())
                .into(ivUserPhoto);
    }

    public void savePreference(DataUser dataUser, String balance){
        Gson gson = new Gson();
        String DataUser = gson.toJson(dataUser);
        SharedPreferences preferences = getActivity().getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password", password);
        editor.putString("balance", balance);
        editor.putString("dataUser", DataUser);
        editor.commit();
        editor.apply();
    }

    private void initTransList() {
        clUserTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent trans = new Intent(getActivity(), ListTransactionActivity.class);
                startActivity(trans);
            }
        });
    }

    @Override
    public void successProduct(List<DataProduct> data) {}

    @Override
    public void successUser(DataUser dataUser, String balance) {
        swipeRefreshLayout.setRefreshing(false);
        initDataView(dataUser, balance);
        savePreference(dataUser, balance);
    }

    @Override
    public void successExchange(Averages averages) {
        btcPrice = averages.getWeek();
    }

    @Override
    public void successCart(List<DataCart> data) {

    }

    @Override
    public void success(Integer data) {

    }

    @Override
    public void failed(String message) {
        swipeRefreshLayout.setRefreshing(false);
    }
}
