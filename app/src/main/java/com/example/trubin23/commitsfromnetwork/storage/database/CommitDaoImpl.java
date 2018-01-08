package com.example.trubin23.commitsfromnetwork.storage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.trubin23.commitsfromnetwork.storage.model.CommitStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 06.01.2018.
 */

public class CommitDaoImpl implements CommitDao {

    private static final String TAG = CommitDaoImpl.class.getSimpleName();

    static final String COMMIT_CREATE_TABLE = "CREATE TABLE " + TABLE_COMMIT + "("
            + COLUMN_COMMIT_SHA + " TEXT PRIMARY KEY, "
            + COLUMN_COMMIT_MESSAGE + " TEXT, "
            + COLUMN_COMMIT_DATE + " TEXT)";

    private DatabaseHelper mDbOpenHelper;

    public CommitDaoImpl(DatabaseHelper dbOpenHelper) {
        mDbOpenHelper = dbOpenHelper;
    }

    @Override
    public void insertCommits(@NonNull List<CommitStorage> commits) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (CommitStorage commit : commits){
                ContentValues values = new ContentValues();
                values.put(COLUMN_COMMIT_SHA, commit.getSha());
                values.put(COLUMN_COMMIT_MESSAGE, commit.getCommitDescription().getMessage());
                values.put(COLUMN_COMMIT_DATE, commit.getCommitDescription().getAuthor().getDate());

                db.insert(TABLE_COMMIT, null, values);
            }

            db.setTransactionSuccessful();
        } catch(Exception e){
            Log.e(TAG, "public void insertCommits(@NonNull List<CommitStorage> commits)", e);
        } finally {
            db.endTransaction();
        }
    }

    @Nullable
    @Override
    public Cursor getCommits() {
        Cursor cursor = null;

        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            cursor = db.query(TABLE_COMMIT, COLUMNS, null,
                              null, null, null, null);

            db.setTransactionSuccessful();
        } catch(Exception e){
            Log.e(TAG, "public Cursor getCommits()", e);
        } finally {
            db.endTransaction();
        }

        return cursor;
    }
}
