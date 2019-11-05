package com.example.tugasakhir.adapter.Product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.Product.DataProduct;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> implements Filterable {

    private List<DataProduct> dataProductList;
    private List<DataProduct> dataProductsFiltered;
    private ProductListener productListener;

    public ProductAdapter(List<DataProduct> dataProductList){
        this.dataProductList = dataProductList;
        dataProductsFiltered = dataProductList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        DataProduct dataProduct = dataProductsFiltered.get(position);
        holder.bind(dataProduct, productListener);
    }

    private DataProduct getItem(int position) {
        return dataProductList.get(position);
    }

    @Override
    public int getItemCount() {
        if (dataProductsFiltered == null) return 0;
        return dataProductsFiltered.size();
    }

    public void setAdapterListener(ProductListener listener) {
        this.productListener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    dataProductsFiltered = dataProductList;
                } else {
                    List<DataProduct> filteredList = new ArrayList<>();
                    for (DataProduct row : dataProductList) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPrice().toString().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    dataProductsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataProductsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataProductsFiltered = (List<DataProduct>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
