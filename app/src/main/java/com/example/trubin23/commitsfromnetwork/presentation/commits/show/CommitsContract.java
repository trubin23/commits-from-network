package com.example.trubin23.commitsfromnetwork.presentation.commits.show;

import android.support.annotation.NonNull;
import com.example.trubin23.commitsfromnetwork.presentation.common.BaseView;
import com.example.trubin23.commitsfromnetwork.presentation.commits.model.CommitView;

import java.util.List;

/**
 * Created by Andrey on 31.12.2017.
 */

class CommitsContract {

    interface View extends BaseView {
        void setCommitsString(@NonNull List<CommitView> commitsView);
    }

    interface Presenter {
        void loadCommits(@NonNull String repoName);
    }
}
