package com.example.trubin23.commitsfromnetwork.storage.database;

/**
 * Created by Andrey on 17.01.2018.
 */

public class UserDaoImpl implements UserDao {

    private static final String TAG = UserDaoImpl.class.getSimpleName();

    static final String USER_CREATE_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_USER_NAME + " TEXT";

    private DatabaseHelper mDbOpenHelper;

    public UserDaoImpl(DatabaseHelper dbOpenHelper) {
        mDbOpenHelper = dbOpenHelper;
    }

}
