package com.example.tugasakhir.adapter.Shipment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.RajaOngkir.DataCost;

import java.util.ArrayList;
import java.util.List;

public class ShipAdapter extends RecyclerView.Adapter<ShipViewHolder> implements Filterable {

    private List<DataCost> dataCostList;
    private List<DataCost> dataCostFiltered;
    private ShipListener shipListener;

    public ShipAdapter(List<DataCost> dataProductList){
        this.dataCostList = dataProductList;
        dataCostFiltered = dataProductList;
    }

    @Override
    public ShipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_ship, parent, false);

        return new ShipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShipViewHolder holder, int position) {
        DataCost dataCost = dataCostFiltered.get(position);
        holder.bind(dataCost, shipListener);
    }

    private DataCost getItem(int position) {
        return dataCostList.get(position);
    }

    @Override
    public int getItemCount() {
        if (dataCostFiltered == null) return 0;
        return dataCostFiltered.size();
    }

    public void setAdapterListener(ShipListener listener) {
        this.shipListener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    dataCostFiltered = dataCostList;
                } else {
                    List<DataCost> filteredList = new ArrayList<>();
                    for (DataCost row : dataCostList) {
                        if (row.getService().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    dataCostFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataCostFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataCostFiltered = (List<DataCost>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
