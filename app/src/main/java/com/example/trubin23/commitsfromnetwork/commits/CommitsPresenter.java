package com.example.trubin23.commitsfromnetwork.commits;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.trubin23.commitsfromnetwork.data.Commit;
import com.example.trubin23.commitsfromnetwork.data.source.CommitsDataSource;
import com.example.trubin23.commitsfromnetwork.data.source.CommitsRepository;

import java.util.List;

/**
 * Created by Andrey on 31.12.2017.
 */

class CommitsPresenter implements CommitsContract.Presenter {

    private static final String OWNER_VALUE = "owner_value";
    private static final String REPO_VALUE = "repo_value";

    private static final String TAG = CommitsPresenter.class.getSimpleName();

    private CommitsRepository mCommitsRepository;

    private final CommitsContract.View mCommitsView;

    CommitsPresenter(@NonNull CommitsRepository commitsRepository,
                     @NonNull CommitsContract.View commitsView) {
        mCommitsRepository = commitsRepository;
        mCommitsView = commitsView;

        mCommitsView.setPresenter(this);
    }

    private void getCommitsDb(@NonNull String owner, @NonNull String repo) {
        mCommitsRepository.getCommitsDb(owner, repo,
                new CommitsDataSource.LoadCommitsCallback() {

                    @Override
                    public void onCommitsLoaded(List<Commit> commits) {
                        mCommitsView.setCommits(commits);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        errorMessage("GetCommitsDbUseCase: error");
                    }
                });
    }

    private void getCommitsNetwork(@NonNull String owner, @NonNull String repo,
                                   @Nullable Integer pageNumber, @Nullable Integer pageSize) {
        mCommitsRepository.getCommitsNetwork(owner, repo, pageNumber, pageSize,
                new CommitsDataSource.LoadCommitsCallback() {
                    @Override
                    public void onCommitsLoaded(List<Commit> commits) {
                        insertCommitsDb(commits, owner, repo);

                        mCommitsView.setCommits(commits);
                        if (commits.size() < pageSize) {
                            mCommitsView.lastPageLoaded();
                        }
                        mCommitsView.loadFinished();
                    }

                    @Override
                    public void onDataNotAvailable() {
                        errorMessage("getCommitsNetwork: error");
                    }
                });
    }

    private void insertCommitsDb(@NonNull List<Commit> commits,
                                 @NonNull String owner, @NonNull String repo) {
        mCommitsRepository.insertCommitsDb(commits, owner, repo);
    }

    @Override
    public void loadCommits(@NonNull String owner, @NonNull String repo,
                            @NonNull Integer pageNumber, @NonNull Integer pageSize) {
        getCommitsDb(owner, repo);
        getCommitsNetwork(owner, repo, pageNumber, pageSize);
    }

    private void errorMessage(@NonNull String message) {
        Log.e(TAG, message);
        mCommitsView.showToast(message);
        mCommitsView.loadFinished();
    }

    @Override
    public void saveRepoData(@NonNull String owner, @NonNull String repo) {
        mCommitsRepository.savePreference(OWNER_VALUE, owner);
        mCommitsRepository.savePreference(REPO_VALUE, repo);
    }

    @Override
    public void loadRepoData() {
        mCommitsRepository.getPreference(OWNER_VALUE, mCommitsView::setOwnerName);
        mCommitsRepository.getPreference(REPO_VALUE, mCommitsView::setRepoName);
    }
}
