package com.example.trubin23.commitsfromnetwork;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.trubin23.commitsfromnetwork.data.source.CommitsRepository;
import com.example.trubin23.commitsfromnetwork.data.source.database.DatabaseHelper;
import com.example.trubin23.commitsfromnetwork.data.source.preferences.CommitsSharedPreferences;

/**
 * Created by Andrey on 24.01.2018.
 */

public class Injection {

    public static CommitsRepository provideCommitsRepository(@NonNull Context context) {
        return CommitsRepository.getInstance(
                CommitsSharedPreferences.getInstance(context),
                DatabaseHelper.getInstance(context));
    }
}
