package com.example.trubin23.commitsfromnetwork.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Andrey on 25.01.2018.
 */

public class AppExecutors {

    private static final int THREAD_COUNT = 2;

    private final Executor mSharedPreferencesThread;

    private final Executor mDbThread;

    private final Executor mNetworkThreads;

    private final Executor mMainThread;

    private AppExecutors(Executor sharedPreferencesThread, Executor dbThread,
                         Executor networkThreads, Executor mainThread) {
        mSharedPreferencesThread = sharedPreferencesThread;
        mDbThread = dbThread;
        mNetworkThreads = networkThreads;
        mMainThread = mainThread;
    }

    public AppExecutors() {
        this(new DiskIOThreadExecutor(), new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),  new MainThreadExecutor());
    }

    public Executor getSharedPreferencesThread() {
        return mSharedPreferencesThread;
    }

    public Executor getDbThread() {
        return mDbThread;
    }

    public Executor getNetworkThreads() {
        return mNetworkThreads;
    }

    public Executor getMainThread() {
        return mMainThread;
    }
}
