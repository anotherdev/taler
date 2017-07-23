package com.anotherdev.taler.common.rx;

import io.reactivex.Observer;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

public class BaseObserver<T> extends DisposableObserver<T> {

    private static final Observer<Object> EMPTY = new BaseObserver<>();


    @SuppressWarnings("unchecked")
    public static <T> Observer<T> logOnError() {
        return (Observer<T>) EMPTY;
    }


    @Override
    public void onNext(T t) {
    }

    @Override
    public void onError(Throwable e) {
        Timber.e(e, String.valueOf(e.getMessage()));
    }

    @Override
    public void onComplete() {
    }
}
