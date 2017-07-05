package com.anotherdev.taler.api.bitcoinaverage;

import com.anotherdev.taler.api.bitcoinaverage.model.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface BitcoinAverageApi {

    @GET("constants/symbols")
    Observable<Response> getSymbols();
}
