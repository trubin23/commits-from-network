package com.example.trubin23.commitsfromnetwork.storage.database;

import static com.example.trubin23.commitsfromnetwork.storage.database.UserDao.COLUMN_USER_ID;
import static com.example.trubin23.commitsfromnetwork.storage.database.UserDao.TABLE_USER;

/**
 * Created by Andrey on 17.01.2018.
 */

public class RepoDaoImpl implements RepoDao {

    private static final String TAG = RepoDaoImpl.class.getSimpleName();

    static final String REPO_CREATE_TABLE = "CREATE TABLE " + TABLE_REPO + "("
            + COLUMN_REPO_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_REPO_NAME + " TEXT, "
            + COLUMN_REPO_USER_ID + " INTEGER, "
            + "FOREIGN KEY (" + COLUMN_REPO_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")";

    private DatabaseHelper mDbOpenHelper;

    public RepoDaoImpl(DatabaseHelper dbOpenHelper) {
        mDbOpenHelper = dbOpenHelper;
    }

}
