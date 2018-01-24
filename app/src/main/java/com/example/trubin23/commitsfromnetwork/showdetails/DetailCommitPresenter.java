package com.example.trubin23.commitsfromnetwork.showdetails;

import android.support.annotation.NonNull;

import com.example.trubin23.commitsfromnetwork.BasePresenter;
import com.example.trubin23.commitsfromnetwork.data.source.CommitsRepository;
import com.example.trubin23.commitsfromnetwork.domain.common.UseCaseHandler;

/**
 * Created by Andrey on 19.01.2018.
 */

public class DetailCommitPresenter extends BasePresenter<DetailCommitContract.View> implements
        DetailCommitContract.Presenter {

    public DetailCommitPresenter(@NonNull CommitsRepository commitsRepository,
                                 @NonNull UseCaseHandler useCaseHandler) {
        super(commitsRepository, useCaseHandler);
    }
}
