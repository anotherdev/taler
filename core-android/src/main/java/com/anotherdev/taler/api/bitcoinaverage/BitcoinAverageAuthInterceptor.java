package com.anotherdev.taler.api.bitcoinaverage;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.common.io.BaseEncoding;

import java.io.IOException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class BitcoinAverageAuthInterceptor implements Interceptor {

    private static final String PUBLIC_KEY = "YmIyNDEzM2YwZDNlNDgwM2IzMTIzYjRkNTY4MzAyYjE";
    private static final String SECRET_KEY = "MzMzZjJhYzgzNWIxNDRlN2EwZDRlOThlZDAwNmI5MTUwZGM1ZmFjN2MzMDc0ODNiYjFkZmRiODM2NmIzNzE1YQ";

    private final Mac hmacSha256;


    BitcoinAverageAuthInterceptor() {
        hmacSha256 = initMac();
    }

    private Mac initMac() {
        Mac mac = null;
        try {
            final String algo = "HmacSHA256";
            mac = Mac.getInstance(algo);
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), algo);
            mac.init(secretKeySpec);
        } catch (Exception e) {
            Timber.e(e, "taler: %s", e.getMessage());
        }
        return mac;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        final String authHeader = request.header(BitcoinAverageApi.AUTH_HEADER_NAME);
        if (!TextUtils.isEmpty(authHeader)) {
            request = request.newBuilder()
                    .header(BitcoinAverageApi.AUTH_HEADER_NAME, getSignature())
                    .build();
        }

        Timber.i("taler: request: %s", request);
        Timber.i("taler: headers:\n%s", request.headers());
        Response response = chain.proceed(request);
        Timber.i("taler: response: %s", response);
        return response;
    }

    private String getSignature() {
        long timestamp = System.currentTimeMillis() / 1000;
        String payload = timestamp + "." + PUBLIC_KEY;
        byte[] input = hmacSha256.doFinal(payload.getBytes());
        String hashHex = BaseEncoding.base16().encode(input).toLowerCase();
        return payload + "." + hashHex;
    }
}
