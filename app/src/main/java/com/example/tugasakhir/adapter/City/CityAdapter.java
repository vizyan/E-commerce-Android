package com.example.tugasakhir.adapter.City;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.Cart.DataCart;
import com.example.tugasakhir.data.model.Product.DataProduct;
import com.example.tugasakhir.data.model.RajaOngkir.DataCity;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityViewHolder> implements Filterable {

    private List<DataCity> dataCityList;
    private List<DataCity> dataCityFiltered;
    private CityListener cityListener;

    public CityAdapter(List<DataCity> dataCityList){
        this.dataCityList = dataCityList;
        dataCityFiltered = dataCityList;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);

        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        DataCity dataCity = dataCityFiltered.get(position);
        holder.bind(dataCity, cityListener);
    }

    private DataCity getItem(int position) {
        return dataCityList.get(position);
    }

    @Override
    public int getItemCount() {
        if (dataCityFiltered == null) return 0;
        return dataCityFiltered.size();
    }

    public void setAdapterListener(CityListener listener) {
        this.cityListener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    dataCityFiltered = dataCityList;
                } else {
                    List<DataCity> filteredList = new ArrayList<>();
                    for (DataCity row : dataCityList) {
                        if (row.getCityName().toLowerCase().contains(charString.toLowerCase()) || row.getProvince().toString().contains(charString.toLowerCase()) || row.getCityId().toString().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    dataCityFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataCityFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataCityFiltered = (List<DataCity>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
