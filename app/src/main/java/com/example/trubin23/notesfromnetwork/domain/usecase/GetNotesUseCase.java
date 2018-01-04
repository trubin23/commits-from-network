package com.example.trubin23.notesfromnetwork.domain.usecase;

import com.example.trubin23.notesfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.notesfromnetwork.domain.model.NoteDomain;
import com.example.trubin23.notesfromnetwork.domain.model.NoteDomainMapper;
import com.example.trubin23.notesfromnetwork.storage.model.NoteStorage;
import com.example.trubin23.notesfromnetwork.storage.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 02.01.2018.
 */

public class GetNotesUseCase extends BaseUseCase<GetNotesUseCase.RequestValues, GetNotesUseCase.ResponseValues> {
    @Override
    protected void executeUseCase(RequestValues requestValues) {
        RetrofitClient.getNotes(new Callback<List<NoteStorage>>() {
            @Override
            public void onResponse(Call<List<NoteStorage>> call, Response<List<NoteStorage>> response) {
                List<NoteStorage> notesStorage = response.body();

                if(response.isSuccessful() && notesStorage != null) {
                    List<NoteDomain> notesDomain = new ArrayList<>();

                    for(NoteStorage noteStorage : notesStorage) {
                        NoteDomain noteDomain = NoteDomainMapper.toNoteDomain(noteStorage);
                        notesDomain.add(noteDomain);
                    }

                    getUseCaseCallback().onSuccess(new ResponseValues(notesDomain));
                }
                else {
                    getUseCaseCallback().onError();
                }
            }

            @Override
            public void onFailure(Call<List<NoteStorage>> call, Throwable t) {
                getUseCaseCallback().onError();
            }
        });
    }

    public static class RequestValues implements BaseUseCase.RequestValues {

    }

    public static class ResponseValues implements BaseUseCase.ResponseValues {
        private List<NoteDomain> mNotesDomain;

        ResponseValues(List<NoteDomain> notesDomain) {
            mNotesDomain = notesDomain;
        }

        public List<NoteDomain> getNotesDomain() {
            return mNotesDomain;
        }
    }
}
