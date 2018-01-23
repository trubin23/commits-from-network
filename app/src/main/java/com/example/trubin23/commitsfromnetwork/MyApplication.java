package com.example.trubin23.commitsfromnetwork;

import android.app.Application;

import com.example.trubin23.commitsfromnetwork.data.source.CommitsRepository;
import com.example.trubin23.commitsfromnetwork.data.source.database.DatabaseHelper;
import com.example.trubin23.commitsfromnetwork.data.source.preferences.CommitsSharedPreferences;


/**
 * Created by Andrey on 07.01.2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CommitsRepository commitsRepository = CommitsRepository.getInstance(
                CommitsSharedPreferences.getInstance(getApplicationContext()),
                DatabaseHelper.getInstance(getApplicationContext()));
    }
}
