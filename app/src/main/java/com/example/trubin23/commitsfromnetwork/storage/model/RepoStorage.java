package com.example.trubin23.commitsfromnetwork.storage.model;

/**
 * Created by Andrey on 17.01.2018.
 */

public class RepoStorage {

    private Long mId;
    private String mName;
    private OwnerStorage mOwnerStorage;

    public RepoStorage(Long id, String name, OwnerStorage ownerStorage) {
        mId = id;
        mName = name;
        mOwnerStorage = ownerStorage;
    }
}
