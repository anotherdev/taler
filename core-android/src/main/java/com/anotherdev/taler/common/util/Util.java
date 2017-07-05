package com.anotherdev.taler.common.util;

import android.os.Looper;

public class Util {

    public static boolean isUiThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static String currentThread() {
        return Thread.currentThread().toString();
    }
}
