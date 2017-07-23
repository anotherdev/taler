package com.anotherdev.taler.view;

import com.anotherdev.taler.api.bitcoinaverage.model.HistoricalData;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

public class HistoricalDataRendererAdapter extends RVRendererAdapter<HistoricalData> {

    public HistoricalDataRendererAdapter(RendererBuilder<HistoricalData> builder) {
        super(builder);
        setCollection(new HistoricalDataAdapteeCollection(this));
    }
}
