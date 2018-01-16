package com.example.trubin23.commitsfromnetwork.storage.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Andrey on 16.01.2018.
 */

public class CustomPreferences {

    private static CustomPreferences mCustomPreferences;
    private SharedPreferences mSharedPreferences;

    private CustomPreferences(@NonNull Context context) {
        mSharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static CustomPreferences getInstance(@NonNull Context context) {
        if (mCustomPreferences == null) {
            mCustomPreferences = new CustomPreferences(context);
        }
        return mCustomPreferences;
    }

    public void putString(@NonNull String key, @NonNull String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Nullable
    public String getString(String key) {
        return mSharedPreferences.getString(key, null);
    }
}
