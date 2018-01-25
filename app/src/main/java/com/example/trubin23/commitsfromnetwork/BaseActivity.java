package com.example.trubin23.commitsfromnetwork;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Andrey on 31.12.2017.
 */

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements BaseView {

    @Nullable
    private BasePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null){
            mPresenter.bind(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null){
            mPresenter.unbind();
        }
    }

    protected void bindPresenterToView(@NonNull BasePresenter presenter){
        mPresenter = presenter;
    }
}
