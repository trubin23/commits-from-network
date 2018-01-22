package com.example.trubin23.commitsfromnetwork.storage.model.load;

import android.support.annotation.NonNull;

import com.squareup.moshi.Json;

/**
 * Created by Andrey on 01.01.2018.
 */

public class CommitLoad {

    @Json(name = "sha")
    private String mSha;
    @Json(name = "commit")
    private CommitDescription mCommitDescription;

    @NonNull
    public String getSha() {
        return mSha;
    }

    public void setSha(@NonNull String sha) {
        mSha = sha;
    }

    @NonNull
    public CommitDescription getCommitDescription() {
        return mCommitDescription;
    }

    public void setCommitDescription(@NonNull CommitDescription commitDescription) {
        mCommitDescription = commitDescription;
    }
}
