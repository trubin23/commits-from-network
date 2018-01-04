package com.example.trubin23.notesfromnetwork.presentation.notes.model;

import com.example.trubin23.notesfromnetwork.domain.model.NoteDomain;

/**
 * Created by Andrey on 04.01.2018.
 */

public class NoteViewMapper {

    public static NoteView toNoteDomain(NoteDomain noteDomain){
        String sha = noteDomain.getSha();
        String message = noteDomain.getMessage();
        String date = noteDomain.getDate();

        return new NoteView(sha, message, date);
    }
}
