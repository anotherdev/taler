package com.anotherdev.taler.api.bitcoinaverage;

import com.anotherdev.taler.api.bitcoinaverage.model.Response;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface BitcoinAverageApi {

    @GET("constants/symbols")
    Single<Response> getSymbols();
}
