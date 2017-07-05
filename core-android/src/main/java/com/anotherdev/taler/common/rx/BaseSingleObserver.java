package com.anotherdev.taler.common.rx;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class BaseSingleObserver<T> implements SingleObserver<T> {

    private static final SingleObserver<Object> EMPTY = new BaseSingleObserver<>();


    @SuppressWarnings("unchecked")
    public static <T> SingleObserver<T> logOnError() {
        return (SingleObserver<T>) EMPTY;
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {
    }

    @Override
    public void onSuccess(@NonNull T t) {
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Timber.e(e, String.valueOf(e.getMessage()));
    }
}
