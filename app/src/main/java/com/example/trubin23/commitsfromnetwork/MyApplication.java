package com.example.trubin23.commitsfromnetwork;

import android.app.Application;
import com.example.trubin23.commitsfromnetwork.storage.database.DatabaseHelper;

/**
 * Created by Andrey on 07.01.2018.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseHelper.getInstance(getApplicationContext());
    }
}
