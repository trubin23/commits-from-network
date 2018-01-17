package com.example.trubin23.commitsfromnetwork.storage.model;

/**
 * Created by Andrey on 17.01.2018.
 */

public class OwnerStorage {

    private long mId;
    private String mName;

    public OwnerStorage(long id, String name) {
        mId = id;
        mName = name;
    }

    public long getId() {
        return mId;
    }
}
