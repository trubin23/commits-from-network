package com.example.trubin23.notesfromnetwork.storage.network;

import com.example.trubin23.notesfromnetwork.storage.model.NoteStorage;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by Andrey on 01.01.2018.
 */

public interface SOService {

    @GET("/repos/{repo}")
    Call<List<NoteStorage>> getNotes(@Path(value = "repo", encoded = true) String repo);
}
