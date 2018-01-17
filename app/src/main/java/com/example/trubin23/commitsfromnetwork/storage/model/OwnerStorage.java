package com.example.trubin23.commitsfromnetwork.storage.model;

import android.support.annotation.NonNull;

/**
 * Created by Andrey on 17.01.2018.
 */

public class OwnerStorage {

    private long mId;
    private String mName;

    public OwnerStorage(long id, @NonNull String name) {
        mId = id;
        mName = name;
    }

    public long getId() {
        return mId;
    }
}
