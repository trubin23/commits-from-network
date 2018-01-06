package com.example.trubin23.notesfromnetwork.storage.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.trubin23.notesfromnetwork.storage.model.NoteStorage;

import java.util.List;

/**
 * Created by Andrey on 06.01.2018.
 */

public class CommitDaoImpl implements CommitDao {

    private static final String TAG = CommitDaoImpl.class.getSimpleName();

    static final String COMMIT_CREATE_TABLE = "create table " + TABLE_COMMIT + "("
            + COLUMN_COMMIT_SHA + " text primary key,"
            + COLUMN_COMMIT_MESSAGE + " text,"
            + COLUMN_COMMIT_DATE + " text)";

    private DatabaseHelper mDbOpenHelper;

    public CommitDaoImpl(DatabaseHelper dbOpenHelper) {
        mDbOpenHelper = dbOpenHelper;
    }

    @Override
    public void insertCommits(@NonNull List<NoteStorage> commits) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (NoteStorage commit : commits){
                ContentValues values = new ContentValues();
                values.put(COLUMN_COMMIT_SHA, commit.getSha());
                values.put(COLUMN_COMMIT_MESSAGE, commit.getCommit().getMessage());
                values.put(COLUMN_COMMIT_DATE, commit.getCommit().getAuthor().getDate());

                db.insert(TABLE_COMMIT, null, values);
            }

            db.setTransactionSuccessful();
        } catch(Exception e){
            Log.e(TAG, "public void insertCommits(@NonNull List<NoteStorage> commits)", e);
        } finally {
            db.endTransaction();
        }
    }
}
