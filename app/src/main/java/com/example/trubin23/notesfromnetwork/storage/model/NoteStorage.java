package com.example.trubin23.notesfromnetwork.storage.model;

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
    @Json(name = "url")
    private String url;
    @Json(name = "html_url")
    private String htmlUrl;
    @Json(name = "comments_url")
    private String commentsUrl;
    @Json(name = "author")
    private Author_ author;
    @Json(name = "committer")
    private Committer_ committer;
    @Json(name = "parents")
    private List<Parent> parents = null;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public Author_ getAuthor() {
        return author;
    }

    public void setAuthor(Author_ author) {
        this.author = author;
    }

    public Committer_ getCommitter() {
        return committer;
    }

    public void setCommitter(Committer_ committer) {
        this.committer = committer;
    }

    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }

}
