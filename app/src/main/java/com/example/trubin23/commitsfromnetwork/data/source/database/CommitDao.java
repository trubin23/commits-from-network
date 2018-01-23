package com.example.trubin23.commitsfromnetwork.data.source.database;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.trubin23.commitsfromnetwork.data.Commit;

import java.util.List;

/**
 * Created by Andrey on 06.01.2018.
 */

public interface CommitDao {
    String TABLE_COMMIT = "table_commit";

    String COLUMN_COMMIT_SHA = "sha";
    String COLUMN_COMMIT_MESSAGE = "message";
    String COLUMN_COMMIT_DATE = "commit_date";
    String COLUMN_COMMIT_REPO_ID = "repo_id";

    String[] COLUMNS_COMMIT = {
            COLUMN_COMMIT_SHA,
            COLUMN_COMMIT_MESSAGE,
            COLUMN_COMMIT_DATE
    };

    void insertCommits(@NonNull final List<Commit> commits, @NonNull Long repoId);

    @Nullable
    Cursor getCommits(@NonNull Long repoId);
}
