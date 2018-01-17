package com.example.trubin23.commitsfromnetwork.storage.model;

import android.support.annotation.NonNull;

/**
 * Created by Andrey on 17.01.2018.
 */

public class RepoStorage {

    private Long mId;
    private String mName;
    private OwnerStorage mOwnerStorage;

    public RepoStorage(@NonNull Long id, @NonNull String name, @NonNull OwnerStorage ownerStorage) {
        mId = id;
        mName = name;
        mOwnerStorage = ownerStorage;
    }

    @NonNull
    public Long getId() {
        return mId;
    }
}
