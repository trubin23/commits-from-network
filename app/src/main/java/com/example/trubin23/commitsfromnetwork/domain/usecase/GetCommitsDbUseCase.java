package com.example.trubin23.commitsfromnetwork.domain.usecase;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.commitsfromnetwork.data.Commit;
import com.example.trubin23.commitsfromnetwork.data.source.database.CommitDao;
import com.example.trubin23.commitsfromnetwork.data.source.database.CommitDaoImpl;
import com.example.trubin23.commitsfromnetwork.data.source.database.DatabaseHelper;
import com.example.trubin23.commitsfromnetwork.data.source.database.OwnerDao;
import com.example.trubin23.commitsfromnetwork.data.source.database.OwnerDaoImpl;
import com.example.trubin23.commitsfromnetwork.data.source.database.RepoDao;
import com.example.trubin23.commitsfromnetwork.data.source.database.RepoDaoImpl;

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
        Long repoId = repoDao.getRepo(repo, ownerId);
        if (repoId == null){
            getUseCaseCallback().onSuccess(new ResponseValues(null));
            return;
        }

        CommitDao commitDao = new CommitDaoImpl(databaseHelper);
        Cursor cursor = commitDao.getCommits(repoId);

        List<Commit> commits = new ArrayList<>();
        if (cursor != null) {
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String sha = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_SHA));
                String message = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_MESSAGE));
                String date = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_DATE));

                Commit commit = new Commit(sha, message, date);
                commits.add(commit);
            }
        }

        getUseCaseCallback().onSuccess(new ResponseValues(commits));
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
        List<Commit> mCommitsDomain;

        ResponseValues(@Nullable List<Commit> commitsDomain){
            if (commitsDomain != null) {
                mCommitsDomain = commitsDomain;
            } else {
                mCommitsDomain = new ArrayList<>();
            }
        }

        @NonNull
        public List<Commit> getCommitsDomain(){
            return mCommitsDomain;
        }
    }
}
