package com.anotherdev.taler.api.bitcoinaverage.model;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("success")
    boolean success;


    public boolean isSuccess() {
        return success;
    }
}
