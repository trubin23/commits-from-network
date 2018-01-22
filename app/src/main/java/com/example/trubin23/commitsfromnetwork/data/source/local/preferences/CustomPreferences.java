package com.example.trubin23.commitsfromnetwork.data.source.local.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Andrey on 16.01.2018.
 */

public class CustomPreferences {

    private static final String OWNER_VALUE = "owner_value";
    private static final String REPO_VALUE = "repo_value";

    private static CustomPreferences mCustomPreferences;
    private SharedPreferences mSharedPreferences;

    private CustomPreferences(@NonNull Context context) {
        mSharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    @Nullable
    public static CustomPreferences getInstance(@Nullable Context context) {
        if (mCustomPreferences == null && context != null) {
            mCustomPreferences = new CustomPreferences(context);
        }
        return mCustomPreferences;
    }

    public void putOwner(@NonNull String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(OWNER_VALUE, value);
        editor.apply();
    }

    public void putRepo(@NonNull String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(REPO_VALUE, value);
        editor.apply();
    }

    @NonNull
    public String getOwner() {
        return mSharedPreferences.getString(OWNER_VALUE, "");
    }

    @NonNull
    public String getRepo() {
        return mSharedPreferences.getString(REPO_VALUE, "");
    }
}
