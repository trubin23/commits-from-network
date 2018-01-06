package com.example.trubin23.notesfromnetwork.storage.model;

import android.support.annotation.NonNull;
import com.squareup.moshi.Json;

/**
 * Created by Andrey on 01.01.2018.
 */

public class Author {

    @Json(name = "date")
    private String date;

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

}
