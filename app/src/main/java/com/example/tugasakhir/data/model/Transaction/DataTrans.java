package com.example.tugasakhir.data.model.Transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataTrans {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("trans_hash")
    @Expose
    private String transHash;
    @SerializedName("resi")
    @Expose
    private String resi;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransHash() {
        return transHash;
    }

    public void setTransHash(String transHash) {
        this.transHash = transHash;
    }

    public String getResi() {
        return resi;
    }

    public void setResi(String resi) {
        this.resi = resi;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

}
