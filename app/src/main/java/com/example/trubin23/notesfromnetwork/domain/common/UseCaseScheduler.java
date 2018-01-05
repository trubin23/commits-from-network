package com.example.trubin23.notesfromnetwork.domain.common;

import android.support.annotation.NonNull;

/**
 * Created by Andrey on 02.01.2018.
 */

interface UseCaseScheduler {

    void execute(Runnable runnable);

    void onSuccess(@NonNull final BaseUseCase.ResponseValues response,
            @NonNull final BaseUseCase.UseCaseCallback useCaseCallback);

    void onError(@NonNull final BaseUseCase.UseCaseCallback useCaseCallback);
}