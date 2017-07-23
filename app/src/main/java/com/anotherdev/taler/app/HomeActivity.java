package com.anotherdev.taler.app;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.anotherdev.taler.R;
import com.anotherdev.taler.api.bitcoinaverage.BitcoinAverage;
import com.anotherdev.taler.api.bitcoinaverage.model.HistoricalData;
import com.anotherdev.taler.api.bitcoinaverage.model.TickerData;
import com.anotherdev.taler.common.rx.BaseObserver;
import com.anotherdev.taler.common.rx.Observables;
import com.anotherdev.taler.view.HistoricalDataRenderer;
import com.anotherdev.taler.view.HistoricalDataRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.List;
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

public class HomeActivity extends TalerActivity {

    private static final String SYMBOL = "BTCEUR";
    private static final int TICKER_INTERVAL_SECOND = 30;
    private static final int HISTORY_INTERVAL_SECOND = 60;

    @BindView(R.id.ticker_timestamp_textview) TextView tickerTimestampTextView;
    @BindView(R.id.ticker_last_textview) TextView tickerLastTextView;
    @BindView(R.id.history_recyclerview) RecyclerView historyRecyclerView;

    private CompositeDisposable tickerDisposable = new CompositeDisposable();
    private CompositeDisposable historyDisposable = new CompositeDisposable();

    private HistoricalDataRendererAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setTitle(SYMBOL);

        HistoricalDataRenderer renderer = new HistoricalDataRenderer();
        RendererBuilder<HistoricalData> builder = new RendererBuilder<>(renderer);
        adapter = new HistoricalDataRendererAdapter(builder);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tickerDisposable.add(subscribeTickerData());
        historyDisposable.add(subscribeHistoryData());
    }

    private Disposable subscribeTickerData() {
        tickerDisposable.clear();
        return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .filter(new Predicate<Long>() {
                    @Override
                    public boolean test(@NonNull Long tick) throws Exception {
                        return tick % TICKER_INTERVAL_SECOND == 0;
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
                .retryWhen(Observables.retryWithDelay(TICKER_INTERVAL_SECOND, TimeUnit.SECONDS))
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

    private Disposable subscribeHistoryData() {
        historyDisposable.clear();
        return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .filter(new Predicate<Long>() {
                    @Override
                    public boolean test(@NonNull Long tick) throws Exception {
                        return tick % HISTORY_INTERVAL_SECOND == 0;
                    }
                })
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(@NonNull Long tick) throws Exception {
                        return SYMBOL;
                    }
                })
                .flatMap(new Function<String, ObservableSource<List<HistoricalData>>>() {
                    @Override
                    public ObservableSource<List<HistoricalData>> apply(@NonNull String symbol) throws Exception {
                        return BitcoinAverage.getApi().getHistoryDaily(symbol).toObservable();
                    }
                })
                .retryWhen(Observables.retryWithDelay(HISTORY_INTERVAL_SECOND, TimeUnit.SECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<List<HistoricalData>>() {
                    @Override
                    public void onNext(List<HistoricalData> data) {
                        adapter.addAll(data.subList(0,3));
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        tickerDisposable.clear();
        historyDisposable.clear();
    }
}
