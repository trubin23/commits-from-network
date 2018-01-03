package com.example.trubin23.notesfromnetwork.domain.common;

/**
 * Created by Andrey on 03.01.2018.
 */

public class UseCaseHandler {
    private static UseCaseHandler mUseCaseHandler;

    private final UseCaseScheduler mUseCaseScheduler;

    private UseCaseHandler(UseCaseScheduler useCaseScheduler) {
        mUseCaseScheduler = useCaseScheduler;
    }

    public static UseCaseHandler getInstance(){
        if (mUseCaseHandler == null){
            mUseCaseHandler = new UseCaseHandler(new UseCaseThreadPoolScheduler());
        }

        return mUseCaseHandler;
    }

    public <T extends BaseUseCase.RequestValues, R extends BaseUseCase.ResponseValues> void execute(
            final BaseUseCase<T, R> useCase, T request, BaseUseCase.UseCaseCallback<R> callback) {
        useCase.setRequest(request);
        useCase.setUseCaseCallback(new UiCallbackWrapper(callback, this));
    }

    private <R extends BaseUseCase.ResponseValues> void notifyResponse(R response, BaseUseCase.UseCaseCallback<R> callback) {
        mUseCaseScheduler.onSuccess(response, callback);
    }

    private <R extends BaseUseCase.ResponseValues> void notifyError(BaseUseCase.UseCaseCallback<R> callback) {
        mUseCaseScheduler.onError(callback);
    }

    private static final class UiCallbackWrapper<R extends BaseUseCase.ResponseValues> implements
            BaseUseCase.UseCaseCallback<R> {
        private final BaseUseCase.UseCaseCallback<R> mCallback;
        private final UseCaseHandler mUseCaseHandler;


        public  UiCallbackWrapper(BaseUseCase.UseCaseCallback<R> callback, UseCaseHandler useCaseHandler) {
            mCallback = callback;
            mUseCaseHandler = useCaseHandler;
        }

        @Override
        public void onSuccess(R response) {
            mUseCaseHandler.notifyResponse(response, mCallback);
        }

        @Override
        public void onError() {
            mUseCaseHandler.notifyError(mCallback);
        }
    }
}
