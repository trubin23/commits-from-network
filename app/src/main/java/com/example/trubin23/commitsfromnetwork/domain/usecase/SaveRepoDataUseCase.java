package com.example.trubin23.commitsfromnetwork.domain.usecase;

import android.support.annotation.NonNull;

import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.commitsfromnetwork.data.source.local.preferences.CustomPreferences;

/**
 * Created by Andrey on 19.01.2018.
 */

public class SaveRepoDataUseCase extends BaseUseCase {

    public static final String OWNER_VALUE = "owner_value";
    public static final String REPO_VALUE = "repo_value";

    @Override
    protected void executeUseCase(@NonNull BaseUseCase.RequestValues requestValues) {
        RequestValues request = (RequestValues) requestValues;

        String owner = request.getOwner();
        String repo = request.getRepo();

        CustomPreferences customPreferences = CustomPreferences.getInstance(null);
        if (customPreferences != null){
            customPreferences.putOwner(owner);
            customPreferences.putRepo(repo);
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
