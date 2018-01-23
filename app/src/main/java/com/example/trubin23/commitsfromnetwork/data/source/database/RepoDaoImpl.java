package com.example.trubin23.commitsfromnetwork.data.source.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.example.trubin23.commitsfromnetwork.data.source.database.OwnerDao.COLUMN_OWNER_ID;
import static com.example.trubin23.commitsfromnetwork.data.source.database.OwnerDao.TABLE_OWNER;

/**
 * Created by Andrey on 17.01.2018.
 */

public class RepoDaoImpl implements RepoDao {

    private static final String TAG = RepoDaoImpl.class.getSimpleName();

    static final String REPO_CREATE_TABLE = "CREATE TABLE " + TABLE_REPO + "("
            + COLUMN_REPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_REPO_NAME + " TEXT, "
            + COLUMN_REPO_USER_ID + " INTEGER, "
            + "FOREIGN KEY (" + COLUMN_REPO_USER_ID + ") REFERENCES " + TABLE_OWNER + "(" + COLUMN_OWNER_ID + "))";

    private DatabaseHelper mDbOpenHelper;

    public RepoDaoImpl(@NonNull DatabaseHelper dbOpenHelper) {
        mDbOpenHelper = dbOpenHelper;
    }

    @Override
    public void insertRepo(@NonNull String repo, @NonNull Long ownerId) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_REPO_NAME, repo);
            values.put(COLUMN_REPO_USER_ID, ownerId);

            db.insertWithOnConflict(TABLE_REPO, null, values, SQLiteDatabase.CONFLICT_IGNORE);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "void insertRepo(@NonNull String repo, @NonNull ownerId ownerId)", e);
        } finally {
            db.endTransaction();
        }
    }

    @Nullable
    @Override
    public Long getRepo(@NonNull String repo, @NonNull Long ownerId) {
        Long repoId = null;

        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            Cursor cursor = db.query(TABLE_REPO, new String[]{COLUMN_REPO_ID},
                    COLUMN_REPO_NAME + " = ? AND " + COLUMN_REPO_USER_ID + " = ?",
                    new String[]{repo, String.valueOf(ownerId)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_REPO_ID));
                cursor.close();

                repoId = id;
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Long getRepo(@NonNull String repo, @NonNull OwnerStorage ownerStorage)", e);
        } finally {
            db.endTransaction();
        }

        return repoId;
    }

}
