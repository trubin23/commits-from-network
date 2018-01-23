package com.example.trubin23.commitsfromnetwork.domain.usecase;

import android.support.annotation.NonNull;

import com.example.trubin23.commitsfromnetwork.data.source.preferences.RepoSharedPreferences;
import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;

import static com.example.trubin23.commitsfromnetwork.data.source.preferences.RepoPreferences.OWNER_VALUE;
import static com.example.trubin23.commitsfromnetwork.data.source.preferences.RepoPreferences.REPO_VALUE;

/**
 * Created by Andrey on 19.01.2018.
 */

public class LoadRepoDataUseCase extends BaseUseCase {

    @Override
    protected void executeUseCase(@NonNull BaseUseCase.RequestValues requestValues) {
        RepoSharedPreferences customPreferences = RepoSharedPreferences.getInstance(null);
        if (customPreferences != null) {
            String owner = customPreferences.getString(OWNER_VALUE);
            String repo = customPreferences.getString(REPO_VALUE);

            owner = owner != null ? owner : "";
            repo = repo != null ? repo : "";

            getUseCaseCallback().onSuccess(new ResponseValues(owner, repo));
        } else {
            getUseCaseCallback().onError();
        }
    }

    public static class RequestValues implements BaseUseCase.RequestValues {
    }

    public static class ResponseValues implements BaseUseCase.ResponseValues {
        private String mOwner;
        private String mRepo;

        ResponseValues(@NonNull String owner, @NonNull String repo) {
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
}
