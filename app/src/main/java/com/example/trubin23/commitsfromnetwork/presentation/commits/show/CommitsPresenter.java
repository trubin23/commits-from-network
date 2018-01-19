package com.example.trubin23.commitsfromnetwork.presentation.commits.show;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.commitsfromnetwork.domain.common.UseCaseHandler;
import com.example.trubin23.commitsfromnetwork.domain.model.CommitDomain;
import com.example.trubin23.commitsfromnetwork.domain.usecase.GetCommitsDbUseCase;
import com.example.trubin23.commitsfromnetwork.domain.usecase.GetCommitsNetworkUseCase;
import com.example.trubin23.commitsfromnetwork.domain.usecase.InsertCommitsDbUseCase;
import com.example.trubin23.commitsfromnetwork.domain.usecase.LoadRepoDataUseCase;
import com.example.trubin23.commitsfromnetwork.domain.usecase.SaveRepoDataUseCase;
import com.example.trubin23.commitsfromnetwork.presentation.commits.model.CommitView;
import com.example.trubin23.commitsfromnetwork.presentation.commits.model.CommitViewMapper;
import com.example.trubin23.commitsfromnetwork.presentation.common.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 31.12.2017.
 */

class CommitsPresenter extends BasePresenter<CommitsContract.View> implements CommitsContract.Presenter {

    private static final String TAG = CommitsPresenter.class.getSimpleName();

    private final GetCommitsDbUseCase mGetCommitsDbUseCase;
    private final GetCommitsNetworkUseCase mGetCommitsNetworkUseCase;
    private final InsertCommitsDbUseCase mInsertCommitsDbUseCase;
    private final SaveRepoDataUseCase mSaveRepoDataUseCase;
    private final LoadRepoDataUseCase mLoadRepoDataUseCase;

    CommitsPresenter(@NonNull UseCaseHandler useCaseHandler) {
        super(useCaseHandler);
        mGetCommitsDbUseCase = new GetCommitsDbUseCase();
        mGetCommitsNetworkUseCase = new GetCommitsNetworkUseCase();
        mInsertCommitsDbUseCase = new InsertCommitsDbUseCase();
        mSaveRepoDataUseCase = new SaveRepoDataUseCase();
        mLoadRepoDataUseCase = new LoadRepoDataUseCase();
    }

    private void getCommitsDb(@NonNull String owner, @NonNull String repo) {
        mUseCaseHandler.execute(mGetCommitsDbUseCase, new GetCommitsDbUseCase.RequestValues(owner, repo),
                new BaseUseCase.UseCaseCallback() {
                    @Override
                    public void onSuccess(@NonNull BaseUseCase.ResponseValues responseValues) {
                        GetCommitsDbUseCase.ResponseValues response =
                                (GetCommitsDbUseCase.ResponseValues) responseValues;

                        List<CommitDomain> commitsDomain = response.getCommitsDomain();
                        List<CommitView> commitsView = new ArrayList<>();
                        for (CommitDomain commitDomain : commitsDomain) {
                            CommitView commitView = CommitViewMapper.toCommitDomain(commitDomain);
                            commitsView.add(commitView);
                        }
                        getView().setCommits(commitsView);
                    }

                    @Override
                    public void onError() {
                        errorMessage("GetCommitsDbUseCase: error");
                    }
                });
    }

    private void getCommitsNetwork(@NonNull String owner, @NonNull String repo,
                                   @Nullable Integer pageNumber, @Nullable Integer pageSize) {
        mUseCaseHandler.execute(mGetCommitsNetworkUseCase,
                new GetCommitsNetworkUseCase.RequestValues(owner, repo, pageNumber, pageSize),
                new BaseUseCase.UseCaseCallback() {
                    @Override
                    public void onSuccess(@NonNull BaseUseCase.ResponseValues responseValues) {
                        GetCommitsNetworkUseCase.ResponseValues response =
                                (GetCommitsNetworkUseCase.ResponseValues) responseValues;

                        List<CommitDomain> commitsDomain = response.getCommitsDomain();
                        insertCommitsDb(commitsDomain, owner, repo);

                        List<CommitView> commitsView = new ArrayList<>();
                        for (CommitDomain commitDomain : commitsDomain) {
                            CommitView commitView = CommitViewMapper.toCommitDomain(commitDomain);
                            commitsView.add(commitView);
                        }

                        getView().setCommits(commitsView);
                        if (commitsView.size() < pageSize) {
                            getView().lastPageLoaded();
                        }
                        getView().loadFinished();
                    }

                    @Override
                    public void onError() {
                        errorMessage("GetCommitsNetworkUseCase: error");
                    }
                });
    }

    private void insertCommitsDb(@NonNull List<CommitDomain> commitsDomain,
                                 @NonNull String owner, @NonNull String repo) {
        mUseCaseHandler.execute(mInsertCommitsDbUseCase,
                new InsertCommitsDbUseCase.RequestValues(commitsDomain, owner, repo),
                new BaseUseCase.UseCaseCallback() {
                    @Override
                    public void onSuccess(@NonNull BaseUseCase.ResponseValues responseValues) {
                    }

                    @Override
                    public void onError() {
                        errorMessage("InsertCommitsDbUseCase: error");
                    }
                });
    }

    @Override
    public void loadCommits(@NonNull String owner, @NonNull String repo,
                            @Nullable Integer pageNumber, @Nullable Integer pageSize) {
        getCommitsDb(owner, repo);
        getCommitsNetwork(owner, repo, pageNumber, pageSize);
    }

    private void errorMessage(@NonNull String message) {
        Log.e(TAG, message);
        getView().showToast(message);
        getView().loadFinished();
    }

    @Override
    public void saveRepoData(@NonNull String owner, @NonNull String repo) {
        mUseCaseHandler.execute(mSaveRepoDataUseCase,
                new SaveRepoDataUseCase.RequestValues(owner, repo),
                new BaseUseCase.UseCaseCallback() {
                    @Override
                    public void onSuccess(@NonNull BaseUseCase.ResponseValues responseValues) {
                    }

                    @Override
                    public void onError() {
                        Log.e(TAG, "SaveRepoDataUseCase: error");
                    }
                });
    }

    @Override
    public void loadRepoData() {
        mUseCaseHandler.execute(mLoadRepoDataUseCase,
                new LoadRepoDataUseCase.RequestValues(),
                new BaseUseCase.UseCaseCallback() {
                    @Override
                    public void onSuccess(@NonNull BaseUseCase.ResponseValues responseValues) {
                        LoadRepoDataUseCase.ResponseValues response =
                                (LoadRepoDataUseCase.ResponseValues) responseValues;

                        String owner = response.getOwner();
                        String repo = response.getRepo();

                        getView().setRepoData(owner, repo);
                    }

                    @Override
                    public void onError() {
                        Log.e(TAG, "SaveRepoDataUseCase: error");
                    }
                });
    }
}
