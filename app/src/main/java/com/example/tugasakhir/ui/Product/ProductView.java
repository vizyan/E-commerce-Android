package com.example.tugasakhir.ui.Product;

import com.example.tugasakhir.data.model.Product.DataProduct;

public interface ProductView {
    void failed(String message);

    void successProduct(DataProduct data);

    void success();
}
