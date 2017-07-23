package com.anotherdev.taler.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anotherdev.taler.R;
import com.anotherdev.taler.api.bitcoinaverage.model.HistoricalData;
import com.pedrogomez.renderers.Renderer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoricalDataRenderer extends Renderer<HistoricalData> {

    @BindView(R.id.history_average_textview) TextView historyAverageTextView;
    @BindView(R.id.history_timestamp_textview) TextView historyTimestampTextView;


    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.view_historical_data, parent, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void setUpView(View view) {
    }

    @Override
    protected void hookListeners(View view) {
    }

    @Override
    public void render() {
        HistoricalData data = getContent();
        historyAverageTextView.setText(String.valueOf(data.getAverage()));
        historyTimestampTextView.setText(data.getTime());
    }
}
