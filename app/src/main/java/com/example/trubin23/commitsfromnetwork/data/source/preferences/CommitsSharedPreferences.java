package com.example.trubin23.commitsfromnetwork.data.source.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Andrey on 16.01.2018.
 */

public class CommitsSharedPreferences {

    private static CommitsSharedPreferences INSTANCE;
    private SharedPreferences mSharedPreferences;

    private CommitsSharedPreferences(@NonNull Context context) {
        Context appContext = context.getApplicationContext();

        mSharedPreferences = appContext.getSharedPreferences(appContext.getPackageName(),
                Context.MODE_PRIVATE);
    }

    @NonNull
    public static CommitsSharedPreferences getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (CommitsSharedPreferences.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CommitsSharedPreferences(context);
                }
            }
        }
        return INSTANCE;
    }

    public void putString(@NonNull String key, @NonNull String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Nullable
    public String getString(@NonNull String key) {
        return mSharedPreferences.getString(key, null);
    }
}
