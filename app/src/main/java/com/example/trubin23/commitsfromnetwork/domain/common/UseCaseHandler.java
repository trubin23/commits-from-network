package com.example.trubin23.commitsfromnetwork.domain.common;

import android.support.annotation.NonNull;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Andrey on 03.01.2018.
 */

public class UseCaseHandler {
    private static UseCaseHandler mUseCaseHandler;

    private static ThreadPoolExecutor mThreadPoolExecutor;

    @NonNull
    public static UseCaseHandler getInstance(){
        if (mUseCaseHandler == null){
            final int POOL_SIZE = 2;
            final int MAX_POOL_SIZE = 4;
            final int TIMEOUT = 30;

            mThreadPoolExecutor = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, TIMEOUT, TimeUnit.SECONDS,
                                                         new ArrayBlockingQueue<>(POOL_SIZE));

            mUseCaseHandler = new UseCaseHandler();
        }

        return mUseCaseHandler;
    }

    public void execute(
            @NonNull final BaseUseCase useCase, @NonNull BaseUseCase.RequestValues request,
            @NonNull BaseUseCase.UseCaseCallback callback) {
        useCase.setRequest(request);
        useCase.setUseCaseCallback(callback);

        mThreadPoolExecutor.execute(useCase::run);
    }
}
