package com.example.tugasakhir.data.model.Stellar.ExchangeBtc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Xlmtobtc {

    @SerializedName("ask")
    @Expose
    private Double ask;
    @SerializedName("bid")
    @Expose
    private Double bid;
    @SerializedName("last")
    @Expose
    private Double last;
    @SerializedName("high")
    @Expose
    private Double high;
    @SerializedName("low")
    @Expose
    private Double low;
    @SerializedName("volume")
    @Expose
    private Double volume;
    @SerializedName("open")
    @Expose
    private Open open;
    @SerializedName("averages")
    @Expose
    private Averages averages;
    @SerializedName("changes")
    @Expose
    private Changes changes;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("display_timestamp")
    @Expose
    private String displayTimestamp;
    @SerializedName("display_symbol")
    @Expose
    private String displaySymbol;

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Open getOpen() {
        return open;
    }

    public void setOpen(Open open) {
        this.open = open;
    }

    public Averages getAverages() {
        return averages;
    }

    public void setAverages(Averages averages) {
        this.averages = averages;
    }

    public Changes getChanges() {
        return changes;
    }

    public void setChanges(Changes changes) {
        this.changes = changes;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getDisplayTimestamp() {
        return displayTimestamp;
    }

    public void setDisplayTimestamp(String displayTimestamp) {
        this.displayTimestamp = displayTimestamp;
    }

    public String getDisplaySymbol() {
        return displaySymbol;
    }

    public void setDisplaySymbol(String displaySymbol) {
        this.displaySymbol = displaySymbol;
    }

}