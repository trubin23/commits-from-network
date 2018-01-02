package com.example.trubin23.notesfromnetwork.domain.common;

/**
 * Created by Andrey on 02.01.2018.
 */

public abstract class BaseUseCase<Q extends BaseUseCase.RequestValues, P extends BaseUseCase.ResponseValues> {

    protected abstract void executeUseCase(Q requestValues);

    public interface RequestValues {
    }

    public interface ResponseValues {
    }
}
