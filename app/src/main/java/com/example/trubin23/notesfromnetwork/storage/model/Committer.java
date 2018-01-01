package com.example.trubin23.notesfromnetwork.storage.model;

import com.squareup.moshi.Json;

/**
 * Created by Andrey on 01.01.2018.
 */

public class Committer {

    @Json(name = "name")
    private String name;
    @Json(name = "email")
    private String email;
    @Json(name = "date")
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
