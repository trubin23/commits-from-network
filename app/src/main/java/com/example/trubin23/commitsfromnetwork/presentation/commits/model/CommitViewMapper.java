package com.example.trubin23.commitsfromnetwork.presentation.commits.model;

import android.support.annotation.NonNull;
import com.example.trubin23.commitsfromnetwork.domain.model.CommitDomain;

/**
 * Created by Andrey on 04.01.2018.
 */

public class CommitViewMapper {

    @NonNull
    public static CommitView toCommitDomain(@NonNull CommitDomain commitDomain){
        String sha = commitDomain.getSha();
        String message = commitDomain.getMessage();
        String date = commitDomain.getDate();

        return new CommitView(sha, message, date);
    }
}
