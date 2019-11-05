package com.example.tugasakhir.data.model.RajaOngkir;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Rajaongkir {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("costs")
    @Expose
    private List<DataCost> costs = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataCost> getCosts() {
        return costs;
    }

    public void setCosts(List<DataCost> costs) {
        this.costs = costs;
    }
}
