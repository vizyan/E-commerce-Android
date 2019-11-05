package com.example.tugasakhir.ui.Dashboard;

import com.example.tugasakhir.data.model.Cart.DataCart;
import com.example.tugasakhir.data.model.Product.DataProduct;
import com.example.tugasakhir.data.model.Stellar.ExchangeBtc.Averages;
import com.example.tugasakhir.data.model.User.DataUser;

import java.util.List;

public interface DashboardView {
    void successProduct(List<DataProduct> data);

    void failed(String message);

    void successUser(DataUser dataUser, String balance);

    void successExchange(Averages averages);

    void successCart(List<DataCart> data);

    void success(Integer data);
}
