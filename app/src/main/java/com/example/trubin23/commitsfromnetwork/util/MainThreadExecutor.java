package com.example.trubin23.commitsfromnetwork.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by Andrey on 25.01.2018.
 */

public class MainThreadExecutor implements Executor{

    private final Handler mMainThreadHandler;

    MainThreadExecutor() {
        mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mMainThreadHandler.post(command);
    }
}
