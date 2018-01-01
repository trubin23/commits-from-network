package com.example.trubin23.notesfromnetwork.storage.model;

import com.squareup.moshi.Json;

/**
 * Created by Andrey on 01.01.2018.
 */

public class Tree {

    @Json(name = "sha")
    private String sha;
    @Json(name = "url")
    private String url;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
