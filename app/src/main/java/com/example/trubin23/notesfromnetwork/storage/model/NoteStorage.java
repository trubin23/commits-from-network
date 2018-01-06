package com.example.trubin23.notesfromnetwork.storage.model;

import android.support.annotation.NonNull;
import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by Andrey on 01.01.2018.
 */

public class NoteStorage {

    @Json(name = "sha")
    private String sha;
    @Json(name = "commit")
    private Commit commit;

    @NonNull
    public String getSha() {
        return sha;
    }

    public void setSha(@NonNull String sha) {
        this.sha = sha;
    }

    @NonNull
    public Commit getCommit() {
        return commit;
    }

    public void setCommit(@NonNull Commit commit) {
        this.commit = commit;
    }

}
