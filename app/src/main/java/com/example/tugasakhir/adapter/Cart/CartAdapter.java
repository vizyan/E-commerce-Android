package com.example.tugasakhir.adapter.Cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.Cart.DataCart;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> implements Filterable {

    private List<DataCart> dataCartsList;
    private List<DataCart> dataCartsFiltered;
    private CartListener projectListener;

    public CartAdapter(List<DataCart> dataCartsList){
        this.dataCartsList = dataCartsList;
        dataCartsFiltered = dataCartsList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        DataCart dataCart = dataCartsFiltered.get(position);
        holder.bind(dataCart, projectListener);
    }

    private DataCart getItem(int position) {
        return dataCartsList.get(position);
    }

    @Override
    public int getItemCount() {
        if (dataCartsFiltered == null) return 0;
        return dataCartsFiltered.size();
    }

    public void setAdapterListener(CartListener listener) {
        this.projectListener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    dataCartsFiltered = dataCartsList;
                } else {
                    List<DataCart> filteredList = new ArrayList<>();
                    for (DataCart row : dataCartsList) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPrice().toString().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    dataCartsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataCartsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataCartsFiltered = (List<DataCart>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
