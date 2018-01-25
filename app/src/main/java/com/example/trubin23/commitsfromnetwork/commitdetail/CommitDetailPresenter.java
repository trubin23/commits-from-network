package com.example.trubin23.commitsfromnetwork.commitdetail;

import android.support.annotation.NonNull;

import com.example.trubin23.commitsfromnetwork.BasePresenter;
import com.example.trubin23.commitsfromnetwork.data.source.CommitsRepository;

/**
 * Created by Andrey on 19.01.2018.
 */

public class CommitDetailPresenter extends BasePresenter<CommitDetailContract.View> implements
        CommitDetailContract.Presenter {

    public CommitDetailPresenter(@NonNull CommitsRepository commitsRepository) {
        super(commitsRepository);
    }
}