package com.example.trubin23.notesfromnetwork.domain.usecase;

import com.example.trubin23.notesfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.notesfromnetwork.storage.model.NoteStorage;
import com.example.trubin23.notesfromnetwork.storage.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                if (response.isSuccessful()){
                    List<NoteStorage> notesStorage = response.body();
                    getUseCaseCallback().onSuccess(new ResponseValues(notesStorage));
                } else {
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
        private List<NoteStorage> mNotesStorage;

        ResponseValues(List<NoteStorage> notesStorage) {
            mNotesStorage = notesStorage;
        }

        public List<NoteStorage> getNotesStorage(){
            return mNotesStorage;
        }
    }
}
