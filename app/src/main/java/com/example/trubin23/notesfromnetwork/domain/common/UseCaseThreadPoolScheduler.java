package com.example.trubin23.notesfromnetwork.domain.common;

import android.os.Handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Andrey on 02.01.2018.
 */

public class UseCaseThreadPoolScheduler implements UseCaseScheduler {

    private ThreadPoolExecutor mThreadPoolExecutor;

    private final Handler mUiHandler;

    public UseCaseThreadPoolScheduler() {
        final int POOL_SIZE = 2;
        final int MAX_POOL_SIZE = 4;
        final int TIMEOUT = 30;

        mThreadPoolExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT, TimeUnit.SECONDS,
                                                     new ArrayBlockingQueue<>(POOL_SIZE));
        mUiHandler = new Handler();
    }

    @Override
    public void execute(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    @Override
    public <R extends BaseUseCase.ResponseValues> void onSuccess(R response, BaseUseCase.UseCaseCallback<R> useCaseCallback) {
        mUiHandler.post(() -> useCaseCallback.onSuccess(response));
    }

    @Override
    public <R extends BaseUseCase.ResponseValues> void onError(BaseUseCase.UseCaseCallback<R> useCaseCallback) {
        mUiHandler.post(useCaseCallback::onError);
    }
}
