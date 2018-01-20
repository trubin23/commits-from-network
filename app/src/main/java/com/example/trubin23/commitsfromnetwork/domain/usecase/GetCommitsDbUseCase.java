package com.example.trubin23.commitsfromnetwork.domain.usecase;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.commitsfromnetwork.domain.model.CommitDomain;
import com.example.trubin23.commitsfromnetwork.storage.database.CommitDao;
import com.example.trubin23.commitsfromnetwork.storage.database.CommitDaoImpl;
import com.example.trubin23.commitsfromnetwork.storage.database.DatabaseHelper;
import com.example.trubin23.commitsfromnetwork.storage.database.OwnerDao;
import com.example.trubin23.commitsfromnetwork.storage.database.OwnerDaoImpl;
import com.example.trubin23.commitsfromnetwork.storage.database.RepoDao;
import com.example.trubin23.commitsfromnetwork.storage.database.RepoDaoImpl;
import com.example.trubin23.commitsfromnetwork.storage.model.RepoStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 08.01.2018.
 */

public class GetCommitsDbUseCase extends BaseUseCase {
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
        Long ownerId = ownerDao.getOwnerId(owner);
        if (ownerId == null){
            getUseCaseCallback().onSuccess(new ResponseValues(null));
            return;
        }

        String repo = request.getRepo();
        RepoDao repoDao = new RepoDaoImpl(databaseHelper);
        RepoStorage repoStorage = repoDao.getRepo(repo, ownerId);
        if (repoStorage == null){
            getUseCaseCallback().onSuccess(new ResponseValues(null));
            return;
        }

        CommitDao commitDao = new CommitDaoImpl(databaseHelper);
        Cursor cursor = commitDao.getCommits(repoStorage);

        List<CommitDomain> commitsDomain = new ArrayList<>();
        if (cursor != null) {
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String sha = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_SHA));
                String message = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_MESSAGE));
                String date = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_DATE));

                CommitDomain commit = new CommitDomain(sha, message, date);
                commitsDomain.add(commit);
            }
        }

        getUseCaseCallback().onSuccess(new ResponseValues(commitsDomain));
    }

    public static class RequestValues implements BaseUseCase.RequestValues {
        private String mOwner;
        private String mRepo;

        public RequestValues(@NonNull String owner, @NonNull String repo){
            mOwner = owner;
            mRepo = repo;
        }

        @NonNull
        String getOwner() {
            return mOwner;
        }

        @NonNull
        String getRepo() {
            return mRepo;
        }
    }

    public static class ResponseValues implements BaseUseCase.ResponseValues {
        List<CommitDomain> mCommitsDomain;

        ResponseValues(@Nullable List<CommitDomain> commitsDomain){
            if (commitsDomain != null) {
                mCommitsDomain = commitsDomain;
            } else {
                mCommitsDomain = new ArrayList<>();
            }
        }

        @NonNull
        public List<CommitDomain> getCommitsDomain(){
            return mCommitsDomain;
        }
    }
}
