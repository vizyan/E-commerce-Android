package com.example.tugasakhir.ui.Transaction;

import com.example.tugasakhir.data.model.Transaction.DataTrans;

import java.util.List;

public interface TransactionView {

    void successTransaction(String hash);

    void successListTrans(List<DataTrans> data);

    void failed(String message);

    String getAmount();

    String getNote();

}
