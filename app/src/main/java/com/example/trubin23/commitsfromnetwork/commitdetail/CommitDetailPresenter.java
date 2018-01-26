package com.example.trubin23.commitsfromnetwork.commitdetail;

import android.support.annotation.NonNull;

import com.example.trubin23.commitsfromnetwork.data.source.CommitsRepository;

/**
 * Created by Andrey on 19.01.2018.
 */

public class CommitDetailPresenter implements CommitDetailContract.Presenter {

    private CommitsRepository mCommitsRepository;

    public CommitDetailPresenter(@NonNull CommitsRepository commitsRepository) {
        mCommitsRepository = commitsRepository;
    }

    @Override
    public void start() {

    }
}
