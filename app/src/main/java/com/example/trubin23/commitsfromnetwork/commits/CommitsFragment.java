package com.example.trubin23.commitsfromnetwork.commits;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.example.trubin23.commitsfromnetwork.R;
import com.example.trubin23.commitsfromnetwork.commitdetail.CommitDetailActivity;
import com.example.trubin23.commitsfromnetwork.commits.commitslist.CommitItemActionHandler;
import com.example.trubin23.commitsfromnetwork.commits.commitslist.CommitsAdapter;
import com.example.trubin23.commitsfromnetwork.commits.commitslist.LoadCommitsActionHandler;
import com.example.trubin23.commitsfromnetwork.commits.commitslist.SimpleScrollListener;
import com.example.trubin23.commitsfromnetwork.data.Commit;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.trubin23.commitsfromnetwork.data.Commit.CLASS_COMMIT;

/**
 * Created by Andrey on 27.01.2018.
 */

public class CommitsFragment extends Fragment implements
        CommitsContract.View,
        LoadCommitsActionHandler,
        CommitItemActionHandler {

    private static final String TAG = CommitsFragment.class.getSimpleName();

    private static final String OWNER_NAME = "owner-name";
    private static final String REPO_NAME = "repo-name";
    private static final String LAST_PAGE_LOADED = "last-page-loaded";
    private static final String COMMIT_ARRAY_LIST = "commit-array-list";

    private CommitsContract.Presenter mPresenter;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private CommitsAdapter mCommitsAdapter;

    private String mOwnerName;
    private String mRepoName;
    private AlertDialog mRepoNameDialog;

    private boolean mLastPageLoaded;

    @NonNull
    public static CommitsFragment newInstance() {
        return new CommitsFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLastPageLoaded = false;

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mCommitsAdapter = new CommitsAdapter(this);
        mRecyclerView.setAdapter(mCommitsAdapter);

        mRecyclerView.addOnScrollListener(new SimpleScrollListener(this));

        mSwipeRefreshLayout.setEnabled(false);

        if (savedInstanceState != null) {
            mOwnerName = savedInstanceState.getString(OWNER_NAME);
            mRepoName = savedInstanceState.getString(REPO_NAME);
            mLastPageLoaded = savedInstanceState.getBoolean(LAST_PAGE_LOADED);

            ArrayList<Commit> commits = savedInstanceState.getParcelableArrayList(COMMIT_ARRAY_LIST);
            mCommitsAdapter.setCommits(commits);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(OWNER_NAME, mOwnerName);
        outState.putString(REPO_NAME, mRepoName);
        outState.putBoolean(LAST_PAGE_LOADED, mLastPageLoaded);

        ArrayList<Commit> commits = new ArrayList<>(mCommitsAdapter.getItems());
        outState.putParcelableArrayList(COMMIT_ARRAY_LIST, commits);
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.loadRepoData();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissRepoDialog();
    }

    @OnClick(R.id.fab_show_commit)
    public void onClickLoadCommits(View v) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View repoNameDialog = layoutInflater.inflate(R.layout.repo_dialog, null);

        final EditText ownerName = repoNameDialog.findViewById(R.id.et_owner);
        final EditText repoName = repoNameDialog.findViewById(R.id.et_repo);

        if (mOwnerName != null) {
            ownerName.setText(mOwnerName);
        }
        if (mRepoName != null) {
            repoName.setText(mRepoName);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(getContext(), CommitDetailActivity.class);
        intent.putExtra(CLASS_COMMIT, commit);
        startActivity(intent);
    }

    @Override
    public void setPresenter(CommitsContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
