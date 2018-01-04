package com.example.trubin23.notesfromnetwork.presentation.notes.show;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.trubin23.notesfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.notesfromnetwork.domain.common.UseCaseHandler;
import com.example.trubin23.notesfromnetwork.domain.model.NoteDomain;
import com.example.trubin23.notesfromnetwork.domain.model.NoteDomainMapper;
import com.example.trubin23.notesfromnetwork.domain.usecase.GetNotesUseCase;
import com.example.trubin23.notesfromnetwork.presentation.common.BasePresenter;
import com.example.trubin23.notesfromnetwork.presentation.notes.model.NoteView;
import com.example.trubin23.notesfromnetwork.presentation.notes.model.NoteViewMapper;
import com.example.trubin23.notesfromnetwork.storage.model.NoteStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 31.12.2017.
 */

class NotesPresenter extends BasePresenter<NotesContract.View> implements NotesContract.Presenter {

    private static final String TAG = NotesPresenter.class.getSimpleName();

    private final GetNotesUseCase mGetNotesUseCase;

    NotesPresenter(@NonNull UseCaseHandler useCaseHandler,
                   @NonNull GetNotesUseCase getNotesUseCase) {
        super(useCaseHandler);
        mGetNotesUseCase = getNotesUseCase;
    }

    @Override
    public void loadCommits(@NonNull String repoName) {
        mUseCaseHandler.execute(mGetNotesUseCase, new GetNotesUseCase.RequestValues(),
                                new BaseUseCase.UseCaseCallback<GetNotesUseCase.ResponseValues>() {
                                    @Override
                                    public void onSuccess(GetNotesUseCase.ResponseValues response) {
                                        List<NoteDomain> notesDomain = response.getNotesDomain();

                                        List<NoteView> notesView = new ArrayList<>();

                                        for(NoteDomain noteDomain : notesDomain) {
                                            NoteView noteView = NoteViewMapper.toNoteDomain(noteDomain);
                                            notesView.add(noteView);
                                        }

                                        getView().setCommitsString(notesView);
                                    }

                                    @Override
                                    public void onError() {
                                        Log.e(TAG, "GetNotesUseCase: error");
                                    }
                                });
    }
}
