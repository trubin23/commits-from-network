package com.example.trubin23.commitsfromnetwork.data.source.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.trubin23.commitsfromnetwork.data.Commit;

import java.util.List;

import static com.example.trubin23.commitsfromnetwork.data.source.database.RepoDao.COLUMN_REPO_ID;
import static com.example.trubin23.commitsfromnetwork.data.source.database.RepoDao.TABLE_REPO;

/**
 * Created by Andrey on 06.01.2018.
 */

public class CommitDaoImpl implements CommitDao {

    private static final String TAG = CommitDaoImpl.class.getSimpleName();

    static final String COMMIT_CREATE_TABLE = "CREATE TABLE " + TABLE_COMMIT + "("
            + COLUMN_COMMIT_SHA + " TEXT PRIMARY KEY, "
            + COLUMN_COMMIT_MESSAGE + " TEXT, "
            + COLUMN_COMMIT_DATE + " TEXT, "
            + COLUMN_COMMIT_REPO_ID + " INTEGER, "
            + "FOREIGN KEY (" + COLUMN_COMMIT_REPO_ID + ") REFERENCES " + TABLE_REPO + "(" + COLUMN_REPO_ID + "))";

    private DatabaseHelper mDbOpenHelper;

    public CommitDaoImpl(@NonNull DatabaseHelper dbOpenHelper) {
        mDbOpenHelper = dbOpenHelper;
    }

    @Override
    public void insertCommits(@NonNull List<Commit> commits, @NonNull Long repoId) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Commit commit : commits) {

                ContentValues values = new ContentValues();
                values.put(COLUMN_COMMIT_SHA, commit.getSha());
                values.put(COLUMN_COMMIT_MESSAGE, commit.getMessage());
                values.put(COLUMN_COMMIT_DATE, commit.getDate());
                values.put(COLUMN_COMMIT_REPO_ID, repoId);

                db.insertWithOnConflict(TABLE_COMMIT, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "void insertCommits(@NonNull List<Commit> commits, @NonNull Long repoId)", e);
        } finally {
            db.endTransaction();
        }
    }

    @Nullable
    @Override
    public Cursor getCommits(@NonNull Long repoId) {
        Cursor cursor = null;

        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            String whereClause = COLUMN_COMMIT_REPO_ID + " = ?";
            String[] whereArgs = new String[]{String.valueOf(repoId)};

            cursor = db.query(TABLE_COMMIT, COLUMNS_COMMIT, whereClause, whereArgs,
                    null, null, null);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Cursor getCommits(@NonNull Long repoId)", e);
        } finally {
            db.endTransaction();
        }

        return cursor;
    }
}
