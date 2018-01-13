package com.example.trubin23.commitsfromnetwork.presentation.commits.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Andrey on 04.01.2018.
 */

public class CommitView implements Parcelable {

    public static String CLASS_COMMIT_VIEW = "class-commit-sha";

    private String mSha;
    private String mMessage;
    private String mDate;

    CommitView(@NonNull String sha, @NonNull String message, @NonNull String date) {
        mSha = sha;
        mMessage = message;
        mDate = date;
    }

    private CommitView(Parcel in) {
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

    public static final Creator<CommitView> CREATOR = new Creator<CommitView>() {
        @Override
        public CommitView createFromParcel(Parcel in) {
            return new CommitView(in);
        }

        @Override
        public CommitView[] newArray(int size) {
            return new CommitView[size];
        }
    };
}
