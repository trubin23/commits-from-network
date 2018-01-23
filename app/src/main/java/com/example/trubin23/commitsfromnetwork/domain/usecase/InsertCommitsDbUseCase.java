package com.example.trubin23.commitsfromnetwork.domain.usecase;

import android.support.annotation.NonNull;

import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.commitsfromnetwork.data.Commit;
import com.example.trubin23.commitsfromnetwork.data.source.database.CommitDao;
import com.example.trubin23.commitsfromnetwork.data.source.database.CommitDaoImpl;
import com.example.trubin23.commitsfromnetwork.data.source.database.DatabaseHelper;
import com.example.trubin23.commitsfromnetwork.data.source.database.OwnerDao;
import com.example.trubin23.commitsfromnetwork.data.source.database.OwnerDaoImpl;
import com.example.trubin23.commitsfromnetwork.data.source.database.RepoDao;
import com.example.trubin23.commitsfromnetwork.data.source.database.RepoDaoImpl;

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

        String owner = request.getOwner();
        OwnerDao ownerDao = new OwnerDaoImpl(databaseHelper);
        ownerDao.insertOwner(owner);
        Long ownerId = ownerDao.getOwnerId(owner);
        if (ownerId == null){
            getUseCaseCallback().onError();
            return;
        }

        String repo = request.getRepo();
        RepoDao repoDao = new RepoDaoImpl(databaseHelper);
        repoDao.insertRepo(repo, ownerId);
        Long repoId = repoDao.getRepo(repo, ownerId);
        if (repoId == null){
            getUseCaseCallback().onError();
            return;
        }

        List<Commit> commits = request.getCommits();
        CommitDao commitDao = new CommitDaoImpl(databaseHelper);
        commitDao.insertCommits(commits, repoId);
        getUseCaseCallback().onSuccess(new ResponseValues());
    }

    public static class RequestValues implements BaseUseCase.RequestValues {
        private List<Commit> mCommitsDomain;
        private String mOwner;
        private String mRepo;

        public RequestValues(@NonNull List<Commit> commitsDomain,
                             @NonNull String owner, @NonNull String repo){
            mCommitsDomain = commitsDomain;
            mOwner = owner;
            mRepo = repo;
        }

        @NonNull
        List<Commit> getCommits() {
            return mCommitsDomain;
        }

        @NonNull
        public String getOwner() {
            return mOwner;
        }

        @NonNull
        String getRepo() {
            return mRepo;
        }
    }

    private static class ResponseValues implements BaseUseCase.ResponseValues {

    }
}
