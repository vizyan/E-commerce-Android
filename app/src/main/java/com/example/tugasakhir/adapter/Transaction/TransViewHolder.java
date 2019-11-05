package com.example.tugasakhir.adapter.Transaction;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.RajaOngkir.Costs;
import com.example.tugasakhir.data.model.RajaOngkir.DataCost;
import com.example.tugasakhir.data.model.Transaction.DataTrans;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

class TransViewHolder extends RecyclerView.ViewHolder {
    private TextView tvTransTotal, tvTransDate;
    private ConstraintLayout clTransItem;
    NumberFormat numberFormat;

    public TransViewHolder(View view) {
        super(view);
        initView(itemView);
    }

    private void initView(View itemView) {
        tvTransTotal= itemView.findViewById(R.id.tvTransTotal);
        tvTransDate = itemView.findViewById(R.id.tvTransDate);
        clTransItem = itemView.findViewById(R.id.clTransItem);
    }

    public void bind(final DataTrans dataTrans, final TransListener transListener) {
        numberFormat = NumberFormat.getInstance(Locale.ITALY);
        String total = numberFormat.format(dataTrans.getTotal());

        tvTransTotal.setText(total);
        tvTransDate.setText(dataTrans.getDate());

        clTransItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transListener.onTransClick(dataTrans);
            }
        });
    }
}
