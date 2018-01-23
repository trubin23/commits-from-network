package com.example.trubin23.commitsfromnetwork.data.source.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Andrey on 16.01.2018.
 */

public class CommitsSharedPreferences {

    public static final String OWNER_VALUE = "owner_value";
    public static final String REPO_VALUE = "repo_value";

    private static CommitsSharedPreferences mCommitsSharedPreferences;
    private SharedPreferences mSharedPreferences;

    private CommitsSharedPreferences(@NonNull Context context) {
        Context appContext = context.getApplicationContext();

        mSharedPreferences = appContext.getSharedPreferences(appContext.getPackageName(),
                Context.MODE_PRIVATE);
    }

    @Nullable
    public static CommitsSharedPreferences getInstance(@Nullable Context context) {
        if (mCommitsSharedPreferences == null && context != null) {
            mCommitsSharedPreferences = new CommitsSharedPreferences(context);
        }
        return mCommitsSharedPreferences;
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
