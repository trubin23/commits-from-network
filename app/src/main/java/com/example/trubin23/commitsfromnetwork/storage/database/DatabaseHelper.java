package com.example.trubin23.commitsfromnetwork.storage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Andrey on 06.01.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DB_NAME = "commits.db";
    private static final int DB_VERSION = 1;

    private static DatabaseHelper mDatabaseHelper;

    private DatabaseHelper(@NonNull Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Nullable
    public static DatabaseHelper getInstance(@Nullable Context context) {
        if (mDatabaseHelper == null && context != null){
            mDatabaseHelper = new DatabaseHelper(context);
        }
        return mDatabaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(CommitDaoImpl.COMMIT_CREATE_TABLE);
            db.setTransactionSuccessful();
        } catch (Exception e){
            Log.e(TAG, "create table " + CommitDao.TABLE_COMMIT, e);
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
