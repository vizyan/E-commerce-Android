package com.example.tugasakhir.ui.Dashboard;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tugasakhir.R;
import com.example.tugasakhir.adapter.Cart.CartAdapter;
import com.example.tugasakhir.adapter.Cart.CartListener;
import com.example.tugasakhir.data.model.Cart.DataCart;
import com.example.tugasakhir.data.model.Product.DataProduct;
import com.example.tugasakhir.data.model.Stellar.ExchangeBtc.Averages;
import com.example.tugasakhir.data.model.User.DataUser;
import com.example.tugasakhir.ui.Product.ProductActivity;
import com.example.tugasakhir.util.Constant;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment implements DashboardView, CartListener {

    private DashboardPresenter dashboardPresenter;
    private CartAdapter cartAdapter;
    private DataCart dataCart;
    private List<DataCart> cartList;
    NumberFormat numberFormat;

    Button btnCheckout;
    ConstraintLayout clFragmentCart;
    ImageView ivCartP;
    RecyclerView rvCart;
    SwipeRefreshLayout srlcart;
    TextView tvTotal;

    int id, sum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initPresenter();
        initView();
        initPreference();
        initRefresh();
    }

    public void initPresenter() {
        this.dashboardPresenter = new DashboardPresenter(this);
    }

    public void initPreference() {
        SharedPreferences preferences = getActivity().getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        String userPreference = preferences.getString("dataUser", "");
        String balancePref = preferences.getString("balance", "");

        if (userPreference.isEmpty()){}
        else {
            Gson gson = new Gson();
            DataUser dataUser = gson.fromJson(userPreference, DataUser.class);
            id = dataUser.getId();
            dashboardPresenter.getCartByUser(id);
        }
    }

    public void initView() {
        rvCart = getActivity().findViewById(R.id.rvCartHome);
        tvTotal = getActivity().findViewById(R.id.tvTotal);
        ivCartP = getActivity().findViewById(R.id.ivCartP);
        srlcart = getActivity().findViewById(R.id.srlFragmentCart);
        btnCheckout = getActivity().findViewById(R.id.btnCheckout);
        clFragmentCart = getActivity().findViewById(R.id.clFragmentCart);
        clFragmentCart.setVisibility(View.INVISIBLE);
    }

    private void initRefresh() {
        srlcart.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dashboardPresenter.getCartByUser(id);
                srlcart.setRefreshing(true);
            }
        });
    }

    public void initAdapter(){
        srlcart.setRefreshing(false);
        initDataAdapter();
    }

    private void initDataAdapter() {
        cartAdapter = new CartAdapter(cartList);
        cartAdapter.setAdapterListener(this);
        rvCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCart.setAdapter(cartAdapter);
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Hapus Keranjang");

        alertDialogBuilder
                .setMessage("Apakah anda ingin menghapus produk ini pada keranjang?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dashboardPresenter.deleteCart(dataCart.getId());
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void successProduct(List<DataProduct> data) {

    }

    @Override
    public void failed(String message) {
        srlcart.setRefreshing(false);
        initAdapter();
}

    @Override
    public void successUser(DataUser dataUser, String balance) {

    }

    @Override
    public void successExchange(Averages averages) {

    }

    @Override
    public void successCart(List<DataCart> data) {
        numberFormat = NumberFormat.getInstance(Locale.ITALY);
        String total = "0";
        sum = 0;
        srlcart.setRefreshing(false);
        cartList = data;
        initAdapter();

        if (data.size()>0){
            clFragmentCart.setVisibility(View.VISIBLE);
        } else {
            clFragmentCart.setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < data.size(); i++){
            DataCart dataCart = data.get(i);
            sum = sum + dataCart.getTotal();
            total = numberFormat.format(dataCart.getTotal());
        }

        tvTotal.setText("Total : Rp " +total);
    }

    @Override
    public void success(Integer data) {
        dashboardPresenter.getCartByUser(id);
    }

    @Override
    public void onItemClick(DataCart dataCart) {
        Intent product = new Intent(getActivity(), ProductActivity.class);
        product.putExtra(Constant.Extra.PRODUCT_ID, dataCart.getIdProduct().toString());
        startActivity(product);
    }

    @Override
    public void deleteItem(DataCart dataCart) {
        this.dataCart = dataCart;
        showDialog();
    }
}
