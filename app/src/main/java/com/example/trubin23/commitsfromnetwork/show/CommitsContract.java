package com.example.trubin23.commitsfromnetwork.show;

import android.support.annotation.NonNull;

import com.example.trubin23.commitsfromnetwork.BaseView;
import com.example.trubin23.commitsfromnetwork.data.Commit;

import java.util.List;

/**
 * Created by Andrey on 31.12.2017.
 */

class CommitsContract {

    interface View extends BaseView {
        void setCommits(@NonNull List<Commit> commitsView);
        void loadFinished();
        void showToast(@NonNull String message);
        void lastPageLoaded();
        void setOwnerName(@NonNull String ownerName);
        void setRepoName(@NonNull String repoName);
    }

    interface Presenter {
        void loadCommits(@NonNull String owner, @NonNull String repo,
                         @NonNull Integer pageNumber, @NonNull Integer pageSize);
        void saveRepoData(@NonNull String owner, @NonNull String repo);
        void loadRepoData();
    }
}
