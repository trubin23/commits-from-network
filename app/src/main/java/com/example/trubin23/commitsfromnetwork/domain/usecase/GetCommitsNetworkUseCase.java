package com.example.trubin23.commitsfromnetwork.domain.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.commitsfromnetwork.data.Commit;
import com.example.trubin23.commitsfromnetwork.data.source.remote.model.CommitMapper;
import com.example.trubin23.commitsfromnetwork.data.source.remote.model.load.CommitLoad;
import com.example.trubin23.commitsfromnetwork.data.source.remote.RetrofitClient;

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
        Integer pageNumber = request.getPageNumber();
        Integer pageSize = request.getPageSize();

        RetrofitClient.getCommits(owner, repo, pageNumber, pageSize, new Callback<List<CommitLoad>>() {
            @Override
            public void onResponse(Call<List<CommitLoad>> call, Response<List<CommitLoad>> response) {
                List<CommitLoad> commitsStorage = response.body();

                if (response.isSuccessful() && commitsStorage != null) {
                    List<Commit> commitsDomain = new ArrayList<>();

                    for (CommitLoad commitLoad : commitsStorage) {
                        Commit commit = CommitMapper.toCommit(commitLoad);
                        commitsDomain.add(commit);
                    }

                    getUseCaseCallback().onSuccess(new ResponseValues(commitsDomain));
                } else {
                    getUseCaseCallback().onError();
                }
            }

            @Override
            public void onFailure(Call<List<CommitLoad>> call, Throwable t) {
                getUseCaseCallback().onError();
            }
        });
    }

    public static class RequestValues implements BaseUseCase.RequestValues {
        private String mOwner, mRepo;
        private Integer mPageNumber, mPageSize;

        public RequestValues(@NonNull String owner, @NonNull String repo,
                             @Nullable Integer pageNumber, @Nullable Integer pageSize) {
            mOwner = owner;
            mRepo = repo;
            mPageNumber = pageNumber;
            mPageSize = pageSize;
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
        Integer getPageNumber() {
            return mPageNumber;
        }

        @Nullable
        Integer getPageSize() {
            return mPageSize;
        }
    }

    public static class ResponseValues implements BaseUseCase.ResponseValues {
        private List<Commit> mCommitsDomain;

        ResponseValues(@NonNull List<Commit> commitsDomain) {
            mCommitsDomain = commitsDomain;
        }

        @NonNull
        public List<Commit> getCommitsDomain() {
            return mCommitsDomain;
        }
    }
}
