package com.anotherdev.taler.api.bitcoinaverage;

import com.anotherdev.taler.api.bitcoinaverage.model.Fiats;
import com.anotherdev.taler.api.bitcoinaverage.model.Symbols;
import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BitcoinAverageApi {

    String AUTH_HEADER_NAME = "X-Signature";
    String AUTH_HEADER = AUTH_HEADER_NAME + ": %s";

    String TESTING_HEADER = "X-testing: testing";

    @GET("constants/time")
    Single<JsonObject> getTime();

    @GET("constants/symbols/global")
    Single<Symbols> getSymbols();

    @Headers(TESTING_HEADER)
    @GET("indices/global/ticker/all")
    Single<JsonObject> getTicker(@Query("crypto") String crypto, @Query("fiat") Fiats fiats);

    @GET("indices/global/ticker/{crypto}{fiat}")
    Single<JsonObject> getTicker(@Path("crypto") String crypto, @Path("fiat") String fiat);
}
