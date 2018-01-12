package com.example.trubin23.commitsfromnetwork.storage.network;

import com.example.trubin23.commitsfromnetwork.storage.model.CommitStorage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Andrey on 01.01.2018.
 */

public interface SOService {

    @GET("/repos/{owner}/{repo}/commits")
    Call<List<CommitStorage>> getCommits(@Path(value = "owner") String owner,
                                         @Path(value = "repo") String repo);

    @GET("/repos/{owner}/{repo}/commits")
    Call<List<CommitStorage>> getPageCommits(@Path(value = "owner") String owner,
                                             @Path(value = "repo") String repo,
                                             @Query(value = "page") String page);
}
