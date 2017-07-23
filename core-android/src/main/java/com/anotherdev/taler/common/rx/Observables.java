package com.anotherdev.taler.common.rx;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class Observables {

    private Observables() {
        // No public object
    }

    public static Function<Observable<? extends Throwable>, Observable<?>> retryWithDelay(final long delay, final TimeUnit unit) {
        return new Function<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> apply(@NonNull Observable<? extends Throwable> errors) throws Exception {
                return errors
                        .flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                                return Observable.timer(delay, unit);
                            }
                        });
            }
        };
    }
}
