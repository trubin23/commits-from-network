package com.example.trubin23.commitsfromnetwork.data.source.remote.model;

import android.support.annotation.NonNull;

import com.example.trubin23.commitsfromnetwork.data.Commit;
import com.example.trubin23.commitsfromnetwork.data.source.remote.model.load.CommitLoad;

/**
 * Created by Andrey on 04.01.2018.
 */

public class CommitMapper {

    @NonNull
    public static Commit toCommit(@NonNull CommitLoad commitLoad){
        String sha = commitLoad.getSha();
        String message = commitLoad.getCommitDescription().getMessage();
        String date = commitLoad.getCommitDescription().getAuthor().getDate();

        return new Commit(sha, message, date);
    }

}
