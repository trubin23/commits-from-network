package com.example.trubin23.commitsfromnetwork.domain.usecase;

import android.database.Cursor;
import android.support.annotation.NonNull;
import com.example.trubin23.commitsfromnetwork.domain.common.BaseUseCase;
import com.example.trubin23.commitsfromnetwork.domain.model.CommitDomain;
import com.example.trubin23.commitsfromnetwork.storage.database.CommitDao;
import com.example.trubin23.commitsfromnetwork.storage.database.CommitDaoImpl;
import com.example.trubin23.commitsfromnetwork.storage.database.DatabaseHelper;
import com.example.trubin23.commitsfromnetwork.storage.model.CommitStorage;

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

        CommitDao commitDao = new CommitDaoImpl(databaseHelper);
        Cursor cursor = commitDao.getCommits();
        if (cursor == null) {
            getUseCaseCallback().onError();
            return;
        }

        List<CommitDomain> commitsDomain = new ArrayList<>();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
            String sha = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_SHA));
            String message = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_MESSAGE));
            String date = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_DATE));

            CommitDomain commit = new CommitDomain(sha, message, date);
            commitsDomain.add(commit);
        }
    }

    public static class RequestValues implements BaseUseCase.RequestValues {
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
