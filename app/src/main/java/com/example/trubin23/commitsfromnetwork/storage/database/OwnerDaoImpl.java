package com.example.trubin23.commitsfromnetwork.storage.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.trubin23.commitsfromnetwork.storage.model.OwnerStorage;

/**
 * Created by Andrey on 17.01.2018.
 */

public class OwnerDaoImpl implements OwnerDao {

    private static final String TAG = OwnerDaoImpl.class.getSimpleName();

    static final String OWNER_CREATE_TABLE = "CREATE TABLE " + TABLE_OWNER + "("
            + COLUMN_OWNER_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_OWNER_NAME + " TEXT)";

    private DatabaseHelper mDbOpenHelper;

    public OwnerDaoImpl(@NonNull DatabaseHelper dbOpenHelper) {
        mDbOpenHelper = dbOpenHelper;
    }

    @NonNull
    @Override
    public OwnerStorage insertOwner(@NonNull String owner) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(COLUMN_OWNER_NAME, owner);

            db.insertWithOnConflict(TABLE_OWNER, null, values, SQLiteDatabase.CONFLICT_IGNORE);

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "public void insertOwner(@NonNull String owner)", e);
        } finally {
            db.endTransaction();
        }
        return null;
    }
}
