package com.anotherdev.taler.app;

import android.app.Application;

import com.anotherdev.taler.api.bitcoinaverage.BitcoinAverage;
import com.anotherdev.taler.api.bitcoinaverage.BitcoinAverageApi;
import com.anotherdev.taler.api.bitcoinaverage.model.Symbols;
import com.anotherdev.taler.common.rx.BaseObserver;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListSet;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TalerApp extends Application {

    private static final ConcurrentSkipListSet<String> CRYPTOS = new ConcurrentSkipListSet<>();
    private static final ConcurrentSkipListSet<String> FIATS = new ConcurrentSkipListSet<>();


    public static ConcurrentSkipListSet<String> getCryptos() {
        return CRYPTOS;
    }

    public static ConcurrentSkipListSet<String> getFiats() {
        return FIATS;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApi();
    }

    private void initApi() {
        Single
                .fromCallable(new Callable<BitcoinAverageApi>() {
                    @Override
                    public BitcoinAverageApi call() throws Exception {
                        return BitcoinAverage.getApi();
                    }
                })
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<BitcoinAverageApi, SingleSource<Symbols>>() {
                    @Override
                    public SingleSource<Symbols> apply(@NonNull BitcoinAverageApi api) throws Exception {
                        return api.getSymbols();
                    }
                })
                .flattenAsObservable(new Function<Symbols, Iterable<String>>() {
                    @Override
                    public Iterable<String> apply(@NonNull Symbols symbols) throws Exception {
                        return symbols.getSymbols();
                    }
                })
                .map(new Function<String, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull String symbol) throws Exception {
                        final int mid = 3;
                        String crypto = symbol.substring(0, mid);
                        String fiat = symbol.substring(mid);
                        CRYPTOS.add(crypto);
                        FIATS.add(fiat);
                        return true;
                    }
                })
                .subscribe(BaseObserver.logOnError());
    }
}
