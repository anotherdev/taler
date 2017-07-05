package com.anotherdev.taler.core.content;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class CoreInitProvider extends BaseInitProvider {

    @Override
    public boolean onCreate() {
        Fabric.with(getContext(), new Crashlytics());
        return false;
    }
}