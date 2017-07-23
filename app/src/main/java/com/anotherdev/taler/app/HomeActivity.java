package com.anotherdev.taler.app;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.anotherdev.taler.R;
import com.anotherdev.taler.api.bitcoinaverage.BitcoinAverage;
import com.anotherdev.taler.api.bitcoinaverage.model.TickerData;
import com.anotherdev.taler.common.rx.BaseObserver;
import com.anotherdev.taler.common.rx.Observables;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class HomeActivity extends TalerActivity {

    private static final String SYMBOL = "BTCEUR";
    private static final int INTERVAL_SECOND = 10;

    @BindView(R.id.ticker_timestamp_textview) TextView tickerTimestampTextView;
    @BindView(R.id.ticker_last_textview) TextView tickerLastTextView;
    @BindView(R.id.history_recyclerview) RecyclerView historyRecyclerView;

    private CompositeDisposable disposables = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setTitle(SYMBOL);
    }

    @Override
    protected void onResume() {
        super.onResume();
        disposables.add(subscribeData());
    }

    private Disposable subscribeData() {
        disposables.clear();
        return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .filter(new Predicate<Long>() {
                    @Override
                    public boolean test(@NonNull Long tick) throws Exception {
                        Timber.i("taler: %s mod %s: %s", tick, INTERVAL_SECOND, tick % INTERVAL_SECOND);
                        return tick % INTERVAL_SECOND == 0;
                    }
                })
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(@NonNull Long tick) throws Exception {
                        return SYMBOL;
                    }
                })
                .flatMap(new Function<String, ObservableSource<TickerData>>() {
                    @Override
                    public ObservableSource<TickerData> apply(@NonNull String symbol) throws Exception {
                        return BitcoinAverage.getApi().getTicker(symbol).toObservable();
                    }
                })
                .retryWhen(Observables.retryWithDelay(INTERVAL_SECOND, TimeUnit.SECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<TickerData>() {
                    @Override
                    public void onNext(TickerData data) {
                        updateTicker(data);
                    }
                });
    }

    private void updateTicker(TickerData data) {
        tickerTimestampTextView.setText(data.getDisplayTimestamp());
        tickerLastTextView.setText(String.valueOf(data.getLast()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        disposables.clear();
    }
}
