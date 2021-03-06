package com.example.trubin23.commitsfromnetwork.commits.commitslist;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by Andrey on 11.01.2018.
 */

public class SimpleScrollListener extends RecyclerView.OnScrollListener  {

    private static final String TAG = SimpleScrollListener.class.getSimpleName();

    private boolean mLoading;

    private LoadCommitsActionHandler mLoadCommitsActionHandler;

    public SimpleScrollListener(@NonNull LoadCommitsActionHandler loadCommitsActionHandler) {
        mLoadCommitsActionHandler = loadCommitsActionHandler;

        mLoading = true;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy <= 0 || !mLoading) {
            return;
        }

        LinearLayoutManager layoutManager =
                (LinearLayoutManager) recyclerView.getLayoutManager();

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
            mLoading = false;
            Log.v(TAG, "Last Item Wow !");

            mLoadCommitsActionHandler.loadCommits();

            mLoading = true;
        }
    }
}
