package com.example.trubin23.notesfromnetwork.domain.common;

/**
 * Created by Andrey on 02.01.2018.
 */

interface UseCaseScheduler {

    void execute(Runnable runnable);

    <R extends BaseUseCase.ResponseValues> void onSuccess(final R response,
                                                          final BaseUseCase.UseCaseCallback<R> useCaseCallback);

    <R extends BaseUseCase.ResponseValues> void onError(final BaseUseCase.UseCaseCallback<R> useCaseCallback);
}