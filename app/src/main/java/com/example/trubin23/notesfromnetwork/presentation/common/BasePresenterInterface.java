package com.example.trubin23.notesfromnetwork.presentation.common;

import android.support.annotation.NonNull;

/**
 * Created by Andrey on 31.12.2017.
 */

public interface BasePresenterInterface<T> {

    void bind(@NonNull T view);

    void unbind();

    @NonNull
    T getView();
}
