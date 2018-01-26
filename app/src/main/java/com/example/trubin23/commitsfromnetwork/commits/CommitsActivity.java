package com.example.trubin23.commitsfromnetwork.commits;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trubin23.commitsfromnetwork.Injection;
import com.example.trubin23.commitsfromnetwork.R;
import com.example.trubin23.commitsfromnetwork.commits.commitslist.CommitItemActionHandler;
import com.example.trubin23.commitsfromnetwork.commits.commitslist.CommitsAdapter;
import com.example.trubin23.commitsfromnetwork.commits.commitslist.LoadCommitsActionHandler;
import com.example.trubin23.commitsfromnetwork.commits.commitslist.SimpleScrollListener;
import com.example.trubin23.commitsfromnetwork.commitdetail.CommitDetailActivity;
import com.example.trubin23.commitsfromnetwork.BaseActivity;
import com.example.trubin23.commitsfromnetwork.data.Commit;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.trubin23.commitsfromnetwork.data.Commit.CLASS_COMMIT;

public class CommitsActivity extends BaseActivity implements
        CommitsContract.View,
        LoadCommitsActionHandler,
        CommitItemActionHandler {

    private static final String TAG = CommitsActivity.class.getSimpleName();

    private static final String OWNER_NAME = "owner-name";
    private static final String REPO_NAME = "repo-name";
    private static final String LAST_PAGE_LOADED = "last-page-loaded";
    private static final String COMMIT_ARRAY_LIST = "commit-array-list";

    private CommitsPresenter mPresenter;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private CommitsAdapter mCommitsAdapter;

    private String mOwnerName;
    private String mRepoName;
    private AlertDialog mRepoNameDialog;

    private boolean mLastPageLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);
        ButterKnife.bind(this);

        mLastPageLoaded = false;

        LinearLayoutManager llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mCommitsAdapter = new CommitsAdapter(this);
        mRecyclerView.setAdapter(mCommitsAdapter);

        mRecyclerView.addOnScrollListener(new SimpleScrollListener(this));

        mSwipeRefreshLayout.setEnabled(false);

        createPresenter();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mOwnerName = savedInstanceState.getString(OWNER_NAME);
        mRepoName = savedInstanceState.getString(REPO_NAME);
        mLastPageLoaded = savedInstanceState.getBoolean(LAST_PAGE_LOADED);

        ArrayList<Commit> commits = savedInstanceState.getParcelableArrayList(COMMIT_ARRAY_LIST);
        mCommitsAdapter.setCommits(commits);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(OWNER_NAME, mOwnerName);
        outState.putString(REPO_NAME, mRepoName);
        outState.putBoolean(LAST_PAGE_LOADED, mLastPageLoaded);

        ArrayList<Commit> commits = new ArrayList<>(mCommitsAdapter.getItems());
        outState.putParcelableArrayList(COMMIT_ARRAY_LIST, commits);
    }

    private void createPresenter() {
        mPresenter = new CommitsPresenter(Injection.provideCommitsRepository(this));
        bindPresenterToView(mPresenter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.loadRepoData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissRepoDialog();
    }

    @OnClick(R.id.fab_show_commit)
    public void onClickLoadCommits(View v) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View repoNameDialog = layoutInflater.inflate(R.layout.repo_name_dialog, null);

        final EditText ownerName = repoNameDialog.findViewById(R.id.et_owner);
        final EditText repoName = repoNameDialog.findViewById(R.id.et_repo);

        if (mOwnerName != null) {
            ownerName.setText(mOwnerName);
        }
        if (mRepoName != null) {
            repoName.setText(mRepoName);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(repoNameDialog);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            mOwnerName = ownerName.getText().toString();
            mRepoName = repoName.getText().toString();

            mLastPageLoaded = false;
            mCommitsAdapter.setCommits(null);

            mPresenter.saveRepoData(mOwnerName, mRepoName);

            loadCommits();
            dismissRepoDialog();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dismissRepoDialog());
        builder.setOnCancelListener(dialog -> dismissRepoDialog());

        mRepoNameDialog = builder.create();
        mRepoNameDialog.show();
    }

    private void dismissRepoDialog() {
        if (mRepoNameDialog != null) {
            mRepoNameDialog.dismiss();
            mRepoNameDialog = null;
        }
    }

    @Override
    public void setOwnerName(@NonNull String owner) {
        mOwnerName = owner;
    }

    @Override
    public void setRepoName(@NonNull String repo) {
        mRepoName = repo;
    }

    @Override
    public void setCommits(@NonNull List<Commit> commits) {
        Log.d(TAG, "Commits count: " + commits.size());

        mCommitsAdapter.addCommits(commits);
    }

    @Override
    public void loadFinished() {
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setEnabled(false);

        LinearLayoutManager layoutManager =
                (LinearLayoutManager) mRecyclerView.getLayoutManager();

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
            loadCommits();
        }
    }

    @Override
    public void showToast(@NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void lastPageLoaded() {
        mLastPageLoaded = true;
    }

    @Override
    public void loadCommits() {
        if (!mLastPageLoaded && !mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setEnabled(true);
            mSwipeRefreshLayout.setRefreshing(true);

            Integer pageNext = 1 + (mCommitsAdapter.getItemCount() + CommitsAdapter.PAGE_SIZE - 1)
                    / CommitsAdapter.PAGE_SIZE;
            mPresenter.loadCommits(mOwnerName, mRepoName, pageNext, CommitsAdapter.PAGE_SIZE);
        }
    }

    @Override
    public void onClickItemRecyclerView(Commit commit) {
        Intent intent = new Intent(CommitsActivity.this, CommitDetailActivity.class);
        intent.putExtra(CLASS_COMMIT, commit);
        startActivity(intent);
    }
}
