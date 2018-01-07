package com.example.trubin23.commitsfromnetwork.storage.model;

import android.support.annotation.NonNull;
import com.squareup.moshi.Json;

/**
 * Created by Andrey on 01.01.2018.
 */

public class Author {

    @Json(name = "date")
    private String mDate;

    @NonNull
    public String getDate() {
        return mDate;
    }

    public void setDate(@NonNull String date) {
        this.mDate = date;
    }

}
