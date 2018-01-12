package com.example.trubin23.commitsfromnetwork.domain.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.commitsfromnetwork.domain.model.CommitDomain;
import com.example.trubin23.commitsfromnetwork.domain.model.CommitDomainMapper;
import com.example.trubin23.commitsfromnetwork.storage.model.CommitStorage;
import com.example.trubin23.commitsfromnetwork.storage.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andrey on 02.01.2018.
 */

public class GetCommitsNetworkUseCase extends BaseUseCase {

    @Override
    protected void executeUseCase(@NonNull BaseUseCase.RequestValues requestValues) {
        RequestValues request = (RequestValues) requestValues;
        String owner = request.getOwner();
        String repo = request.getRepo();
        String pageNumber = request.getPageNumber();

        RetrofitClient.getCommits(owner, repo, pageNumber, new Callback<List<CommitStorage>>() {
            @Override
            public void onResponse(Call<List<CommitStorage>> call, Response<List<CommitStorage>> response) {
                List<CommitStorage> commitsStorage = response.body();

                if (response.isSuccessful() && commitsStorage != null) {
                    List<CommitDomain> commitsDomain = new ArrayList<>();

                    for (CommitStorage commitStorage : commitsStorage) {
                        CommitDomain commitDomain = CommitDomainMapper.toCommitDomain(commitStorage);
                        commitsDomain.add(commitDomain);
                    }

                    getUseCaseCallback().onSuccess(new ResponseValues(commitsDomain));
                } else {
                    getUseCaseCallback().onError();
                }
            }

            @Override
            public void onFailure(Call<List<CommitStorage>> call, Throwable t) {
                getUseCaseCallback().onError();
            }
        });
    }

    public static class RequestValues implements BaseUseCase.RequestValues {
        private String mOwner, mRepo, mPageNumber;

        public RequestValues(@NonNull String owner, @NonNull String repo, @Nullable String pageNumber) {
            mOwner = owner;
            mRepo = repo;
            mPageNumber = pageNumber;
        }

        @NonNull
        String getOwner() {
            return mOwner;
        }

        @NonNull
        String getRepo() {
            return mRepo;
        }

        @Nullable
        String getPageNumber() {
            return mPageNumber;
        }
    }

    public static class ResponseValues implements BaseUseCase.ResponseValues {
        private List<CommitDomain> mCommitsDomain;

        ResponseValues(@NonNull List<CommitDomain> commitsDomain) {
            mCommitsDomain = commitsDomain;
        }

        @NonNull
        public List<CommitDomain> getCommitsDomain() {
            return mCommitsDomain;
        }
    }
}
