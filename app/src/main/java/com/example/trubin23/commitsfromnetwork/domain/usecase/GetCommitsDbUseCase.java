package com.example.trubin23.commitsfromnetwork.domain.usecase;

import android.support.annotation.NonNull;

import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.commitsfromnetwork.domain.model.CommitDomain;
import com.example.trubin23.commitsfromnetwork.storage.database.CommitDao;
import com.example.trubin23.commitsfromnetwork.storage.database.CommitDaoImpl;
import com.example.trubin23.commitsfromnetwork.storage.database.DatabaseHelper;

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
        String repo = request.getRepo();

        CommitDao commitDao = new CommitDaoImpl(databaseHelper);
//        Cursor cursor = commitDao.getCommits(repo);
//
//        List<CommitDomain> commitsDomain = new ArrayList<>();
//        if (cursor != null) {
//            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
//                String sha = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_SHA));
//                String message = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_MESSAGE));
//                String date = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_DATE));
//
//                CommitDomain commit = new CommitDomain(sha, message, date);
//                commitsDomain.add(commit);
//            }
//        }
//
//        getUseCaseCallback().onSuccess(new ResponseValues(commitsDomain));
    }

    public static class RequestValues implements BaseUseCase.RequestValues {
        private String mRepo;

        public RequestValues(@NonNull String repo){
            mRepo = repo;
        }

        @NonNull
        String getRepo() {
            return mRepo;
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
