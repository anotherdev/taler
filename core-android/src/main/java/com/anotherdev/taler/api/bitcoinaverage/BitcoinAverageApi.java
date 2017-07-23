package com.anotherdev.taler.api.bitcoinaverage;

import com.anotherdev.taler.api.bitcoinaverage.model.Fiats;
import com.anotherdev.taler.api.bitcoinaverage.model.HistoricalData;
import com.anotherdev.taler.api.bitcoinaverage.model.Symbols;
import com.anotherdev.taler.api.bitcoinaverage.model.TickerData;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BitcoinAverageApi {

    String AUTH_HEADER_NAME = "X-Signature";
    String AUTH_HEADER = AUTH_HEADER_NAME + ": %s";


    @GET("constants/time")
    Single<JsonObject> getTime();

    @GET("constants/symbols/global")
    Single<Symbols> getSymbols();

    @Headers(AUTH_HEADER)
    @GET("indices/global/ticker/all")
    Single<TickerData> getTicker(@Query("crypto") String crypto, @Query("fiat") Fiats fiats);

    @Headers(AUTH_HEADER)
    @GET("indices/global/ticker/{crypto}{fiat}")
    Single<TickerData> getTicker(@Path("crypto") String crypto, @Path("fiat") String fiat);

    @Headers(AUTH_HEADER)
    @GET("indices/global/ticker/{symbol}")
    Single<TickerData> getTicker(@Path("symbol") String symbol);

    @GET("indices/global/history/{symbol}?period=daily&format=json")
    Single<List<HistoricalData>> getHistoryDaily(@Path("symbol") String symbol);

    @Headers(AUTH_HEADER)
    @GET("indices/global/history/{symbol}")
    Single<List<HistoricalData>> getHistorySince(@Path("symbol") String symbol, @Query("since") long timestamp);
}
