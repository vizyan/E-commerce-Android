package com.example.tugasakhir.adapter.Cart;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.Cart.DataCart;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

class CartViewHolder extends RecyclerView.ViewHolder {
    private TextView tvCartName, tvCartPrice, tvCartMuch;
    private ImageView ivItemP;
    private ConstraintLayout clCartItem;
    ImageButton ibDeleteItem;
    NumberFormat numberFormat;

    public CartViewHolder(View view) {
        super(view);
        initView(itemView);
    }

    private void initView(View itemView) {
        tvCartName = itemView.findViewById(R.id.tvShCtName);
        tvCartPrice = itemView.findViewById(R.id.tvShCtProvincy);
        tvCartMuch = itemView.findViewById(R.id.tvShCtPostal);
        ivItemP = itemView.findViewById(R.id.ivCartP);
        ibDeleteItem = itemView.findViewById(R.id.ibDeleteCart);
        clCartItem = itemView.findViewById(R.id.clCartItem);
    }

    public void bind(final DataCart dataCart, final CartListener cartListener) {
        numberFormat = NumberFormat.getInstance(Locale.ITALY);
        String total = numberFormat.format(dataCart.getTotal());
        cartListener.displayImgProject(ivItemP, dataCart);
        tvCartName.setText(dataCart.getName());
        tvCartPrice.setText("Rp "+total);
        tvCartMuch.setText("Jumlah : "+dataCart.getMuch().toString());

        Picasso.get()
                .load(dataCart.getPhoto())
                .resize(100, 100)
                .centerInside()
                .into(ivItemP);

        clCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartListener.onCartClick(dataCart);
            }
        });

        ibDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartListener.deleteItem(dataCart);
            }
        });
    }
}
