package com.example.trubin23.commitsfromnetwork.storage.model.load;

import android.support.annotation.NonNull;
import com.squareup.moshi.Json;

/**
 * Created by Andrey on 01.01.2018.
 */

public class CommitDescription {

    @Json(name = "author")
    private Author mAuthor;
    @Json(name = "message")
    private String mMessage;

    @NonNull
    public Author getAuthor() {
        return mAuthor;
    }

    public void setAuthor(@NonNull Author author) {
        mAuthor = author;
    }

    @NonNull
    public String getMessage() {
        return mMessage;
    }

    public void setMessage(@NonNull String message) {
        mMessage = message;
    }

}
