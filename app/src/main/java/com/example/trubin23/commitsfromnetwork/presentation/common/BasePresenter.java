package com.example.trubin23.commitsfromnetwork.presentation.common;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.trubin23.commitsfromnetwork.domain.common.UseCaseHandler;

/**
 * Created by Andrey on 31.12.2017.
 */

public class BasePresenter<T extends BaseView> implements BasePresenterInterface<T> {

    private static final String TAG = BasePresenter.class.getSimpleName();

    private T mView;

    protected UseCaseHandler mUseCaseHandler;

    public BasePresenter(@NonNull UseCaseHandler useCaseHandler){
        mUseCaseHandler = useCaseHandler;
    }

    @Override
    public void bind(@NonNull T view) {
        mView = view;
        Log.d(TAG, "binding presenter [" + this + "] with view [" + mView + "]");
    }

    @Override
    public void unbind() {
        Log.d(TAG, "unbinding presenter [" + this + "] with view [" + mView + "]");
        mView = null;
    }

    @NonNull
    @Override
    public T getView() {
        return mView;
    }
}
