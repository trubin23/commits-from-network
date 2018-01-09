package com.example.trubin23.commitsfromnetwork.presentation.commits.show;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.trubin23.commitsfromnetwork.R;
import com.example.trubin23.commitsfromnetwork.presentation.commits.model.CommitView;
import com.example.trubin23.commitsfromnetwork.presentation.common.BaseActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommitsActivity extends BaseActivity implements CommitsContract.View {

    private static final String TAG = CommitsActivity.class.getSimpleName();

    private CommitsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);
        ButterKnife.bind(this);

        createPresenter();
    }

    private void createPresenter() {
        mPresenter = new CommitsPresenter(mUseCaseHandler);
        bindPresenterToView(mPresenter);
    }

    @OnClick(R.id.btn_load_commits)
    public void onClickLoadCommits(View v) {
//        String repoName = mRepoName.getText().toString();
//        mPresenter.loadCommits(repoName);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialogRepoName = layoutInflater.inflate(R.layout.dialog_repo_name, null);

        final EditText repoName = (EditText) dialogRepoName.findViewById(R.id.et_repo_name);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogRepoName);
        builder.setTitle(R.string.label_repo_name);

        builder.setPositiveButton(android.R.string.ok, null);
        builder.setNegativeButton(android.R.string.cancel, null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void setCommitsString(@NonNull List<CommitView> commitsView) {
        Log.d(TAG, "Commits count: " + commitsView.size());
//        for(CommitView commit : commitsView){
//            Log.d(TAG, commit.getSha());
//            Log.d(TAG, commit.getMessage());
//            Log.d(TAG, commit.getDate());
//            Log.d(TAG, "----------------------------------------");
//        }
    }
}
