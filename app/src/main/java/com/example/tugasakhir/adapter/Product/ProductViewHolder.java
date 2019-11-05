package com.example.tugasakhir.adapter.Product;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.Product.DataProduct;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

class ProductViewHolder extends RecyclerView.ViewHolder {
    private TextView tvItemName, tvItemPrice;
    private ImageView ivItemP;
    private ConstraintLayout clProductItem;
    NumberFormat numberFormat;

    public ProductViewHolder(View view) {
        super(view);
        initView(itemView);
    }

    private void initView(View itemView) {
        tvItemName = itemView.findViewById(R.id.tvItemPName);
        tvItemPrice = itemView.findViewById(R.id.tvItemPPrice);
        ivItemP = itemView.findViewById(R.id.ivCartP);
        clProductItem = itemView.findViewById(R.id.clProductItem);
    }

    public void bind(final DataProduct dataProduct, final ProductListener productListener) {
        numberFormat = NumberFormat.getInstance(Locale.ITALY);
        String price = numberFormat.format(dataProduct.getPrice());
        productListener.displayImgProduct(ivItemP, dataProduct);
        tvItemName.setText(dataProduct.getName());
        tvItemPrice.setText("Rp "+price);

        Picasso.get()
                .load(dataProduct.getPhoto())
                .resize(0, 100)
                .centerCrop()
                .into(ivItemP);

        clProductItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productListener.onProductClick(dataProduct);
            }
        });
    }
}
