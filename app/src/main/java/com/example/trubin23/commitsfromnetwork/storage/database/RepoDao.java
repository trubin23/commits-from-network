package com.example.trubin23.commitsfromnetwork.storage.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.trubin23.commitsfromnetwork.storage.model.OwnerStorage;
import com.example.trubin23.commitsfromnetwork.storage.model.RepoStorage;

/**
 * Created by Andrey on 16.01.2018.
 */

public interface RepoDao {
    String TABLE_REPO = "table_repo";

    String COLUMN_REPO_ID = "id";
    String COLUMN_REPO_NAME = "name";
    String COLUMN_REPO_USER_ID = "owner_id";

    void insertRepo(@NonNull String repo, @NonNull OwnerStorage ownerStorage);

    @Nullable
    RepoStorage getRepo(@NonNull String repo, @NonNull OwnerStorage ownerStorage);
}
