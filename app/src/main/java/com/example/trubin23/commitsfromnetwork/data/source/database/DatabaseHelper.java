package com.example.trubin23.commitsfromnetwork.data.source.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by Andrey on 06.01.2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DB_NAME = "commits.db";
    private static final int DB_VERSION = 1;

    private static volatile DatabaseHelper INSTANCE;

    private DatabaseHelper(@NonNull Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @NonNull
    public static DatabaseHelper getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DatabaseHelper(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(OwnerDaoImpl.OWNER_CREATE_TABLE);
            db.execSQL(RepoDaoImpl.REPO_CREATE_TABLE);
            db.execSQL(CommitDaoImpl.COMMIT_CREATE_TABLE);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "create tables", e);
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
