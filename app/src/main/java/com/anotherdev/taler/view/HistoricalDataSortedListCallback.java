package com.anotherdev.taler.view;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;

import com.anotherdev.taler.api.bitcoinaverage.model.HistoricalData;
import com.google.common.base.Objects;

class HistoricalDataSortedListCallback extends SortedListAdapterCallback<HistoricalData> {

    HistoricalDataSortedListCallback(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    public int compare(HistoricalData hd1, HistoricalData hd2) {
        return hd1.compareTo(hd2);
    }

    @Override
    public boolean areContentsTheSame(HistoricalData hd1, HistoricalData hd2) {
        return Objects.equal(hd1, hd2);
    }

    @Override
    public boolean areItemsTheSame(HistoricalData hd1, HistoricalData hd2) {
        return Objects.equal(hd1.hashCode(), hd2.hashCode());
    }
}
