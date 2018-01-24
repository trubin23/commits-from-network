package com.example.trubin23.commitsfromnetwork.data.source;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.trubin23.commitsfromnetwork.data.Commit;
import com.example.trubin23.commitsfromnetwork.data.source.database.CommitDao;
import com.example.trubin23.commitsfromnetwork.data.source.database.CommitDaoImpl;
import com.example.trubin23.commitsfromnetwork.data.source.database.DatabaseHelper;
import com.example.trubin23.commitsfromnetwork.data.source.database.OwnerDao;
import com.example.trubin23.commitsfromnetwork.data.source.database.OwnerDaoImpl;
import com.example.trubin23.commitsfromnetwork.data.source.database.RepoDao;
import com.example.trubin23.commitsfromnetwork.data.source.database.RepoDaoImpl;
import com.example.trubin23.commitsfromnetwork.data.source.preferences.CommitsSharedPreferences;
import com.example.trubin23.commitsfromnetwork.data.source.remote.RetrofitClient;
import com.example.trubin23.commitsfromnetwork.data.source.remote.model.CommitMapper;
import com.example.trubin23.commitsfromnetwork.data.source.remote.model.load.CommitLoad;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andrey on 23.01.2018.
 */

public class CommitsRepository implements CommitsDataSource {

    private static CommitsRepository INSTANCE;

    private CommitsSharedPreferences mCommitsSharedPreferences;

    private OwnerDao mOwnerDao;
    private RepoDao mRepoDao;
    private CommitDao mCommitDao;

    private CommitsRepository(@NonNull CommitsSharedPreferences commitsSharedPreferences,
                              @NonNull DatabaseHelper databaseHelper) {
        mCommitsSharedPreferences = commitsSharedPreferences;

        mOwnerDao = new OwnerDaoImpl(databaseHelper);
        mRepoDao = new RepoDaoImpl(databaseHelper);
        mCommitDao = new CommitDaoImpl(databaseHelper);
    }

    public static CommitsRepository getInstance(@NonNull CommitsSharedPreferences commitsSharedPreferences,
                                                @NonNull DatabaseHelper databaseHelper) {
        if (INSTANCE == null) {
            INSTANCE = new CommitsRepository(commitsSharedPreferences, databaseHelper);
        }
        return INSTANCE;
    }

    @Override
    public void savePreference(@NonNull String key, @NonNull String value) {
        mCommitsSharedPreferences.putString(key, value);
    }

    @Override
    public void getPreference(@NonNull String key, @NonNull GetPreferenceCallback callback) {
        String value = mCommitsSharedPreferences.getString(key);
        callback.onPreferenceGot(value);
    }

    @Override
    public void getCommitsDb(@NonNull String owner, @NonNull String repo,
                             @NonNull LoadCommitsCallback callback) {
        Long ownerId = mOwnerDao.getOwnerId(owner);
        if (ownerId == null) {
            callback.onDataNotAvailable();
            return;
        }

        Long repoId = mRepoDao.getRepo(repo, ownerId);
        if (repoId == null) {
            callback.onDataNotAvailable();
            return;
        }

        Cursor cursor = mCommitDao.getCommits(repoId);
        if (cursor == null) {
            callback.onDataNotAvailable();
            return;
        }

        List<Commit> commits = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String sha = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_SHA));
            String message = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_MESSAGE));
            String date = cursor.getString(cursor.getColumnIndex(CommitDao.COLUMN_COMMIT_DATE));

            Commit commit = new Commit(sha, message, date);
            commits.add(commit);
        }

        callback.onCommitsLoaded(commits);
    }

    @Override
    public void insertCommitsDb(@NonNull List<Commit> commits, @NonNull String owner,
                                @NonNull String repo) {
        mOwnerDao.insertOwner(owner);
        Long ownerId = mOwnerDao.getOwnerId(owner);
        if (ownerId == null) {
            return;
        }

        mRepoDao.insertRepo(repo, ownerId);
        Long repoId = mRepoDao.getRepo(repo, ownerId);
        if (repoId == null) {
            return;
        }

        mCommitDao.insertCommits(commits, repoId);
    }

    @Override
    public void getCommitsNetwork(@NonNull String owner, @NonNull String repo,
                                  @Nullable Integer pageNumber, @Nullable Integer pageSize,
                                  @NonNull LoadCommitsCallback callback) {
        RetrofitClient.getCommits(owner, repo, pageNumber, pageSize, new Callback<List<CommitLoad>>() {
            @Override
            public void onResponse(Call<List<CommitLoad>> call, Response<List<CommitLoad>> response) {
                List<CommitLoad> commitsLoad = response.body();

                if (response.isSuccessful() && commitsLoad != null) {
                    List<Commit> commits = new ArrayList<>();

                    for (CommitLoad commitLoad : commitsLoad) {
                        Commit commit = CommitMapper.toCommit(commitLoad);
                        commits.add(commit);
                    }

                    callback.onCommitsLoaded(commits);
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<List<CommitLoad>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }
}
