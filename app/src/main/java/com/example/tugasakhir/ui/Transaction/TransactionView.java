package com.example.tugasakhir.ui.Transaction;

import com.example.tugasakhir.data.model.Product.DataProduct;
import com.example.tugasakhir.data.model.Transaction.DataTrans;

import java.util.List;

public interface TransactionView {

    String getAmount();

    String getNote();

    void successTransaction(String hash);

    void successListTrans(List<DataTrans> data);

    void successCart();

    void successProduct(DataProduct data);

    void failed(String message);
}
