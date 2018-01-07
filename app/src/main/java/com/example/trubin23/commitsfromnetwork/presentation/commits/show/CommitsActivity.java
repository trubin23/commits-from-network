package com.example.trubin23.commitsfromnetwork.presentation.commits.show;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.trubin23.commitsfromnetwork.R;
import com.example.trubin23.commitsfromnetwork.domain.usecase.GetCommitsUseCase;
import com.example.trubin23.commitsfromnetwork.presentation.common.BaseActivity;
import com.example.trubin23.commitsfromnetwork.presentation.commits.model.CommitView;

import java.util.List;

public class CommitsActivity extends BaseActivity implements CommitsContract.View {

    private static final String TAG = CommitsActivity.class.getSimpleName();

    @BindView(R.id.et_repo_name)
    EditText mRepoName;

    @BindView(R.id.btn_load_commits)
    Button mLoadCommits;

    private CommitsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);
        ButterKnife.bind(this);

        createPresenter();
    }

    private void createPresenter() {
        GetCommitsUseCase getCommitsUseCase = new GetCommitsUseCase();
        mPresenter = new CommitsPresenter(mUseCaseHandler, getCommitsUseCase);
        bindPresenterToView(mPresenter);
    }

    @OnClick(R.id.btn_load_commits)
    public void onClickLoadCommits(View v) {
        String repoName = mRepoName.getText().toString();
        mPresenter.loadCommits(repoName);
    }

    @Override
    public void setCommitsString(@NonNull List<CommitView> commitsView) {
        for(CommitView commit : commitsView){
            Log.d(TAG, commit.getSha());
            Log.d(TAG, commit.getMessage());
            Log.d(TAG, commit.getDate());
            Log.d(TAG, "----------------------------------------");
        }
    }
}
