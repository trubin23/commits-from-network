package com.example.trubin23.commitsfromnetwork.domain.model;

import android.support.annotation.NonNull;
import com.example.trubin23.commitsfromnetwork.storage.model.Author;
import com.example.trubin23.commitsfromnetwork.storage.model.CommitDescription;
import com.example.trubin23.commitsfromnetwork.storage.model.CommitStorage;

/**
 * Created by Andrey on 04.01.2018.
 */

public class CommitDomainMapper {

    @NonNull
    public static CommitDomain toCommitDomain(@NonNull CommitStorage commitStorage){
        String sha = commitStorage.getSha();
        String message = commitStorage.getCommitDescription().getMessage();
        String date = commitStorage.getCommitDescription().getAuthor().getDate();

        return new CommitDomain(sha, message, date);
    }

    @NonNull
    public static CommitStorage toCommitStorage(@NonNull CommitDomain commitDomain){
        String sha = commitDomain.getSha();
        String message = commitDomain.getMessage();
        String date = commitDomain.getDate();

        Author author = new Author();
        author.setDate(date);

        CommitDescription commitDescription = new CommitDescription();
        commitDescription.setAuthor(author);
        commitDescription.setMessage(message);

        CommitStorage commitStorage = new CommitStorage();
        commitStorage.setCommitDescription(commitDescription);
        commitStorage.setSha(sha);

        return commitStorage;
    }

}
