package com.example.trubin23.commitsfromnetwork.data.source.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Andrey on 17.01.2018.
 */

public class OwnerDaoImpl implements OwnerDao {

    private static final String TAG = OwnerDaoImpl.class.getSimpleName();

    static final String OWNER_CREATE_TABLE = "CREATE TABLE " + TABLE_OWNER + "("
            + COLUMN_OWNER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_OWNER_NAME + " TEXT UNIQUE NOT NULL)";

    private DatabaseHelper mDbOpenHelper;

    public OwnerDaoImpl(@NonNull DatabaseHelper dbOpenHelper) {
        mDbOpenHelper = dbOpenHelper;
    }

    @Override
    public void insertOwner(@NonNull String owner) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_OWNER_NAME, owner);

            db.insertWithOnConflict(TABLE_OWNER, null, values, SQLiteDatabase.CONFLICT_IGNORE);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "void insertOwner(@NonNull String owner)", e);
        } finally {
            db.endTransaction();
        }
    }

    @Nullable
    @Override
    public Long getOwnerId(@NonNull String owner) {
        Long ownerId = null;

        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            Cursor cursor = db.query(TABLE_OWNER, new String[]{COLUMN_OWNER_ID},
                    COLUMN_OWNER_NAME + " = ?", new String[]{owner},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_OWNER_ID));
                cursor.close();

                ownerId = id;
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Long getOwnerId(@NonNull String owner)", e);
        } finally {
            db.endTransaction();
        }

        return ownerId;
    }
}
