package com.example.trubin23.commitsfromnetwork.domain.usecase;

import android.support.annotation.NonNull;

import com.example.trubin23.commitsfromnetwork.data.source.preferences.RepoSharedPreferences;
import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;

import static com.example.trubin23.commitsfromnetwork.data.source.preferences.RepoPreferences.OWNER_VALUE;
import static com.example.trubin23.commitsfromnetwork.data.source.preferences.RepoPreferences.REPO_VALUE;

/**
 * Created by Andrey on 19.01.2018.
 */

public class SaveRepoDataUseCase extends BaseUseCase {

    @Override
    protected void executeUseCase(@NonNull BaseUseCase.RequestValues requestValues) {
        RequestValues request = (RequestValues) requestValues;

        String owner = request.getOwner();
        String repo = request.getRepo();

        RepoSharedPreferences customPreferences = RepoSharedPreferences.getInstance(null);
        if (customPreferences != null){
            customPreferences.putString(OWNER_VALUE, owner);
            customPreferences.putString(REPO_VALUE, repo);
            getUseCaseCallback().onSuccess(new ResponseValues());
        } else {
            getUseCaseCallback().onError();
        }
    }

    public static class RequestValues implements BaseUseCase.RequestValues {
        private String mOwner;
        private String mRepo;

        public RequestValues(@NonNull String owner, @NonNull String repo) {
            mOwner = owner;
            mRepo = repo;
        }

        public String getOwner() {
            return mOwner;
        }

        public String getRepo() {
            return mRepo;
        }
    }

    public static class ResponseValues implements BaseUseCase.ResponseValues{
    }
}
