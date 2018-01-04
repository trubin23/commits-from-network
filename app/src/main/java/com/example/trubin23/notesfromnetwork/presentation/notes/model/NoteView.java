package com.example.trubin23.notesfromnetwork.presentation.notes.model;

/**
 * Created by Andrey on 04.01.2018.
 */

public class NoteView {
    private String mSha;
    private String mMessage;
    private String mDate;

    public NoteView(String sha, String message, String date) {
        mSha = sha;
        mMessage = message;
        mDate = date;
    }

    public String getSha() {
        return mSha;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getDate() {
        return mDate;
    }
}
