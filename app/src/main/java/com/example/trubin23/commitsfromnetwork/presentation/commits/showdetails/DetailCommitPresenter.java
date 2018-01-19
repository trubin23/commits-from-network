package com.example.trubin23.commitsfromnetwork.presentation.commits.showdetails;

import android.support.annotation.NonNull;

import com.example.trubin23.commitsfromnetwork.domain.common.UseCaseHandler;
import com.example.trubin23.commitsfromnetwork.presentation.common.BasePresenter;

/**
 * Created by Andrey on 19.01.2018.
 */

public class DetailCommitPresenter extends BasePresenter<DetailCommitContract.View> implements
        DetailCommitContract.Presenter {

    public DetailCommitPresenter(@NonNull UseCaseHandler useCaseHandler) {
        super(useCaseHandler);
    }
}
