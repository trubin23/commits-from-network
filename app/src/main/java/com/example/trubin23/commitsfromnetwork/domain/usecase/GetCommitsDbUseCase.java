package com.example.trubin23.commitsfromnetwork.domain.usecase;

import android.database.Cursor;
import android.support.annotation.NonNull;
import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.commitsfromnetwork.domain.model.CommitDomain;
import com.example.trubin23.commitsfromnetwork.storage.database.CommitDao;
import com.example.trubin23.commitsfromnetwork.storage.database.CommitDaoImpl;
import com.example.trubin23.commitsfromnetwork.storage.database.DatabaseHelper;

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
        String repoName = request.getRepoName();

        CommitDao commitDao = new CommitDaoImpl(databaseHelper);
        Cursor cursor = commitDao.getCommits(repoName);

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
        private String mRepoName;

        public RequestValues(@NonNull String repoName){
            mRepoName = repoName;
        }

        @NonNull
        public String getRepoName() {
            return mRepoName;
        }
    }

    public static class ResponseValues implements BaseUseCase.ResponseValues {
        List<CommitDomain> mCommitsDomain;

        ResponseValues(@NonNull List<CommitDomain> commitsDomain){
            mCommitsDomain = commitsDomain;
        }

        public List<CommitDomain> getCommitsDomain(){
            return mCommitsDomain;
        }
    }
}
