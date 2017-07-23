package com.anotherdev.taler.view;

import android.support.v7.util.SortedList;

import com.anotherdev.taler.api.bitcoinaverage.model.HistoricalData;
import com.pedrogomez.renderers.AdapteeCollection;

import java.util.Collection;

import timber.log.Timber;

class HistoricalDataAdapteeCollection implements AdapteeCollection<HistoricalData> {

    private final HistoricalDataSortedListCallback callback;
    private final HistoricalDataRendererAdapter adapter;
    private final SortedList<HistoricalData> history;


    HistoricalDataAdapteeCollection(HistoricalDataRendererAdapter adapter) {
        this.adapter = adapter;
        callback = new HistoricalDataSortedListCallback(adapter);
        history = new SortedList<>(HistoricalData.class, callback);
    }

    @Override
    public int size() {
        return history.size();
    }

    @Override
    public HistoricalData get(int i) {
        return history.get(i);
    }

    @Override
    public boolean add(HistoricalData item) {
        history.add(item);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return history.remove((HistoricalData) o);
    }

    @Override
    public boolean addAll(Collection<? extends HistoricalData> histories) {
        for (HistoricalData h : histories) {
            add(h);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> histories) {
        for (Object h : histories) {
            remove(h);
        }
        return true;
    }

    @Override
    public void clear() {
        history.clear();
    }
}
