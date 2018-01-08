package com.example.trubin23.commitsfromnetwork.presentation.commits.show;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.commitsfromnetwork.domain.common.UseCaseHandler;
import com.example.trubin23.commitsfromnetwork.domain.model.CommitDomain;
import com.example.trubin23.commitsfromnetwork.domain.usecase.GetCommitsDbUseCase;
import com.example.trubin23.commitsfromnetwork.domain.usecase.GetCommitsNetworkUseCase;
import com.example.trubin23.commitsfromnetwork.presentation.common.BasePresenter;
import com.example.trubin23.commitsfromnetwork.presentation.commits.model.CommitView;
import com.example.trubin23.commitsfromnetwork.presentation.commits.model.CommitViewMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 31.12.2017.
 */

class CommitsPresenter extends BasePresenter<CommitsContract.View> implements CommitsContract.Presenter {

    private static final String TAG = CommitsPresenter.class.getSimpleName();

    private final GetCommitsDbUseCase mGetCommitsDbUseCase;
    private final GetCommitsNetworkUseCase mGetCommitsNetworkUseCase;

    CommitsPresenter(@NonNull UseCaseHandler useCaseHandler) {
        super(useCaseHandler);
        mGetCommitsDbUseCase = new GetCommitsDbUseCase();
        mGetCommitsNetworkUseCase = new GetCommitsNetworkUseCase();;
    }

    private void getCommitsDb(){
        mUseCaseHandler.execute(mGetCommitsDbUseCase, new GetCommitsDbUseCase.RequestValues(),
                new BaseUseCase.UseCaseCallback() {
            @Override
            public void onSuccess(@NonNull BaseUseCase.ResponseValues responseValues) {
                GetCommitsDbUseCase.ResponseValues response =
                        (GetCommitsDbUseCase.ResponseValues) responseValues;

                List<CommitDomain> commitsDomain = response.getCommitsDomain();
                List<CommitView> commitsView = new ArrayList<>();
                for(CommitDomain commitDomain : commitsDomain) {
                    CommitView commitView = CommitViewMapper.toCommitDomain(commitDomain);
                    commitsView.add(commitView);
                }
                getView().setCommitsString(commitsView);
            }

            @Override
            public void onError() {
                Log.e(TAG, "GetCommitsDbUseCase: error");
            }
        });
    }

    private void getCommitsNetwork(@NonNull String repoName) {
        mUseCaseHandler.execute(mGetCommitsNetworkUseCase, new GetCommitsNetworkUseCase.RequestValues(repoName),
                new BaseUseCase.UseCaseCallback() {
                    @Override
                    public void onSuccess(@NonNull BaseUseCase.ResponseValues responseValues) {
                        GetCommitsNetworkUseCase.ResponseValues response =
                                (GetCommitsNetworkUseCase.ResponseValues) responseValues;

                        List<CommitDomain> commitsDomain = response.getCommitsDomain();
                        List<CommitView> commitsView = new ArrayList<>();
                        for(CommitDomain commitDomain : commitsDomain) {
                            CommitView commitView = CommitViewMapper.toCommitDomain(commitDomain);
                            commitsView.add(commitView);
                        }
                        getView().setCommitsString(commitsView);
                    }

                    @Override
                    public void onError() {
                        Log.e(TAG, "GetCommitsNetworkUseCase: error");
                    }
                });
    }

    @Override
    public void loadCommits(@NonNull String repoName) {
        getCommitsDb();
        getCommitsNetwork(repoName);
    }
}
