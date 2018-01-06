package com.example.trubin23.notesfromnetwork.storage.database;

import android.support.annotation.NonNull;
import com.example.trubin23.notesfromnetwork.storage.model.NoteStorage;

import java.util.List;

/**
 * Created by Andrey on 06.01.2018.
 */

public interface CommitDao {
    String TABLE_COMMIT = "commit";

    String COLUMN_COMMIT_SHA = "sha";
    String COLUMN_COMMIT_MESSAGE = "message";
    String COLUMN_COMMIT_DATE = "date";

    String[] COLUMNS = {
            COLUMN_COMMIT_SHA,
            COLUMN_COMMIT_MESSAGE,
            COLUMN_COMMIT_DATE
    };

    void insertCommits(@NonNull final List<NoteStorage> commits);
}
