package com.example.trubin23.commitsfromnetwork.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Andrey on 04.01.2018.
 */

public class Commit implements Parcelable {

    public static final String CLASS_COMMIT = "class-commit";

    private String mSha;
    private String mMessage;
    private String mDate;

    public Commit(@NonNull String sha, @NonNull String message, @NonNull String date) {
        mSha = sha;
        mMessage = message;
        mDate = date;
    }

    private Commit(Parcel in) {
        mSha = in.readString();
        mMessage = in.readString();
        mDate = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSha);
        dest.writeString(mMessage);
        dest.writeString(mDate);
    }

    public static final Creator<Commit> CREATOR = new Creator<Commit>() {
        @Override
        public Commit createFromParcel(Parcel in) {
            return new Commit(in);
        }

        @Override
        public Commit[] newArray(int size) {
            return new Commit[size];
        }
    };
}
