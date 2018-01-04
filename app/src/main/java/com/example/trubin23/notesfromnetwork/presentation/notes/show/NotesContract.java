package com.example.trubin23.notesfromnetwork.presentation.notes.show;

import android.support.annotation.NonNull;
import com.example.trubin23.notesfromnetwork.presentation.common.BaseView;
import com.example.trubin23.notesfromnetwork.storage.model.NoteStorage;

import java.util.List;

/**
 * Created by Andrey on 31.12.2017.
 */

class NotesContract {

    interface View extends BaseView {
        void setCommitsString(List<NoteStorage> notesStorage);
    }

    interface Presenter {
        void loadCommits(@NonNull String repoName);
    }
}
