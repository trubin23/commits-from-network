package com.example.trubin23.notesfromnetwork.domain.common;

import android.support.annotation.NonNull;

/**
 * Created by Andrey on 02.01.2018.
 */

interface UseCaseScheduler {

    void execute(Runnable runnable);

    <R extends BaseUseCase.ResponseValues> void onSuccess(
            @NonNull final R response, @NonNull final BaseUseCase.UseCaseCallback<R> useCaseCallback);

    <R extends BaseUseCase.ResponseValues> void onError(@NonNull final BaseUseCase.UseCaseCallback<R> useCaseCallback);
}