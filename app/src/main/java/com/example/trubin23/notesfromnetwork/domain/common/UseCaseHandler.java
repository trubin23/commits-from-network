package com.example.trubin23.notesfromnetwork.domain.common;

import android.support.annotation.NonNull;

/**
 * Created by Andrey on 03.01.2018.
 */

public class UseCaseHandler {
    private static UseCaseHandler mUseCaseHandler;

    private final UseCaseScheduler mUseCaseScheduler;

    private UseCaseHandler(@NonNull UseCaseScheduler useCaseScheduler) {
        mUseCaseScheduler = useCaseScheduler;
    }

    @NonNull
    public static UseCaseHandler getInstance(){
        if (mUseCaseHandler == null){
            mUseCaseHandler = new UseCaseHandler(new UseCaseThreadPoolScheduler());
        }

        return mUseCaseHandler;
    }

    public <T extends BaseUseCase.RequestValues, R extends BaseUseCase.ResponseValues> void execute(
            @NonNull final BaseUseCase<T, R> useCase, @NonNull T request,
            @NonNull BaseUseCase.UseCaseCallback<R> callback) {
        useCase.setRequest(request);
        useCase.setUseCaseCallback(new UiCallbackWrapper(callback, this));

        mUseCaseScheduler.execute(useCase::run);
    }

    private <R extends BaseUseCase.ResponseValues> void notifyResponse(
            @NonNull R response, @NonNull BaseUseCase.UseCaseCallback<R> callback) {
        mUseCaseScheduler.onSuccess(response, callback);
    }

    private <R extends BaseUseCase.ResponseValues> void notifyError(@NonNull BaseUseCase.UseCaseCallback<R> callback) {
        mUseCaseScheduler.onError(callback);
    }

    private static final class UiCallbackWrapper<R extends BaseUseCase.ResponseValues> implements
            BaseUseCase.UseCaseCallback<R> {
        private final BaseUseCase.UseCaseCallback<R> mCallback;
        private final UseCaseHandler mUseCaseHandler;

        UiCallbackWrapper(@NonNull BaseUseCase.UseCaseCallback<R> callback, @NonNull UseCaseHandler useCaseHandler) {
            mCallback = callback;
            mUseCaseHandler = useCaseHandler;
        }

        @Override
        public void onSuccess(@NonNull R response) {
            mUseCaseHandler.notifyResponse(response, mCallback);
        }

        @Override
        public void onError() {
            mUseCaseHandler.notifyError(mCallback);
        }
    }
}
