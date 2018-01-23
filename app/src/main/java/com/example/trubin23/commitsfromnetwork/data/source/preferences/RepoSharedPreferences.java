package com.example.trubin23.commitsfromnetwork.data.source.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Andrey on 16.01.2018.
 */

public class RepoSharedPreferences implements RepoPreferences {

    private static RepoSharedPreferences mCommitsSharedPreferences;
    private SharedPreferences mSharedPreferences;

    private RepoSharedPreferences(@NonNull Context context) {
        mSharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    @Nullable
    public static RepoSharedPreferences getInstance(@Nullable Context context) {
        if (mCommitsSharedPreferences == null && context != null) {
            mCommitsSharedPreferences = new RepoSharedPreferences(context);
        }
        return mCommitsSharedPreferences;
    }

    @Override
    public void putString(@NonNull String key, @NonNull String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Nullable
    @Override
    public String getString(@NonNull String key) {
        return mSharedPreferences.getString(key, null);
    }
}
