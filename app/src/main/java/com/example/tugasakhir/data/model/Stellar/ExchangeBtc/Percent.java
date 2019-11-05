package com.example.tugasakhir.data.model.Stellar.ExchangeBtc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Percent {

    @SerializedName("hour")
    @Expose
    private Double hour;
    @SerializedName("day")
    @Expose
    private Double day;
    @SerializedName("week")
    @Expose
    private Double week;
    @SerializedName("month")
    @Expose
    private Double month;
    @SerializedName("month_3")
    @Expose
    private Double month3;
    @SerializedName("month_6")
    @Expose
    private Double month6;
    @SerializedName("year")
    @Expose
    private Double year;

    public Double getHour() {
        return hour;
    }

    public void setHour(Double hour) {
        this.hour = hour;
    }

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

    public Double getMonth3() {
        return month3;
    }

    public void setMonth3(Double month3) {
        this.month3 = month3;
    }

    public Double getMonth6() {
        return month6;
    }

    public void setMonth6(Double month6) {
        this.month6 = month6;
    }

    public Double getYear() {
        return year;
    }

    public void setYear(Double year) {
        this.year = year;
    }

}