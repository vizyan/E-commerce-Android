package com.example.tugasakhir.adapter.City;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.RajaOngkir.DataCity;

class CityViewHolder extends RecyclerView.ViewHolder {
    private TextView tvCtName, tvCtType, tvCtProvincy, tvCtPostal;
    private ConstraintLayout clCtItem;

    public CityViewHolder(View view) {
        super(view);
        initView(itemView);
    }

    private void initView(View itemView) {
        tvCtName = itemView.findViewById(R.id.tvShCtName);
        tvCtType = itemView.findViewById(R.id.tvShCtType);
        tvCtProvincy = itemView.findViewById(R.id.tvShCtProvincy);
        tvCtPostal = itemView.findViewById(R.id.tvShCtPostal);
        clCtItem = itemView.findViewById(R.id.clCtItem);
    }

    public void bind(final DataCity dataCity, final CityListener cityListener) {
        tvCtType.setText(dataCity.getType());
        tvCtName.setText(dataCity.getCityName());
        tvCtProvincy.setText(dataCity.getProvince());
        tvCtPostal.setText(dataCity.getPostalCode());

        clCtItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clCtItem.setBackgroundResource(R.color.gradient5);
                tvCtName.setTextColor(Color.parseColor("#FFFFFF"));
                tvCtPostal.setTextColor(Color.parseColor("#FFFFFF"));
                tvCtProvincy.setTextColor(Color.parseColor("#FFFFFF"));
                tvCtType.setTextColor(Color.parseColor("#FFFFFF"));
                cityListener.onCityClick(dataCity);
            }
        });
    }
}
