package com.anotherdev.taler.api.bitcoinaverage;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BitcoinAverage {

    private static final String BASE_URL = "https://apiv2.bitcoinaverage.com/";

    private static class ApiHolder {
        private static final BitcoinAverageApi API = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient.Builder()
                        .addNetworkInterceptor(new BitcoinAverageAuthInterceptor())
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
                .create(BitcoinAverageApi.class);
    }


    private BitcoinAverage() {
        // classic singleton for now instead of dagger
    }

    public static BitcoinAverageApi getApi() {
        return ApiHolder.API;
    }
}
