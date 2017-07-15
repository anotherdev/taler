package com.anotherdev.taler.core.content;

import com.anotherdev.taler.analytics.log.CrashlyticsTree;
import com.anotherdev.taler.api.bitcoinaverage.BitcoinAverage;
import com.anotherdev.taler.api.bitcoinaverage.BitcoinAverageApi;
import com.anotherdev.taler.common.rx.BaseObserver;
import com.crashlytics.android.Crashlytics;

import java.util.concurrent.Callable;

import io.fabric.sdk.android.Fabric;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CoreInitProvider extends BaseInitProvider {

    @Override
    public boolean onCreate() {
        Fabric.with(getContext(), new Crashlytics());

        Timber.plant(new CrashlyticsTree());

        initApi();
        return false;
    }

    private void initApi() {
        Observable
                .fromCallable(new Callable<BitcoinAverageApi>() {
                    @Override
                    public BitcoinAverageApi call() throws Exception {
                        return BitcoinAverage.getApi();
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(BaseObserver.logOnError());
    }
}