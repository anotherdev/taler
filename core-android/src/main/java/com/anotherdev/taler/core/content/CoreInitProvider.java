package com.anotherdev.taler.core.content;

import com.anotherdev.taler.analytics.log.CrashlyticsTree;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class CoreInitProvider extends BaseInitProvider {

    @Override
    public boolean onCreate() {
        Fabric.with(getContext(), new Crashlytics());

        Timber.plant(new CrashlyticsTree());
        return false;
    }
}