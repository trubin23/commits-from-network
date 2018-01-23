package com.example.trubin23.commitsfromnetwork.data.source.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Andrey on 16.01.2018.
 */

public interface RepoDao {
    String TABLE_REPO = "table_repo";

    String COLUMN_REPO_ID = "id";
    String COLUMN_REPO_NAME = "name";
    String COLUMN_REPO_USER_ID = "owner_id";

    void insertRepo(@NonNull String repo, @NonNull Long ownerId);

    @Nullable
    Long getRepo(@NonNull String repo, @NonNull Long ownerId);
}
