package com.example.trubin23.notesfromnetwork.domain.common;

import android.support.annotation.NonNull;

/**
 * Created by Andrey on 02.01.2018.
 */

public abstract class BaseUseCase {

    private BaseUseCase.RequestValues mRequestValues;
    private UseCaseCallback mUseCaseCallback;

    protected abstract void executeUseCase(@NonNull RequestValues requestValues);

    void run() {
        executeUseCase(mRequestValues);
    }

    void setRequest(@NonNull BaseUseCase.RequestValues request) {
        mRequestValues = request;
    }

    void setUseCaseCallback(@NonNull UseCaseCallback useCaseCallback) {
        mUseCaseCallback = useCaseCallback;
    }

    @NonNull
    protected UseCaseCallback getUseCaseCallback(){
        return mUseCaseCallback;
    }

    public interface RequestValues {
    }

    public interface ResponseValues {
    }

    public interface UseCaseCallback {
        void onSuccess(@NonNull BaseUseCase.ResponseValues response);
        void onError();
    }
}
