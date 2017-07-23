package com.anotherdev.taler.api.bitcoinaverage.model;

import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class HistoricalData implements Comparable<HistoricalData> {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN, Locale.US);
    private static final Ordering<String> ORDERING = Ordering.natural().reverse().nullsLast();

    String time = "";
    double average;


    public String getTime() {
        return time;
    }

    public long getTimestamp() {
        long timestamp = 0;
        try {
            Date date = DATE_FORMAT.parse(time);
            timestamp = date.getTime() / 1000;
        } catch (Exception e) {
            Timber.e(e, "taler: %s", e.getMessage());
        }
        return timestamp;
    }

    public double getAverage() {
        return average;
    }

    @Override
    public String toString() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof HistoricalData)) return false;
        HistoricalData other = (HistoricalData) o;
        return Objects.equal(time, other.time);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(time);
    }

    @Override
    public int compareTo(@NonNull HistoricalData other) {
        return ComparisonChain.start()
                .compare(this.time, other.time, ORDERING)
                .result();
    }
}
