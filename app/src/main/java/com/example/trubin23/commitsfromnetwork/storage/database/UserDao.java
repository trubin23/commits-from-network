package com.example.trubin23.commitsfromnetwork.storage.database;

/**
 * Created by Andrey on 16.01.2018.
 */

public interface UserDao {
    String TABLE_USER = "table_user";

    String COLUMN_USER_ID = "id";
    String COLUMN_USER_NAME = "date";

    String[] COLUMNS = {
            COLUMN_USER_ID,
            COLUMN_USER_NAME
    };
}
