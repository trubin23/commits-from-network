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

    public void execute(
            @NonNull final BaseUseCase useCase, @NonNull BaseUseCase.RequestValues request,
            @NonNull BaseUseCase.UseCaseCallback callback) {
        useCase.setRequest(request);
        useCase.setUseCaseCallback(new UiCallbackWrapper(callback, this));

        mUseCaseScheduler.execute(useCase::run);
    }

    private void notifyResponse(
            @NonNull BaseUseCase.ResponseValues response,
            @NonNull BaseUseCase.UseCaseCallback callback) {
        mUseCaseScheduler.onSuccess(response, callback);
    }

    private void notifyError(@NonNull BaseUseCase.UseCaseCallback callback) {
        mUseCaseScheduler.onError(callback);
    }

    private static final class UiCallbackWrapper implements
            BaseUseCase.UseCaseCallback {
        private final BaseUseCase.UseCaseCallback mCallback;
        private final UseCaseHandler mUseCaseHandler;

        UiCallbackWrapper(@NonNull BaseUseCase.UseCaseCallback callback,
                          @NonNull UseCaseHandler useCaseHandler) {
            mCallback = callback;
            mUseCaseHandler = useCaseHandler;
        }

        @Override
        public void onSuccess(@NonNull BaseUseCase.ResponseValues response) {
            mUseCaseHandler.notifyResponse(response, mCallback);
        }

        @Override
        public void onError() {
            mUseCaseHandler.notifyError(mCallback);
        }
    }
}
