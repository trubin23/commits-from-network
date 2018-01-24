package com.example.trubin23.commitsfromnetwork.show;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.trubin23.commitsfromnetwork.BasePresenter;
import com.example.trubin23.commitsfromnetwork.data.Commit;
import com.example.trubin23.commitsfromnetwork.data.source.CommitsRepository;
import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.commitsfromnetwork.domain.common.UseCaseHandler;
import com.example.trubin23.commitsfromnetwork.domain.usecase.GetCommitsDbUseCase;
import com.example.trubin23.commitsfromnetwork.domain.usecase.GetCommitsNetworkUseCase;
import com.example.trubin23.commitsfromnetwork.domain.usecase.InsertCommitsDbUseCase;

import java.util.List;

import static com.example.trubin23.commitsfromnetwork.data.source.preferences.CommitsSharedPreferences.OWNER_VALUE;
import static com.example.trubin23.commitsfromnetwork.data.source.preferences.CommitsSharedPreferences.REPO_VALUE;

/**
 * Created by Andrey on 31.12.2017.
 */

class CommitsPresenter extends BasePresenter<CommitsContract.View> implements CommitsContract.Presenter {

    private static final String TAG = CommitsPresenter.class.getSimpleName();

    private final GetCommitsDbUseCase mGetCommitsDbUseCase;
    private final GetCommitsNetworkUseCase mGetCommitsNetworkUseCase;
    private final InsertCommitsDbUseCase mInsertCommitsDbUseCase;

    CommitsPresenter(@NonNull CommitsRepository commitsRepository,
                     @NonNull UseCaseHandler useCaseHandler) {
        super(commitsRepository, useCaseHandler);
        mGetCommitsDbUseCase = new GetCommitsDbUseCase();
        mGetCommitsNetworkUseCase = new GetCommitsNetworkUseCase();
        mInsertCommitsDbUseCase = new InsertCommitsDbUseCase();
    }

    private void getCommitsDb(@NonNull String owner, @NonNull String repo) {
        mUseCaseHandler.execute(mGetCommitsDbUseCase, new GetCommitsDbUseCase.RequestValues(owner, repo),
                new BaseUseCase.UseCaseCallback() {
                    @Override
                    public void onSuccess(@NonNull BaseUseCase.ResponseValues responseValues) {
                        GetCommitsDbUseCase.ResponseValues response =
                                (GetCommitsDbUseCase.ResponseValues) responseValues;

                        List<Commit> commits = response.getCommitsDomain();
                        getView().setCommits(commits);
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

                        List<Commit> commits = response.getCommitsDomain();
                        insertCommitsDb(commits, owner, repo);

                        getView().setCommits(commits);
                        if (commits.size() < pageSize) {
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

    private void insertCommitsDb(@NonNull List<Commit> commitsDomain,
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
                            @NonNull Integer pageNumber, @NonNull Integer pageSize) {
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
        mCommitsRepository.savePreference(OWNER_VALUE, owner);
        mCommitsRepository.savePreference(REPO_VALUE, repo);
    }

    @Override
    public void loadRepoData() {
        mCommitsRepository.getPreference(OWNER_VALUE, value -> getView().setOwnerName(value));
        mCommitsRepository.getPreference(REPO_VALUE, value -> getView().setRepoName(value));
    }
}
