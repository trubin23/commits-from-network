package com.example.trubin23.commitsfromnetwork.util;

import java.util.concurrent.Executor;

/**
 * Created by Andrey on 25.01.2018.
 */

public class AppExecutors {

    private final Executor mSharedPreferencesThread;

    private final Executor mDbThread;

    private final Executor mMainThread;

    private AppExecutors(Executor sharedPreferencesThread,
                         Executor dbThread, Executor mainThread) {
        mSharedPreferencesThread = sharedPreferencesThread;
        mDbThread = dbThread;
        mMainThread = mainThread;
    }

    public AppExecutors() {
        this(new DiskIOThreadExecutor(), new DiskIOThreadExecutor(),
                new MainThreadExecutor());
    }

    public Executor getSharedPreferencesThread() {
        return mSharedPreferencesThread;
    }

    public Executor getDbThread() {
        return mDbThread;
    }

    public Executor getMainThread() {
        return mMainThread;
    }
}
