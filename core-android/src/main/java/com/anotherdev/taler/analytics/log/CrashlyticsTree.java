package com.anotherdev.taler.analytics.log;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

import timber.log.BuildConfig;
import timber.log.Timber;

public class CrashlyticsTree extends Timber.Tree {

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            if (BuildConfig.DEBUG) {
                Crashlytics.log(priority, tag, message);
            }
        } else {
            Crashlytics.log(priority, tag, message);
        }

        if (t != null) {
            Crashlytics.logException(t);
        }
    }
}
