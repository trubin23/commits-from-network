package com.example.trubin23.commitsfromnetwork.storage.model;

import android.support.annotation.NonNull;

/**
 * Created by Andrey on 17.01.2018.
 */

public class RepoStorage {

    private Long mId;
    private String mName;
    private Long mOwnerId;

    public RepoStorage(@NonNull Long id, @NonNull String name, @NonNull Long ownerId) {
        mId = id;
        mName = name;
        mOwnerId = ownerId;
    }

    @NonNull
    public Long getId() {
        return mId;
    }
}
