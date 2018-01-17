package com.example.trubin23.commitsfromnetwork.domain.usecase;

import android.support.annotation.NonNull;
import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.commitsfromnetwork.domain.model.CommitDomain;
import com.example.trubin23.commitsfromnetwork.domain.model.CommitDomainMapper;
import com.example.trubin23.commitsfromnetwork.storage.database.CommitDao;
import com.example.trubin23.commitsfromnetwork.storage.database.CommitDaoImpl;
import com.example.trubin23.commitsfromnetwork.storage.database.DatabaseHelper;
import com.example.trubin23.commitsfromnetwork.storage.model.CommitStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 07.01.2018.
 */

public class InsertCommitsDbUseCase extends BaseUseCase {

    @Override
    protected void executeUseCase(@NonNull BaseUseCase.RequestValues requestValues) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(null);
        if (databaseHelper == null) {
            getUseCaseCallback().onError();
            return;
        }

        RequestValues request = (RequestValues) requestValues;
        String repo = request.getRepo();

        List<CommitDomain> commitsDomain = request.getCommitsDomain();
        List<CommitStorage> commitsStorage = new ArrayList<>();
        for (CommitDomain commitDomain : commitsDomain){
            CommitStorage commitStorage = CommitDomainMapper.toCommitStorage(commitDomain);
            commitsStorage.add(commitStorage);
        }

        CommitDao commitDao = new CommitDaoImpl(databaseHelper);
        commitDao.insertCommits(commitsStorage, repo);
        getUseCaseCallback().onSuccess(new ResponseValues());
    }

    public static class RequestValues implements BaseUseCase.RequestValues {
        private List<CommitDomain> mCommitsDomain;
        private String mRepo;

        public RequestValues(@NonNull List<CommitDomain> commitsDomain, @NonNull String owner, @NonNull String repo){
            mCommitsDomain = commitsDomain;
            mRepo = repo;
        }

        @NonNull
        List<CommitDomain> getCommitsDomain() {
            return mCommitsDomain;
        }

        @NonNull
        String getRepo() {
            return mRepo;
        }
    }

    public static class ResponseValues implements BaseUseCase.ResponseValues {

    }
}
