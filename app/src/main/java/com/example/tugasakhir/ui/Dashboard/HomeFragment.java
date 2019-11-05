package com.example.tugasakhir.ui.Dashboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.adapter.Product.ProductAdapter;
import com.example.tugasakhir.adapter.Product.ProductListener;
import com.example.tugasakhir.data.model.Cart.DataCart;
import com.example.tugasakhir.data.model.Product.DataProduct;
import com.example.tugasakhir.data.model.Stellar.ExchangeBtc.Averages;
import com.example.tugasakhir.data.model.User.DataUser;
import com.example.tugasakhir.ui.Product.ProductActivity;
import com.example.tugasakhir.util.Constant;

import java.util.List;

public class HomeFragment extends Fragment implements DashboardView, ProductListener {

    private DashboardPresenter dashboardPresenter;
    private ProductAdapter productAdapter;
    AlertDialog alertDialog;
    AlertDialog.Builder dialogBuilder;

    RecyclerView rvProduct;
    SwipeRefreshLayout srlShop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initPresenter();
        initView();
        initDataPresenter();
        initRefresh();
    }


    public void initPresenter() {
        this.dashboardPresenter = new DashboardPresenter(this);
    }

    public void initView() {
        rvProduct = getActivity().findViewById(R.id.rvProductHome);
        srlShop = getActivity().findViewById(R.id.srlFragmentShop);
    }

    public void initDataPresenter() {
        dashboardPresenter.getAllProduct();
        showProgressDialog();
    }

    private void initRefresh() {
        srlShop.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initDataPresenter();
            }
        });
    }

    public void showProgressDialog() {
        dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
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
    public void successProduct(List<DataProduct> data) {
        srlShop.setRefreshing(false);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        productAdapter = new ProductAdapter(data);
        productAdapter.setAdapterListener(this);
        rvProduct.setLayoutManager(manager);
        rvProduct.setAdapter(productAdapter);
        hideProgressDialog();
    }

    @Override
    public void successUser(DataUser dataUser, String balance) {

    }

    @Override
    public void successExchange(Averages averages) {

    }

    @Override
    public void successCart(List<DataCart> data) {

    }

    @Override
    public void success(Integer data) {

    }

    @Override
    public void failed(String message) {
        srlShop.setRefreshing(false);
        hideProgressDialog();
    }

    @Override
    public void onProductClick(DataProduct dataProduct) {
        Intent product = new Intent(getActivity(), ProductActivity.class);
        product.putExtra(Constant.Extra.PRODUCT_ID, dataProduct.getId().toString());
        startActivity(product);
    }
}
