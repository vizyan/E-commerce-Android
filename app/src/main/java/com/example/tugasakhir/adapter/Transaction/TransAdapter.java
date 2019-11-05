package com.example.tugasakhir.adapter.Transaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhir.R;
import com.example.tugasakhir.data.model.RajaOngkir.DataCost;
import com.example.tugasakhir.data.model.Transaction.DataTrans;

import java.util.ArrayList;
import java.util.List;

public class TransAdapter extends RecyclerView.Adapter<TransViewHolder> implements Filterable {

    private List<DataTrans> dataTransList;
    private List<DataTrans> dataTransFiltered;
    private TransListener transListener;

    public TransAdapter(List<DataTrans> dataTransList){
        this.dataTransList = dataTransList;
        dataTransFiltered = dataTransList;
    }

    @Override
    public TransViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_trans, parent, false);

        return new TransViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransViewHolder holder, int position) {
        DataTrans dataTrans = dataTransFiltered.get(position);
        holder.bind(dataTrans, transListener);
    }

    private DataTrans getItem(int position) {
        return dataTransList.get(position);
    }

    @Override
    public int getItemCount() {
        if (dataTransFiltered == null) return 0;
        return dataTransFiltered.size();
    }

    public void setAdapterListener(TransListener listener) {
        this.transListener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    dataTransFiltered = dataTransList;
                } else {
                    List<DataTrans> filteredList = new ArrayList<>();
                    for (DataTrans row : dataTransList) {
                        if (row.getDate().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    dataTransFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataTransFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataTransFiltered = (List<DataTrans>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
