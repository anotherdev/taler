package com.anotherdev.taler.common.rx;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class BaseObserver<T> implements Observer<T> {

    private static final Observer<Object> EMPTY = new BaseObserver<>();


    @SuppressWarnings("unchecked")
    public static <T> Observer<T> logOnError() {
        return (Observer<T>) EMPTY;
    }


    @Override
    public void onSubscribe(@NonNull Disposable d) {
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
