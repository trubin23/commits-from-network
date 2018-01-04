package com.example.trubin23.notesfromnetwork.presentation.notes.model;

import android.support.annotation.NonNull;
import com.example.trubin23.notesfromnetwork.domain.model.NoteDomain;

/**
 * Created by Andrey on 04.01.2018.
 */

public class NoteViewMapper {

    @NonNull
    public static NoteView toNoteDomain(@NonNull NoteDomain noteDomain){
        String sha = noteDomain.getSha();
        String message = noteDomain.getMessage();
        String date = noteDomain.getDate();

        return new NoteView(sha, message, date);
    }
}
