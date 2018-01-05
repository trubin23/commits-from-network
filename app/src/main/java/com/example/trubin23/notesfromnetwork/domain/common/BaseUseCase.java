package com.example.trubin23.notesfromnetwork.domain.common;

import android.support.annotation.NonNull;

/**
 * Created by Andrey on 02.01.2018.
 */

public abstract class BaseUseCase<T extends BaseUseCase.ResponseValues> {

    private BaseUseCase.RequestValues mRequestValues;
    private UseCaseCallback<T> mUseCaseCallback;

    protected abstract void executeUseCase(@NonNull RequestValues requestValues);

    void run() {
        executeUseCase(mRequestValues);
    }

    void setRequest(@NonNull BaseUseCase.RequestValues request) {
        mRequestValues = request;
    }

    void setUseCaseCallback(@NonNull UseCaseCallback<T> useCaseCallback) {
        mUseCaseCallback = useCaseCallback;
    }

    @NonNull
    protected UseCaseCallback<T> getUseCaseCallback(){
        return mUseCaseCallback;
    }

    public interface RequestValues {
    }

    public interface ResponseValues {
    }

    public interface UseCaseCallback<R extends ResponseValues> {
        void onSuccess(@NonNull R response);
        void onError();
    }
}
