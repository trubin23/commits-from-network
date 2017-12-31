package com.example.trubin23.notesfromnetwork.presentation.common;

/**
 * Created by Andrey on 31.12.2017.
 */

public interface BasePresenterInterface<T> {

    void bind(T view);

    void unbind();

    T getView();
}
