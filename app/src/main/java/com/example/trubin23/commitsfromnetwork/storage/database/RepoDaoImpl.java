package com.example.trubin23.commitsfromnetwork.storage.database;

import android.support.annotation.NonNull;

import static com.example.trubin23.commitsfromnetwork.storage.database.OwnerDao.COLUMN_OWNER_ID;
import static com.example.trubin23.commitsfromnetwork.storage.database.OwnerDao.TABLE_OWNER;

/**
 * Created by Andrey on 17.01.2018.
 */

public class RepoDaoImpl implements RepoDao {

    static final String REPO_CREATE_TABLE = "CREATE TABLE " + TABLE_REPO + "("
            + COLUMN_REPO_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_REPO_NAME + " TEXT, "
            + COLUMN_REPO_USER_ID + " INTEGER, "
            + "FOREIGN KEY (" + COLUMN_REPO_USER_ID + ") REFERENCES " + TABLE_OWNER + "(" + COLUMN_OWNER_ID + "))";

    private DatabaseHelper mDbOpenHelper;

    public RepoDaoImpl(@NonNull DatabaseHelper dbOpenHelper) {
        mDbOpenHelper = dbOpenHelper;
    }

}
