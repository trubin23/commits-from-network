package com.example.trubin23.notesfromnetwork.storage.model;

import com.squareup.moshi.Json;

/**
 * Created by Andrey on 01.01.2018.
 */

public class Commit {

    @Json(name = "author")
    private Author author;
    @Json(name = "committer")
    private Committer committer;
    @Json(name = "message")
    private String message;
    @Json(name = "tree")
    private Tree tree;
    @Json(name = "url")
    private String url;
    @Json(name = "comment_count")
    private Integer commentCount;
    @Json(name = "verification")
    private Verification verification;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Committer getCommitter() {
        return committer;
    }

    public void setCommitter(Committer committer) {
        this.committer = committer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Verification getVerification() {
        return verification;
    }

    public void setVerification(Verification verification) {
        this.verification = verification;
    }

}
