package com.example.trubin23.commitsfromnetwork.presentation.commits.show;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trubin23.commitsfromnetwork.R;
import com.example.trubin23.commitsfromnetwork.presentation.commits.model.CommitView;
import com.example.trubin23.commitsfromnetwork.presentation.commits.show.commitslist.CommitsAdapter;
import com.example.trubin23.commitsfromnetwork.presentation.common.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommitsActivity extends BaseActivity implements CommitsContract.View {

    private static final String TAG = CommitsActivity.class.getSimpleName();

    private CommitsPresenter mPresenter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private CommitsAdapter mCommitsAdapter;

    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);
        ButterKnife.bind(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mCommitsAdapter = new CommitsAdapter(null);
        mRecyclerView.setAdapter(mCommitsAdapter);

        createPresenter();
    }

    private void createPresenter() {
        mPresenter = new CommitsPresenter(mUseCaseHandler);
        bindPresenterToView(mPresenter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissRepoDialog();
    }

    @OnClick(R.id.btn_load_commits)
    public void onClickLoadCommits(View v) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialogRepoName = layoutInflater.inflate(R.layout.dialog_repo_name, null);

        final EditText repoName = dialogRepoName.findViewById(R.id.et_repo_name);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogRepoName);
        builder.setTitle(R.string.label_repo_name);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            mPresenter.loadCommits(repoName.getText().toString());
            dismissRepoDialog();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dismissRepoDialog());
        builder.setOnCancelListener(dialog -> dismissRepoDialog());

        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    private void dismissRepoDialog(){
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }

    @Override
    public void setCommits(@NonNull List<CommitView> commitsView) {
        Log.d(TAG, "Commits count: " + commitsView.size());

        runOnUiThread(() -> mCommitsAdapter.setCommits(commitsView));
    }

    @Override
    public void showToast(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
