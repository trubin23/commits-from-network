package com.example.trubin23.notesfromnetwork.domain.model;

import android.support.annotation.NonNull;
import com.example.trubin23.notesfromnetwork.storage.model.NoteStorage;

/**
 * Created by Andrey on 04.01.2018.
 */

public class NoteDomainMapper {

    @NonNull
    public static NoteDomain toNoteDomain(@NonNull NoteStorage noteStorage){
        String sha = noteStorage.getSha();
        String message = noteStorage.getCommit().getMessage();
        String date = noteStorage.getCommit().getCommitAuthor().getDate();

        return new NoteDomain(sha, message, date);
    }

}
