package com.example.tugasakhir.data.model.Stellar.ExchangeBtc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Averages {

    @SerializedName("day")
    @Expose
    private Double day;
    @SerializedName("week")
    @Expose
    private Double week;
    @SerializedName("month")
    @Expose
    private Double month;

    public Double getDay() {
        return day;
    }

    public void setDay(Double day) {
        this.day = day;
    }

    public Double getWeek() {
        return week;
    }

    public void setWeek(Double week) {
        this.week = week;
    }

    public Double getMonth() {
        return month;
    }

    public void setMonth(Double month) {
        this.month = month;
    }
}
