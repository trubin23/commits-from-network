package com.example.trubin23.commitsfromnetwork.storage.database;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.trubin23.commitsfromnetwork.storage.model.CommitStorage;

import java.util.List;

/**
 * Created by Andrey on 06.01.2018.
 */

public interface CommitDao {
    String TABLE_COMMIT = "table_commit";

    String COLUMN_COMMIT_SHA = "sha";
    String COLUMN_COMMIT_MESSAGE = "message";
    String COLUMN_COMMIT_DATE = "date";
    String COLUMN_COMMIT_REPO_NAME = "repo_name";

    String[] COLUMNS = {
            COLUMN_COMMIT_SHA,
            COLUMN_COMMIT_MESSAGE,
            COLUMN_COMMIT_DATE,
            COLUMN_COMMIT_REPO_NAME
    };

    void insertCommits(@NonNull final List<CommitStorage> commits, @NonNull String repoName);

    @Nullable
    Cursor getCommits(@NonNull String repoName);
}
