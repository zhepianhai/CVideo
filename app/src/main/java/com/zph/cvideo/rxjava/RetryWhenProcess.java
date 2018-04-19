package com.zph.cvideo.rxjava;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.Function;

/**
 *
 * @author zph
 * @date 2018/4/19
 */

public class RetryWhenProcess implements Function<Observable<Throwable>, ObservableSource<?>> {
    private static final String TAG = RetryWhenProcess.class.getSimpleName();
    public static final int PROCESS_TIME = 2;
    /**
     * 重试间隔
     */
    private long mInterval;
    private long tryTimes = 1;
    private long maxTryTime = 3;

    public RetryWhenProcess(long interval) {

        mInterval = interval;
    }

    @Override
    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {

        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                if (throwable instanceof SocketTimeoutException && ++tryTimes <= maxTryTime) {
                    return Observable.timer(mInterval, TimeUnit.SECONDS);
                } else if (throwable instanceof CompositeException) {
                    CompositeException compositeException = (CompositeException) throwable;
                    //结合rxcache会把异常进行包裹才会返回，需要解析提取
                    for (Throwable innerthrowable : compositeException.getExceptions()) {
                        if (innerthrowable instanceof SocketTimeoutException && ++tryTimes <= maxTryTime) {
                            return Observable.timer(mInterval, TimeUnit.SECONDS);
                        }
                    }
                }
                return Observable.error(throwable);
            }

        });
    }
}
