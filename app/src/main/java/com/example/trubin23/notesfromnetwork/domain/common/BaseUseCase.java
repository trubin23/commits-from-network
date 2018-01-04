package com.example.trubin23.notesfromnetwork.domain.common;

import android.support.annotation.NonNull;

/**
 * Created by Andrey on 02.01.2018.
 */

public abstract class BaseUseCase<Q extends BaseUseCase.RequestValues, P extends BaseUseCase.ResponseValues> {

    private Q mRequestValues;
    private UseCaseCallback<P> mUseCaseCallback;

    protected abstract void executeUseCase(@NonNull Q requestValues);

    <T extends RequestValues> void setRequest(@NonNull Q request) {
        mRequestValues = request;
    }

    void setUseCaseCallback(@NonNull UseCaseCallback<P> useCaseCallback) {
        mUseCaseCallback = useCaseCallback;
    }

    @NonNull
    protected UseCaseCallback<P> getUseCaseCallback(){
        return mUseCaseCallback;
    }

    void run() {
        executeUseCase(mRequestValues);
    }

    public interface RequestValues {
    }

    public interface ResponseValues {
    }

    public interface UseCaseCallback<R> {
        void onSuccess(@NonNull R response);
        void onError();
    }
}
