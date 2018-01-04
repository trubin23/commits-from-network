package com.example.trubin23.notesfromnetwork.domain.model;

import android.support.annotation.NonNull;

/**
 * Created by Andrey on 04.01.2018.
 */

public class NoteDomain {
    private String mSha;
    private String mMessage;
    private String mDate;

    public NoteDomain(@NonNull String sha, @NonNull String message, @NonNull String date) {
        mSha = sha;
        mMessage = message;
        mDate = date;
    }

    @NonNull
    public String getSha() {
        return mSha;
    }

    @NonNull
    public String getMessage() {
        return mMessage;
    }

    @NonNull
    public String getDate() {
        return mDate;
    }
}
