package com.example.trubin23.commitsfromnetwork.data.source;

import com.example.trubin23.commitsfromnetwork.data.source.database.CommitDao;
import com.example.trubin23.commitsfromnetwork.data.source.database.CommitDaoImpl;
import com.example.trubin23.commitsfromnetwork.data.source.database.DatabaseHelper;
import com.example.trubin23.commitsfromnetwork.data.source.database.OwnerDao;
import com.example.trubin23.commitsfromnetwork.data.source.database.OwnerDaoImpl;
import com.example.trubin23.commitsfromnetwork.data.source.database.RepoDao;
import com.example.trubin23.commitsfromnetwork.data.source.database.RepoDaoImpl;
import com.example.trubin23.commitsfromnetwork.data.source.preferences.CommitsSharedPreferences;
import com.example.trubin23.commitsfromnetwork.data.source.remote.RetrofitClient;

/**
 * Created by Andrey on 23.01.2018.
 */

public class CommitsRepository implements CommitsDataSource {

    private static CommitsRepository INSTANCE;

    private CommitsSharedPreferences mCommitsSharedPreferences;

    private OwnerDao mOwnerDao;
    private RepoDao mRepoDao;
    private CommitDao mCommitDao;

    private RetrofitClient mRetrofitClient;

    private CommitsRepository(CommitsSharedPreferences commitsSharedPreferences,
                              DatabaseHelper databaseHelper,
                              RetrofitClient retrofitClient) {
        mCommitsSharedPreferences = commitsSharedPreferences;

        mOwnerDao = new OwnerDaoImpl(databaseHelper);
        mRepoDao = new RepoDaoImpl(databaseHelper);
        mCommitDao = new CommitDaoImpl(databaseHelper);

        mRetrofitClient = retrofitClient;
    }

    public static CommitsRepository getInstance(){
        if (INSTANCE == null){
            INSTANCE = new CommitsRepository(null, null, null);
        }
        return INSTANCE;
    }
}
