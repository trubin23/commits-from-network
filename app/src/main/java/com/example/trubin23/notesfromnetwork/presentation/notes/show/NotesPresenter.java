package com.example.trubin23.notesfromnetwork.presentation.notes.show;

import android.support.annotation.NonNull;
import com.example.trubin23.notesfromnetwork.presentation.common.BasePresenter;

/**
 * Created by Andrey on 31.12.2017.
 */

class NotesPresenter extends BasePresenter<NotesContract.View> implements NotesContract.Presenter {

    private static final String TAG = NotesPresenter.class.getSimpleName();

    @Override
    public void loadCommits(@NonNull String repoName) {
        getView().setCommitsString(repoName);
    }
}
