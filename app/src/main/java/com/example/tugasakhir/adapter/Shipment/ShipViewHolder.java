package com.example.tugasakhir.adapter.Shipment;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.RajaOngkir.Costs;
import com.example.tugasakhir.data.model.RajaOngkir.DataCost;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

class ShipViewHolder extends RecyclerView.ViewHolder {
    private TextView tvShName, tvShPrice, tvShDesc;
    private ImageView ivItemP;
    private ConstraintLayout clShItem;
    private List<Costs> costsList;
    private Costs costs;
    NumberFormat numberFormat;

    public ShipViewHolder(View view) {
        super(view);
        initView(itemView);
    }

    private void initView(View itemView) {
        tvShName = itemView.findViewById(R.id.tvShName);
        tvShPrice = itemView.findViewById(R.id.tvShPrice);
        tvShDesc = itemView.findViewById(R.id.tvShDesc);
        clShItem = itemView.findViewById(R.id.clShItem);
    }

    public void bind(final DataCost dataCost, final ShipListener shipListener) {
        numberFormat = NumberFormat.getInstance(Locale.ITALY);
        costsList = dataCost.getCost();
        costs = costsList.get(0);

        tvShName.setText(dataCost.getService());
        tvShPrice.setText("Rp "+numberFormat.format(costs.getValue()));
        tvShDesc.setText("Estimasi : "+costs.getEtd()+" hari");

        clShItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shipListener.onShipClick(dataCost);
                clShItem.setBackgroundResource(R.color.gradient5);
                tvShDesc.setTextColor(Color.parseColor("#FFFFFF"));
                tvShName.setTextColor(Color.parseColor("#FFFFFF"));
                tvShPrice.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
    }
}
