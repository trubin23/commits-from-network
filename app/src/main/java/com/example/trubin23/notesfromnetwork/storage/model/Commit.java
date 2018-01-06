package com.example.trubin23.notesfromnetwork.storage.model;

import android.support.annotation.NonNull;
import com.squareup.moshi.Json;

/**
 * Created by Andrey on 01.01.2018.
 */

public class Commit {

    @Json(name = "author")
    private Author author;
    @Json(name = "message")
    private String message;

    @NonNull
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull Author author) {
        this.author = author;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    public void setMessage(@NonNull String message) {
        this.message = message;
    }

}
