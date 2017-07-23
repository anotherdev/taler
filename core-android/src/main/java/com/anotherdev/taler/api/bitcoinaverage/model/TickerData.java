package com.anotherdev.taler.api.bitcoinaverage.model;

import com.google.gson.annotations.SerializedName;

public class TickerData {

    double last;
    @SerializedName("display_timestamp") String displayTimestamp;


    public double getLast() {
        return last;
    }

    public String getDisplayTimestamp() {
        return displayTimestamp;
    }
}
