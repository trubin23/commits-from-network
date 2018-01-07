package com.example.trubin23.commitsfromnetwork.storage.network;

import com.example.trubin23.commitsfromnetwork.storage.model.CommitStorage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by Andrey on 01.01.2018.
 */

public interface SOService {

    @GET("/repos/{repo}")
    Call<List<CommitStorage>> getCommits(@Path(value = "repo", encoded = true) String repo);
}
