package com.example.trubin23.commitsfromnetwork.storage.database;

/**
 * Created by Andrey on 16.01.2018.
 */

public interface RepoDao {
    String TABLE_REPO = "table_repo";

    String COLUMN_REPO_ID = "id";
    String COLUMN_REPO_NAME = "name";
    String COLUMN_REPO_USER_ID = "user_id";

    String[] COLUMNS = {
            COLUMN_REPO_ID,
            COLUMN_REPO_NAME,
            COLUMN_REPO_USER_ID
    };
}
